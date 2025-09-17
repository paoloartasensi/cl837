/*     */ package com.android.chileaf.fitness.callback;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.os.Parcel;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.android.chileaf.model.HistoryOf3D;
/*     */ import com.android.chileaf.model.HistoryOfHeartRate;
/*     */ import com.android.chileaf.model.HistoryOfRecord;
/*     */ import com.android.chileaf.model.HistoryOfRespiratoryRate;
/*     */ import com.android.chileaf.model.HistoryOfSport;
/*     */ import com.android.chileaf.model.HistorySleep;
/*     */ import com.android.chileaf.model.IntervalStep;
/*     */ import com.android.chileaf.util.DateUtil;
/*     */ import com.android.chileaf.util.HexUtil;
/*     */ import com.android.chileaf.util.LogUtil;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import no.nordicsemi.android.ble.callback.profile.ProfileReadResponse;
/*     */ import no.nordicsemi.android.ble.data.Data;
/*     */ import no.nordicsemi.android.ble.utils.ParserUtils;
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
/*     */ public abstract class WearReceivedDataCallback
/*     */   extends ProfileReadResponse
/*     */   implements UserInfoCallback, BodySportCallback, BluetoothStatusCallback, HistoryOfSportCallback, HistoryOfHRRecordCallback, HistoryOfHRDataCallback, HistoryOfRRRecordCallback, HistoryOfRRDataCallback, IntervalStepCallback, SingleTapRecordCallback, HeartRateStatusCallback, BloodOxygenCallback, TemperatureCallback, HistoryOfSingleRecordCallback, HeartRateAlarmCallback, AccelerometerCallback, HeartRateMaxCallback, HistoryOfSleepCallback, Sensor3DFrequencyCallback, Sensor3DStatusCallback, HistoryOf3DDataCallback, BodyHealthCallback, Sensor6DFrequencyCallback, Sensor6DRawDataCallback, BodySportHealthCallback
/*     */ {
/*     */   private static final long END_TAG = 4294967295L;
/*  51 */   private long mStamp = 0L;
/*     */   private boolean isCL833 = false;
/*     */   private boolean isStamp = false;
/*  54 */   private List<Data> mPackages = new ArrayList<>();
/*     */   
/*     */   private List<HistoryOfSport> mHistoryOfSports;
/*     */   
/*     */   private List<HistoryOfRecord> mHistoryOfRecords;
/*     */   
/*     */   private List<HistoryOfHeartRate> mHistoryOfHeartRates;
/*     */   
/*     */   private List<HistoryOfRecord> mRespiratoryRatesRecords;
/*     */   
/*     */   private List<HistoryOfRespiratoryRate> mHistoryOfRespiratoryRates;
/*     */   
/*     */   private List<IntervalStep> mIntervalSteps;
/*     */   
/*     */   private List<HistoryOfRecord> mSingleTapRecords;
/*     */   
/*     */   private List<HistorySleep> mHistoryOfSleeps;
/*     */   public static final int TYPE_SPORT = 2;
/*     */   public static final int TYPE_HEART = 4;
/*     */   public static final int TYPE_HEARTS = 6;
/*     */   public static final int TYPE_HEART_RR = 8;
/*     */   public static final int TYPE_HEART_RRS = 16;
/*     */   public static final int TYPE_INTERVAL = 18;
/*     */   public static final int TYPE_SINGLE_TAP = 20;
/*     */   public static final int TYPE_SLEEP = 22;
/*     */   public static final int TYPE_HISTORY_3D = 24;
/*     */   
/*     */   public WearReceivedDataCallback() {}
/*     */   
/*     */   protected WearReceivedDataCallback(Parcel in) {
/*  84 */     super(in);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {
/*  89 */     super.onDataReceived(device, data);
/*  90 */     boolean validate = (data.getIntValue(17, 1).intValue() == data.size());
/*  91 */     if (!validate) {
/*  92 */       onInvalidDataReceived(device, data);
/*  93 */       LogUtil.w("onDataReceived:length:%s", new Object[] { Integer.valueOf(data.size()) });
/*     */     } 
/*     */     
/*  96 */     byte[] value = data.getValue();
/*  97 */     int mode = data.getIntValue(17, 2).intValue();
/*     */     try {
/*  99 */       if (mode == 3) {
/* 100 */         int age = getIntParse(value, 5, 1);
/* 101 */         int sex = getIntParse(value, 6, 1);
/* 102 */         int weight = getIntParse(value, 7, 1);
/* 103 */         int height = getIntParse(value, 8, 1);
/* 104 */         long mobile = getLongParse(value, 9, 5);
/* 105 */         onUserInfoReceived(device, age, sex, weight, height, mobile);
/* 106 */       } else if (mode == 5) {
/* 107 */         int cmd = getIntParse(value, 3, 1);
/* 108 */         if (cmd == 3) {
/* 109 */           if (this.mHistoryOfSleeps == null) {
/* 110 */             this.mHistoryOfSleeps = new ArrayList<>();
/*     */           }
/* 112 */           for (int j = 4; j < value.length; j++) {
/* 113 */             int len = value[j];
/* 114 */             if (len >= 1)
/* 115 */             { j++;
/* 116 */               long utc = getLongParse(value, j, 4);
/* 117 */               j += 4;
/* 118 */               utc *= 1000L;
/* 119 */               utc -= 28800000L;
/* 120 */               int[] actions = new int[len];
/* 121 */               for (int i = 0; i < len; i++) {
/* 122 */                 actions[i] = getIntParse(value, i + j, 1);
/*     */               }
/* 124 */               j += len - 1;
/* 125 */               HistorySleep historySleep = new HistorySleep(utc, actions);
/* 126 */               this.mHistoryOfSleeps.add(historySleep);
/* 127 */               if (j == value.length - 2)
/*     */                 break;  } 
/* 129 */           }  onHistoryOfSleepReceived(device, this.mHistoryOfSleeps);
/* 130 */           this.mHistoryOfSleeps.clear();
/*     */         } 
/* 132 */       } else if (mode == 12) {
/*     */         
/* 134 */         byte[] slice = subSlice(3, value);
/* 135 */         for (int i = 0; i < slice.length / 6; i++) {
/* 136 */           int offset = i * 6;
/* 137 */           int x = data.getIntValue(34, offset + 3).intValue();
/* 138 */           int y = data.getIntValue(34, offset + 5).intValue();
/* 139 */           int z = data.getIntValue(34, offset + 7).intValue();
/* 140 */           onAccelerometerReceived(device, x, y, z);
/*     */         } 
/* 142 */       } else if (mode != 18) {
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
/* 158 */         if (mode == 19) {
/* 159 */           int vo2Max = getIntParse(value, 3, 1);
/* 160 */           int breathRate = getIntParse(value, 4, 1);
/* 161 */           int emotion = getIntParse(value, 5, 1);
/* 162 */           int pressure = getIntParse(value, 6, 1);
/* 163 */           int stamina = getIntParse(value, 7, 1);
/* 164 */           onSportHealthReceived(device, vo2Max, breathRate, emotion, pressure, stamina);
/* 165 */         } else if (mode == 21) {
/* 166 */           int step = getIntParse(value, 3, 3);
/* 167 */           int distance = getIntParse(value, 6, 3);
/* 168 */           int calorie = getIntParse(value, 9, 3);
/* 169 */           onSportReceived(device, step, distance, calorie);
/* 170 */         } else if (mode == 63) {
/* 171 */           int status = getIntParse(value, 3, 1);
/* 172 */           onBluetoothStatusReceived(device, (status == 1));
/* 173 */         } else if (mode == 22) {
/* 174 */           if (this.mHistoryOfSports == null) {
/* 175 */             this.mHistoryOfSports = new ArrayList<>();
/*     */           }
/* 177 */           long utcTag = getLongParse(value, 3, 4);
/* 178 */           if (this.isCL833) {
/* 179 */             byte[] slice = subSlice(3, value);
/* 180 */             parseSportHistory(slice);
/* 181 */             Collections.reverse(this.mHistoryOfSports);
/* 182 */             onHistoryOfSportReceived(device, this.mHistoryOfSports);
/* 183 */             this.mHistoryOfSports.clear();
/*     */           }
/* 185 */           else if (utcTag != 4294967295L) {
/* 186 */             this.mPackages.add(data);
/*     */           } else {
/*     */             
/* 189 */             for (int index = 0; index < this.mPackages.size(); index++) {
/* 190 */               byte[] slice = subSlice(3, ((Data)this.mPackages.get(index)).getValue());
/* 191 */               parseSportHistory(slice);
/* 192 */               LogUtil.d("HistoryOfSport index:%d values:%s", new Object[] { Integer.valueOf(index), ParserUtils.parse(slice) });
/*     */             } 
/* 194 */             onHistoryOfSportReceived(device, this.mHistoryOfSports);
/* 195 */             this.mHistoryOfSports.clear();
/* 196 */             this.mPackages.clear();
/*     */           }
/*     */         
/* 199 */         } else if (mode == 33) {
/* 200 */           if (this.mHistoryOfRecords == null) {
/* 201 */             this.mHistoryOfRecords = new ArrayList<>();
/*     */           }
/* 203 */           long utcTag = getLongParse(value, 3, 4);
/* 204 */           if (utcTag != 4294967295L) {
/* 205 */             this.mPackages.add(data);
/*     */           }
/*     */           else {
/*     */             
/* 209 */             for (int index = 0; index < this.mPackages.size(); index++) {
/* 210 */               byte[] slice = subSlice(3, ((Data)this.mPackages.get(index)).getValue());
/* 211 */               LogUtil.d("mHistoryOfRecords index:%d values:%s", new Object[] { Integer.valueOf(index), ParserUtils.parse(slice) });
/* 212 */               for (int i = 0; i < slice.length / 4; i++) {
/* 213 */                 int offset = i * 4;
/* 214 */                 long stamp = getLongParse(slice, offset, 4);
/* 215 */                 long record = DateUtil.restoreZoneUTC(stamp);
/* 216 */                 this.mHistoryOfRecords.add(new HistoryOfRecord(stamp, record));
/* 217 */                 LogUtil.d("mHistoryOfRecords index:%d record:%s", new Object[] { Integer.valueOf(i), Long.valueOf(stamp) });
/*     */               } 
/*     */             } 
/* 220 */             onHistoryOfHRRecordReceived(device, this.mHistoryOfRecords);
/* 221 */             this.mHistoryOfRecords.clear();
/* 222 */             this.mPackages.clear();
/*     */           } 
/* 224 */         } else if (mode == 34 || mode == 35) {
/* 225 */           if (this.mHistoryOfHeartRates == null) {
/* 226 */             this.mHistoryOfHeartRates = new ArrayList<>();
/*     */           }
/* 228 */           if (mode == 34) {
/* 229 */             if (!this.isStamp) {
/* 230 */               this.mStamp = getLongParse(value, 3, 4);
/* 231 */               this.isStamp = true;
/*     */             } 
/* 233 */             this.mPackages.add(data);
/*     */           } 
/* 235 */           if (mode == 35) {
/*     */ 
/*     */             
/* 238 */             for (int index = 0; index < this.mPackages.size(); index++) {
/* 239 */               byte[] slice = subSlice(3, ((Data)this.mPackages.get(index)).getValue());
/* 240 */               LogUtil.d("mHistoryOfHeartRates index:%d values:%s", new Object[] { Integer.valueOf(index), ParserUtils.parse(slice) });
/* 241 */               for (int i = 4; i < slice.length; i++) {
/* 242 */                 int heart = getIntParse(slice, i, 1);
/* 243 */                 long stamp = DateUtil.restoreZoneUTC(this.mStamp);
/* 244 */                 HistoryOfHeartRate heartRate = new HistoryOfHeartRate(stamp, heart);
/* 245 */                 this.mHistoryOfHeartRates.add(heartRate);
/* 246 */                 this.mStamp++;
/*     */               } 
/*     */             } 
/* 249 */             onHistoryOfHRDataReceived(device, this.mHistoryOfHeartRates);
/* 250 */             this.mHistoryOfHeartRates.clear();
/* 251 */             this.mPackages.clear();
/* 252 */             this.isStamp = false;
/* 253 */             this.mStamp = 0L;
/*     */           } 
/* 255 */         } else if (mode == 36) {
/* 256 */           if (this.mRespiratoryRatesRecords == null) {
/* 257 */             this.mRespiratoryRatesRecords = new ArrayList<>();
/*     */           }
/* 259 */           long utcTag = getLongParse(value, 3, 4);
/* 260 */           if (utcTag != 4294967295L) {
/* 261 */             this.mPackages.add(data);
/*     */           } else {
/*     */             
/* 264 */             int length = 4;
/*     */             
/* 266 */             for (int index = 0; index < this.mPackages.size(); index++) {
/* 267 */               byte[] slice = subSlice(3, ((Data)this.mPackages.get(index)).getValue());
/* 268 */               LogUtil.d("mRespiratoryRatesRecords index:%d values:%s", new Object[] { Integer.valueOf(index), ParserUtils.parse(slice) });
/* 269 */               for (int i = 0; i < slice.length / length; i++) {
/* 270 */                 int offset = i * length;
/* 271 */                 long stamp = getLongParse(slice, offset, 4);
/* 272 */                 long record = DateUtil.restoreZoneUTC(stamp);
/* 273 */                 HistoryOfRecord historyRecord = new HistoryOfRecord(stamp, record);
/* 274 */                 this.mRespiratoryRatesRecords.add(historyRecord);
/*     */               } 
/*     */             } 
/* 277 */             onHistoryOfRRRecordReceived(device, this.mRespiratoryRatesRecords);
/* 278 */             LogUtil.d("onHistoryOfRRRecordReceived size:%d", new Object[] { Integer.valueOf(this.mRespiratoryRatesRecords.size()) });
/* 279 */             this.mRespiratoryRatesRecords.clear();
/* 280 */             this.mPackages.clear();
/*     */           } 
/* 282 */         } else if (mode == 37 || mode == 38) {
/* 283 */           if (this.mHistoryOfRespiratoryRates == null) {
/* 284 */             this.mHistoryOfRespiratoryRates = new ArrayList<>();
/*     */           }
/* 286 */           if (mode == 37) {
/* 287 */             if (!this.isStamp) {
/* 288 */               this.mStamp = getLongParse(value, 3, 4);
/* 289 */               this.isStamp = true;
/*     */             } 
/* 291 */             this.mPackages.add(data);
/*     */           } 
/* 293 */           if (mode == 38) {
/*     */             
/* 295 */             int length = 2;
/*     */             
/* 297 */             for (int index = 0; index < this.mPackages.size(); index++) {
/* 298 */               byte[] slice = subSlice(7, ((Data)this.mPackages.get(index)).getValue());
/* 299 */               LogUtil.d("index:%d HistoryOfRespiratoryRates mValues:%s", new Object[] { Integer.valueOf(index), ParserUtils.parse(slice) });
/* 300 */               for (int i = 0; i < slice.length / length; i++) {
/* 301 */                 int offset = i * length;
/* 302 */                 int respiratory = getIntParse(slice, offset, 2);
/* 303 */                 long stamp = DateUtil.restoreZoneUTC(this.mStamp);
/* 304 */                 HistoryOfRespiratoryRate respiratoryRate = new HistoryOfRespiratoryRate(stamp, respiratory);
/* 305 */                 this.mHistoryOfRespiratoryRates.add(respiratoryRate);
/* 306 */                 this.mStamp++;
/*     */               } 
/*     */             } 
/* 309 */             LogUtil.d("onHistoryOfRRDataReceived :%s", new Object[] { this.mHistoryOfRespiratoryRates.toString() });
/* 310 */             onHistoryOfRRDataReceived(device, this.mHistoryOfRespiratoryRates);
/* 311 */             this.mHistoryOfRespiratoryRates.clear();
/* 312 */             this.mPackages.clear();
/* 313 */             this.isStamp = false;
/* 314 */             this.mStamp = 0L;
/*     */           } 
/* 316 */         } else if (mode == 55) {
/* 317 */           if (value[1] <= 6) {
/*     */             return;
/*     */           }
/* 320 */           int bSwitch = getIntParse(value, 3, 1);
/* 321 */           if (value[1] <= 8) {
/*     */             return;
/*     */           }
/* 324 */           int blValue = getIntParse(value, 4, 1);
/* 325 */           int gesture = getIntParse(value, 5, 1);
/* 326 */           int piValue = getIntParse(value, 6, 1);
/* 327 */           int onwrist = getIntParse(value, 7, 1);
/* 328 */           onBloodOxygenReceived(device, bSwitch, String.valueOf(blValue), gesture, piValue, onwrist);
/* 329 */         } else if (mode == 56) {
/* 330 */           float BadSituation = getIntParse(value, 3, 2) / 10.0F;
/* 331 */           float wrist = getIntParse(value, 5, 2) / 10.0F;
/* 332 */           float body = getIntParse(value, 7, 2) / 10.0F;
/* 333 */           onTemperatureReceived(device, BadSituation, wrist, body);
/* 334 */         } else if (mode == 64 || mode == 65) {
/* 335 */           if (this.mIntervalSteps == null) {
/* 336 */             this.mIntervalSteps = new ArrayList<>();
/*     */           }
/* 338 */           if (mode == 64) {
/* 339 */             this.mPackages.add(data);
/*     */           }
/* 341 */           if (mode == 65) {
/*     */ 
/*     */ 
/*     */             
/* 345 */             for (int index = 0; index < this.mPackages.size(); index++) {
/* 346 */               byte[] slice = subSlice(3, ((Data)this.mPackages.get(index)).getValue());
/* 347 */               LogUtil.d("mIntervalSteps index:%d values:%s", new Object[] { Integer.valueOf(index), ParserUtils.parse(slice) });
/* 348 */               for (int i = 0; i < slice.length / 8; i++) {
/* 349 */                 int offset = i * 8;
/* 350 */                 long stamp = DateUtil.restoreZoneUTC(getLongParse(slice, offset, 4));
/* 351 */                 int step = getIntParse(slice, offset + 4, 4);
/* 352 */                 IntervalStep intervalStep = new IntervalStep(stamp, step);
/* 353 */                 this.mIntervalSteps.add(intervalStep);
/*     */               } 
/*     */             } 
/* 356 */             onIntervalStepReceived(device, this.mIntervalSteps);
/* 357 */             this.mIntervalSteps.clear();
/* 358 */             this.mPackages.clear();
/*     */           } 
/* 360 */         } else if (mode == 66 || mode == 67) {
/* 361 */           if (this.mSingleTapRecords == null) {
/* 362 */             this.mSingleTapRecords = new ArrayList<>();
/*     */           }
/* 364 */           if (mode == 66) {
/* 365 */             this.mPackages.add(data);
/*     */           }
/* 367 */           if (mode == 67) {
/*     */             
/* 369 */             for (int index = 0; index < this.mPackages.size(); index++) {
/*     */               
/* 371 */               byte[] slice = subSlice(3, ((Data)this.mPackages.get(index)).getValue());
/* 372 */               LogUtil.d("mSingleTapRecords index:%d values:%s", new Object[] { Integer.valueOf(index), ParserUtils.parse(slice) });
/* 373 */               for (int i = 0; i < slice.length / 4; i++) {
/* 374 */                 int offset = i * 4;
/* 375 */                 long stamp = getLongParse(slice, offset, 4);
/* 376 */                 long record = DateUtil.restoreZoneUTC(stamp);
/* 377 */                 this.mSingleTapRecords.add(new HistoryOfRecord(stamp, record));
/*     */               } 
/*     */             } 
/* 380 */             onSingleTapRecordReceived(device, this.mSingleTapRecords);
/* 381 */             this.mSingleTapRecords.clear();
/* 382 */             this.mPackages.clear();
/*     */           } 
/* 384 */         } else if (mode == 70) {
/*     */ 
/*     */           
/* 387 */           int min = getIntParse(value, 4, 1);
/* 388 */           int max = getIntParse(value, 5, 1);
/* 389 */           int goal = getIntParse(value, 6, 1);
/* 390 */           onHeartRateStatusReceived(device, min, max, goal);
/*     */         }
/* 392 */         else if (mode == 73) {
/* 393 */           long stamp = DateUtil.restoreZoneUTC(getLongParse(value, 3, 4));
/* 394 */           long step = getLongParse(value, 7, 3);
/* 395 */           long distance = getLongParse(value, 10, 3);
/* 396 */           long calorie = getLongParse(value, 13, 3);
/* 397 */           onHistorySingleRecordReceived(device, stamp, step, distance, calorie);
/* 398 */         } else if (mode == 91) {
/* 399 */           long stamp = DateUtil.restoreZoneUTC(getLongParse(value, 3, 4));
/* 400 */           int enabled = getIntParse(value, 7, 1);
/* 401 */           onHeartRateAlarmReceived(device, stamp, (enabled == 1));
/* 402 */         } else if (mode == 96) {
/* 403 */           int sequence = getIntParse(value, 3, 1);
/* 404 */           parseSensorRawList(device, sequence, new Data(subSlice(4, value)));
/* 405 */         } else if (mode == 97) {
/*     */           
/* 407 */           int sensor = getIntParse(value, 3, 1);
/* 408 */           onSensor6DFrequencyReceived(device, sensor);
/* 409 */         } else if (mode == 100) {
/* 410 */           long stamp = getLongParse(value, 3, 4);
/* 411 */           int mills = getIntParse(value, 7, 2);
/* 412 */           long utc = DateUtil.restoreZoneUTCTimeInMillis(stamp * 1000L + mills);
/* 413 */           int sequence = getIntParse(value, 9, 1);
/* 414 */           parseSensorRawList(device, utc, sequence, data);
/* 415 */         } else if (mode == 117) {
/* 416 */           int cmd = getIntParse(value, 4, 1);
/* 417 */           if (cmd == 6) {
/* 418 */             int max = getIntParse(value, 5, 1);
/* 419 */             onHeartRateMaxReceived(device, max);
/* 420 */           } else if (cmd == 11) {
/* 421 */             int frequency = getIntParse(value, 5, 1);
/* 422 */             onSensor3DFrequencyReceived(device, frequency);
/* 423 */           } else if (cmd == 12) {
/* 424 */             boolean enabled = (getIntParse(value, 5, 1) == 1);
/* 425 */             onSensor3DStatusReceived(device, enabled);
/* 426 */           } else if (cmd == 15) {
/* 427 */             int vo2Max = getIntParse(value, 5, 1);
/* 428 */             int breathRate = getIntParse(value, 6, 1);
/* 429 */             int emotionLevel = getIntParse(value, 7, 1);
/* 430 */             int stressPercent = getIntParse(value, 8, 1);
/* 431 */             int stamina = getIntParse(value, 9, 1);
/* 432 */             float tp = (float)getLongParse(value, 10, 4) / 1000.0F;
/* 433 */             float lf = (float)getLongParse(value, 14, 4) / 1000.0F;
/* 434 */             float hf = (float)getLongParse(value, 18, 4) / 1000.0F;
/* 435 */             onHealthReceived(device, vo2Max, breathRate, emotionLevel, stressPercent, stamina, tp, lf, hf);
/*     */           } 
/* 437 */         } else if (mode == 119 || mode == 120) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 444 */           if (mode == 119 || mode == 120)
/*     */           {
/* 446 */             int length = 6;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 451 */             byte[] slice = subSlice(3, data.getValue());
/* 452 */             for (int i = 0; i < slice.length / length; i++) {
/* 453 */               int offset = i * length;
/*     */               
/* 455 */               int accelerometerX = getSInt16(slice, offset);
/* 456 */               int accelerometerY = getSInt16(slice, offset + 2);
/* 457 */               int accelerometerZ = getSInt16(slice, offset + 4);
/*     */               
/* 459 */               HistoryOf3D history = new HistoryOf3D(accelerometerX, accelerometerY, accelerometerZ);
/* 460 */               onHistoryOf3DDataReceived(device, history, (mode == 120));
/*     */             
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 471 */     catch (Exception e) {
/* 472 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setCL833(boolean isCL833) {
/* 477 */     this.isCL833 = isCL833;
/*     */   }
/*     */   
/*     */   private int getSInt16(byte[] data, int offset) {
/* 481 */     return unsignedToSigned(unsignedBytesToInt(data[offset], data[offset + 1]), 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int unsignedByteToInt(byte b) {
/* 488 */     return b & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int unsignedBytesToInt(byte b0, byte b1) {
/* 495 */     return unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int unsignedToSigned(int unsigned, int size) {
/* 503 */     if ((unsigned & 1 << size - 1) != 0) {
/* 504 */       unsigned = -1 * ((1 << size - 1) - (unsigned & (1 << size - 1) - 1));
/*     */     }
/* 506 */     return unsigned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearType(int type) {
/* 515 */     switch (type) {
/*     */       case 2:
/* 517 */         if (this.mHistoryOfSports != null) {
/* 518 */           this.mHistoryOfSports.clear();
/*     */         }
/*     */         break;
/*     */       case 4:
/* 522 */         if (this.mHistoryOfRecords != null) {
/* 523 */           this.mHistoryOfRecords.clear();
/*     */         }
/*     */         break;
/*     */       case 6:
/* 527 */         if (this.mHistoryOfHeartRates != null) {
/* 528 */           this.mHistoryOfHeartRates.clear();
/*     */         }
/*     */         break;
/*     */       case 8:
/* 532 */         if (this.mRespiratoryRatesRecords != null) {
/* 533 */           this.mRespiratoryRatesRecords.clear();
/*     */         }
/*     */         break;
/*     */       case 16:
/* 537 */         if (this.mHistoryOfRespiratoryRates != null) {
/* 538 */           this.mHistoryOfRespiratoryRates.clear();
/*     */         }
/*     */         break;
/*     */       case 18:
/* 542 */         if (this.mIntervalSteps != null) {
/* 543 */           this.mIntervalSteps.clear();
/*     */         }
/*     */         break;
/*     */       case 20:
/* 547 */         if (this.mSingleTapRecords != null) {
/* 548 */           this.mSingleTapRecords.clear();
/*     */         }
/*     */         break;
/*     */       case 22:
/* 552 */         if (this.mHistoryOfSleeps != null) {
/* 553 */           this.mHistoryOfSleeps.clear();
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 564 */     this.mPackages.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseSportHistory(byte[] value) {
/* 572 */     LogUtil.d("HistoryOfSport length:%d values:%s", new Object[] { Integer.valueOf(value.length), ParserUtils.parse(value) });
/* 573 */     for (int i = 0; i < value.length / 10; i++) {
/* 574 */       int offset = i * 10;
/* 575 */       long stamp = getLongParse(value, offset, 4);
/* 576 */       long step = getLongParse(value, offset + 4, 3);
/* 577 */       long calorie = getLongParse(value, offset + 7, 3);
/* 578 */       stamp = DateUtil.restoreZoneUTC(stamp);
/* 579 */       this.mHistoryOfSports.add(new HistoryOfSport(stamp, step, calorie));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void parseSensorRawList(BluetoothDevice device, int sequence, Data data) {
/* 588 */     LogUtil.d("parseSensorRawList values:%s", new Object[] { ParserUtils.parse(data.getValue()) });
/* 589 */     for (int i = 0; i < data.size() / 12; i++) {
/* 590 */       int offset = i * 12;
/* 591 */       int gyroscopeX = data.getIntValue(34, offset).intValue();
/* 592 */       int gyroscopeY = data.getIntValue(34, offset + 2).intValue();
/* 593 */       int gyroscopeZ = data.getIntValue(34, offset + 4).intValue();
/* 594 */       int accelerometerX = data.getIntValue(34, offset + 6).intValue();
/* 595 */       int accelerometerY = data.getIntValue(34, offset + 8).intValue();
/* 596 */       int accelerometerZ = data.getIntValue(34, offset + 10).intValue();
/* 597 */       onSensor6DRawDataReceived(device, 255L, sequence, gyroscopeX, gyroscopeY, gyroscopeZ, accelerometerX, accelerometerY, accelerometerZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void parseSensorRawList(BluetoothDevice device, long utc, int sequence, Data data) {
/* 607 */     for (int i = 0; i < data.size() / 12; i++) {
/* 608 */       int offset = i * 12;
/* 609 */       int gyroscopeX = data.getIntValue(34, offset + 10).intValue();
/* 610 */       int gyroscopeY = data.getIntValue(34, offset + 12).intValue();
/* 611 */       int gyroscopeZ = data.getIntValue(34, offset + 14).intValue();
/* 612 */       int accelerometerX = data.getIntValue(34, offset + 16).intValue();
/* 613 */       int accelerometerY = data.getIntValue(34, offset + 18).intValue();
/* 614 */       int accelerometerZ = data.getIntValue(34, offset + 20).intValue();
/* 615 */       onSensor6DRawDataReceived(device, utc, sequence, gyroscopeX, gyroscopeY, gyroscopeZ, accelerometerX, accelerometerY, accelerometerZ);
/*     */     } 
/*     */   }
/*     */   
/*     */   private synchronized byte[] subSlice(int start, byte[] value) {
/* 620 */     return HexUtil.subByte(value, start, value.length - 1);
/*     */   }
/*     */   
/*     */   private long getLongParse(byte[] bytes, int pos, int len) {
/* 624 */     long val = 0L;
/* 625 */     len += pos;
/* 626 */     for (int i = pos; i < len; i++) {
/* 627 */       val <<= 8L;
/* 628 */       val |= bytes[i] & 0xFFL;
/*     */     } 
/* 630 */     return val;
/*     */   }
/*     */   
/*     */   private int getIntParse(byte[] bytes, int pos, int len) {
/* 634 */     int val = 0;
/* 635 */     len += pos;
/* 636 */     for (int i = pos; i < len; i++) {
/* 637 */       val <<= 8;
/* 638 */       val |= bytes[i] & 0xFF;
/*     */     } 
/* 640 */     return val;
/*     */   }
/*     */   
/*     */   @Retention(RetentionPolicy.SOURCE)
/*     */   public static @interface DataType {}
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\WearReceivedDataCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */