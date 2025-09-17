/*     */ package no.nordicsemi.android.support.v18.scanner;
/*     */ 
/*     */ import android.bluetooth.BluetoothAdapter;
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.os.Parcel;
/*     */ import android.os.ParcelUuid;
/*     */ import android.os.Parcelable;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
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
/*     */ public final class ScanFilter
/*     */   implements Parcelable
/*     */ {
/*     */   @Nullable
/*     */   private final String deviceName;
/*     */   @Nullable
/*     */   private final String deviceAddress;
/*     */   @Nullable
/*     */   private final ParcelUuid serviceUuid;
/*     */   @Nullable
/*     */   private final ParcelUuid serviceUuidMask;
/*     */   @Nullable
/*     */   private final ParcelUuid serviceDataUuid;
/*     */   @Nullable
/*     */   private final byte[] serviceData;
/*     */   @Nullable
/*     */   private final byte[] serviceDataMask;
/*     */   private final int manufacturerId;
/*     */   @Nullable
/*     */   private final byte[] manufacturerData;
/*     */   @Nullable
/*     */   private final byte[] manufacturerDataMask;
/*  79 */   private static final ScanFilter EMPTY = (new Builder()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ScanFilter(@Nullable String name, @Nullable String deviceAddress, @Nullable ParcelUuid uuid, @Nullable ParcelUuid uuidMask, @Nullable ParcelUuid serviceDataUuid, @Nullable byte[] serviceData, @Nullable byte[] serviceDataMask, int manufacturerId, @Nullable byte[] manufacturerData, @Nullable byte[] manufacturerDataMask) {
/*  87 */     this.deviceName = name;
/*  88 */     this.serviceUuid = uuid;
/*  89 */     this.serviceUuidMask = uuidMask;
/*  90 */     this.deviceAddress = deviceAddress;
/*  91 */     this.serviceDataUuid = serviceDataUuid;
/*  92 */     this.serviceData = serviceData;
/*  93 */     this.serviceDataMask = serviceDataMask;
/*  94 */     this.manufacturerId = manufacturerId;
/*  95 */     this.manufacturerData = manufacturerData;
/*  96 */     this.manufacturerDataMask = manufacturerDataMask;
/*     */   }
/*     */ 
/*     */   
/*     */   public int describeContents() {
/* 101 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToParcel(Parcel dest, int flags) {
/* 106 */     dest.writeInt((this.deviceName == null) ? 0 : 1);
/* 107 */     if (this.deviceName != null) {
/* 108 */       dest.writeString(this.deviceName);
/*     */     }
/* 110 */     dest.writeInt((this.deviceAddress == null) ? 0 : 1);
/* 111 */     if (this.deviceAddress != null) {
/* 112 */       dest.writeString(this.deviceAddress);
/*     */     }
/* 114 */     dest.writeInt((this.serviceUuid == null) ? 0 : 1);
/* 115 */     if (this.serviceUuid != null) {
/* 116 */       dest.writeParcelable((Parcelable)this.serviceUuid, flags);
/* 117 */       dest.writeInt((this.serviceUuidMask == null) ? 0 : 1);
/* 118 */       if (this.serviceUuidMask != null) {
/* 119 */         dest.writeParcelable((Parcelable)this.serviceUuidMask, flags);
/*     */       }
/*     */     } 
/* 122 */     dest.writeInt((this.serviceDataUuid == null) ? 0 : 1);
/* 123 */     if (this.serviceDataUuid != null) {
/* 124 */       dest.writeParcelable((Parcelable)this.serviceDataUuid, flags);
/* 125 */       dest.writeInt((this.serviceData == null) ? 0 : 1);
/* 126 */       if (this.serviceData != null) {
/* 127 */         dest.writeInt(this.serviceData.length);
/* 128 */         dest.writeByteArray(this.serviceData);
/*     */         
/* 130 */         dest.writeInt((this.serviceDataMask == null) ? 0 : 1);
/* 131 */         if (this.serviceDataMask != null) {
/* 132 */           dest.writeInt(this.serviceDataMask.length);
/* 133 */           dest.writeByteArray(this.serviceDataMask);
/*     */         } 
/*     */       } 
/*     */     } 
/* 137 */     dest.writeInt(this.manufacturerId);
/* 138 */     dest.writeInt((this.manufacturerData == null) ? 0 : 1);
/* 139 */     if (this.manufacturerData != null) {
/* 140 */       dest.writeInt(this.manufacturerData.length);
/* 141 */       dest.writeByteArray(this.manufacturerData);
/*     */       
/* 143 */       dest.writeInt((this.manufacturerDataMask == null) ? 0 : 1);
/* 144 */       if (this.manufacturerDataMask != null) {
/* 145 */         dest.writeInt(this.manufacturerDataMask.length);
/* 146 */         dest.writeByteArray(this.manufacturerDataMask);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   public static final Parcelable.Creator<ScanFilter> CREATOR = new Parcelable.Creator<ScanFilter>()
/*     */     {
/*     */       public ScanFilter[] newArray(int size)
/*     */       {
/* 158 */         return new ScanFilter[size];
/*     */       }
/*     */ 
/*     */       
/*     */       public ScanFilter createFromParcel(Parcel in) {
/* 163 */         ScanFilter.Builder builder = new ScanFilter.Builder();
/* 164 */         if (in.readInt() == 1) {
/* 165 */           builder.setDeviceName(in.readString());
/*     */         }
/* 167 */         if (in.readInt() == 1) {
/* 168 */           builder.setDeviceAddress(in.readString());
/*     */         }
/* 170 */         if (in.readInt() == 1) {
/* 171 */           ParcelUuid uuid = (ParcelUuid)in.readParcelable(ParcelUuid.class.getClassLoader());
/* 172 */           builder.setServiceUuid(uuid);
/* 173 */           if (in.readInt() == 1) {
/* 174 */             ParcelUuid uuidMask = (ParcelUuid)in.readParcelable(ParcelUuid.class
/* 175 */                 .getClassLoader());
/* 176 */             builder.setServiceUuid(uuid, uuidMask);
/*     */           } 
/*     */         } 
/* 179 */         if (in.readInt() == 1) {
/* 180 */           ParcelUuid serviceDataUuid = (ParcelUuid)in.readParcelable(ParcelUuid.class.getClassLoader());
/* 181 */           if (in.readInt() == 1) {
/* 182 */             int serviceDataLength = in.readInt();
/* 183 */             byte[] serviceData = new byte[serviceDataLength];
/* 184 */             in.readByteArray(serviceData);
/* 185 */             if (in.readInt() == 0) {
/*     */               
/* 187 */               builder.setServiceData(serviceDataUuid, serviceData);
/*     */             } else {
/* 189 */               int serviceDataMaskLength = in.readInt();
/* 190 */               byte[] serviceDataMask = new byte[serviceDataMaskLength];
/* 191 */               in.readByteArray(serviceDataMask);
/*     */               
/* 193 */               builder.setServiceData(serviceDataUuid, serviceData, serviceDataMask);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 198 */         int manufacturerId = in.readInt();
/* 199 */         if (in.readInt() == 1) {
/* 200 */           int manufacturerDataLength = in.readInt();
/* 201 */           byte[] manufacturerData = new byte[manufacturerDataLength];
/* 202 */           in.readByteArray(manufacturerData);
/* 203 */           if (in.readInt() == 0) {
/* 204 */             builder.setManufacturerData(manufacturerId, manufacturerData);
/*     */           } else {
/* 206 */             int manufacturerDataMaskLength = in.readInt();
/* 207 */             byte[] manufacturerDataMask = new byte[manufacturerDataMaskLength];
/* 208 */             in.readByteArray(manufacturerDataMask);
/* 209 */             builder.setManufacturerData(manufacturerId, manufacturerData, manufacturerDataMask);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 214 */         return builder.build();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getDeviceName() {
/* 223 */     return this.deviceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ParcelUuid getServiceUuid() {
/* 231 */     return this.serviceUuid;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ParcelUuid getServiceUuidMask() {
/* 236 */     return this.serviceUuidMask;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getDeviceAddress() {
/* 241 */     return this.deviceAddress;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public byte[] getServiceData() {
/* 246 */     return this.serviceData;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public byte[] getServiceDataMask() {
/* 251 */     return this.serviceDataMask;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ParcelUuid getServiceDataUuid() {
/* 256 */     return this.serviceDataUuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getManufacturerId() {
/* 263 */     return this.manufacturerId;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public byte[] getManufacturerData() {
/* 268 */     return this.manufacturerData;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public byte[] getManufacturerDataMask() {
/* 273 */     return this.manufacturerDataMask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(@Nullable ScanResult scanResult) {
/* 281 */     if (scanResult == null) {
/* 282 */       return false;
/*     */     }
/* 284 */     BluetoothDevice device = scanResult.getDevice();
/*     */     
/* 286 */     if (this.deviceAddress != null && !this.deviceAddress.equals(device.getAddress())) {
/* 287 */       return false;
/*     */     }
/*     */     
/* 290 */     ScanRecord scanRecord = scanResult.getScanRecord();
/*     */ 
/*     */     
/* 293 */     if (scanRecord == null && (this.deviceName != null || this.serviceUuid != null || this.manufacturerData != null || this.serviceData != null))
/*     */     {
/*     */       
/* 296 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 300 */     if (this.deviceName != null && !this.deviceName.equals(scanRecord.getDeviceName())) {
/* 301 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 305 */     if (this.serviceUuid != null && !matchesServiceUuids(this.serviceUuid, this.serviceUuidMask, scanRecord
/* 306 */         .getServiceUuids())) {
/* 307 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 311 */     if (this.serviceDataUuid != null && scanRecord != null && 
/* 312 */       !matchesPartialData(this.serviceData, this.serviceDataMask, scanRecord
/* 313 */         .getServiceData(this.serviceDataUuid))) {
/* 314 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 319 */     if (this.manufacturerId >= 0 && scanRecord != null)
/*     */     {
/* 321 */       if (!matchesPartialData(this.manufacturerData, this.manufacturerDataMask, scanRecord
/* 322 */           .getManufacturerSpecificData(this.manufacturerId))) {
/* 323 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 327 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean matchesServiceUuids(@Nullable ParcelUuid uuid, @Nullable ParcelUuid parcelUuidMask, @Nullable List<ParcelUuid> uuids) {
/* 336 */     if (uuid == null) {
/* 337 */       return true;
/*     */     }
/* 339 */     if (uuids == null) {
/* 340 */       return false;
/*     */     }
/*     */     
/* 343 */     for (ParcelUuid parcelUuid : uuids) {
/* 344 */       UUID uuidMask = (parcelUuidMask == null) ? null : parcelUuidMask.getUuid();
/* 345 */       if (matchesServiceUuid(uuid.getUuid(), uuidMask, parcelUuid.getUuid())) {
/* 346 */         return true;
/*     */       }
/*     */     } 
/* 349 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean matchesServiceUuid(@NonNull UUID uuid, @Nullable UUID mask, @NonNull UUID data) {
/* 356 */     if (mask == null) {
/* 357 */       return uuid.equals(data);
/*     */     }
/* 359 */     if ((uuid.getLeastSignificantBits() & mask.getLeastSignificantBits()) != (data
/* 360 */       .getLeastSignificantBits() & mask.getLeastSignificantBits())) {
/* 361 */       return false;
/*     */     }
/* 363 */     return 
/* 364 */       ((uuid.getMostSignificantBits() & mask.getMostSignificantBits()) == (data.getMostSignificantBits() & mask.getMostSignificantBits()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesPartialData(@Nullable byte[] data, @Nullable byte[] dataMask, @Nullable byte[] parsedData) {
/* 372 */     if (data == null)
/*     */     {
/*     */       
/* 375 */       return (parsedData != null);
/*     */     }
/* 377 */     if (parsedData == null || parsedData.length < data.length) {
/* 378 */       return false;
/*     */     }
/* 380 */     if (dataMask == null) {
/* 381 */       for (int j = 0; j < data.length; j++) {
/* 382 */         if (parsedData[j] != data[j]) {
/* 383 */           return false;
/*     */         }
/*     */       } 
/* 386 */       return true;
/*     */     } 
/* 388 */     for (int i = 0; i < data.length; i++) {
/* 389 */       if ((dataMask[i] & parsedData[i]) != (dataMask[i] & data[i])) {
/* 390 */         return false;
/*     */       }
/*     */     } 
/* 393 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 398 */     return "BluetoothLeScanFilter [deviceName=" + this.deviceName + ", deviceAddress=" + this.deviceAddress + ", mUuid=" + this.serviceUuid + ", uuidMask=" + this.serviceUuidMask + ", serviceDataUuid=" + 
/*     */ 
/*     */       
/* 401 */       Objects.toString(this.serviceDataUuid) + ", serviceData=" + 
/* 402 */       Arrays.toString(this.serviceData) + ", serviceDataMask=" + 
/* 403 */       Arrays.toString(this.serviceDataMask) + ", manufacturerId=" + this.manufacturerId + ", manufacturerData=" + 
/* 404 */       Arrays.toString(this.manufacturerData) + ", manufacturerDataMask=" + 
/* 405 */       Arrays.toString(this.manufacturerDataMask) + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 410 */     return Objects.hash(new Object[] { this.deviceName, this.deviceAddress, Integer.valueOf(this.manufacturerId), 
/* 411 */           Integer.valueOf(Arrays.hashCode(this.manufacturerData)), 
/* 412 */           Integer.valueOf(Arrays.hashCode(this.manufacturerDataMask)), this.serviceDataUuid, 
/*     */           
/* 414 */           Integer.valueOf(Arrays.hashCode(this.serviceData)), 
/* 415 */           Integer.valueOf(Arrays.hashCode(this.serviceDataMask)), this.serviceUuid, this.serviceUuidMask });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 421 */     if (this == obj) {
/* 422 */       return true;
/*     */     }
/* 424 */     if (obj == null || getClass() != obj.getClass()) {
/* 425 */       return false;
/*     */     }
/* 427 */     ScanFilter other = (ScanFilter)obj;
/* 428 */     return (Objects.equals(this.deviceName, other.deviceName) && 
/* 429 */       Objects.equals(this.deviceAddress, other.deviceAddress) && this.manufacturerId == other.manufacturerId && 
/*     */       
/* 431 */       Objects.deepEquals(this.manufacturerData, other.manufacturerData) && 
/* 432 */       Objects.deepEquals(this.manufacturerDataMask, other.manufacturerDataMask) && 
/* 433 */       Objects.equals(this.serviceDataUuid, other.serviceDataUuid) && 
/* 434 */       Objects.deepEquals(this.serviceData, other.serviceData) && 
/* 435 */       Objects.deepEquals(this.serviceDataMask, other.serviceDataMask) && 
/* 436 */       Objects.equals(this.serviceUuid, other.serviceUuid) && 
/* 437 */       Objects.equals(this.serviceUuidMask, other.serviceUuidMask));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isAllFieldsEmpty() {
/* 445 */     return EMPTY.equals(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private String deviceName;
/*     */     
/*     */     private String deviceAddress;
/*     */     
/*     */     private ParcelUuid serviceUuid;
/*     */     
/*     */     private ParcelUuid uuidMask;
/*     */     
/*     */     private ParcelUuid serviceDataUuid;
/*     */     
/*     */     private byte[] serviceData;
/*     */     private byte[] serviceDataMask;
/* 463 */     private int manufacturerId = -1;
/*     */     
/*     */     private byte[] manufacturerData;
/*     */     
/*     */     private byte[] manufacturerDataMask;
/*     */ 
/*     */     
/*     */     public Builder setDeviceName(@Nullable String deviceName) {
/* 471 */       this.deviceName = deviceName;
/* 472 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setDeviceAddress(@Nullable String deviceAddress) {
/* 484 */       if (deviceAddress != null && !BluetoothAdapter.checkBluetoothAddress(deviceAddress)) {
/* 485 */         throw new IllegalArgumentException("invalid device address " + deviceAddress);
/*     */       }
/* 487 */       this.deviceAddress = deviceAddress;
/* 488 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setServiceUuid(@Nullable ParcelUuid serviceUuid) {
/* 495 */       this.serviceUuid = serviceUuid;
/* 496 */       this.uuidMask = null;
/* 497 */       return this;
/*     */     }
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
/*     */     public Builder setServiceUuid(@Nullable ParcelUuid serviceUuid, @Nullable ParcelUuid uuidMask) {
/* 510 */       if (uuidMask != null && serviceUuid == null) {
/* 511 */         throw new IllegalArgumentException("uuid is null while uuidMask is not null!");
/*     */       }
/* 513 */       this.serviceUuid = serviceUuid;
/* 514 */       this.uuidMask = uuidMask;
/* 515 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setServiceData(@NonNull ParcelUuid serviceDataUuid, @Nullable byte[] serviceData) {
/* 526 */       if (serviceDataUuid == null) {
/* 527 */         throw new IllegalArgumentException("serviceDataUuid is null!");
/*     */       }
/* 529 */       this.serviceDataUuid = serviceDataUuid;
/* 530 */       this.serviceData = serviceData;
/* 531 */       this.serviceDataMask = null;
/* 532 */       return this;
/*     */     }
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
/*     */     public Builder setServiceData(@NonNull ParcelUuid serviceDataUuid, @Nullable byte[] serviceData, @Nullable byte[] serviceDataMask) {
/* 549 */       if (serviceDataUuid == null) {
/* 550 */         throw new IllegalArgumentException("serviceDataUuid is null");
/*     */       }
/* 552 */       if (serviceDataMask != null) {
/* 553 */         if (serviceData == null) {
/* 554 */           throw new IllegalArgumentException("serviceData is null while serviceDataMask is not null");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 559 */         if (serviceData.length != serviceDataMask.length) {
/* 560 */           throw new IllegalArgumentException("size mismatch for service data and service data mask");
/*     */         }
/*     */       } 
/*     */       
/* 564 */       this.serviceDataUuid = serviceDataUuid;
/* 565 */       this.serviceData = serviceData;
/* 566 */       this.serviceDataMask = serviceDataMask;
/* 567 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setManufacturerData(int manufacturerId, @Nullable byte[] manufacturerData) {
/* 579 */       if (manufacturerData != null && manufacturerId < 0) {
/* 580 */         throw new IllegalArgumentException("invalid manufacture id");
/*     */       }
/* 582 */       this.manufacturerId = manufacturerId;
/* 583 */       this.manufacturerData = manufacturerData;
/* 584 */       this.manufacturerDataMask = null;
/* 585 */       return this;
/*     */     }
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
/*     */     public Builder setManufacturerData(int manufacturerId, @Nullable byte[] manufacturerData, @Nullable byte[] manufacturerDataMask) {
/* 602 */       if (manufacturerData != null && manufacturerId < 0) {
/* 603 */         throw new IllegalArgumentException("invalid manufacture id");
/*     */       }
/* 605 */       if (manufacturerDataMask != null) {
/* 606 */         if (manufacturerData == null) {
/* 607 */           throw new IllegalArgumentException("manufacturerData is null while manufacturerDataMask is not null");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 612 */         if (manufacturerData.length != manufacturerDataMask.length) {
/* 613 */           throw new IllegalArgumentException("size mismatch for manufacturerData and manufacturerDataMask");
/*     */         }
/*     */       } 
/*     */       
/* 617 */       this.manufacturerId = manufacturerId;
/* 618 */       this.manufacturerData = manufacturerData;
/* 619 */       this.manufacturerDataMask = manufacturerDataMask;
/* 620 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ScanFilter build() {
/* 629 */       return new ScanFilter(this.deviceName, this.deviceAddress, this.serviceUuid, this.uuidMask, this.serviceDataUuid, this.serviceData, this.serviceDataMask, this.manufacturerId, this.manufacturerData, this.manufacturerDataMask);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\ScanFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */