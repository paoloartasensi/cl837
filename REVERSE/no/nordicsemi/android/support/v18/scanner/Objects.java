/*    */ package no.nordicsemi.android.support.v18.scanner;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Objects
/*    */ {
/*    */   static boolean deepEquals(Object a, Object b) {
/* 36 */     if (a == null || b == null)
/* 37 */       return (a == b); 
/* 38 */     if (a instanceof Object[] && b instanceof Object[])
/* 39 */       return Arrays.deepEquals((Object[])a, (Object[])b); 
/* 40 */     if (a instanceof boolean[] && b instanceof boolean[])
/* 41 */       return Arrays.equals((boolean[])a, (boolean[])b); 
/* 42 */     if (a instanceof byte[] && b instanceof byte[])
/* 43 */       return Arrays.equals((byte[])a, (byte[])b); 
/* 44 */     if (a instanceof char[] && b instanceof char[])
/* 45 */       return Arrays.equals((char[])a, (char[])b); 
/* 46 */     if (a instanceof double[] && b instanceof double[])
/* 47 */       return Arrays.equals((double[])a, (double[])b); 
/* 48 */     if (a instanceof float[] && b instanceof float[])
/* 49 */       return Arrays.equals((float[])a, (float[])b); 
/* 50 */     if (a instanceof int[] && b instanceof int[])
/* 51 */       return Arrays.equals((int[])a, (int[])b); 
/* 52 */     if (a instanceof long[] && b instanceof long[])
/* 53 */       return Arrays.equals((long[])a, (long[])b); 
/* 54 */     if (a instanceof short[] && b instanceof short[]) {
/* 55 */       return Arrays.equals((short[])a, (short[])b);
/*    */     }
/* 57 */     return a.equals(b);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static boolean equals(Object a, Object b) {
/* 64 */     return (a == null) ? ((b == null)) : a.equals(b);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static int hash(Object... values) {
/* 73 */     return Arrays.hashCode(values);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static String toString(Object o) {
/* 80 */     return (o == null) ? "null" : o.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\Objects.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */