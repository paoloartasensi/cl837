package com.jeremyliao.liveeventbus.ipc.decode;

import android.content.Intent;
import com.jeremyliao.liveeventbus.ipc.DataType;
import com.jeremyliao.liveeventbus.ipc.IpcConst;
import com.jeremyliao.liveeventbus.ipc.json.JsonConverter;

public class ValueDecoder implements IDecoder {
    private final JsonConverter jsonConverter;

    /* renamed from: com.jeremyliao.liveeventbus.ipc.decode.ValueDecoder$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$jeremyliao$liveeventbus$ipc$DataType;

        /* JADX WARNING: Can't wrap try/catch for region: R(22:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|(3:21|22|24)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(24:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|24) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0054 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0060 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0078 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.jeremyliao.liveeventbus.ipc.DataType[] r0 = com.jeremyliao.liveeventbus.ipc.DataType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$jeremyliao$liveeventbus$ipc$DataType = r0
                com.jeremyliao.liveeventbus.ipc.DataType r1 = com.jeremyliao.liveeventbus.ipc.DataType.STRING     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$jeremyliao$liveeventbus$ipc$DataType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.jeremyliao.liveeventbus.ipc.DataType r1 = com.jeremyliao.liveeventbus.ipc.DataType.INTEGER     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$jeremyliao$liveeventbus$ipc$DataType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.jeremyliao.liveeventbus.ipc.DataType r1 = com.jeremyliao.liveeventbus.ipc.DataType.BOOLEAN     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$jeremyliao$liveeventbus$ipc$DataType     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.jeremyliao.liveeventbus.ipc.DataType r1 = com.jeremyliao.liveeventbus.ipc.DataType.LONG     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$jeremyliao$liveeventbus$ipc$DataType     // Catch:{ NoSuchFieldError -> 0x003e }
                com.jeremyliao.liveeventbus.ipc.DataType r1 = com.jeremyliao.liveeventbus.ipc.DataType.FLOAT     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$jeremyliao$liveeventbus$ipc$DataType     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.jeremyliao.liveeventbus.ipc.DataType r1 = com.jeremyliao.liveeventbus.ipc.DataType.DOUBLE     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = $SwitchMap$com$jeremyliao$liveeventbus$ipc$DataType     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.jeremyliao.liveeventbus.ipc.DataType r1 = com.jeremyliao.liveeventbus.ipc.DataType.PARCELABLE     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r0 = $SwitchMap$com$jeremyliao$liveeventbus$ipc$DataType     // Catch:{ NoSuchFieldError -> 0x0060 }
                com.jeremyliao.liveeventbus.ipc.DataType r1 = com.jeremyliao.liveeventbus.ipc.DataType.SERIALIZABLE     // Catch:{ NoSuchFieldError -> 0x0060 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0060 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0060 }
            L_0x0060:
                int[] r0 = $SwitchMap$com$jeremyliao$liveeventbus$ipc$DataType     // Catch:{ NoSuchFieldError -> 0x006c }
                com.jeremyliao.liveeventbus.ipc.DataType r1 = com.jeremyliao.liveeventbus.ipc.DataType.BUNDLE     // Catch:{ NoSuchFieldError -> 0x006c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006c }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006c }
            L_0x006c:
                int[] r0 = $SwitchMap$com$jeremyliao$liveeventbus$ipc$DataType     // Catch:{ NoSuchFieldError -> 0x0078 }
                com.jeremyliao.liveeventbus.ipc.DataType r1 = com.jeremyliao.liveeventbus.ipc.DataType.JSON     // Catch:{ NoSuchFieldError -> 0x0078 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0078 }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0078 }
            L_0x0078:
                int[] r0 = $SwitchMap$com$jeremyliao$liveeventbus$ipc$DataType     // Catch:{ NoSuchFieldError -> 0x0084 }
                com.jeremyliao.liveeventbus.ipc.DataType r1 = com.jeremyliao.liveeventbus.ipc.DataType.UNKNOWN     // Catch:{ NoSuchFieldError -> 0x0084 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0084 }
                r2 = 11
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0084 }
            L_0x0084:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.jeremyliao.liveeventbus.ipc.decode.ValueDecoder.AnonymousClass1.<clinit>():void");
        }
    }

    public ValueDecoder(JsonConverter jsonConverter2) {
        this.jsonConverter = jsonConverter2;
    }

    public Object decode(Intent intent) {
        int intExtra = intent.getIntExtra(IpcConst.VALUE_TYPE, -1);
        if (intExtra >= 0) {
            switch (AnonymousClass1.$SwitchMap$com$jeremyliao$liveeventbus$ipc$DataType[DataType.values()[intExtra].ordinal()]) {
                case 1:
                    return intent.getStringExtra(IpcConst.VALUE);
                case 2:
                    return Integer.valueOf(intent.getIntExtra(IpcConst.VALUE, -1));
                case 3:
                    return Boolean.valueOf(intent.getBooleanExtra(IpcConst.VALUE, false));
                case 4:
                    return Long.valueOf(intent.getLongExtra(IpcConst.VALUE, -1));
                case 5:
                    return Float.valueOf(intent.getFloatExtra(IpcConst.VALUE, -1.0f));
                case 6:
                    return Double.valueOf(intent.getDoubleExtra(IpcConst.VALUE, -1.0d));
                case 7:
                    return intent.getParcelableExtra(IpcConst.VALUE);
                case 8:
                    return intent.getSerializableExtra(IpcConst.VALUE);
                case 9:
                    return intent.getBundleExtra(IpcConst.VALUE);
                case 10:
                    try {
                        String stringExtra = intent.getStringExtra(IpcConst.VALUE);
                        String stringExtra2 = intent.getStringExtra(IpcConst.CLASS_NAME);
                        Class<?> cls = null;
                        try {
                            cls = Class.forName(stringExtra2);
                        } catch (ClassNotFoundException unused) {
                            int lastIndexOf = stringExtra2.lastIndexOf(46);
                            if (lastIndexOf != -1) {
                                String substring = stringExtra2.substring(0, lastIndexOf);
                                String substring2 = stringExtra2.substring(lastIndexOf + 1);
                                cls = Class.forName(substring + "$" + substring2);
                            }
                        }
                        return this.jsonConverter.fromJson(stringExtra, cls);
                    } catch (Exception e) {
                        throw new DecodeException((Throwable) e);
                    }
                default:
                    throw new DecodeException();
            }
        } else {
            throw new DecodeException("Index Error");
        }
    }
}
