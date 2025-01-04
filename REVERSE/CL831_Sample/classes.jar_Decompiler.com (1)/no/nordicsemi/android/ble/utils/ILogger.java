package no.nordicsemi.android.ble.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public interface ILogger {
   int getMinLogPriority();

   void log(int var1, @NonNull String var2);

   void log(int var1, @StringRes int var2, @Nullable Object... var3);
}
