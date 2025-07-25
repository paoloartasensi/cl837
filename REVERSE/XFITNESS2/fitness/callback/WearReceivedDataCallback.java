package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import androidx.annotation.NonNull;
import com.android.chileaf.model.HistoryOf3D;
import com.android.chileaf.model.HistoryOfHeartRate;
import com.android.chileaf.model.HistoryOfRecord;
import com.android.chileaf.model.HistoryOfRespiratoryRate;
import com.android.chileaf.model.HistoryOfSport;
import com.android.chileaf.model.HistorySleep;
import com.android.chileaf.model.IntervalStep;
import com.android.chileaf.util.DateUtil;
import com.android.chileaf.util.HexUtil;
import com.android.chileaf.util.LogUtil;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import no.nordicsemi.android.ble.callback.profile.ProfileReadResponse;
import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.ble.utils.ParserUtils;

public abstract class WearReceivedDataCallback extends ProfileReadResponse implements UserInfoCallback, BodySportCallback, BluetoothStatusCallback, HistoryOfSportCallback, HistoryOfHRRecordCallback, HistoryOfHRDataCallback, HistoryOfRRRecordCallback, HistoryOfRRDataCallback, IntervalStepCallback, SingleTapRecordCallback, HeartRateStatusCallback, BloodOxygenCallback, TemperatureCallback, HistoryOfSingleRecordCallback, HeartRateAlarmCallback, AccelerometerCallback, HeartRateMaxCallback, HistoryOfSleepCallback, Sensor3DFrequencyCallback, Sensor3DStatusCallback, HistoryOf3DDataCallback, BodyHealthCallback, Sensor6DFrequencyCallback, Sensor6DRawDataCallback, BodySportHealthCallback {
   private static final long END_TAG = 4294967295L;
   private long mStamp = 0L;
   private boolean isCL833 = false;
   private boolean isStamp = false;
   private List<Data> mPackages = new ArrayList();
   private List<HistoryOfSport> mHistoryOfSports;
   private List<HistoryOfRecord> mHistoryOfRecords;
   private List<HistoryOfHeartRate> mHistoryOfHeartRates;
   private List<HistoryOfRecord> mRespiratoryRatesRecords;
   private List<HistoryOfRespiratoryRate> mHistoryOfRespiratoryRates;
   private List<IntervalStep> mIntervalSteps;
   private List<HistoryOfRecord> mSingleTapRecords;
   private List<HistorySleep> mHistoryOfSleeps;
   public static final int TYPE_SPORT = 2;
   public static final int TYPE_HEART = 4;
   public static final int TYPE_HEARTS = 6;
   public static final int TYPE_HEART_RR = 8;
   public static final int TYPE_HEART_RRS = 16;
   public static final int TYPE_INTERVAL = 18;
   public static final int TYPE_SINGLE_TAP = 20;
   public static final int TYPE_SLEEP = 22;
   public static final int TYPE_HISTORY_3D = 24;

   public WearReceivedDataCallback() {
   }

   protected WearReceivedDataCallback(final Parcel in) {
      super(in);
   }

   public void onDataReceived(@NonNull final BluetoothDevice device, @NonNull final Data data) {
      super.onDataReceived(device, data);
      boolean validate = data.getIntValue(17, 1) == data.size();
      if (!validate) {
         this.onInvalidDataReceived(device, data);
         LogUtil.w("onDataReceived:length:%s", data.size());
      }

      byte[] value = data.getValue();
      int mode = data.getIntValue(17, 2);

      try {
         int cmd;
         int vo2Max;
         int breathRate;
         int emotionLevel;
         long stamp;
         if (mode == 3) {
            cmd = this.getIntParse(value, 5, 1);
            vo2Max = this.getIntParse(value, 6, 1);
            breathRate = this.getIntParse(value, 7, 1);
            emotionLevel = this.getIntParse(value, 8, 1);
            stamp = this.getLongParse(value, 9, 5);
            this.onUserInfoReceived(device, cmd, vo2Max, breathRate, emotionLevel, stamp);
         } else {
            int accelerometerY;
            long utc;
            if (mode == 5) {
               cmd = this.getIntParse(value, 3, 1);
               if (cmd == 3) {
                  if (this.mHistoryOfSleeps == null) {
                     this.mHistoryOfSleeps = new ArrayList();
                  }

                  for(vo2Max = 4; vo2Max < value.length; ++vo2Max) {
                     int len = value[vo2Max];
                     if (len >= 1) {
                        ++vo2Max;
                        utc = this.getLongParse(value, vo2Max, 4);
                        vo2Max += 4;
                        utc *= 1000L;
                        utc -= 28800000L;
                        int[] actions = new int[len];

                        for(accelerometerY = 0; accelerometerY < len; ++accelerometerY) {
                           actions[accelerometerY] = this.getIntParse(value, accelerometerY + vo2Max, 1);
                        }

                        vo2Max += len - 1;
                        HistorySleep historySleep = new HistorySleep(utc, actions);
                        this.mHistoryOfSleeps.add(historySleep);
                        if (vo2Max == value.length - 2) {
                           break;
                        }
                     }
                  }

                  this.onHistoryOfSleepReceived(device, this.mHistoryOfSleeps);
                  this.mHistoryOfSleeps.clear();
               }
            } else {
               byte[] slice;
               int stressPercent;
               int stamina;
               if (mode == 12) {
                  slice = this.subSlice(3, value);

                  for(breathRate = 0; breathRate < slice.length / 6; ++breathRate) {
                     cmd = breathRate * 6;
                     emotionLevel = data.getIntValue(34, cmd + 3);
                     stressPercent = data.getIntValue(34, cmd + 5);
                     stamina = data.getIntValue(34, cmd + 7);
                     this.onAccelerometerReceived(device, emotionLevel, stressPercent, stamina);
                  }
               } else if (mode != 18) {
                  if (mode == 19) {
                     cmd = this.getIntParse(value, 3, 1);
                     vo2Max = this.getIntParse(value, 4, 1);
                     breathRate = this.getIntParse(value, 5, 1);
                     emotionLevel = this.getIntParse(value, 6, 1);
                     stressPercent = this.getIntParse(value, 7, 1);
                     this.onSportHealthReceived(device, cmd, vo2Max, breathRate, emotionLevel, stressPercent);
                  } else if (mode == 21) {
                     cmd = this.getIntParse(value, 3, 3);
                     vo2Max = this.getIntParse(value, 6, 3);
                     breathRate = this.getIntParse(value, 9, 3);
                     this.onSportReceived(device, cmd, vo2Max, breathRate);
                  } else if (mode == 63) {
                     cmd = this.getIntParse(value, 3, 1);
                     this.onBluetoothStatusReceived(device, cmd == 1);
                  } else {
                     long stamp;
                     byte[] slice;
                     if (mode == 22) {
                        if (this.mHistoryOfSports == null) {
                           this.mHistoryOfSports = new ArrayList();
                        }

                        stamp = this.getLongParse(value, 3, 4);
                        if (this.isCL833) {
                           slice = this.subSlice(3, value);
                           this.parseSportHistory(slice);
                           Collections.reverse(this.mHistoryOfSports);
                           this.onHistoryOfSportReceived(device, this.mHistoryOfSports);
                           this.mHistoryOfSports.clear();
                        } else if (stamp != 4294967295L) {
                           this.mPackages.add(data);
                        } else {
                           for(emotionLevel = 0; emotionLevel < this.mPackages.size(); ++emotionLevel) {
                              slice = this.subSlice(3, ((Data)this.mPackages.get(emotionLevel)).getValue());
                              this.parseSportHistory(slice);
                              LogUtil.d("HistoryOfSport index:%d values:%s", emotionLevel, ParserUtils.parse(slice));
                           }

                           this.onHistoryOfSportReceived(device, this.mHistoryOfSports);
                           this.mHistoryOfSports.clear();
                           this.mPackages.clear();
                        }
                     } else {
                        long stamp;
                        long record;
                        if (mode == 33) {
                           if (this.mHistoryOfRecords == null) {
                              this.mHistoryOfRecords = new ArrayList();
                           }

                           stamp = this.getLongParse(value, 3, 4);
                           if (stamp != 4294967295L) {
                              this.mPackages.add(data);
                           } else {
                              for(stressPercent = 0; stressPercent < this.mPackages.size(); ++stressPercent) {
                                 byte[] slice = this.subSlice(3, ((Data)this.mPackages.get(stressPercent)).getValue());
                                 LogUtil.d("mHistoryOfRecords index:%d values:%s", stressPercent, ParserUtils.parse(slice));

                                 for(stamina = 0; stamina < slice.length / 4; ++stamina) {
                                    breathRate = stamina * 4;
                                    record = this.getLongParse(slice, breathRate, 4);
                                    stamp = DateUtil.restoreZoneUTC(record);
                                    this.mHistoryOfRecords.add(new HistoryOfRecord(record, stamp));
                                    LogUtil.d("mHistoryOfRecords index:%d record:%s", stamina, record);
                                 }
                              }

                              this.onHistoryOfHRRecordReceived(device, this.mHistoryOfRecords);
                              this.mHistoryOfRecords.clear();
                              this.mPackages.clear();
                           }
                        } else {
                           byte[] slice;
                           long stamp;
                           if (mode != 34 && mode != 35) {
                              int accelerometerZ;
                              if (mode == 36) {
                                 if (this.mRespiratoryRatesRecords == null) {
                                    this.mRespiratoryRatesRecords = new ArrayList();
                                 }

                                 stamp = this.getLongParse(value, 3, 4);
                                 if (stamp != 4294967295L) {
                                    this.mPackages.add(data);
                                 } else {
                                    int length = 4;

                                    for(stamina = 0; stamina < this.mPackages.size(); ++stamina) {
                                       slice = this.subSlice(3, ((Data)this.mPackages.get(stamina)).getValue());
                                       LogUtil.d("mRespiratoryRatesRecords index:%d values:%s", stamina, ParserUtils.parse(slice));

                                       for(accelerometerY = 0; accelerometerY < slice.length / length; ++accelerometerY) {
                                          accelerometerZ = accelerometerY * length;
                                          stamp = this.getLongParse(slice, accelerometerZ, 4);
                                          long record = DateUtil.restoreZoneUTC(stamp);
                                          HistoryOfRecord historyRecord = new HistoryOfRecord(stamp, record);
                                          this.mRespiratoryRatesRecords.add(historyRecord);
                                       }
                                    }

                                    this.onHistoryOfRRRecordReceived(device, this.mRespiratoryRatesRecords);
                                    LogUtil.d("onHistoryOfRRRecordReceived size:%d", this.mRespiratoryRatesRecords.size());
                                    this.mRespiratoryRatesRecords.clear();
                                    this.mPackages.clear();
                                 }
                              } else {
                                 byte length;
                                 if (mode != 37 && mode != 38) {
                                    if (mode == 55) {
                                       if (value[1] <= 6) {
                                          return;
                                       }

                                       cmd = this.getIntParse(value, 3, 1);
                                       if (value[1] <= 8) {
                                          return;
                                       }

                                       vo2Max = this.getIntParse(value, 4, 1);
                                       breathRate = this.getIntParse(value, 5, 1);
                                       emotionLevel = this.getIntParse(value, 6, 1);
                                       stressPercent = this.getIntParse(value, 7, 1);
                                       this.onBloodOxygenReceived(device, cmd, String.valueOf(vo2Max), breathRate, emotionLevel, stressPercent);
                                    } else if (mode == 56) {
                                       float BadSituation = (float)this.getIntParse(value, 3, 2) / 10.0F;
                                       float wrist = (float)this.getIntParse(value, 5, 2) / 10.0F;
                                       float body = (float)this.getIntParse(value, 7, 2) / 10.0F;
                                       this.onTemperatureReceived(device, BadSituation, wrist, body);
                                    } else if (mode != 64 && mode != 65) {
                                       if (mode != 66 && mode != 67) {
                                          if (mode == 70) {
                                             cmd = this.getIntParse(value, 4, 1);
                                             vo2Max = this.getIntParse(value, 5, 1);
                                             breathRate = this.getIntParse(value, 6, 1);
                                             this.onHeartRateStatusReceived(device, cmd, vo2Max, breathRate);
                                          } else if (mode == 73) {
                                             stamp = DateUtil.restoreZoneUTC(this.getLongParse(value, 3, 4));
                                             long step = this.getLongParse(value, 7, 3);
                                             stamp = this.getLongParse(value, 10, 3);
                                             record = this.getLongParse(value, 13, 3);
                                             this.onHistorySingleRecordReceived(device, stamp, step, stamp, record);
                                          } else if (mode == 91) {
                                             stamp = DateUtil.restoreZoneUTC(this.getLongParse(value, 3, 4));
                                             breathRate = this.getIntParse(value, 7, 1);
                                             this.onHeartRateAlarmReceived(device, stamp, breathRate == 1);
                                          } else if (mode == 96) {
                                             cmd = this.getIntParse(value, 3, 1);
                                             this.parseSensorRawList(device, cmd, new Data(this.subSlice(4, value)));
                                          } else if (mode == 97) {
                                             cmd = this.getIntParse(value, 3, 1);
                                             this.onSensor6DFrequencyReceived(device, cmd);
                                          } else if (mode == 100) {
                                             stamp = this.getLongParse(value, 3, 4);
                                             breathRate = this.getIntParse(value, 7, 2);
                                             utc = DateUtil.restoreZoneUTCTimeInMillis(stamp * 1000L + (long)breathRate);
                                             stamina = this.getIntParse(value, 9, 1);
                                             this.parseSensorRawList(device, utc, stamina, data);
                                          } else if (mode == 117) {
                                             cmd = this.getIntParse(value, 4, 1);
                                             if (cmd == 6) {
                                                vo2Max = this.getIntParse(value, 5, 1);
                                                this.onHeartRateMaxReceived(device, vo2Max);
                                             } else if (cmd == 11) {
                                                vo2Max = this.getIntParse(value, 5, 1);
                                                this.onSensor3DFrequencyReceived(device, vo2Max);
                                             } else if (cmd == 12) {
                                                boolean enabled = this.getIntParse(value, 5, 1) == 1;
                                                this.onSensor3DStatusReceived(device, enabled);
                                             } else if (cmd == 15) {
                                                vo2Max = this.getIntParse(value, 5, 1);
                                                breathRate = this.getIntParse(value, 6, 1);
                                                emotionLevel = this.getIntParse(value, 7, 1);
                                                stressPercent = this.getIntParse(value, 8, 1);
                                                stamina = this.getIntParse(value, 9, 1);
                                                float tp = (float)this.getLongParse(value, 10, 4) / 1000.0F;
                                                float lf = (float)this.getLongParse(value, 14, 4) / 1000.0F;
                                                float hf = (float)this.getLongParse(value, 18, 4) / 1000.0F;
                                                this.onHealthReceived(device, vo2Max, breathRate, emotionLevel, stressPercent, stamina, tp, lf, hf);
                                             }
                                          } else if ((mode == 119 || mode == 120) && (mode == 119 || mode == 120)) {
                                             length = 6;
                                             slice = this.subSlice(3, data.getValue());

                                             for(emotionLevel = 0; emotionLevel < slice.length / length; ++emotionLevel) {
                                                stressPercent = emotionLevel * length;
                                                stamina = this.getSInt16(slice, stressPercent);
                                                accelerometerY = this.getSInt16(slice, stressPercent + 2);
                                                accelerometerZ = this.getSInt16(slice, stressPercent + 4);
                                                HistoryOf3D history = new HistoryOf3D(stamina, accelerometerY, accelerometerZ);
                                                this.onHistoryOf3DDataReceived(device, history, mode == 120);
                                             }
                                          }
                                       } else {
                                          if (this.mSingleTapRecords == null) {
                                             this.mSingleTapRecords = new ArrayList();
                                          }

                                          if (mode == 66) {
                                             this.mPackages.add(data);
                                          }

                                          if (mode == 67) {
                                             for(vo2Max = 0; vo2Max < this.mPackages.size(); ++vo2Max) {
                                                slice = this.subSlice(3, ((Data)this.mPackages.get(vo2Max)).getValue());
                                                LogUtil.d("mSingleTapRecords index:%d values:%s", vo2Max, ParserUtils.parse(slice));

                                                for(emotionLevel = 0; emotionLevel < slice.length / 4; ++emotionLevel) {
                                                   breathRate = emotionLevel * 4;
                                                   stamp = this.getLongParse(slice, breathRate, 4);
                                                   record = DateUtil.restoreZoneUTC(stamp);
                                                   this.mSingleTapRecords.add(new HistoryOfRecord(stamp, record));
                                                }
                                             }

                                             this.onSingleTapRecordReceived(device, this.mSingleTapRecords);
                                             this.mSingleTapRecords.clear();
                                             this.mPackages.clear();
                                          }
                                       }
                                    } else {
                                       if (this.mIntervalSteps == null) {
                                          this.mIntervalSteps = new ArrayList();
                                       }

                                       if (mode == 64) {
                                          this.mPackages.add(data);
                                       }

                                       if (mode == 65) {
                                          for(emotionLevel = 0; emotionLevel < this.mPackages.size(); ++emotionLevel) {
                                             slice = this.subSlice(3, ((Data)this.mPackages.get(emotionLevel)).getValue());
                                             LogUtil.d("mIntervalSteps index:%d values:%s", emotionLevel, ParserUtils.parse(slice));

                                             for(stressPercent = 0; stressPercent < slice.length / 8; ++stressPercent) {
                                                cmd = stressPercent * 8;
                                                stamp = DateUtil.restoreZoneUTC(this.getLongParse(slice, cmd, 4));
                                                accelerometerZ = this.getIntParse(slice, cmd + 4, 4);
                                                IntervalStep intervalStep = new IntervalStep(stamp, accelerometerZ);
                                                this.mIntervalSteps.add(intervalStep);
                                             }
                                          }

                                          this.onIntervalStepReceived(device, this.mIntervalSteps);
                                          this.mIntervalSteps.clear();
                                          this.mPackages.clear();
                                       }
                                    }
                                 } else {
                                    if (this.mHistoryOfRespiratoryRates == null) {
                                       this.mHistoryOfRespiratoryRates = new ArrayList();
                                    }

                                    if (mode == 37) {
                                       if (!this.isStamp) {
                                          this.mStamp = this.getLongParse(value, 3, 4);
                                          this.isStamp = true;
                                       }

                                       this.mPackages.add(data);
                                    }

                                    if (mode == 38) {
                                       length = 2;

                                       for(emotionLevel = 0; emotionLevel < this.mPackages.size(); ++emotionLevel) {
                                          slice = this.subSlice(7, ((Data)this.mPackages.get(emotionLevel)).getValue());
                                          LogUtil.d("index:%d HistoryOfRespiratoryRates mValues:%s", emotionLevel, ParserUtils.parse(slice));

                                          for(stressPercent = 0; stressPercent < slice.length / length; ++stressPercent) {
                                             stamina = stressPercent * length;
                                             accelerometerY = this.getIntParse(slice, stamina, 2);
                                             long stamp = DateUtil.restoreZoneUTC(this.mStamp);
                                             HistoryOfRespiratoryRate respiratoryRate = new HistoryOfRespiratoryRate(stamp, accelerometerY);
                                             this.mHistoryOfRespiratoryRates.add(respiratoryRate);
                                             ++this.mStamp;
                                          }
                                       }

                                       LogUtil.d("onHistoryOfRRDataReceived :%s", this.mHistoryOfRespiratoryRates.toString());
                                       this.onHistoryOfRRDataReceived(device, this.mHistoryOfRespiratoryRates);
                                       this.mHistoryOfRespiratoryRates.clear();
                                       this.mPackages.clear();
                                       this.isStamp = false;
                                       this.mStamp = 0L;
                                    }
                                 }
                              }
                           } else {
                              if (this.mHistoryOfHeartRates == null) {
                                 this.mHistoryOfHeartRates = new ArrayList();
                              }

                              if (mode == 34) {
                                 if (!this.isStamp) {
                                    this.mStamp = this.getLongParse(value, 3, 4);
                                    this.isStamp = true;
                                 }

                                 this.mPackages.add(data);
                              }

                              if (mode == 35) {
                                 for(breathRate = 0; breathRate < this.mPackages.size(); ++breathRate) {
                                    slice = this.subSlice(3, ((Data)this.mPackages.get(breathRate)).getValue());
                                    LogUtil.d("mHistoryOfHeartRates index:%d values:%s", breathRate, ParserUtils.parse(slice));

                                    for(emotionLevel = 4; emotionLevel < slice.length; ++emotionLevel) {
                                       stressPercent = this.getIntParse(slice, emotionLevel, 1);
                                       stamp = DateUtil.restoreZoneUTC(this.mStamp);
                                       HistoryOfHeartRate heartRate = new HistoryOfHeartRate(stamp, stressPercent);
                                       this.mHistoryOfHeartRates.add(heartRate);
                                       ++this.mStamp;
                                    }
                                 }

                                 this.onHistoryOfHRDataReceived(device, this.mHistoryOfHeartRates);
                                 this.mHistoryOfHeartRates.clear();
                                 this.mPackages.clear();
                                 this.isStamp = false;
                                 this.mStamp = 0L;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (Exception var18) {
         var18.printStackTrace();
      }

   }

   public void setCL833(boolean isCL833) {
      this.isCL833 = isCL833;
   }

   private int getSInt16(byte[] data, int offset) {
      return this.unsignedToSigned(this.unsignedBytesToInt(data[offset], data[offset + 1]), 16);
   }

   private int unsignedByteToInt(final byte b) {
      return b & 255;
   }

   private int unsignedBytesToInt(final byte b0, final byte b1) {
      return this.unsignedByteToInt(b0) + (this.unsignedByteToInt(b1) << 8);
   }

   private int unsignedToSigned(int unsigned, final int size) {
      if ((unsigned & 1 << size - 1) != 0) {
         unsigned = -1 * ((1 << size - 1) - (unsigned & (1 << size - 1) - 1));
      }

      return unsigned;
   }

   public void clearType(int type) {
      switch(type) {
      case 2:
         if (this.mHistoryOfSports != null) {
            this.mHistoryOfSports.clear();
         }
      case 3:
      case 5:
      case 7:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 17:
      case 19:
      case 21:
      case 23:
      case 24:
      default:
         break;
      case 4:
         if (this.mHistoryOfRecords != null) {
            this.mHistoryOfRecords.clear();
         }
         break;
      case 6:
         if (this.mHistoryOfHeartRates != null) {
            this.mHistoryOfHeartRates.clear();
         }
         break;
      case 8:
         if (this.mRespiratoryRatesRecords != null) {
            this.mRespiratoryRatesRecords.clear();
         }
         break;
      case 16:
         if (this.mHistoryOfRespiratoryRates != null) {
            this.mHistoryOfRespiratoryRates.clear();
         }
         break;
      case 18:
         if (this.mIntervalSteps != null) {
            this.mIntervalSteps.clear();
         }
         break;
      case 20:
         if (this.mSingleTapRecords != null) {
            this.mSingleTapRecords.clear();
         }
         break;
      case 22:
         if (this.mHistoryOfSleeps != null) {
            this.mHistoryOfSleeps.clear();
         }
      }

      this.mPackages.clear();
   }

   private void parseSportHistory(final byte[] value) {
      LogUtil.d("HistoryOfSport length:%d values:%s", value.length, ParserUtils.parse(value));

      for(int i = 0; i < value.length / 10; ++i) {
         int offset = i * 10;
         long stamp = this.getLongParse(value, offset, 4);
         long step = this.getLongParse(value, offset + 4, 3);
         long calorie = this.getLongParse(value, offset + 7, 3);
         stamp = DateUtil.restoreZoneUTC(stamp);
         this.mHistoryOfSports.add(new HistoryOfSport(stamp, step, calorie));
      }

   }

   private synchronized void parseSensorRawList(BluetoothDevice device, final int sequence, final Data data) {
      LogUtil.d("parseSensorRawList values:%s", ParserUtils.parse(data.getValue()));

      for(int i = 0; i < data.size() / 12; ++i) {
         int offset = i * 12;
         int gyroscopeX = data.getIntValue(34, offset);
         int gyroscopeY = data.getIntValue(34, offset + 2);
         int gyroscopeZ = data.getIntValue(34, offset + 4);
         int accelerometerX = data.getIntValue(34, offset + 6);
         int accelerometerY = data.getIntValue(34, offset + 8);
         int accelerometerZ = data.getIntValue(34, offset + 10);
         this.onSensor6DRawDataReceived(device, 255L, sequence, gyroscopeX, gyroscopeY, gyroscopeZ, accelerometerX, accelerometerY, accelerometerZ);
      }

   }

   private synchronized void parseSensorRawList(BluetoothDevice device, final long utc, final int sequence, final Data data) {
      for(int i = 0; i < data.size() / 12; ++i) {
         int offset = i * 12;
         int gyroscopeX = data.getIntValue(34, offset + 10);
         int gyroscopeY = data.getIntValue(34, offset + 12);
         int gyroscopeZ = data.getIntValue(34, offset + 14);
         int accelerometerX = data.getIntValue(34, offset + 16);
         int accelerometerY = data.getIntValue(34, offset + 18);
         int accelerometerZ = data.getIntValue(34, offset + 20);
         this.onSensor6DRawDataReceived(device, utc, sequence, gyroscopeX, gyroscopeY, gyroscopeZ, accelerometerX, accelerometerY, accelerometerZ);
      }

   }

   private synchronized byte[] subSlice(final int start, final byte[] value) {
      return HexUtil.subByte(value, start, value.length - 1);
   }

   private long getLongParse(byte[] bytes, int pos, int len) {
      long val = 0L;
      len += pos;

      for(int i = pos; i < len; ++i) {
         val <<= 8;
         val |= (long)bytes[i] & 255L;
      }

      return val;
   }

   private int getIntParse(byte[] bytes, int pos, int len) {
      int val = 0;
      len += pos;

      for(int i = pos; i < len; ++i) {
         val <<= 8;
         val |= bytes[i] & 255;
      }

      return val;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface DataType {
   }
}
