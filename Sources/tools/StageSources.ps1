[CmdletBinding()]
param(
    [Parameter(Mandatory = $true)]
    [ValidateSet('Swing', 'SWT', 'Android')]
    [string]$Variant,
    [Parameter(Mandatory = $true)]
    [string]$OutputDirectory,
    [string]$ResourceOutputDirectory,
    [switch]$Replace
)

# Windows PowerShell 5.1 compatible. No SDK or Python dependency.
$ErrorActionPreference = 'Stop'
$base = Split-Path -Parent $PSScriptRoot
$layout = ConvertFrom-StringData ([IO.File]::ReadAllText((Join-Path $base 'source-layout.properties')))
$roots = @()
$files = @{} # PowerShell string keys are case-insensitive, including on Windows.
$output = [IO.Path]::GetFullPath($OutputDirectory)
$comparison = [StringComparison]::OrdinalIgnoreCase
$basePrefix = [IO.Path]::GetFullPath($base).TrimEnd('\') + '\'
$rootList = $layout["$Variant.source.roots"]
if (!$rootList) { throw "Missing source roots for $Variant" }

foreach ($relativeRoot in $rootList.Split(';')) {
    $relativeRoot = $relativeRoot.Replace('\', '/')
    if (!$relativeRoot -or [IO.Path]::IsPathRooted($relativeRoot) -or
        $relativeRoot.Split('/') -contains 'Archive' -or $relativeRoot.Split('/') -contains '..') {
        throw "Invalid source root: $relativeRoot"
    }
    $root = [IO.Path]::GetFullPath((Join-Path $base $relativeRoot)).TrimEnd('\')
    if (!$root.StartsWith($basePrefix, $comparison)) { throw "Source root escapes or contains the repository: $relativeRoot" }
    if (!(Test-Path -LiteralPath $root -PathType Container)) { throw "Missing source root: $root" }
    $roots += $root
    foreach ($source in Get-ChildItem -LiteralPath $root -Recurse -File) {
        $relative = $source.FullName.Substring($root.Length + 1).Replace('\', '/')
        if ($source.Extension -ieq '.class') { continue }
        if ($source.Extension -cne '.java' -and !$relative.StartsWith('com/') -and !$relative.StartsWith('META-INF/')) { continue }
        if ($files.ContainsKey($relative)) { throw "Duplicate source/resource: $relative" }
        $files.Add($relative, $source.FullName)
    }
}

function Assert-OutputPath([string]$path) {
    $prefix = $path.TrimEnd('\') + '\'
    foreach ($root in $roots) {
        if ($prefix.StartsWith($root + '\', $comparison) -or
            ($root + '\').StartsWith($prefix, $comparison)) {
            throw "Output overlaps a source root: $path"
        }
    }
}

Assert-OutputPath $output
$marker = Join-Path $output '.teechart-stage'
if ((Test-Path -LiteralPath $output) -and @(Get-ChildItem -LiteralPath $output -Force).Count) {
    if (!$Replace -or !(Test-Path -LiteralPath $marker) -or
        ([IO.File]::ReadAllText($marker) -ne $Variant)) {
        throw "Output is not an owned $Variant staging directory: $output"
    }
    Remove-Item -LiteralPath $output -Recurse -Force
}
if ($ResourceOutputDirectory) {
    $resourceOutput = [IO.Path]::GetFullPath($ResourceOutputDirectory)
    Assert-OutputPath $resourceOutput
    if (($resourceOutput.TrimEnd('\') + '\').StartsWith($output.TrimEnd('\') + '\', $comparison) -or
        ($output.TrimEnd('\') + '\').StartsWith($resourceOutput.TrimEnd('\') + '\', $comparison)) {
        throw 'Resource output must be separate from the staging directory.'
    }
    if ((Test-Path -LiteralPath $resourceOutput) -and @(Get-ChildItem -LiteralPath $resourceOutput -Force).Count) {
        throw "Resource output must be empty: $resourceOutput"
    }
}

[IO.Directory]::CreateDirectory($output) | Out-Null
$javaFiles = @()
foreach ($relative in @($files.Keys | Sort-Object)) {
    $target = Join-Path $output $relative
    [IO.Directory]::CreateDirectory((Split-Path -Parent $target)) | Out-Null
    [IO.File]::Copy($files[$relative], $target)
    if ($relative.EndsWith('.java')) {
        $javaFiles += '"' + $target.Replace('\', '/') + '"'
    } elseif ($ResourceOutputDirectory) {
        $resourceTarget = Join-Path $resourceOutput $relative
        [IO.Directory]::CreateDirectory((Split-Path -Parent $resourceTarget)) | Out-Null
        [IO.File]::Copy($files[$relative], $resourceTarget)
    }
}
$utf8 = New-Object System.Text.UTF8Encoding($false)
[IO.File]::WriteAllLines((Join-Path $output 'javac.args'), [string[]]$javaFiles, $utf8)
[IO.File]::WriteAllText($marker, $Variant, $utf8)
Write-Output "Staged $Variant`: $($javaFiles.Count) Java files and $($files.Count - $javaFiles.Count) resources."
