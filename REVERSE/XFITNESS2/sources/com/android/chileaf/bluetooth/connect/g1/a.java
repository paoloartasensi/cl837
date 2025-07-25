package com.android.chileaf.bluetooth.connect.g1;

/* compiled from: GattError */
public class a {
    public static String a(int i2) {
        if (i2 == 34) {
            return "GATT CONN LMP TIMEOUT";
        }
        if (i2 == 257) {
            return "TOO MANY OPEN CONNECTIONS";
        }
        if (i2 == 58) {
            return "GATT CONTROLLER BUSY";
        }
        if (i2 == 59) {
            return "GATT UNACCEPT CONN INTERVAL";
        }
        switch (i2) {
            case 1:
                return "GATT INVALID HANDLE";
            case 2:
                return "GATT READ NOT PERMIT";
            case 3:
                return "GATT WRITE NOT PERMIT";
            case 4:
                return "GATT INVALID PDU";
            case 5:
                return "GATT INSUF AUTHENTICATION";
            case 6:
                return "GATT REQ NOT SUPPORTED";
            case 7:
                return "GATT INVALID OFFSET";
            case 8:
                return "GATT INSUF AUTHORIZATION";
            case 9:
                return "GATT PREPARE Q FULL";
            case 10:
                return "GATT NOT FOUND";
            case 11:
                return "GATT NOT LONG";
            case 12:
                return "GATT INSUF KEY SIZE";
            case 13:
                return "GATT INVALID ATTR LEN";
            case 14:
                return "GATT ERR UNLIKELY";
            case 15:
                return "GATT INSUF ENCRYPTION";
            case 16:
                return "GATT UNSUPPORT GRP TYPE";
            case 17:
                return "GATT INSUF RESOURCE";
            default:
                switch (i2) {
                    case 128:
                        return "GATT NO RESOURCES";
                    case 129:
                        return "GATT INTERNAL ERROR";
                    case 130:
                        return "GATT WRONG STATE";
                    case 131:
                        return "GATT DB FULL";
                    case 132:
                        return "GATT BUSY";
                    case 133:
                        return "GATT ERROR";
                    case 134:
                        return "GATT CMD STARTED";
                    case 135:
                        return "GATT ILLEGAL PARAMETER";
                    case 136:
                        return "GATT PENDING";
                    case 137:
                        return "GATT AUTH FAIL";
                    case 138:
                        return "GATT MORE";
                    case 139:
                        return "GATT INVALID CFG";
                    case 140:
                        return "GATT SERVICE STARTED";
                    case 141:
                        return "GATT ENCRYPTED NO MITM";
                    case 142:
                        return "GATT NOT ENCRYPTED";
                    case 143:
                        return "GATT CONGESTED";
                    default:
                        switch (i2) {
                            case 253:
                                return "GATT CCCD CFG ERROR";
                            case 254:
                                return "GATT PROCEDURE IN PROGRESS";
                            case 255:
                                return "GATT VALUE OUT OF RANGE";
                            default:
                                return "UNKNOWN (" + i2 + ")";
                        }
                }
        }
    }

    public static String b(int i2) {
        if (i2 == 0) {
            return "SUCCESS";
        }
        if (i2 == 1) {
            return "GATT CONN L2C FAILURE";
        }
        if (i2 == 8) {
            return "GATT CONN TIMEOUT";
        }
        if (i2 == 19) {
            return "GATT CONN TERMINATE PEER USER";
        }
        if (i2 == 22) {
            return "GATT CONN TERMINATE LOCAL HOST";
        }
        if (i2 == 34) {
            return "GATT CONN LMP TIMEOUT";
        }
        if (i2 == 62) {
            return "GATT CONN FAIL ESTABLISH";
        }
        if (i2 == 133) {
            return "GATT ERROR";
        }
        if (i2 == 256) {
            return "GATT CONN CANCEL ";
        }
        return "UNKNOWN (" + i2 + ")";
    }
}
