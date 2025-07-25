@echo off
echo ===========================================
echo CL831 SDK .dex Decompiler Script
echo ===========================================
echo.

set SDK_PATH=%~dp0REVERSE\CL831_SDK
set OUTPUT_PATH=%~dp0REVERSE\CL831_DECOMPILED

echo Source: %SDK_PATH%
echo Output: %OUTPUT_PATH%
echo.

if not exist "%OUTPUT_PATH%" mkdir "%OUTPUT_PATH%"

echo Decompiling .dex files with jadx...
"%~dp0tools\bin\jadx.bat" -d "%OUTPUT_PATH%" "%SDK_PATH%\classes.dex" "%SDK_PATH%\classes2.dex" "%SDK_PATH%\classes3.dex" "%SDK_PATH%\classes4.dex" "%SDK_PATH%\classes5.dex"

echo.
echo ===========================================
echo Decompilation completed!
echo Check results in: %OUTPUT_PATH%
echo ===========================================

pause
