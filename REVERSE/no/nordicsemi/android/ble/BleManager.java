/*      */ package no.nordicsemi.android.ble;
/*      */ 
/*      */ import android.annotation.SuppressLint;
/*      */ import android.bluetooth.BluetoothDevice;
/*      */ import android.bluetooth.BluetoothGatt;
/*      */ import android.bluetooth.BluetoothGattCharacteristic;
/*      */ import android.bluetooth.BluetoothGattDescriptor;
/*      */ import android.bluetooth.BluetoothGattServer;
/*      */ import android.content.BroadcastReceiver;
/*      */ import android.content.Context;
/*      */ import android.content.Intent;
/*      */ import android.content.IntentFilter;
/*      */ import android.os.Handler;
/*      */ import android.os.Looper;
/*      */ import androidx.annotation.IntRange;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.annotation.RequiresApi;
/*      */ import androidx.annotation.RequiresPermission;
/*      */ import androidx.annotation.RestrictTo;
/*      */ import androidx.annotation.StringRes;
/*      */ import java.util.UUID;
/*      */ import no.nordicsemi.android.ble.callback.ConnectionParametersUpdatedCallback;
/*      */ import no.nordicsemi.android.ble.data.Data;
/*      */ import no.nordicsemi.android.ble.data.DataProvider;
/*      */ import no.nordicsemi.android.ble.observer.BondingObserver;
/*      */ import no.nordicsemi.android.ble.observer.ConnectionObserver;
/*      */ import no.nordicsemi.android.ble.utils.ILogger;
/*      */ import no.nordicsemi.android.ble.utils.ParserUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @SuppressLint({"MissingPermission"})
/*      */ public abstract class BleManager
/*      */   implements ILogger
/*      */ {
/*  107 */   static final UUID CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
/*      */   
/*  109 */   static final UUID BATTERY_SERVICE = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb");
/*  110 */   static final UUID BATTERY_LEVEL_CHARACTERISTIC = UUID.fromString("00002A19-0000-1000-8000-00805f9b34fb");
/*      */   
/*  112 */   static final UUID GENERIC_ATTRIBUTE_SERVICE = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");
/*  113 */   static final UUID SERVICE_CHANGED_CHARACTERISTIC = UUID.fromString("00002A05-0000-1000-8000-00805f9b34fb");
/*      */   
/*      */   public static final int PAIRING_VARIANT_PIN = 0;
/*      */   
/*      */   public static final int PAIRING_VARIANT_PASSKEY = 1;
/*      */   
/*      */   public static final int PAIRING_VARIANT_PASSKEY_CONFIRMATION = 2;
/*      */   public static final int PAIRING_VARIANT_CONSENT = 3;
/*      */   public static final int PAIRING_VARIANT_DISPLAY_PASSKEY = 4;
/*      */   public static final int PAIRING_VARIANT_DISPLAY_PIN = 5;
/*      */   public static final int PAIRING_VARIANT_OOB_CONSENT = 6;
/*      */   private final Context context;
/*      */   private BleServerManager serverManager;
/*      */   @NonNull
/*      */   final BleManagerGattCallback requestHandler;
/*      */   @Deprecated
/*      */   protected BleManagerCallbacks callbacks;
/*      */   @Nullable
/*      */   BondingObserver bondingObserver;
/*      */   @Nullable
/*      */   ConnectionObserver connectionObserver;
/*      */   
/*  135 */   private final BroadcastReceiver mPairingRequestBroadcastReceiver = new BroadcastReceiver()
/*      */     {
/*      */       public void onReceive(Context context, Intent intent) {
/*  138 */         BluetoothDevice device = (BluetoothDevice)intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
/*      */ 
/*      */         
/*  141 */         BluetoothDevice bluetoothDevice = BleManager.this.requestHandler.getBluetoothDevice();
/*  142 */         if (bluetoothDevice == null || device == null || 
/*  143 */           !device.getAddress().equals(bluetoothDevice.getAddress())) {
/*      */           return;
/*      */         }
/*      */         
/*  147 */         int variant = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_VARIANT", 0);
/*  148 */         int key = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", -1);
/*  149 */         BleManager.this.log(3, "[Broadcast] Action received: android.bluetooth.device.action.PAIRING_REQUEST, pairing variant: " + 
/*  150 */             ParserUtils.pairingVariantToString(variant) + " (" + variant + "); key: " + key);
/*      */         
/*  152 */         BleManager.this.onPairingRequestReceived(device, variant, key);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BleManager(@NonNull Context context) {
/*  164 */     this(context, new Handler(Looper.getMainLooper()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BleManager(@NonNull Context context, @NonNull Handler handler) {
/*  177 */     this.context = context;
/*  178 */     this.requestHandler = getGattCallback();
/*  179 */     this.requestHandler.init(this, handler);
/*      */     
/*  181 */     context.registerReceiver(this.mPairingRequestBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initialize() {
/*  224 */     this.requestHandler.initialize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
/*  236 */     return this.requestHandler.isRequiredServiceSupported(gatt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isOptionalServiceSupported(@NonNull BluetoothGatt gatt) {
/*  248 */     return this.requestHandler.isOptionalServiceSupported(gatt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onServerReady(@NonNull BluetoothGattServer server) {
/*  266 */     this.requestHandler.onServerReady(server);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onServicesInvalidated() {
/*  278 */     this.requestHandler.onServicesInvalidated();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onDeviceReady() {
/*  286 */     this.requestHandler.onDeviceReady();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onManagerReady() {
/*  294 */     this.requestHandler.onManagerReady();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() {
/*      */     try {
/*  305 */       this.context.unregisterReceiver(this.mPairingRequestBroadcastReceiver);
/*  306 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  309 */     if (this.serverManager != null) {
/*  310 */       this.serverManager.removeManager(this);
/*      */     }
/*  312 */     this.requestHandler.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void runOnCallbackThread(@NonNull Runnable runnable) {
/*  322 */     this.requestHandler.post(runnable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void setGattCallbacks(@NonNull BleManagerCallbacks callbacks) {
/*  333 */     this.callbacks = callbacks;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setConnectionObserver(@Nullable ConnectionObserver callback) {
/*  343 */     this.connectionObserver = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/*      */   @Nullable
/*      */   public final ConnectionObserver getConnectionObserver() {
/*  352 */     return this.connectionObserver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setBondingObserver(@Nullable BondingObserver callback) {
/*  363 */     this.bondingObserver = callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/*      */   @Nullable
/*      */   public final BondingObserver getBondingObserver() {
/*  372 */     return this.bondingObserver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void useServer(@NonNull BleServerManager server) {
/*  383 */     if (this.serverManager != null) {
/*  384 */       this.serverManager.removeManager(this);
/*      */     }
/*  386 */     this.serverManager = server;
/*  387 */     server.addManager(this);
/*  388 */     this.requestHandler.useServer(server);
/*      */   }
/*      */   
/*      */   final void closeServer() {
/*  392 */     this.serverManager = null;
/*  393 */     this.requestHandler.useServer(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onPairingRequestReceived(@NonNull BluetoothDevice device, int variant, int key) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   protected BleManagerGattCallback getGattCallback() {
/*  429 */     return new BleManagerGattCallback()
/*      */       {
/*      */         protected boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
/*  432 */           return false;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         protected void onServicesInvalidated() {}
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected final Context getContext() {
/*  449 */     return this.context;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public BluetoothDevice getBluetoothDevice() {
/*  461 */     return this.requestHandler.getBluetoothDevice();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isConnected() {
/*  469 */     return this.requestHandler.isConnected();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isReady() {
/*  477 */     return this.requestHandler.isReady();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean isBonded() {
/*  488 */     BluetoothDevice bluetoothDevice = this.requestHandler.getBluetoothDevice();
/*  489 */     return (bluetoothDevice != null && bluetoothDevice
/*  490 */       .getBondState() == 12);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getConnectionState() {
/*  504 */     return this.requestHandler.getConnectionState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @IntRange(from = -1L, to = 100L)
/*      */   public final int getBatteryValue() {
/*  520 */     return this.requestHandler.getBatteryValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinLogPriority() {
/*  531 */     return 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void log(int priority, @NonNull String message) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void log(int priority, @StringRes int messageRes, @Nullable Object... params) {
/*  555 */     String message = this.context.getString(messageRes, params);
/*  556 */     log(priority, message);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected boolean shouldAutoConnect() {
/*  588 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean shouldClearCacheWhenDisconnected() {
/*  614 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @IntRange(from = 0L)
/*      */   protected int getServiceDiscoveryDelay(boolean bonded) {
/*  647 */     return bonded ? 1600 : 300;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   public final ConnectRequest connect(@NonNull BluetoothDevice device) {
/*  672 */     return Request.connect(device)
/*  673 */       .useAutoConnect(shouldAutoConnect())
/*  674 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   public final ConnectRequest connect(@NonNull BluetoothDevice device, int phy) {
/*  707 */     return Request.connect(device)
/*  708 */       .usePreferredPhy(phy)
/*  709 */       .useAutoConnect(shouldAutoConnect())
/*  710 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   public final DisconnectRequest disconnect() {
/*  723 */     return Request.disconnect().setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void attachClientConnection(BluetoothDevice client) {
/*  731 */     this.requestHandler.attachClientConnection(client);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
/*      */   protected Request createBond() {
/*  761 */     return createBondInsecure();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
/*      */   protected Request createBondInsecure() {
/*  789 */     return Request.createBond().setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
/*      */   protected Request ensureBond() {
/*  821 */     return Request.ensureBond().setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
/*      */   protected Request removeBond() {
/*  840 */     return Request.removeBond().setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected ValueChangedCallback setNotificationCallback(@Nullable BluetoothGattCharacteristic characteristic) {
/*  858 */     return this.requestHandler.getValueChangedCallback(characteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected ValueChangedCallback setIndicationCallback(@Nullable BluetoothGattCharacteristic characteristic) {
/*  876 */     return setNotificationCallback(characteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected ValueChangedCallback setWriteCallback(@Nullable BluetoothGattCharacteristic serverCharacteristic) {
/*  892 */     return this.requestHandler.getValueChangedCallback(serverCharacteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected ValueChangedCallback setWriteCallback(@Nullable BluetoothGattDescriptor serverDescriptor) {
/*  908 */     return this.requestHandler.getValueChangedCallback(serverDescriptor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeNotificationCallback(@Nullable BluetoothGattCharacteristic characteristic) {
/*  918 */     this.requestHandler.removeValueChangedCallback(characteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeIndicationCallback(@Nullable BluetoothGattCharacteristic characteristic) {
/*  928 */     removeNotificationCallback(characteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeWriteCallback(@Nullable BluetoothGattCharacteristic serverCharacteristic) {
/*  938 */     this.requestHandler.removeValueChangedCallback(serverCharacteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeWriteCallback(@Nullable BluetoothGattDescriptor serverDescriptor) {
/*  948 */     this.requestHandler.removeValueChangedCallback(serverDescriptor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WaitForValueChangedRequest waitForNotification(@Nullable BluetoothGattCharacteristic characteristic) {
/*  967 */     return Request.newWaitForNotificationRequest(characteristic)
/*  968 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WaitForValueChangedRequest waitForIndication(@Nullable BluetoothGattCharacteristic characteristic) {
/*  987 */     return Request.newWaitForIndicationRequest(characteristic)
/*  988 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WaitForValueChangedRequest waitForWrite(@Nullable BluetoothGattCharacteristic serverCharacteristic) {
/* 1007 */     return Request.newWaitForWriteRequest(serverCharacteristic)
/* 1008 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WaitForValueChangedRequest waitForWrite(@Nullable BluetoothGattDescriptor serverDescriptor) {
/* 1027 */     return Request.newWaitForWriteRequest(serverDescriptor)
/* 1028 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected ConditionalWaitRequest<Void> waitIf(@NonNull ConditionalWaitRequest.Condition<Void> condition) {
/* 1041 */     return Request.<Void>newConditionalWaitRequest(condition, null)
/* 1042 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected <T> ConditionalWaitRequest<T> waitIf(@Nullable T parameter, @NonNull ConditionalWaitRequest.Condition<T> condition) {
/* 1057 */     return Request.<T>newConditionalWaitRequest(condition, parameter)
/* 1058 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected ConditionalWaitRequest<Void> waitUntil(@NonNull ConditionalWaitRequest.Condition<Void> condition) {
/* 1071 */     return waitIf(condition).negate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected <T> ConditionalWaitRequest<T> waitUntil(@Nullable T parameter, @NonNull ConditionalWaitRequest.Condition<T> condition) {
/* 1086 */     return waitIf(parameter, condition).negate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected ConditionalWaitRequest<BluetoothGattCharacteristic> waitUntilNotificationsEnabled(@Nullable BluetoothGattCharacteristic serverCharacteristic) {
/* 1099 */     return waitUntil(serverCharacteristic, characteristic -> {
/*      */           if (characteristic == null)
/*      */             return false; 
/*      */           BluetoothGattDescriptor cccd = characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
/*      */           if (cccd == null)
/*      */             return false; 
/*      */           byte[] value = this.requestHandler.getDescriptorValue(cccd);
/* 1106 */           return (value != null && value.length == 2 && (value[0] & 0x1) == 1);
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected ConditionalWaitRequest<BluetoothGattCharacteristic> waitUntilIndicationsEnabled(@Nullable BluetoothGattCharacteristic serverCharacteristic) {
/* 1120 */     return waitUntil(serverCharacteristic, characteristic -> {
/*      */           if (characteristic == null)
/*      */             return false; 
/*      */           BluetoothGattDescriptor cccd = characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
/*      */           if (cccd == null)
/*      */             return false; 
/*      */           byte[] value = this.requestHandler.getDescriptorValue(cccd);
/* 1127 */           return (value != null && value.length == 2 && (value[0] & 0x2) == 2);
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WaitForReadRequest waitForRead(@Nullable BluetoothGattCharacteristic serverCharacteristic) {
/* 1147 */     return Request.newWaitForReadRequest(serverCharacteristic)
/* 1148 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WaitForReadRequest waitForRead(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data) {
/* 1165 */     return Request.newWaitForReadRequest(serverCharacteristic, data)
/* 1166 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WaitForReadRequest waitForRead(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data, int offset, int length) {
/* 1185 */     return Request.newWaitForReadRequest(serverCharacteristic, data, offset, length)
/* 1186 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WaitForReadRequest waitForRead(@Nullable BluetoothGattDescriptor serverDescriptor) {
/* 1204 */     return Request.newWaitForReadRequest(serverDescriptor)
/* 1205 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WaitForReadRequest waitForRead(@Nullable BluetoothGattDescriptor serverDescriptor, @Nullable byte[] data) {
/* 1222 */     return Request.newWaitForReadRequest(serverDescriptor, data)
/* 1223 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WaitForReadRequest waitForRead(@Nullable BluetoothGattDescriptor serverDescriptor, @Nullable byte[] data, int offset, int length) {
/* 1242 */     return Request.newWaitForReadRequest(serverDescriptor, data, offset, length)
/* 1243 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setCharacteristicValue(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable DataProvider provider) {
/* 1261 */     this.requestHandler.setCharacteristicValue(serverCharacteristic, provider);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected SetValueRequest setCharacteristicValue(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable Data data) {
/* 1278 */     return Request.newSetValueRequest(serverCharacteristic, (data != null) ? data.getValue() : null)
/* 1279 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected SetValueRequest setCharacteristicValue(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data) {
/* 1296 */     return Request.newSetValueRequest(serverCharacteristic, data)
/* 1297 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected SetValueRequest setCharacteristicValue(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data, int offset, int length) {
/* 1317 */     return Request.newSetValueRequest(serverCharacteristic, data, offset, length)
/* 1318 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setDescriptorValue(@Nullable BluetoothGattDescriptor serverDescriptor, @Nullable DataProvider provider) {
/* 1336 */     this.requestHandler.setDescriptorValue(serverDescriptor, provider);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected SetValueRequest setDescriptorValue(@Nullable BluetoothGattDescriptor serverDescriptor, @Nullable Data data) {
/* 1353 */     return Request.newSetValueRequest(serverDescriptor, (data != null) ? data.getValue() : null)
/* 1354 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected SetValueRequest setDescriptorValue(@Nullable BluetoothGattDescriptor serverDescriptor, @Nullable byte[] data) {
/* 1371 */     return Request.newSetValueRequest(serverDescriptor, data)
/* 1372 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected SetValueRequest setDescriptorValue(@Nullable BluetoothGattDescriptor serverDescriptor, @Nullable byte[] data, int offset, int length) {
/* 1391 */     return Request.newSetValueRequest(serverDescriptor, data, offset, length)
/* 1392 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest enableNotifications(@Nullable BluetoothGattCharacteristic characteristic) {
/* 1409 */     return Request.newEnableNotificationsRequest(characteristic)
/* 1410 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest disableNotifications(@Nullable BluetoothGattCharacteristic characteristic) {
/* 1426 */     return Request.newDisableNotificationsRequest(characteristic)
/* 1427 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest enableIndications(@Nullable BluetoothGattCharacteristic characteristic) {
/* 1443 */     return Request.newEnableIndicationsRequest(characteristic)
/* 1444 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest disableIndications(@Nullable BluetoothGattCharacteristic characteristic) {
/* 1460 */     return Request.newDisableIndicationsRequest(characteristic)
/* 1461 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected ReadRequest readCharacteristic(@Nullable BluetoothGattCharacteristic characteristic) {
/* 1477 */     return Request.newReadRequest(characteristic)
/* 1478 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic characteristic, @Nullable Data data, int writeType) {
/* 1502 */     return Request.newWriteRequest(characteristic, (data != null) ? data.getValue() : null, writeType)
/* 1503 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, int writeType) {
/* 1527 */     return Request.newWriteRequest(characteristic, data, writeType)
/* 1528 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, int offset, int length, int writeType) {
/* 1554 */     return Request.newWriteRequest(characteristic, data, offset, length, writeType)
/* 1555 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   protected WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic characteristic, @Nullable Data data) {
/* 1579 */     return Request.newWriteRequest(characteristic, (data != null) ? data.getValue() : null)
/* 1580 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   protected WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data) {
/* 1604 */     return Request.newWriteRequest(characteristic, data)
/* 1605 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NonNull
/*      */   protected WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, int offset, int length) {
/* 1632 */     return Request.newWriteRequest(characteristic, data, offset, length)
/* 1633 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected ReadRequest readDescriptor(@Nullable BluetoothGattDescriptor descriptor) {
/* 1649 */     return Request.newReadRequest(descriptor)
/* 1650 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest writeDescriptor(@Nullable BluetoothGattDescriptor descriptor, @Nullable Data data) {
/* 1672 */     return Request.newWriteRequest(descriptor, (data != null) ? data.getValue() : null)
/* 1673 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest writeDescriptor(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] data) {
/* 1695 */     return Request.newWriteRequest(descriptor, data)
/* 1696 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest writeDescriptor(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] data, int offset, int length) {
/* 1721 */     return Request.newWriteRequest(descriptor, data, offset, length)
/* 1722 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest sendNotification(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable Data data) {
/* 1746 */     return Request.newNotificationRequest(serverCharacteristic, (data != null) ? data.getValue() : null)
/* 1747 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest sendNotification(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data) {
/* 1770 */     return Request.newNotificationRequest(serverCharacteristic, data)
/* 1771 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest sendNotification(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data, int offset, int length) {
/* 1797 */     return Request.newNotificationRequest(serverCharacteristic, data, offset, length)
/* 1798 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest sendIndication(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable Data data) {
/* 1821 */     return Request.newIndicationRequest(serverCharacteristic, (data != null) ? data.getValue() : null)
/* 1822 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest sendIndication(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data) {
/* 1845 */     return Request.newIndicationRequest(serverCharacteristic, data)
/* 1846 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected WriteRequest sendIndication(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data, int offset, int length) {
/* 1872 */     return Request.newIndicationRequest(serverCharacteristic, data, offset, length)
/* 1873 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected RequestQueue beginAtomicRequestQueue() {
/* 1885 */     return (new RequestQueue()).setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   protected ReliableWriteRequest beginReliableWrite() {
/* 1926 */     return Request.newReliableWriteRequest()
/* 1927 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean isReliableWriteInProgress() {
/* 1935 */     return this.requestHandler.isReliableWriteInProgress();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void readBatteryLevel() {
/* 1945 */     Request.newReadBatteryLevelRequest()
/* 1946 */       .setRequestHandler(this.requestHandler)
/* 1947 */       .with(this.requestHandler.getBatteryLevelCallback())
/* 1948 */       .enqueue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void enableBatteryLevelNotifications() {
/* 1959 */     Request.newEnableBatteryLevelNotificationsRequest()
/* 1960 */       .setRequestHandler(this.requestHandler)
/* 1961 */       .before(device -> this.requestHandler.setBatteryLevelNotificationCallback())
/* 1962 */       .done(device -> log(4, "Battery Level notifications enabled"))
/* 1963 */       .enqueue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void disableBatteryLevelNotifications() {
/* 1973 */     Request.newDisableBatteryLevelNotificationsRequest()
/* 1974 */       .setRequestHandler(this.requestHandler)
/* 1975 */       .done(device -> log(4, "Battery Level notifications disabled"))
/* 1976 */       .enqueue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MtuRequest requestMtu(@IntRange(from = 23L, to = 517L) int mtu) {
/* 1991 */     return Request.newMtuRequest(mtu).setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @IntRange(from = 23L, to = 517L)
/*      */   protected int getMtu() {
/* 2008 */     return this.requestHandler.getMtu();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void overrideMtu(@IntRange(from = 23L, to = 517L) int mtu) {
/* 2020 */     this.requestHandler.overrideMtu(mtu);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequiresApi(api = 21)
/*      */   protected ConnectionPriorityRequest requestConnectionPriority(int priority) {
/* 2051 */     return Request.newConnectionPriorityRequest(priority)
/* 2052 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequiresApi(api = 26)
/*      */   protected void setConnectionParametersListener(@Nullable ConnectionParametersUpdatedCallback callback) {
/* 2067 */     this.requestHandler.setConnectionParametersListener(callback);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PhyRequest setPreferredPhy(int txPhy, int rxPhy, int phyOptions) {
/* 2093 */     return Request.newSetPreferredPhyRequest(txPhy, rxPhy, phyOptions)
/* 2094 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PhyRequest readPhy() {
/* 2110 */     return Request.newReadPhyRequest()
/* 2111 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ReadRssiRequest readRssi() {
/* 2123 */     return Request.newReadRssiRequest().setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Request refreshDeviceCache() {
/* 2145 */     return Request.newRefreshCacheRequest()
/* 2146 */       .setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SleepRequest sleep(@IntRange(from = 0L) long delay) {
/* 2160 */     return Request.newSleepRequest(delay).setRequestHandler(this.requestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected final void enqueue(@NonNull Request request) {
/* 2171 */     this.requestHandler.enqueue(request);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void cancelQueue() {
/* 2185 */     this.requestHandler.cancelQueue();
/*      */   }
/*      */   
/*      */   protected static abstract class BleManagerGattCallback extends BleManagerHandler {}
/*      */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\BleManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */