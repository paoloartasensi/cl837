package no.nordicsemi.android.ble.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public interface ILogger {
  int getMinLogPriority();
  
  void log(int paramInt, @NonNull String paramString);
  
  void log(int paramInt1, @StringRes int paramInt2, @Nullable Object... paramVarArgs);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\bl\\utils\ILogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */