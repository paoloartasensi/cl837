/*     */ package no.nordicsemi.android.ble.error;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GattError
/*     */ {
/*     */   public static final int GATT_SUCCESS = 0;
/*     */   public static final int GATT_CONN_L2C_FAILURE = 1;
/*     */   public static final int GATT_CONN_TIMEOUT = 8;
/*     */   public static final int GATT_CONN_TERMINATE_PEER_USER = 19;
/*     */   public static final int GATT_CONN_TERMINATE_LOCAL_HOST = 22;
/*     */   public static final int GATT_CONN_FAIL_ESTABLISH = 62;
/*     */   public static final int GATT_CONN_LMP_TIMEOUT = 34;
/*     */   public static final int GATT_CONN_CANCEL = 256;
/*     */   public static final int GATT_ERROR = 133;
/*     */   public static final int GATT_INVALID_HANDLE = 1;
/*     */   public static final int GATT_READ_NOT_PERMIT = 2;
/*     */   public static final int GATT_WRITE_NOT_PERMIT = 3;
/*     */   public static final int GATT_INVALID_PDU = 4;
/*     */   public static final int GATT_INSUF_AUTHENTICATION = 5;
/*     */   public static final int GATT_REQ_NOT_SUPPORTED = 6;
/*     */   public static final int GATT_INVALID_OFFSET = 7;
/*     */   public static final int GATT_INSUF_AUTHORIZATION = 8;
/*     */   public static final int GATT_PREPARE_Q_FULL = 9;
/*     */   public static final int GATT_NOT_FOUND = 10;
/*     */   public static final int GATT_NOT_LONG = 11;
/*     */   public static final int GATT_INSUF_KEY_SIZE = 12;
/*     */   public static final int GATT_INVALID_ATTR_LEN = 13;
/*     */   public static final int GATT_ERR_UNLIKELY = 14;
/*     */   public static final int GATT_INSUF_ENCRYPTION = 15;
/*     */   public static final int GATT_UNSUPPORT_GRP_TYPE = 16;
/*     */   public static final int GATT_INSUF_RESOURCE = 17;
/*     */   public static final int GATT_CONTROLLER_BUSY = 58;
/*     */   public static final int GATT_UNACCEPT_CONN_INTERVAL = 59;
/*     */   public static final int GATT_ILLEGAL_PARAMETER = 135;
/*     */   public static final int GATT_NO_RESOURCES = 128;
/*     */   public static final int GATT_INTERNAL_ERROR = 129;
/*     */   public static final int GATT_WRONG_STATE = 130;
/*     */   public static final int GATT_DB_FULL = 131;
/*     */   public static final int GATT_BUSY = 132;
/*     */   public static final int GATT_CMD_STARTED = 134;
/*     */   public static final int GATT_PENDING = 136;
/*     */   public static final int GATT_AUTH_FAIL = 137;
/*     */   public static final int GATT_MORE = 138;
/*     */   public static final int GATT_INVALID_CFG = 139;
/*     */   public static final int GATT_SERVICE_STARTED = 140;
/*     */   public static final int GATT_ENCRYPTED_NO_MITM = 141;
/*     */   public static final int GATT_NOT_ENCRYPTED = 142;
/*     */   public static final int GATT_CONGESTED = 143;
/*     */   public static final int GATT_CCCD_CFG_ERROR = 253;
/*     */   public static final int GATT_PROCEDURE_IN_PROGRESS = 254;
/*     */   public static final int GATT_VALUE_OUT_OF_RANGE = 255;
/*     */   public static final int TOO_MANY_OPEN_CONNECTIONS = 257;
/*     */   
/*     */   public static String parseConnectionError(int error) {
/*  92 */     switch (error) {
/*     */       case 0:
/*  94 */         return "SUCCESS";
/*     */       case 1:
/*  96 */         return "GATT CONN L2C FAILURE";
/*     */       case 8:
/*  98 */         return "GATT CONN TIMEOUT";
/*     */       case 19:
/* 100 */         return "GATT CONN TERMINATE PEER USER";
/*     */       case 22:
/* 102 */         return "GATT CONN TERMINATE LOCAL HOST";
/*     */       case 62:
/* 104 */         return "GATT CONN FAIL ESTABLISH";
/*     */       case 34:
/* 106 */         return "GATT CONN LMP TIMEOUT";
/*     */       case 256:
/* 108 */         return "GATT CONN CANCEL ";
/*     */       case 133:
/* 110 */         return "GATT ERROR";
/*     */     } 
/* 112 */     return "UNKNOWN (" + error + ")";
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
/*     */   public static String parse(int error) {
/* 124 */     switch (error) {
/*     */       case 1:
/* 126 */         return "GATT INVALID HANDLE";
/*     */       case 2:
/* 128 */         return "GATT READ NOT PERMIT";
/*     */       case 3:
/* 130 */         return "GATT WRITE NOT PERMIT";
/*     */       case 4:
/* 132 */         return "GATT INVALID PDU";
/*     */       case 5:
/* 134 */         return "GATT INSUF AUTHENTICATION";
/*     */       case 6:
/* 136 */         return "GATT REQ NOT SUPPORTED";
/*     */       case 7:
/* 138 */         return "GATT INVALID OFFSET";
/*     */       case 8:
/* 140 */         return "GATT INSUF AUTHORIZATION";
/*     */       case 9:
/* 142 */         return "GATT PREPARE Q FULL";
/*     */       case 10:
/* 144 */         return "GATT NOT FOUND";
/*     */       case 11:
/* 146 */         return "GATT NOT LONG";
/*     */       case 12:
/* 148 */         return "GATT INSUF KEY SIZE";
/*     */       case 13:
/* 150 */         return "GATT INVALID ATTR LEN";
/*     */       case 14:
/* 152 */         return "GATT ERR UNLIKELY";
/*     */       case 15:
/* 154 */         return "GATT INSUF ENCRYPTION";
/*     */       case 16:
/* 156 */         return "GATT UNSUPPORT GRP TYPE";
/*     */       case 17:
/* 158 */         return "GATT INSUF RESOURCE";
/*     */       case 34:
/* 160 */         return "GATT CONN LMP TIMEOUT";
/*     */       case 58:
/* 162 */         return "GATT CONTROLLER BUSY";
/*     */       case 59:
/* 164 */         return "GATT UNACCEPT CONN INTERVAL";
/*     */       case 135:
/* 166 */         return "GATT ILLEGAL PARAMETER";
/*     */       case 128:
/* 168 */         return "GATT NO RESOURCES";
/*     */       case 129:
/* 170 */         return "GATT INTERNAL ERROR";
/*     */       case 130:
/* 172 */         return "GATT WRONG STATE";
/*     */       case 131:
/* 174 */         return "GATT DB FULL";
/*     */       case 132:
/* 176 */         return "GATT BUSY";
/*     */       case 133:
/* 178 */         return "GATT ERROR";
/*     */       case 134:
/* 180 */         return "GATT CMD STARTED";
/*     */       case 136:
/* 182 */         return "GATT PENDING";
/*     */       case 137:
/* 184 */         return "GATT AUTH FAIL";
/*     */       case 138:
/* 186 */         return "GATT MORE";
/*     */       case 139:
/* 188 */         return "GATT INVALID CFG";
/*     */       case 140:
/* 190 */         return "GATT SERVICE STARTED";
/*     */       case 141:
/* 192 */         return "GATT ENCRYPTED NO MITM";
/*     */       case 142:
/* 194 */         return "GATT NOT ENCRYPTED";
/*     */       case 143:
/* 196 */         return "GATT CONGESTED";
/*     */       case 253:
/* 198 */         return "GATT CCCD CFG ERROR";
/*     */       case 254:
/* 200 */         return "GATT PROCEDURE IN PROGRESS";
/*     */       case 255:
/* 202 */         return "GATT VALUE OUT OF RANGE";
/*     */       case 257:
/* 204 */         return "TOO MANY OPEN CONNECTIONS";
/*     */     } 
/* 206 */     return "UNKNOWN (" + error + ")";
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\error\GattError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */