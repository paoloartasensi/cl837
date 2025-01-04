package no.nordicsemi.android.ble;

import android.os.Handler;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.AfterCallback;
import no.nordicsemi.android.ble.callback.BeforeCallback;
import no.nordicsemi.android.ble.callback.FailCallback;
import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;

public class DisconnectRequest extends TimeoutableRequest {
   DisconnectRequest(@NonNull Request.Type type) {
      super(type);
   }

   @NonNull
   DisconnectRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
      super.setRequestHandler(requestHandler);
      return this;
   }

   @NonNull
   public DisconnectRequest setHandler(@Nullable Handler handler) {
      super.setHandler(handler);
      return this;
   }

   @NonNull
   public DisconnectRequest timeout(@IntRange(from = 0L) long timeout) {
      super.timeout(timeout);
      return this;
   }

   @NonNull
   public DisconnectRequest done(@NonNull SuccessCallback callback) {
      super.done(callback);
      return this;
   }

   @NonNull
   public DisconnectRequest fail(@NonNull FailCallback callback) {
      super.fail(callback);
      return this;
   }

   @NonNull
   public DisconnectRequest invalid(@NonNull InvalidRequestCallback callback) {
      super.invalid(callback);
      return this;
   }

   @NonNull
   public DisconnectRequest before(@NonNull BeforeCallback callback) {
      super.before(callback);
      return this;
   }

   @NonNull
   public DisconnectRequest then(@NonNull AfterCallback callback) {
      super.then(callback);
      return this;
   }
}
