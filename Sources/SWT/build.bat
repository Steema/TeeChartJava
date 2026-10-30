@echo off
setlocal
pushd "%~dp0"
set "JAVAC=javac.exe"
set "JAR=jar.exe"
if defined javahome (
    set "JAVAC=%javahome%\javac.exe"
    set "JAR=%javahome%\jar.exe"
)
if defined JAVA_HOME (
    set "JAVAC=%JAVA_HOME%\bin\javac.exe"
    set "JAR=%JAVA_HOME%\bin\jar.exe"
)

rem The pinned external dependencies are ignored, not distributed with this repo.
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "GetDependencies.ps1"
if errorlevel 1 goto failed

rem Only owned build/staging output is replaced; source roots are never cleaned.
if exist "build\classes" rmdir /s /q "build\classes"
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "..\tools\StageSources.ps1" -Variant SWT -OutputDirectory "%CD%\build\staged-src" -ResourceOutputDirectory "%CD%\build\classes" -Replace
if errorlevel 1 goto failed
"%JAVAC%" %* -classpath "libs\*" -d "build\classes" @build\staged-src\javac.args
if errorlevel 1 goto failed
"%JAR%" cfm "build\TeeChart.SWT.jar" "build\classes\META-INF\MANIFEST.MF" -C "build\classes" .
if errorlevel 1 goto failed
echo Created %CD%\build\TeeChart.SWT.jar
popd
exit /b 0

:failed
popd
exit /b 1
