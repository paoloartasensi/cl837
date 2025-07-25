# ==========================================
# CL831 SDK .dex Decompiler Script (PowerShell)
# ==========================================

Write-Host "==========================================="
Write-Host "CL831 SDK .dex Decompiler Script"
Write-Host "==========================================="
Write-Host ""

$ScriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
$SdkPath = Join-Path $ScriptPath "REVERSE\CL831_SDK"
$OutputPath = Join-Path $ScriptPath "REVERSE\CL831_DECOMPILED"
$JadxPath = Join-Path $ScriptPath "tools\bin\jadx.bat"

Write-Host "Source: $SdkPath"
Write-Host "Output: $OutputPath"
Write-Host "Jadx: $JadxPath"
Write-Host ""

# Create output directory if it doesn't exist
if (-not (Test-Path $OutputPath)) {
    New-Item -ItemType Directory -Path $OutputPath -Force | Out-Null
    Write-Host "Created output directory: $OutputPath"
}

# Check if jadx exists
if (-not (Test-Path $JadxPath)) {
    Write-Host "ERROR: jadx not found at $JadxPath" -ForegroundColor Red
    Write-Host "Please run the installation script first." -ForegroundColor Red
    exit 1
}

# Find all .dex files
$DexFiles = Get-ChildItem -Path $SdkPath -Filter "*.dex" | Select-Object -ExpandProperty FullName

if ($DexFiles.Count -eq 0) {
    Write-Host "No .dex files found in $SdkPath" -ForegroundColor Yellow
    exit 1
}

Write-Host "Found $($DexFiles.Count) .dex files:"
foreach ($file in $DexFiles) {
    Write-Host "  - $(Split-Path -Leaf $file)" -ForegroundColor Cyan
}
Write-Host ""

Write-Host "Decompiling .dex files with jadx..." -ForegroundColor Green

# Run jadx decompilation
try {
    $ArgumentList = @("-d", $OutputPath) + $DexFiles
    $Process = Start-Process -FilePath $JadxPath -ArgumentList $ArgumentList -Wait -PassThru -NoNewWindow
    
    if ($Process.ExitCode -eq 0) {
        Write-Host ""
        Write-Host "==========================================="
        Write-Host "Decompilation completed successfully!" -ForegroundColor Green
        Write-Host "Check results in: $OutputPath"
        Write-Host "==========================================="
        
        # Show summary of decompiled files
        $JavaFiles = Get-ChildItem -Path $OutputPath -Filter "*.java" -Recurse
        Write-Host ""
        Write-Host "Decompiled $($JavaFiles.Count) Java source files" -ForegroundColor Cyan
        
        # Open output directory in explorer
        $OpenExplorer = Read-Host "Open output directory in Explorer? (y/n)"
        if ($OpenExplorer -eq "y" -or $OpenExplorer -eq "Y") {
            Start-Process "explorer.exe" -ArgumentList $OutputPath
        }
    } else {
        Write-Host "ERROR: Decompilation failed with exit code $($Process.ExitCode)" -ForegroundColor Red
    }
} catch {
    Write-Host "ERROR: Failed to run jadx: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "Press any key to continue..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
