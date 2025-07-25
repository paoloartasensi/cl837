@echo off
REM =============================================
REM CL831 SDK .dex Decompiler Script (Enhanced)
REM Handles errors and provides multiple output formats
REM =============================================

echo =============================================
echo CL831 SDK .dex Decompiler Script (Enhanced)
echo =============================================
echo.

REM Ensure we're in conda base environment
call conda activate base

set SDK_PATH=%~dp0REVERSE\CL831_SDK
set OUTPUT_PATH=%~dp0REVERSE\CL831_DECOMPILED
set JADX_PATH=%~dp0tools\bin\jadx.bat

echo Source: %SDK_PATH%
echo Output: %OUTPUT_PATH%
echo Jadx: %JADX_PATH%
echo.

if not exist "%OUTPUT_PATH%" mkdir "%OUTPUT_PATH%"

REM Check if .dex files exist
if not exist "%SDK_PATH%\classes.dex" (
    echo ERROR: No .dex files found in %SDK_PATH%
    echo Please ensure the CL831_SDK contains .dex files
    pause
    exit /b 1
)

echo Found .dex files:
dir "%SDK_PATH%\*.dex" /b
echo.

echo [1/3] Decompiling with standard options...
"%JADX_PATH%" ^
    -d "%OUTPUT_PATH%\standard" ^
    --threads-count 4 ^
    --decompilation-mode auto ^
    --show-bad-code ^
    --comments-level info ^
    "%SDK_PATH%\classes.dex" "%SDK_PATH%\classes2.dex" "%SDK_PATH%\classes3.dex" "%SDK_PATH%\classes4.dex" "%SDK_PATH%\classes5.dex"

echo.
echo [2/3] Decompiling with fallback mode (for error cases)...
"%JADX_PATH%" ^
    -d "%OUTPUT_PATH%\fallback" ^
    --threads-count 4 ^
    --decompilation-mode fallback ^
    --show-bad-code ^
    --no-debug-info ^
    --comments-level error ^
    "%SDK_PATH%\classes.dex" "%SDK_PATH%\classes2.dex" "%SDK_PATH%\classes3.dex" "%SDK_PATH%\classes4.dex" "%SDK_PATH%\classes5.dex"

echo.
echo [3/3] Extracting resources and manifests...
"%JADX_PATH%" ^
    -d "%OUTPUT_PATH%\resources" ^
    --no-src ^
    --comments-level none ^
    "%SDK_PATH%\classes.dex" "%SDK_PATH%\classes2.dex" "%SDK_PATH%\classes3.dex" "%SDK_PATH%\classes4.dex" "%SDK_PATH%\classes5.dex"

echo.
echo =============================================
echo Decompilation Summary
echo =============================================

REM Count decompiled files
for /f %%i in ('dir "%OUTPUT_PATH%\standard\*.java" /s /b 2^>nul ^| find /c /v ""') do set JAVA_COUNT=%%i
for /f %%i in ('dir "%OUTPUT_PATH%\fallback\*.java" /s /b 2^>nul ^| find /c /v ""') do set FALLBACK_COUNT=%%i

echo Standard mode: %JAVA_COUNT% Java files
echo Fallback mode: %FALLBACK_COUNT% Java files
echo.

REM Look for interesting classes
echo Searching for Chileaf/BLE related classes...
if exist "%OUTPUT_PATH%\standard" (
    findstr /s /i /m "chileaf\|bluetooth\|ble\|characteristic\|service" "%OUTPUT_PATH%\standard\*.java" 2>nul | head -10
)
echo.

echo Check results in:
echo   %OUTPUT_PATH%\standard    - Main decompilation
echo   %OUTPUT_PATH%\fallback    - Error recovery mode  
echo   %OUTPUT_PATH%\resources   - Resources only
echo.

REM Open output directory
set /p OPEN_DIR="Open output directory? (y/n): "
if /i "%OPEN_DIR%"=="y" start explorer "%OUTPUT_PATH%"

echo =============================================
echo Note: Errors are normal in .dex decompilation
echo Check both standard and fallback outputs
echo =============================================

pause
