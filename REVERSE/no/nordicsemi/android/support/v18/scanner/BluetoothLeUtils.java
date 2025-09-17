/*     */ package no.nordicsemi.android.support.v18.scanner;
/*     */ 
/*     */ import android.util.SparseArray;
/*     */ import androidx.annotation.Nullable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BluetoothLeUtils
/*     */ {
/*     */   static String toString(@Nullable SparseArray<byte[]> array) {
/*  39 */     if (array == null) {
/*  40 */       return "null";
/*     */     }
/*  42 */     if (array.size() == 0) {
/*  43 */       return "{}";
/*     */     }
/*  45 */     StringBuilder buffer = new StringBuilder();
/*  46 */     buffer.append('{');
/*  47 */     for (int i = 0; i < array.size(); i++) {
/*  48 */       buffer.append(array.keyAt(i)).append("=").append(Arrays.toString((byte[])array.valueAt(i)));
/*     */     }
/*  50 */     buffer.append('}');
/*  51 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> String toString(@Nullable Map<T, byte[]> map) {
/*  58 */     if (map == null) {
/*  59 */       return "null";
/*     */     }
/*  61 */     if (map.isEmpty()) {
/*  62 */       return "{}";
/*     */     }
/*  64 */     StringBuilder buffer = new StringBuilder();
/*  65 */     buffer.append('{');
/*  66 */     Iterator<Map.Entry<T, byte[]>> it = map.entrySet().iterator();
/*  67 */     while (it.hasNext()) {
/*  68 */       Map.Entry<T, byte[]> entry = it.next();
/*  69 */       Object key = entry.getKey();
/*     */       
/*  71 */       buffer.append(key).append("=").append(Arrays.toString(map.get(key)));
/*  72 */       if (it.hasNext()) {
/*  73 */         buffer.append(", ");
/*     */       }
/*     */     } 
/*  76 */     buffer.append('}');
/*  77 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean equals(@Nullable SparseArray<byte[]> array, @Nullable SparseArray<byte[]> otherArray) {
/*  85 */     if (array == otherArray) {
/*  86 */       return true;
/*     */     }
/*  88 */     if (array == null || otherArray == null) {
/*  89 */       return false;
/*     */     }
/*  91 */     if (array.size() != otherArray.size()) {
/*  92 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  96 */     for (int i = 0; i < array.size(); i++) {
/*  97 */       if (array.keyAt(i) != otherArray.keyAt(i) || 
/*  98 */         !Arrays.equals((byte[])array.valueAt(i), (byte[])otherArray.valueAt(i))) {
/*  99 */         return false;
/*     */       }
/*     */     } 
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> boolean equals(@Nullable Map<T, byte[]> map, Map<T, byte[]> otherMap) {
/* 109 */     if (map == otherMap) {
/* 110 */       return true;
/*     */     }
/* 112 */     if (map == null || otherMap == null) {
/* 113 */       return false;
/*     */     }
/* 115 */     if (map.size() != otherMap.size()) {
/* 116 */       return false;
/*     */     }
/* 118 */     Set<T> keys = map.keySet();
/* 119 */     if (!keys.equals(otherMap.keySet())) {
/* 120 */       return false;
/*     */     }
/* 122 */     for (T key : keys) {
/* 123 */       if (!Objects.deepEquals(map.get(key), otherMap.get(key))) {
/* 124 */         return false;
/*     */       }
/*     */     } 
/* 127 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\BluetoothLeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */