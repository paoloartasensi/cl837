package com.android.chileaf.util;

import android.os.Build.VERSION;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LogUtil {
   private static final LogUtil.Tree DEBUG_TREE = new LogUtil.DebugTree();

   public static void v(@NonNull String message, Object... args) {
      DEBUG_TREE.v(message, args);
   }

   public static void v(Throwable t, @NonNull String message, Object... args) {
      DEBUG_TREE.v(t, message, args);
   }

   public static void v(Throwable t) {
      DEBUG_TREE.v(t);
   }

   public static void d(@NonNull String message, Object... args) {
      DEBUG_TREE.d(message, args);
   }

   public static void d(Throwable t, @NonNull String message, Object... args) {
      DEBUG_TREE.d(t, message, args);
   }

   public static void d(Throwable t) {
      DEBUG_TREE.d(t);
   }

   public static void i(@NonNull String message, Object... args) {
      DEBUG_TREE.i(message, args);
   }

   public static void i(Throwable t, @NonNull String message, Object... args) {
      DEBUG_TREE.i(t, message, args);
   }

   public static void i(Throwable t) {
      DEBUG_TREE.i(t);
   }

   public static void w(@NonNull String message, Object... args) {
      DEBUG_TREE.w(message, args);
   }

   public static void w(Throwable t, @NonNull String message, Object... args) {
      DEBUG_TREE.w(t, message, args);
   }

   public static void w(Throwable t) {
      DEBUG_TREE.w(t);
   }

   public static void e(@NonNull String message, Object... args) {
      DEBUG_TREE.e(message, args);
   }

   public static void e(Throwable t, @NonNull String message, Object... args) {
      DEBUG_TREE.e(t, message, args);
   }

   public static void e(Throwable t) {
      DEBUG_TREE.e(t);
   }

   public static void wtf(@NonNull String message, Object... args) {
      DEBUG_TREE.wtf(message, args);
   }

   public static void wtf(Throwable t, @NonNull String message, Object... args) {
      DEBUG_TREE.wtf(t, message, args);
   }

   public static void wtf(Throwable t) {
      DEBUG_TREE.wtf(t);
   }

   public static void log(int invoke, int priority, @NonNull String message, Object... args) {
      DEBUG_TREE.log(invoke, priority, message, args);
   }

   public static void log(int priority, @NonNull String message, Object... args) {
      DEBUG_TREE.log(priority, message, args);
   }

   public static void log(int priority, Throwable t, @NonNull String message, Object... args) {
      DEBUG_TREE.log(priority, t, message, args);
   }

   public static void log(int priority, Throwable t) {
      DEBUG_TREE.log(priority, t);
   }

   @NonNull
   public static LogUtil.Tree tag(String tag) {
      DEBUG_TREE.explicitTag.set(tag);
      return DEBUG_TREE;
   }

   public static void setDebug(boolean debug) {
      DEBUG_TREE.setDebug(debug);
   }

   private LogUtil() {
      throw new AssertionError("No instances.");
   }

   public static class DebugTree extends LogUtil.Tree {
      private static final int MAX_LOG_LENGTH = 4000;
      private static final int MAX_TAG_LENGTH = 23;
      private static final int CALL_STACK_INDEX = 5;
      private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");

      @Nullable
      protected String createStackElementTag(@NonNull StackTraceElement element) {
         String tag = element.getClassName();
         Matcher m = ANONYMOUS_CLASS.matcher(tag);
         if (m.find()) {
            tag = m.replaceAll("");
         }

         tag = tag.substring(tag.lastIndexOf(46) + 1);
         return tag.length() > 23 && VERSION.SDK_INT < 24 ? tag.substring(0, 23) : tag;
      }

      final String getTag() {
         String tag = super.getTag();
         if (tag != null) {
            return tag;
         } else {
            StackTraceElement[] stackTrace = (new Throwable()).getStackTrace();
            if (stackTrace.length <= 5) {
               throw new IllegalStateException("Synthetic stacktrace didn't have enough elements: are you using proguard?");
            } else {
               return this.createStackElementTag(stackTrace[5]);
            }
         }
      }

      protected void log(int priority, String tag, @NonNull String message, Throwable t) {
         if (message.length() < 4000) {
            if (priority == 7) {
               Log.wtf(tag, message);
            } else {
               Log.println(priority, tag, message);
            }

         } else {
            int i = 0;

            int end;
            for(int length = message.length(); i < length; i = end + 1) {
               int newline = message.indexOf(10, i);
               newline = newline != -1 ? newline : length;

               do {
                  end = Math.min(newline, i + 4000);
                  String part = message.substring(i, end);
                  if (priority == 7) {
                     Log.wtf(tag, part);
                  } else {
                     Log.println(priority, tag, part);
                  }

                  i = end;
               } while(end < newline);
            }

         }
      }
   }

   public abstract static class Tree {
      private boolean isDebug;
      private final ThreadLocal<String> explicitTag = new ThreadLocal();

      @Nullable
      String getTag() {
         String tag = (String)this.explicitTag.get();
         if (tag != null) {
            this.explicitTag.remove();
         }

         return tag;
      }

      public void v(String message, Object... args) {
         this.prepareLog(5, 2, (Throwable)null, message, args);
      }

      public void v(Throwable t, String message, Object... args) {
         this.prepareLog(5, 2, t, message, args);
      }

      public void v(Throwable t) {
         this.prepareLog(5, 2, t, (String)null);
      }

      public void d(String message, Object... args) {
         this.prepareLog(5, 3, (Throwable)null, message, args);
      }

      public void d(Throwable t, String message, Object... args) {
         this.prepareLog(5, 3, t, message, args);
      }

      public void d(Throwable t) {
         this.prepareLog(5, 3, t, (String)null);
      }

      public void i(String message, Object... args) {
         this.prepareLog(5, 4, (Throwable)null, message, args);
      }

      public void i(Throwable t, String message, Object... args) {
         this.prepareLog(5, 4, t, message, args);
      }

      public void i(Throwable t) {
         this.prepareLog(5, 4, t, (String)null);
      }

      public void w(String message, Object... args) {
         this.prepareLog(5, 5, (Throwable)null, message, args);
      }

      public void w(Throwable t, String message, Object... args) {
         this.prepareLog(5, 5, t, message, args);
      }

      public void w(Throwable t) {
         this.prepareLog(5, 5, t, (String)null);
      }

      public void e(String message, Object... args) {
         this.prepareLog(5, 6, (Throwable)null, message, args);
      }

      public void e(Throwable t, String message, Object... args) {
         this.prepareLog(5, 6, t, message, args);
      }

      public void e(Throwable t) {
         this.prepareLog(5, 6, t, (String)null);
      }

      public void wtf(String message, Object... args) {
         this.prepareLog(5, 7, (Throwable)null, message, args);
      }

      public void wtf(Throwable t, String message, Object... args) {
         this.prepareLog(5, 7, t, message, args);
      }

      public void wtf(Throwable t) {
         this.prepareLog(5, 7, t, (String)null);
      }

      public void log(int invoke, int priority, String message, Object... args) {
         this.prepareLog(invoke, priority, (Throwable)null, message, args);
      }

      public void log(int priority, String message, Object... args) {
         this.prepareLog(5, priority, (Throwable)null, message, args);
      }

      public void log(int priority, Throwable t, String message, Object... args) {
         this.prepareLog(5, priority, t, message, args);
      }

      public void log(int priority, Throwable t) {
         this.prepareLog(5, priority, t, (String)null);
      }

      public void setDebug(boolean debug) {
         this.isDebug = debug;
      }

      private void prepareLog(int invoke, int priority, Throwable t, String message, Object... args) {
         if (this.isDebug) {
            if (message != null && message.length() == 0) {
               message = null;
            }

            if (message == null) {
               if (t == null) {
                  return;
               }

               message = this.getStackTraceString(t);
            } else {
               if (args != null && args.length > 0) {
                  message = this.formatMessage(message, args);
               }

               if (t != null) {
                  message = message + "\n" + this.getStackTraceString(t);
               }
            }

            String tag = null;
            String prefix = null;

            try {
               StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[invoke];
               String fileName = stackTrace.getFileName();
               int lineNumber = stackTrace.getLineNumber();
               tag = fileName.substring(0, fileName.lastIndexOf("."));
               prefix = "(" + fileName + ":" + lineNumber + ") ";
            } catch (Exception var11) {
               var11.printStackTrace();
            }

            if (tag == null) {
               tag = this.getTag();
            }

            if (prefix != null) {
               message = prefix + message;
            }

            this.log(priority, tag, message, t);
         }
      }

      private String formatMessage(@NonNull String message, @NonNull Object[] args) {
         return String.format(message, args);
      }

      private String getStackTraceString(Throwable t) {
         StringWriter sw = new StringWriter(256);
         PrintWriter pw = new PrintWriter(sw, false);
         t.printStackTrace(pw);
         pw.flush();
         return sw.toString();
      }

      protected abstract void log(int priority, @Nullable String tag, @NonNull String message, @Nullable Throwable t);
   }
}
