# Copyright 2026 Steema Software SL
# SPDX-License-Identifier: Apache-2.0
[CmdletBinding()]
param(
    [string]$LibraryDirectory,
    [switch]$IncludeSources
)

$ErrorActionPreference = 'Stop'
if ([string]::IsNullOrWhiteSpace($LibraryDirectory)) {
    $LibraryDirectory = Join-Path $PSScriptRoot 'libs'
}
[Net.ServicePointManager]::SecurityProtocol = [Net.ServicePointManager]::SecurityProtocol -bor [Net.SecurityProtocolType]::Tls12
$manifest = Get-Content -LiteralPath (Join-Path $PSScriptRoot 'dependencies.json') -Raw | ConvertFrom-Json

function Get-Sha256([string]$Path) {
    $stream = [IO.File]::OpenRead($Path)
    $algorithm = [Security.Cryptography.SHA256]::Create()
    try {
        return [BitConverter]::ToString($algorithm.ComputeHash($stream)).Replace('-', '').ToLowerInvariant()
    } finally {
        $stream.Dispose()
        $algorithm.Dispose()
    }
}

function Get-VerifiedFile([string]$Url, [string]$Expected, [string]$Directory) {
    $uri = [Uri]$Url
    if ($uri.Scheme -ne 'https' -or $uri.Host -ne 'archive.eclipse.org') {
        throw 'Only the pinned official HTTPS archive is accepted.'
    }
    $name = [IO.Path]::GetFileName($uri.AbsolutePath)
    if (-not $name.EndsWith('.jar') -or $name -ne [IO.Path]::GetFileName($name)) {
        throw 'Invalid dependency filename.'
    }
    [IO.Directory]::CreateDirectory($Directory) | Out-Null
    $target = Join-Path $Directory $name
    if (Test-Path -LiteralPath $target) {
        if ((Get-Sha256 $target) -ne $Expected) {
            throw "Unexpected existing dependency; preserve/review it before retrying: $target"
        }
        Write-Output "Verified cached dependency: $name"
        return
    }
    $temporary = Join-Path $Directory ($name + '.' + [Guid]::NewGuid().ToString('N') + '.part')
    try {
        Invoke-WebRequest -Uri $Url -UseBasicParsing -TimeoutSec 120 -OutFile $temporary
        if ((Get-Sha256 $temporary) -ne $Expected) {
            throw "Downloaded dependency checksum mismatch: $name"
        }
        Move-Item -LiteralPath $temporary -Destination $target -ErrorAction Stop
        Write-Output "Downloaded and verified: $name"
    } finally {
        if (Test-Path -LiteralPath $temporary) { Remove-Item -LiteralPath $temporary }
    }
}

foreach ($dependency in $manifest.dependencies) {
    Get-VerifiedFile $dependency.url $dependency.sha256 $LibraryDirectory
    if ($IncludeSources) {
        Get-VerifiedFile $dependency.source_url $dependency.source_sha256 (Join-Path $PSScriptRoot 'build\dependency-sources')
    }
}
