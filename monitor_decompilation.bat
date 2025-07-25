@echo off
REM =============================================
REM Monitor Decompilation Progress
REM =============================================

call conda activate base

set OUTPUT_PATH=%~dp0REVERSE\CL831_DECOMPILED

:monitor_loop
cls
echo =============================================
echo Decompilation Progress Monitor
echo =============================================
echo Time: %time%
echo.

if exist "%OUTPUT_PATH%\standard" (
    echo Standard Mode Output:
    for /f %%i in ('dir "%OUTPUT_PATH%\standard\*.java" /s /b 2^>nul ^| find /c /v ""') do echo   Java files: %%i
    for /f %%i in ('dir "%OUTPUT_PATH%\standard" /s /a:-d 2^>nul ^| find /c /v ""') do echo   Total files: %%i
    echo.
)

if exist "%OUTPUT_PATH%\fallback" (
    echo Fallback Mode Output:
    for /f %%i in ('dir "%OUTPUT_PATH%\fallback\*.java" /s /b 2^>nul ^| find /c /v ""') do echo   Java files: %%i
    for /f %%i in ('dir "%OUTPUT_PATH%\fallback" /s /a:-d 2^>nul ^| find /c /v ""') do echo   Total files: %%i
    echo.
)

echo Press Ctrl+C to stop monitoring
echo Refreshing in 10 seconds...
timeout /t 10 /nobreak >nul
goto monitor_loop
