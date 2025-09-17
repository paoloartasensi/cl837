/*     */ package com.android.chileaf.util;
/*     */ 
/*     */ import android.os.Build;
/*     */ import android.util.Log;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LogUtil
/*     */ {
/*     */   public static void v(@NonNull String message, Object... args) {
/*  23 */     DEBUG_TREE.v(message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void v(Throwable t, @NonNull String message, Object... args) {
/*  30 */     DEBUG_TREE.v(t, message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void v(Throwable t) {
/*  37 */     DEBUG_TREE.v(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void d(@NonNull String message, Object... args) {
/*  44 */     DEBUG_TREE.d(message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void d(Throwable t, @NonNull String message, Object... args) {
/*  51 */     DEBUG_TREE.d(t, message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void d(Throwable t) {
/*  58 */     DEBUG_TREE.d(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void i(@NonNull String message, Object... args) {
/*  65 */     DEBUG_TREE.i(message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void i(Throwable t, @NonNull String message, Object... args) {
/*  72 */     DEBUG_TREE.i(t, message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void i(Throwable t) {
/*  79 */     DEBUG_TREE.i(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void w(@NonNull String message, Object... args) {
/*  86 */     DEBUG_TREE.w(message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void w(Throwable t, @NonNull String message, Object... args) {
/*  93 */     DEBUG_TREE.w(t, message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void w(Throwable t) {
/* 100 */     DEBUG_TREE.w(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void e(@NonNull String message, Object... args) {
/* 107 */     DEBUG_TREE.e(message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void e(Throwable t, @NonNull String message, Object... args) {
/* 114 */     DEBUG_TREE.e(t, message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void e(Throwable t) {
/* 121 */     DEBUG_TREE.e(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void wtf(@NonNull String message, Object... args) {
/* 128 */     DEBUG_TREE.wtf(message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void wtf(Throwable t, @NonNull String message, Object... args) {
/* 135 */     DEBUG_TREE.wtf(t, message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void wtf(Throwable t) {
/* 142 */     DEBUG_TREE.wtf(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void log(int invoke, int priority, @NonNull String message, Object... args) {
/* 149 */     DEBUG_TREE.log(invoke, priority, message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void log(int priority, @NonNull String message, Object... args) {
/* 156 */     DEBUG_TREE.log(priority, message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void log(int priority, Throwable t, @NonNull String message, Object... args) {
/* 163 */     DEBUG_TREE.log(priority, t, message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void log(int priority, Throwable t) {
/* 170 */     DEBUG_TREE.log(priority, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public static Tree tag(String tag) {
/* 178 */     DEBUG_TREE.explicitTag.set(tag);
/* 179 */     return DEBUG_TREE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDebug(boolean debug) {
/* 186 */     DEBUG_TREE.setDebug(debug);
/*     */   }
/*     */   
/*     */   private LogUtil() {
/* 190 */     throw new AssertionError("No instances.");
/*     */   }
/*     */   
/* 193 */   private static final Tree DEBUG_TREE = new DebugTree();
/*     */   
/*     */   public static abstract class Tree
/*     */   {
/*     */     private boolean isDebug;
/* 198 */     private final ThreadLocal<String> explicitTag = new ThreadLocal<>();
/*     */     
/*     */     @Nullable
/*     */     String getTag() {
/* 202 */       String tag = this.explicitTag.get();
/* 203 */       if (tag != null) {
/* 204 */         this.explicitTag.remove();
/*     */       }
/* 206 */       return tag;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void v(String message, Object... args) {
/* 213 */       prepareLog(5, 2, null, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void v(Throwable t, String message, Object... args) {
/* 220 */       prepareLog(5, 2, t, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void v(Throwable t) {
/* 227 */       prepareLog(5, 2, t, null, new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void d(String message, Object... args) {
/* 234 */       prepareLog(5, 3, null, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void d(Throwable t, String message, Object... args) {
/* 241 */       prepareLog(5, 3, t, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void d(Throwable t) {
/* 248 */       prepareLog(5, 3, t, null, new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void i(String message, Object... args) {
/* 255 */       prepareLog(5, 4, null, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void i(Throwable t, String message, Object... args) {
/* 262 */       prepareLog(5, 4, t, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void i(Throwable t) {
/* 269 */       prepareLog(5, 4, t, null, new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void w(String message, Object... args) {
/* 276 */       prepareLog(5, 5, null, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void w(Throwable t, String message, Object... args) {
/* 283 */       prepareLog(5, 5, t, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void w(Throwable t) {
/* 290 */       prepareLog(5, 5, t, null, new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void e(String message, Object... args) {
/* 297 */       prepareLog(5, 6, null, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void e(Throwable t, String message, Object... args) {
/* 304 */       prepareLog(5, 6, t, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void e(Throwable t) {
/* 311 */       prepareLog(5, 6, t, null, new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void wtf(String message, Object... args) {
/* 318 */       prepareLog(5, 7, null, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void wtf(Throwable t, String message, Object... args) {
/* 325 */       prepareLog(5, 7, t, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void wtf(Throwable t) {
/* 332 */       prepareLog(5, 7, t, null, new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void log(int invoke, int priority, String message, Object... args) {
/* 339 */       prepareLog(invoke, priority, null, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void log(int priority, String message, Object... args) {
/* 346 */       prepareLog(5, priority, null, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void log(int priority, Throwable t, String message, Object... args) {
/* 353 */       prepareLog(5, priority, t, message, args);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void log(int priority, Throwable t) {
/* 360 */       prepareLog(5, priority, t, null, new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setDebug(boolean debug) {
/* 367 */       this.isDebug = debug;
/*     */     }
/*     */ 
/*     */     
/*     */     private void prepareLog(int invoke, int priority, Throwable t, String message, Object... args) {
/* 372 */       if (!this.isDebug) {
/*     */         return;
/*     */       }
/* 375 */       if (message != null && message.length() == 0) {
/* 376 */         message = null;
/*     */       }
/* 378 */       if (message == null) {
/* 379 */         if (t == null) {
/*     */           return;
/*     */         }
/* 382 */         message = getStackTraceString(t);
/*     */       } else {
/* 384 */         if (args != null && args.length > 0) {
/* 385 */           message = formatMessage(message, args);
/*     */         }
/* 387 */         if (t != null) {
/* 388 */           message = message + "\n" + getStackTraceString(t);
/*     */         }
/*     */       } 
/* 391 */       String tag = null;
/* 392 */       String prefix = null;
/*     */       try {
/* 394 */         StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[invoke];
/* 395 */         String fileName = stackTrace.getFileName();
/* 396 */         int lineNumber = stackTrace.getLineNumber();
/* 397 */         tag = fileName.substring(0, fileName.lastIndexOf("."));
/* 398 */         prefix = "(" + fileName + ":" + lineNumber + ") ";
/* 399 */       } catch (Exception e) {
/* 400 */         e.printStackTrace();
/*     */       } 
/* 402 */       if (tag == null) {
/* 403 */         tag = getTag();
/*     */       }
/* 405 */       if (prefix != null) {
/* 406 */         message = prefix + message;
/*     */       }
/* 408 */       log(priority, tag, message, t);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String formatMessage(@NonNull String message, @NonNull Object[] args) {
/* 415 */       return String.format(message, args);
/*     */     }
/*     */ 
/*     */     
/*     */     private String getStackTraceString(Throwable t) {
/* 420 */       StringWriter sw = new StringWriter(256);
/* 421 */       PrintWriter pw = new PrintWriter(sw, false);
/* 422 */       t.printStackTrace(pw);
/* 423 */       pw.flush();
/* 424 */       return sw.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract void log(int param1Int, @Nullable String param1String1, @NonNull String param1String2, @Nullable Throwable param1Throwable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DebugTree
/*     */     extends Tree
/*     */   {
/*     */     private static final int MAX_LOG_LENGTH = 4000;
/*     */ 
/*     */     
/*     */     private static final int MAX_TAG_LENGTH = 23;
/*     */ 
/*     */     
/*     */     private static final int CALL_STACK_INDEX = 5;
/*     */ 
/*     */     
/* 446 */     private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     protected String createStackElementTag(@NonNull StackTraceElement element) {
/* 457 */       String tag = element.getClassName();
/* 458 */       Matcher m = ANONYMOUS_CLASS.matcher(tag);
/* 459 */       if (m.find()) {
/* 460 */         tag = m.replaceAll("");
/*     */       }
/* 462 */       tag = tag.substring(tag.lastIndexOf('.') + 1);
/*     */       
/* 464 */       if (tag.length() <= 23 || Build.VERSION.SDK_INT >= 24) {
/* 465 */         return tag;
/*     */       }
/* 467 */       return tag.substring(0, 23);
/*     */     }
/*     */ 
/*     */     
/*     */     final String getTag() {
/* 472 */       String tag = super.getTag();
/* 473 */       if (tag != null) {
/* 474 */         return tag;
/*     */       }
/*     */ 
/*     */       
/* 478 */       StackTraceElement[] stackTrace = (new Throwable()).getStackTrace();
/* 479 */       if (stackTrace.length <= 5) {
/* 480 */         throw new IllegalStateException("Synthetic stacktrace didn't have enough elements: are you using proguard?");
/*     */       }
/* 482 */       return createStackElementTag(stackTrace[5]);
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
/*     */     protected void log(int priority, String tag, @NonNull String message, Throwable t) {
/* 494 */       if (message.length() < 4000) {
/* 495 */         if (priority == 7) {
/* 496 */           Log.wtf(tag, message);
/*     */         } else {
/* 498 */           Log.println(priority, tag, message);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/* 503 */       for (int i = 0, length = message.length(); i < length; ) {
/* 504 */         int newline = message.indexOf('\n', i);
/* 505 */         newline = (newline != -1) ? newline : length;
/*     */         while (true) {
/* 507 */           int end = Math.min(newline, i + 4000);
/* 508 */           String part = message.substring(i, end);
/* 509 */           if (priority == 7) {
/* 510 */             Log.wtf(tag, part);
/*     */           } else {
/* 512 */             Log.println(priority, tag, part);
/*     */           } 
/* 514 */           i = end;
/* 515 */           if (i >= newline)
/*     */             i++; 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chilea\\util\LogUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */