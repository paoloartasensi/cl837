package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.AfterCallback;
import no.nordicsemi.android.ble.callback.BeforeCallback;
import no.nordicsemi.android.ble.callback.FailCallback;
import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;

public class ConnectRequest extends TimeoutableRequest {
   @NonNull
   private final BluetoothDevice device;
   private int preferredPhy;
   @IntRange(
      from = 0L
   )
   private int attempt = 0;
   @IntRange(
      from = 0L
   )
   private int retries = 0;
   @IntRange(
      from = 0L
   )
   private int delay = 0;
   private boolean autoConnect = false;

   ConnectRequest(@NonNull Request.Type type, @NonNull BluetoothDevice device) {
      super(type);
      this.device = device;
      this.preferredPhy = 1;
   }

   @NonNull
   ConnectRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
      super.setRequestHandler(requestHandler);
      return this;
   }

   @NonNull
   public ConnectRequest setHandler(@Nullable Handler handler) {
      super.setHandler(handler);
      return this;
   }

   @NonNull
   public ConnectRequest timeout(@IntRange(from = 0L) long timeout) {
      super.timeout(timeout);
      return this;
   }

   @NonNull
   public ConnectRequest done(@NonNull SuccessCallback callback) {
      super.done(callback);
      return this;
   }

   @NonNull
   public ConnectRequest fail(@NonNull FailCallback callback) {
      super.fail(callback);
      return this;
   }

   @NonNull
   public ConnectRequest invalid(@NonNull InvalidRequestCallback callback) {
      super.invalid(callback);
      return this;
   }

   @NonNull
   public ConnectRequest before(@NonNull BeforeCallback callback) {
      super.before(callback);
      return this;
   }

   @NonNull
   public ConnectRequest then(@NonNull AfterCallback callback) {
      super.then(callback);
      return this;
   }

   public ConnectRequest retry(@IntRange(from = 0L) int count) {
      this.retries = count;
      this.delay = 0;
      return this;
   }

   public ConnectRequest retry(@IntRange(from = 0L) int count, @IntRange(from = 0L) int delay) {
      this.retries = count;
      this.delay = delay;
      return this;
   }

   public ConnectRequest useAutoConnect(boolean autoConnect) {
      this.autoConnect = autoConnect;
      return this;
   }

   public ConnectRequest usePreferredPhy(int phy) {
      this.preferredPhy = phy;
      return this;
   }

   public void cancelPendingConnection() {
      this.cancel();
   }

   public void cancel() {
      if (!this.started) {
         this.cancelled = true;
         this.finished = true;
      } else if (!this.finished) {
         this.cancelled = true;
         this.requestHandler.cancelQueue();
      }

   }

   @NonNull
   public BluetoothDevice getDevice() {
      return this.device;
   }

   int getPreferredPhy() {
      return this.preferredPhy;
   }

   boolean canRetry() {
      if (this.retries > 0) {
         --this.retries;
         return true;
      } else {
         return false;
      }
   }

   boolean isFirstAttempt() {
      return this.attempt++ == 0;
   }

   @IntRange(
      from = 0L
   )
   int getRetryDelay() {
      return this.delay;
   }

   boolean shouldAutoConnect() {
      return this.autoConnect;
   }
}
