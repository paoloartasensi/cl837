package no.nordicsemi.android.ble;

import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.AfterCallback;
import no.nordicsemi.android.ble.callback.BeforeCallback;
import no.nordicsemi.android.ble.callback.FailCallback;
import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;

public final class ConditionalWaitRequest<T> extends AwaitingRequest<T> implements Operation {
   @NonNull
   private final ConditionalWaitRequest.Condition<T> condition;
   @Nullable
   private final T parameter;
   private boolean expected = false;

   ConditionalWaitRequest(@NonNull Request.Type type, @NonNull ConditionalWaitRequest.Condition<T> condition, @Nullable T parameter) {
      super(type);
      this.condition = condition;
      this.parameter = parameter;
   }

   @NonNull
   ConditionalWaitRequest<T> setRequestHandler(@NonNull RequestHandler requestHandler) {
      super.setRequestHandler(requestHandler);
      return this;
   }

   @NonNull
   public ConditionalWaitRequest<T> setHandler(@Nullable Handler handler) {
      super.setHandler(handler);
      return this;
   }

   @NonNull
   public ConditionalWaitRequest<T> done(@NonNull SuccessCallback callback) {
      super.done(callback);
      return this;
   }

   @NonNull
   public ConditionalWaitRequest<T> fail(@NonNull FailCallback callback) {
      super.fail(callback);
      return this;
   }

   @NonNull
   public ConditionalWaitRequest<T> invalid(@NonNull InvalidRequestCallback callback) {
      super.invalid(callback);
      return this;
   }

   @NonNull
   public ConditionalWaitRequest<T> before(@NonNull BeforeCallback callback) {
      super.before(callback);
      return this;
   }

   @NonNull
   public ConditionalWaitRequest<T> then(@NonNull AfterCallback callback) {
      super.then(callback);
      return this;
   }

   @NonNull
   public ConditionalWaitRequest<T> negate() {
      this.expected = true;
      return this;
   }

   boolean isFulfilled() {
      try {
         return this.condition.predicate(this.parameter) == this.expected;
      } catch (Exception var2) {
         Log.e("ConditionalWaitRequest", "Error while checking predicate", var2);
         return true;
      }
   }

   public interface Condition<T> {
      boolean predicate(@Nullable T var1);
   }
}
