@echo off
REM =============================================
REM CL837 Project Complete Setup Script
REM =============================================

echo =============================================
echo CL837 Project Complete Setup Script
echo =============================================
echo.

REM Activate conda base environment
echo [1/6] Activating conda base environment...
call conda activate base
if errorlevel 1 (
    echo ERROR: Failed to activate conda base environment
    echo Please ensure Anaconda/Miniconda is properly installed
    pause
    exit /b 1
)
echo ✅ Conda base environment activated

REM Check Java installation
echo.
echo [2/6] Checking Java installation...
java -version
if errorlevel 1 (
    echo ERROR: Java not found. Please install Java JDK 8 or higher
    pause
    exit /b 1
)
echo ✅ Java installation verified

REM Check Flutter installation
echo.
echo [3/6] Checking Flutter installation...
flutter --version
if errorlevel 1 (
    echo ERROR: Flutter not found. Please install Flutter SDK
    pause
    exit /b 1
)
echo ✅ Flutter installation verified

REM Install Flutter dependencies
echo.
echo [4/6] Installing Flutter dependencies...
flutter pub get
if errorlevel 1 (
    echo ERROR: Failed to install Flutter dependencies
    pause
    exit /b 1
)
echo ✅ Flutter dependencies installed

REM Verify jadx installation
echo.
echo [5/6] Checking jadx decompiler...
if not exist "tools\bin\jadx.bat" (
    echo ❌ jadx not found. Installing...
    
    REM Create tools directory
    if not exist "tools" mkdir tools
    cd tools
    
    REM Download jadx
    echo Downloading jadx decompiler...
    powershell -Command "Invoke-WebRequest -Uri 'https://github.com/skylot/jadx/releases/download/v1.5.0/jadx-1.5.0.zip' -OutFile 'jadx.zip'"
    if errorlevel 1 (
        echo ERROR: Failed to download jadx
        cd ..
        pause
        exit /b 1
    )
    
    REM Extract jadx
    echo Extracting jadx...
    powershell -Command "Expand-Archive -Path 'jadx.zip' -DestinationPath '.' -Force"
    if errorlevel 1 (
        echo ERROR: Failed to extract jadx
        cd ..
        pause
        exit /b 1
    )
    
    cd ..
    echo ✅ jadx decompiler installed
) else (
    echo ✅ jadx decompiler already installed
)

REM Test jadx functionality
echo.
echo [6/6] Testing jadx functionality...
tools\bin\jadx.bat --version
if errorlevel 1 (
    echo ERROR: jadx test failed
    pause
    exit /b 1
)
echo ✅ jadx decompiler working correctly

REM Final verification
echo.
echo =============================================
echo 🎉 CL837 Project Setup Complete!
echo =============================================
echo.
echo ✅ Conda base environment: ACTIVE
echo ✅ Java: INSTALLED
echo ✅ Flutter: INSTALLED
echo ✅ Flutter dependencies: INSTALLED
echo ✅ jadx decompiler: INSTALLED
echo.
echo Available commands:
echo   flutter run              - Run the Flutter app
echo   decompile_dex.bat        - Decompile .dex files
echo   decompile_dex.ps1        - Decompile .dex files (PowerShell)
echo.
echo You can now:
echo 1. Run 'flutter run' to start the app
echo 2. Run 'decompile_dex.bat' to decompile SDK files
echo 3. Open the project in VS Code for development
echo.

pause
