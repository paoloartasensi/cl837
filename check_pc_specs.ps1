# Script per analizzare le specifiche del PC per ottimizzare Gradle
# Esegui con: .\check_pc_specs.ps1

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "   ANALISI SPECIFICHE PC PER GRADLE" -ForegroundColor Cyan
Write-Host "========================================`n" -ForegroundColor Cyan

# 1. CPU Information
Write-Host "📊 CPU INFORMATION:" -ForegroundColor Green
$cpu = Get-WmiObject Win32_Processor
Write-Host "  Nome: $($cpu.Name)"
Write-Host "  Core Fisici: $($cpu.NumberOfCores)"
Write-Host "  Core Logici (Thread): $($cpu.NumberOfLogicalProcessors)"
Write-Host "  Velocità: $($cpu.MaxClockSpeed) MHz"
Write-Host ""

# 2. RAM Information
Write-Host "💾 RAM INFORMATION:" -ForegroundColor Green
$ram = Get-WmiObject Win32_ComputerSystem
$totalRAM = [math]::Round($ram.TotalPhysicalMemory / 1GB, 2)
Write-Host "  RAM Totale: $totalRAM GB"

$os = Get-WmiObject Win32_OperatingSystem
$freeRAM = [math]::Round($os.FreePhysicalMemory / 1MB, 2)
$usedRAM = [math]::Round($totalRAM - $freeRAM, 2)
Write-Host "  RAM Libera: $freeRAM GB"
Write-Host "  RAM Utilizzata: $usedRAM GB"
Write-Host ""

# 3. Disk Space
Write-Host "💿 DISK SPACE (C:):" -ForegroundColor Green
$disk = Get-WmiObject Win32_LogicalDisk -Filter "DeviceID='C:'"
$totalDisk = [math]::Round($disk.Size / 1GB, 2)
$freeDisk = [math]::Round($disk.FreeSpace / 1GB, 2)
$usedDisk = [math]::Round($totalDisk - $freeDisk, 2)
Write-Host "  Totale: $totalDisk GB"
Write-Host "  Libero: $freeDisk GB"
Write-Host "  Usato: $usedDisk GB"
Write-Host ""

# 4. Java Information
Write-Host "☕ JAVA INFORMATION:" -ForegroundColor Green
try {
    $javaVersion = java -version 2>&1 | Select-String "version"
    Write-Host "  $javaVersion"
} catch {
    Write-Host "  Java non trovato nel PATH" -ForegroundColor Yellow
}

# Check Java in Android Studio
$androidStudioJava = "C:\Program Files\Android\Android Studio\jbr\bin\java.exe"
if (Test-Path $androidStudioJava) {
    Write-Host "  Android Studio JBR trovato: Yes"
    $jbrVersion = & $androidStudioJava -version 2>&1 | Select-String "version"
    Write-Host "    $jbrVersion"
} else {
    Write-Host "  Android Studio JBR: Not Found" -ForegroundColor Yellow
}
Write-Host ""

# 5. Gradle Daemon Info
Write-Host "🔧 GRADLE DAEMON INFO:" -ForegroundColor Green
$gradleDaemonDir = "$env:USERPROFILE\.gradle\daemon"
if (Test-Path $gradleDaemonDir) {
    $daemonDirs = Get-ChildItem -Path $gradleDaemonDir -Directory
    Write-Host "  Versioni Gradle installate:"
    foreach ($dir in $daemonDirs) {
        Write-Host "    - $($dir.Name)"
    }
} else {
    Write-Host "  Nessun daemon Gradle trovato"
}
Write-Host ""

# 6. Current Gradle Settings
Write-Host "⚙️  GRADLE PROPERTIES CORRENTI:" -ForegroundColor Green
$gradlePropsPath = ".\android\gradle.properties"
if (Test-Path $gradlePropsPath) {
    $gradleProps = Get-Content $gradlePropsPath | Select-String "org.gradle"
    if ($gradleProps) {
        foreach ($prop in $gradleProps) {
            Write-Host "  $prop"
        }
    } else {
        Write-Host "  Nessuna configurazione Gradle trovata" -ForegroundColor Yellow
    }
} else {
    Write-Host "  File gradle.properties non trovato" -ForegroundColor Yellow
}
Write-Host ""

# 7. Raccomandazioni
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   RACCOMANDAZIONI GRADLE" -ForegroundColor Cyan
Write-Host "========================================`n" -ForegroundColor Cyan

$recommendedMaxHeap = [math]::Floor($totalRAM * 0.25)
$recommendedWorkers = [math]::Min($cpu.NumberOfLogicalProcessors, 8)

Write-Host "💡 Basato sulle tue specifiche:" -ForegroundColor Yellow
Write-Host ""
Write-Host "  RAM Totale: $totalRAM GB"
Write-Host "  Core Logici: $($cpu.NumberOfLogicalProcessors)"
Write-Host ""
Write-Host "  Configurazione Gradle Raccomandata:" -ForegroundColor Green
Write-Host "  -----------------------------------"
Write-Host "  org.gradle.jvmargs=-Xmx${recommendedMaxHeap}g -XX:MaxMetaspaceSize=512m -XX:+HeapDumpOnOutOfMemoryError"
Write-Host "  org.gradle.parallel=true"
Write-Host "  org.gradle.workers.max=$recommendedWorkers"
Write-Host "  org.gradle.caching=true"
Write-Host "  org.gradle.daemon=true"
Write-Host ""

# 8. Memory Usage Breakdown
Write-Host "📈 MEMORY ALLOCATION SUGGERITA:" -ForegroundColor Yellow
Write-Host "  -----------------------------------"
$systemReserve = 2
$ideAlloc = [math]::Floor($totalRAM * 0.3)
$gradleAlloc = $recommendedMaxHeap
$available = [math]::Floor($totalRAM - $systemReserve - $ideAlloc - $gradleAlloc)

Write-Host "  Sistema/Windows:        ${systemReserve}GB (riservato)"
Write-Host "  IDE (VS Code/AS):       ${ideAlloc}GB (circa 30%)"
Write-Host "  Gradle Daemon:          ${gradleAlloc}GB (circa 25%)"
Write-Host "  Disponibile per altro:  ${available}GB"
Write-Host "  -----------------------------------"
Write-Host "  TOTALE:                 ${totalRAM}GB"
Write-Host ""

# 9. Warnings
if ($totalRAM -lt 8) {
    Write-Host "⚠️  ATTENZIONE: RAM sotto 8GB - build potrebbero essere lenti" -ForegroundColor Red
}

if ($freeDisk -lt 10) {
    Write-Host "⚠️  ATTENZIONE: Poco spazio disco (< 10GB libero)" -ForegroundColor Red
}

Write-Host "`n✅ Analisi completata!`n" -ForegroundColor Green
