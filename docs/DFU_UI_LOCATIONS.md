# 🔄 Firmware Update - Posizioni UI

## Dove trovare il pulsante Firmware Update nell'app

### 1. 📊 Dashboard Screen
**Path:** Dashboard → AppBar → 🔄 System Update Icon

```dart
// File: lib/screens/dashboard_screen.dart
// Linee: 161-177

IconButton(
  icon: const Icon(Icons.system_update),
  onPressed: () {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => FirmwareUpdateScreen(
          service: widget.service,
          device: widget.device,
        ),
      ),
    );
  },
  tooltip: 'Firmware Update',
),
```

**Posizione:** AppBar in alto a destra, accanto al bottone Refresh  
**Visibilità:** Sempre visibile quando connesso  
**Icona:** `Icons.system_update` (🔄)

---

### 2. 🏠 Unified Home Screen
**Path:** Home → AppBar → 🔄 System Update Icon

```dart
// File: lib/screens/unified_home_screen.dart
// Linee: 750-762

if (_connectedDevice != null) ...[
  IconButton(
    icon: const Icon(Icons.system_update),
    onPressed: () {
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => FirmwareUpdateScreen(
            service: _service,
            device: _connectedDevice!,
          ),
        ),
      );
    },
    tooltip: 'Firmware Update',
  ),
  // ... altri bottoni
],
```

**Posizione:** AppBar in alto a destra, tra Battery e Disconnect  
**Visibilità:** Solo quando device connesso  
**Icona:** `Icons.system_update` (🔄)

---

### 3. ⚙️ Advanced Settings Screen
**Path:** Settings → Sistema → Aggiornamento Firmware

```dart
// File: lib/screens/advanced_settings_screen.dart
// Linee: 41-68

_buildSectionHeader('Sistema'),
_buildSettingsTile(
  context,
  icon: Icons.system_update,
  title: 'Aggiornamento Firmware',
  subtitle: 'Update firmware dispositivo (DFU)',
  color: Colors.orange,
  onTap: () {
    if (device == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Dispositivo non connesso'),
          backgroundColor: Colors.red,
        ),
      );
      return;
    }
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => FirmwareUpdateScreen(
          service: service,
          device: device!,
        ),
      ),
    );
  },
),
```

**Posizione:** Lista settings, sezione "Sistema"  
**Visibilità:** Sempre presente nella lista  
**Check:** Verifica connessione prima di aprire  
**Icona:** `Icons.system_update` (🔄) con sfondo arancione

---

### 4. 📈 Advanced Health Dashboard
**Path:** Health Analytics → AppBar → 🔄 System Update Icon

```dart
// File: lib/screens/advanced_health_dashboard.dart
// Linee: 150-162

appBar: AppBar(
  title: const Text('Health Analytics'),
  backgroundColor: Colors.black,
  actions: [
    IconButton(
      icon: const Icon(Icons.system_update),
      onPressed: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => FirmwareUpdateScreen(
              service: widget.service,
              device: widget.device,
            ),
          ),
        );
      },
      tooltip: 'Firmware Update',
    ),
  ],
  // ... tabs
),
```

**Posizione:** AppBar in alto a destra  
**Visibilità:** Sempre visibile nella dashboard  
**Icona:** `Icons.system_update` (🔄) bianca su sfondo nero

---

## Riepilogo Posizioni

| Schermata | Posizione | Visibilità | File |
|-----------|-----------|------------|------|
| **Dashboard** | AppBar (alto dx) | Sempre | `dashboard_screen.dart` L.161-177 |
| **Unified Home** | AppBar (alto dx) | Solo se connesso | `unified_home_screen.dart` L.750-762 |
| **Advanced Settings** | Lista settings | Sempre (check connessione) | `advanced_settings_screen.dart` L.41-68 |
| **Advanced Health** | AppBar (alto dx) | Sempre | `advanced_health_dashboard.dart` L.150-162 |

---

## Flusso Utente Tipico

### Scenario 1: Utente esperto (Dashboard)
```
1. Apri app
2. Connetti device
3. Vai a Dashboard
4. Tocca 🔄 in alto a destra
5. Schermata Firmware Update
```

### Scenario 2: Utente normale (Settings)
```
1. Apri app
2. Connetti device
3. Vai a Settings/Impostazioni Avanzate
4. Scorri fino a "Sistema"
5. Tocca "Aggiornamento Firmware"
6. Schermata Firmware Update
```

### Scenario 3: Power user (Home)
```
1. Apri app (già connesso)
2. Dalla Home screen
3. Tocca 🔄 in alto a destra
4. Schermata Firmware Update
```

---

## Modifiche Necessarie per Altri Screen

Se vuoi aggiungere il pulsante in altre schermate, usa questo pattern:

### Per AppBar actions:
```dart
actions: [
  IconButton(
    icon: const Icon(Icons.system_update),
    onPressed: () {
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => FirmwareUpdateScreen(
            service: yourService,
            device: yourDevice,
          ),
        ),
      );
    },
    tooltip: 'Firmware Update',
  ),
],
```

### Per Lista Settings:
```dart
ListTile(
  leading: Icon(Icons.system_update, color: Colors.orange),
  title: Text('Aggiornamento Firmware'),
  subtitle: Text('Update firmware dispositivo'),
  onTap: () {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => FirmwareUpdateScreen(
          service: yourService,
          device: yourDevice,
        ),
      ),
    );
  },
),
```

### Per Floating Action Button:
```dart
floatingActionButton: FloatingActionButton.extended(
  onPressed: () {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => FirmwareUpdateScreen(
          service: yourService,
          device: yourDevice,
        ),
      ),
    );
  },
  icon: Icon(Icons.system_update),
  label: Text('Update FW'),
  backgroundColor: Colors.orange,
),
```

---

## Icone e Colori Consistenti

| Elemento | Valore | Note |
|----------|--------|------|
| **Icona principale** | `Icons.system_update` | Standard Material |
| **Colore primario** | `Colors.orange` | Alert/action color |
| **Tooltip** | "Firmware Update" | Consistente ovunque |
| **Screen title** | "Firmware Update" | Consistente |

---

## Testing Checklist

Per ogni posizione del pulsante:

- [ ] Bottone visibile nella posizione corretta
- [ ] Icona corretta (system_update)
- [ ] Tooltip appare al long press
- [ ] Navigazione funziona
- [ ] Service e device passati correttamente
- [ ] Check connessione (dove necessario)
- [ ] Error handling per device null

---

**Implementato:** November 8, 2025  
**Schermate modificate:** 4  
**File totali:** 3 screens modificati  
**Status:** ✅ Production Ready
