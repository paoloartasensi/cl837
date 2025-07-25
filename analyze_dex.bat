@echo off
REM =============================================
REM CL831 SDK Analysis Script
REM Analyzes decompiled Java files for BLE/Chileaf code
REM =============================================

echo =============================================
echo CL831 SDK Analysis Script
echo =============================================
echo.

call conda activate base

set DECOMPILED_PATH=%~dp0REVERSE\CL831_DECOMPILED
set ANALYSIS_PATH=%~dp0REVERSE\CL831_ANALYSIS

if not exist "%DECOMPILED_PATH%" (
    echo ERROR: Decompiled files not found at %DECOMPILED_PATH%
    echo Please run decompile_dex_enhanced.bat first
    pause
    exit /b 1
)

if not exist "%ANALYSIS_PATH%" mkdir "%ANALYSIS_PATH%"

echo [1/5] Searching for BLE Service classes...
findstr /s /i /m "BluetoothGattService\|BleService\|GattService" "%DECOMPILED_PATH%\standard\*.java" > "%ANALYSIS_PATH%\ble_services.txt" 2>nul

echo [2/5] Searching for Characteristic classes...
findstr /s /i /m "BluetoothGattCharacteristic\|Characteristic" "%DECOMPILED_PATH%\standard\*.java" > "%ANALYSIS_PATH%\characteristics.txt" 2>nul

echo [3/5] Searching for Chileaf specific classes...
findstr /s /i /m "chileaf\|CL831\|CL837" "%DECOMPILED_PATH%\standard\*.java" > "%ANALYSIS_PATH%\chileaf_classes.txt" 2>nul

echo [4/5] Searching for Command/Protocol classes...
findstr /s /i /m "command\|protocol\|packet\|0x" "%DECOMPILED_PATH%\standard\*.java" > "%ANALYSIS_PATH%\commands.txt" 2>nul

echo [5/5] Searching for Device/Sensor classes...
findstr /s /i /m "device\|sensor\|accelerometer\|heart\|spo2" "%DECOMPILED_PATH%\standard\*.java" > "%ANALYSIS_PATH%\devices.txt" 2>nul

echo.
echo =============================================
echo Analysis Results
echo =============================================

echo BLE Services found:
type "%ANALYSIS_PATH%\ble_services.txt" 2>nul | wc -l
echo.

echo Characteristics found:
type "%ANALYSIS_PATH%\characteristics.txt" 2>nul | wc -l
echo.

echo Chileaf classes found:
type "%ANALYSIS_PATH%\chileaf_classes.txt" 2>nul | wc -l
echo.

echo Command/Protocol classes found:
type "%ANALYSIS_PATH%\commands.txt" 2>nul | wc -l
echo.

echo Device/Sensor classes found:
type "%ANALYSIS_PATH%\devices.txt" 2>nul | wc -l
echo.

echo =============================================
echo Top 10 Most Interesting Files
echo =============================================

REM Show first 10 files from each category
echo === BLE Services ===
type "%ANALYSIS_PATH%\ble_services.txt" 2>nul | head -5
echo.

echo === Chileaf Classes ===
type "%ANALYSIS_PATH%\chileaf_classes.txt" 2>nul | head -5
echo.

echo === Commands ===
type "%ANALYSIS_PATH%\commands.txt" 2>nul | head -5
echo.

echo Analysis files saved to: %ANALYSIS_PATH%
echo.

set /p OPEN_ANALYSIS="Open analysis directory? (y/n): "
if /i "%OPEN_ANALYSIS%"=="y" start explorer "%ANALYSIS_PATH%"

pause
