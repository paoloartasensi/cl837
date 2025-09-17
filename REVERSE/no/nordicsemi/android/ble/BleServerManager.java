/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.annotation.SuppressLint;
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.bluetooth.BluetoothGattCharacteristic;
/*     */ import android.bluetooth.BluetoothGattDescriptor;
/*     */ import android.bluetooth.BluetoothGattServer;
/*     */ import android.bluetooth.BluetoothGattServerCallback;
/*     */ import android.bluetooth.BluetoothGattService;
/*     */ import android.bluetooth.BluetoothManager;
/*     */ import android.content.Context;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.RequiresApi;
/*     */ import androidx.annotation.StringRes;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Queue;
/*     */ import java.util.UUID;
/*     */ import no.nordicsemi.android.ble.data.Data;
/*     */ import no.nordicsemi.android.ble.observer.ServerObserver;
/*     */ import no.nordicsemi.android.ble.utils.ILogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BleServerManager
/*     */   implements ILogger
/*     */ {
/*  48 */   private static final UUID CHARACTERISTIC_EXTENDED_PROPERTIES_DESCRIPTOR_UUID = UUID.fromString("00002900-0000-1000-8000-00805f9b34fb");
/*  49 */   private static final UUID CLIENT_USER_DESCRIPTION_DESCRIPTOR_UUID = UUID.fromString("00002901-0000-1000-8000-00805f9b34fb");
/*  50 */   private static final UUID CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
/*     */ 
/*     */   
/*     */   private BluetoothGattServer server;
/*     */   
/*  55 */   private final List<BleManager> managers = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private final Context context;
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerObserver serverObserver;
/*     */ 
/*     */ 
/*     */   
/*     */   private Queue<BluetoothGattService> serverServices;
/*     */ 
/*     */ 
/*     */   
/*     */   private List<BluetoothGattCharacteristic> sharedCharacteristics;
/*     */ 
/*     */ 
/*     */   
/*     */   private List<BluetoothGattDescriptor> sharedDescriptors;
/*     */ 
/*     */   
/*     */   private final BluetoothGattServerCallback gattServerCallback;
/*     */ 
/*     */ 
/*     */   
/*     */   @SuppressLint({"MissingPermission"})
/*     */   public final boolean open() {
/*  84 */     if (this.server != null) {
/*  85 */       return true;
/*     */     }
/*  87 */     this.serverServices = new LinkedList<>(initializeServer());
/*  88 */     BluetoothManager bm = (BluetoothManager)this.context.getSystemService("bluetooth");
/*  89 */     if (bm != null) {
/*  90 */       this.server = bm.openGattServer(this.context, this.gattServerCallback);
/*     */     }
/*  92 */     if (this.server != null) {
/*  93 */       log(4, "[Server] Server started successfully");
/*     */       try {
/*  95 */         BluetoothGattService service = this.serverServices.remove();
/*  96 */         this.server.addService(service);
/*  97 */       } catch (NoSuchElementException e) {
/*  98 */         if (this.serverObserver != null)
/*  99 */           this.serverObserver.onServerReady(); 
/* 100 */       } catch (Exception e) {
/* 101 */         close();
/* 102 */         return false;
/*     */       } 
/* 104 */       return true;
/*     */     } 
/* 106 */     log(5, "GATT server initialization failed");
/* 107 */     this.serverServices = null;
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SuppressLint({"MissingPermission"})
/*     */   public final void close() {
/* 116 */     if (this.server != null) {
/* 117 */       this.server.close();
/* 118 */       this.server = null;
/*     */     } 
/* 120 */     this.serverServices = null;
/* 121 */     for (BleManager manager : this.managers) {
/*     */ 
/*     */       
/* 124 */       manager.closeServer();
/* 125 */       manager.close();
/*     */     } 
/* 127 */     this.managers.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setServerObserver(@Nullable ServerObserver observer) {
/* 136 */     this.serverObserver = observer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   final BluetoothGattServer getServer() {
/* 144 */     return this.server;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void addManager(@NonNull BleManager manager) {
/* 152 */     if (!this.managers.contains(manager)) {
/* 153 */       this.managers.add(manager);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void removeManager(@NonNull BleManager manager) {
/* 162 */     this.managers.remove(manager);
/*     */   }
/*     */   
/*     */   final boolean isShared(@NonNull BluetoothGattCharacteristic characteristic) {
/* 166 */     return (this.sharedCharacteristics != null && this.sharedCharacteristics.contains(characteristic));
/*     */   }
/*     */   
/*     */   final boolean isShared(@NonNull BluetoothGattDescriptor descriptor) {
/* 170 */     return (this.sharedDescriptors != null && this.sharedDescriptors.contains(descriptor));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BleManagerHandler getRequestHandler(@NonNull BluetoothDevice device) {
/* 175 */     for (BleManager manager : this.managers) {
/* 176 */       if (device.equals(manager.getBluetoothDevice())) {
/* 177 */         return manager.requestHandler;
/*     */       }
/*     */     } 
/* 180 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinLogPriority() {
/* 191 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(int priority, @NonNull String message) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(int priority, @StringRes int messageRes, @Nullable Object... params) {
/* 215 */     String message = this.context.getString(messageRes, params);
/* 216 */     log(priority, message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattService service(@NonNull UUID uuid, BluetoothGattCharacteristic... characteristics) {
/* 250 */     BluetoothGattService service = new BluetoothGattService(uuid, 0);
/* 251 */     for (BluetoothGattCharacteristic characteristic : characteristics) {
/* 252 */       service.addCharacteristic(characteristic);
/*     */     }
/* 254 */     return service;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattCharacteristic characteristic(@NonNull UUID uuid, int properties, int permissions, @Nullable byte[] initialValue, BluetoothGattDescriptor... descriptors) {
/* 291 */     boolean writableAuxiliaries = false;
/* 292 */     boolean cccdFound = false;
/* 293 */     boolean cepdFound = false;
/* 294 */     BluetoothGattDescriptor cepd = null;
/* 295 */     for (BluetoothGattDescriptor descriptor : descriptors) {
/* 296 */       if (CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID.equals(descriptor.getUuid())) {
/* 297 */         cccdFound = true;
/* 298 */       } else if (CLIENT_USER_DESCRIPTION_DESCRIPTOR_UUID.equals(descriptor.getUuid()) && 0 != (descriptor
/* 299 */         .getPermissions() & 0x70)) {
/*     */ 
/*     */ 
/*     */         
/* 303 */         writableAuxiliaries = true;
/* 304 */       } else if (CHARACTERISTIC_EXTENDED_PROPERTIES_DESCRIPTOR_UUID.equals(descriptor.getUuid())) {
/* 305 */         cepd = descriptor;
/* 306 */         cepdFound = true;
/*     */       } 
/*     */     } 
/*     */     
/* 310 */     if (writableAuxiliaries) {
/* 311 */       if (cepd == null) {
/* 312 */         cepd = new BluetoothGattDescriptor(CHARACTERISTIC_EXTENDED_PROPERTIES_DESCRIPTOR_UUID, 1);
/*     */         
/* 314 */         cepd.setValue(new byte[] { 2, 0 });
/*     */       }
/* 316 */       else if (cepd.getValue() != null && (cepd.getValue()).length == 2) {
/* 317 */         cepd.getValue()[0] = (byte)(cepd.getValue()[0] | 0x2);
/*     */       } else {
/* 319 */         cepd.setValue(new byte[] { 2, 0 });
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 324 */     boolean cccdRequired = ((properties & 0x30) != 0);
/*     */     
/* 326 */     boolean reliableWrite = (cepd != null && cepd.getValue() != null && (cepd.getValue()).length == 2 && (cepd.getValue()[0] & 0x1) != 0);
/* 327 */     if (writableAuxiliaries || reliableWrite) {
/* 328 */       properties |= 0x80;
/*     */     }
/* 330 */     if ((properties & 0x80) != 0 && cepd == null) {
/* 331 */       cepd = new BluetoothGattDescriptor(CHARACTERISTIC_EXTENDED_PROPERTIES_DESCRIPTOR_UUID, 1);
/* 332 */       cepd.setValue(new byte[] { 0, 0 });
/*     */     } 
/*     */ 
/*     */     
/* 336 */     BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(uuid, properties, permissions);
/* 337 */     if (cccdRequired && !cccdFound) {
/* 338 */       characteristic.addDescriptor(cccd());
/*     */     }
/* 340 */     for (BluetoothGattDescriptor descriptor : descriptors) {
/* 341 */       characteristic.addDescriptor(descriptor);
/*     */     }
/* 343 */     if (cepd != null && !cepdFound) {
/* 344 */       characteristic.addDescriptor(cepd);
/*     */     }
/* 346 */     characteristic.setValue(initialValue);
/* 347 */     return characteristic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattCharacteristic characteristic(@NonNull UUID uuid, int properties, int permissions, @Nullable Data initialValue, BluetoothGattDescriptor... descriptors) {
/* 382 */     return characteristic(uuid, properties, permissions, (initialValue != null) ? initialValue.getValue() : null, descriptors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattCharacteristic characteristic(@NonNull UUID uuid, int properties, int permissions, BluetoothGattDescriptor... descriptors) {
/* 415 */     return characteristic(uuid, properties, permissions, (byte[])null, descriptors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattCharacteristic sharedCharacteristic(@NonNull UUID uuid, int properties, int permissions, @Nullable byte[] initialValue, BluetoothGattDescriptor... descriptors) {
/* 450 */     BluetoothGattCharacteristic characteristic = characteristic(uuid, properties, permissions, initialValue, descriptors);
/* 451 */     if (this.sharedCharacteristics == null)
/* 452 */       this.sharedCharacteristics = new ArrayList<>(); 
/* 453 */     this.sharedCharacteristics.add(characteristic);
/* 454 */     return characteristic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattCharacteristic sharedCharacteristic(@NonNull UUID uuid, int properties, int permissions, @Nullable Data initialValue, BluetoothGattDescriptor... descriptors) {
/* 489 */     return sharedCharacteristic(uuid, properties, permissions, (initialValue != null) ? initialValue.getValue() : null, descriptors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattCharacteristic sharedCharacteristic(@NonNull UUID uuid, int properties, int permissions, BluetoothGattDescriptor... descriptors) {
/* 522 */     return sharedCharacteristic(uuid, properties, permissions, (byte[])null, descriptors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattDescriptor descriptor(@NonNull UUID uuid, int permissions, @Nullable byte[] initialValue) {
/* 543 */     BluetoothGattDescriptor descriptor = new BluetoothGattDescriptor(uuid, permissions);
/* 544 */     descriptor.setValue(initialValue);
/* 545 */     return descriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattDescriptor descriptor(@NonNull UUID uuid, int permissions, @Nullable Data initialValue) {
/* 566 */     return descriptor(uuid, permissions, (initialValue != null) ? initialValue.getValue() : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattDescriptor sharedDescriptor(@NonNull UUID uuid, int permissions, @Nullable byte[] initialValue) {
/* 587 */     BluetoothGattDescriptor descriptor = descriptor(uuid, permissions, initialValue);
/* 588 */     if (this.sharedDescriptors == null)
/* 589 */       this.sharedDescriptors = new ArrayList<>(); 
/* 590 */     this.sharedDescriptors.add(descriptor);
/* 591 */     return descriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattDescriptor sharedDescriptor(@NonNull UUID uuid, int permissions, @Nullable Data initialValue) {
/* 612 */     return sharedDescriptor(uuid, permissions, (initialValue != null) ? initialValue.getValue() : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattDescriptor cccd() {
/* 623 */     return descriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID, 17, new byte[] { 0, 0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattDescriptor sharedCccd() {
/* 636 */     return sharedDescriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID, 17, new byte[] { 0, 0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattDescriptor reliableWrite() {
/* 650 */     return sharedDescriptor(CHARACTERISTIC_EXTENDED_PROPERTIES_DESCRIPTOR_UUID, 1, new byte[] { 1, 0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   protected final BluetoothGattDescriptor description(@Nullable String description, boolean writableAuxiliaries) {
/* 667 */     BluetoothGattDescriptor cud = descriptor(CLIENT_USER_DESCRIPTION_DESCRIPTOR_UUID, 0x1 | (
/* 668 */         writableAuxiliaries ? 16 : 0), 
/* 669 */         (description != null) ? description.getBytes() : null);
/* 670 */     if (!writableAuxiliaries) {
/* 671 */       if (this.sharedDescriptors == null)
/* 672 */         this.sharedDescriptors = new ArrayList<>(); 
/* 673 */       this.sharedDescriptors.add(cud);
/*     */     } 
/* 675 */     return cud;
/*     */   }
/*     */   public BleServerManager(@NonNull Context context) {
/* 678 */     this.gattServerCallback = new BluetoothGattServerCallback()
/*     */       {
/*     */         @SuppressLint({"MissingPermission"})
/*     */         public void onServiceAdded(int status, @NonNull BluetoothGattService service)
/*     */         {
/* 683 */           if (status == 0) {
/*     */             try {
/* 685 */               BluetoothGattService nextService = BleServerManager.this.serverServices.remove();
/* 686 */               BleServerManager.this.server.addService(nextService);
/* 687 */             } catch (Exception e) {
/* 688 */               BleServerManager.this.log(4, "[Server] All services added successfully");
/* 689 */               if (BleServerManager.this.serverObserver != null)
/* 690 */                 BleServerManager.this.serverObserver.onServerReady(); 
/* 691 */               BleServerManager.this.serverServices = null;
/*     */             } 
/*     */           } else {
/* 694 */             BleServerManager.this.log(6, "[Server] Adding service failed with error " + status);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void onConnectionStateChange(@NonNull BluetoothDevice device, int status, int newState) {
/* 700 */           if (status == 0 && newState == 2) {
/* 701 */             BleServerManager.this.log(4, "[Server] " + device.getAddress() + " is now connected");
/* 702 */             if (BleServerManager.this.serverObserver != null)
/* 703 */               BleServerManager.this.serverObserver.onDeviceConnectedToServer(device); 
/*     */           } else {
/* 705 */             if (status == 0) {
/* 706 */               BleServerManager.this.log(4, "[Server] " + device.getAddress() + " is disconnected");
/*     */             } else {
/* 708 */               BleServerManager.this.log(5, "[Server] " + device.getAddress() + " has disconnected connected with status: " + status);
/*     */             } 
/* 710 */             if (BleServerManager.this.serverObserver != null) {
/* 711 */               BleServerManager.this.serverObserver.onDeviceDisconnectedFromServer(device);
/*     */             }
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void onCharacteristicReadRequest(@NonNull BluetoothDevice device, int requestId, int offset, @NonNull BluetoothGattCharacteristic characteristic) {
/* 719 */           BleManagerHandler handler = BleServerManager.this.getRequestHandler(device);
/* 720 */           if (handler != null) {
/* 721 */             handler.onCharacteristicReadRequest(BleServerManager.this.server, device, requestId, offset, characteristic);
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void onCharacteristicWriteRequest(@NonNull BluetoothDevice device, int requestId, @NonNull BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, @NonNull byte[] value) {
/* 730 */           BleManagerHandler handler = BleServerManager.this.getRequestHandler(device);
/* 731 */           if (handler != null) {
/* 732 */             handler.onCharacteristicWriteRequest(BleServerManager.this.server, device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void onDescriptorReadRequest(@NonNull BluetoothDevice device, int requestId, int offset, @NonNull BluetoothGattDescriptor descriptor) {
/* 740 */           BleManagerHandler handler = BleServerManager.this.getRequestHandler(device);
/* 741 */           if (handler != null) {
/* 742 */             handler.onDescriptorReadRequest(BleServerManager.this.server, device, requestId, offset, descriptor);
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void onDescriptorWriteRequest(@NonNull BluetoothDevice device, int requestId, @NonNull BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, @NonNull byte[] value) {
/* 751 */           BleManagerHandler handler = BleServerManager.this.getRequestHandler(device);
/* 752 */           if (handler != null) {
/* 753 */             handler.onDescriptorWriteRequest(BleServerManager.this.server, device, requestId, descriptor, preparedWrite, responseNeeded, offset, value);
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void onExecuteWrite(@NonNull BluetoothDevice device, int requestId, boolean execute) {
/* 761 */           BleManagerHandler handler = BleServerManager.this.getRequestHandler(device);
/* 762 */           if (handler != null) {
/* 763 */             handler.onExecuteWrite(BleServerManager.this.server, device, requestId, execute);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         @RequiresApi(api = 21)
/*     */         public void onNotificationSent(@NonNull BluetoothDevice device, int status) {
/* 770 */           BleManagerHandler handler = BleServerManager.this.getRequestHandler(device);
/* 771 */           if (handler != null) {
/* 772 */             handler.onNotificationSent(BleServerManager.this.server, device, status);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         @RequiresApi(api = 22)
/*     */         public void onMtuChanged(@NonNull BluetoothDevice device, int mtu) {
/* 779 */           BleManagerHandler handler = BleServerManager.this.getRequestHandler(device);
/* 780 */           if (handler != null)
/* 781 */             handler.onMtuChanged(BleServerManager.this.server, device, mtu); 
/*     */         }
/*     */       };
/*     */     this.context = context;
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   protected abstract List<BluetoothGattService> initializeServer();
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\BleServerManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */