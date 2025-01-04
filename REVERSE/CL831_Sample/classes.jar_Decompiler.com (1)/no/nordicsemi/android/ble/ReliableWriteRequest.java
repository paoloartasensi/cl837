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

public final class ReliableWriteRequest extends RequestQueue {
   private boolean initialized;
   private boolean closed;

   @NonNull
   ReliableWriteRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
      super.setRequestHandler(requestHandler);
      return this;
   }

   @NonNull
   public ReliableWriteRequest setHandler(@Nullable Handler handler) {
      super.setHandler(handler);
      return this;
   }

   @NonNull
   public ReliableWriteRequest done(@NonNull SuccessCallback callback) {
      super.done(callback);
      return this;
   }

   @NonNull
   public ReliableWriteRequest fail(@NonNull FailCallback callback) {
      super.fail(callback);
      return this;
   }

   @NonNull
   public ReliableWriteRequest invalid(@NonNull InvalidRequestCallback callback) {
      super.invalid(callback);
      return this;
   }

   @NonNull
   public ReliableWriteRequest before(@NonNull BeforeCallback callback) {
      super.before(callback);
      return this;
   }

   @NonNull
   public ReliableWriteRequest then(@NonNull AfterCallback callback) {
      super.then(callback);
      return this;
   }

   @NonNull
   public ReliableWriteRequest timeout(@IntRange(from = 0L) long timeout) {
      super.timeout(timeout);
      return this;
   }

   @NonNull
   public ReliableWriteRequest add(@NonNull Operation operation) {
      super.add(operation);
      if (operation instanceof WriteRequest) {
         ((WriteRequest)operation).forceSplit();
      }

      return this;
   }

   public void abort() {
      this.cancel();
   }

   public int size() {
      int size = super.size();
      if (!this.initialized) {
         ++size;
      }

      if (!this.closed) {
         ++size;
      }

      return size;
   }

   Request getNext() {
      if (!this.initialized) {
         this.initialized = true;
         return newBeginReliableWriteRequest();
      } else if (super.isEmpty()) {
         this.closed = true;
         return this.cancelled ? newAbortReliableWriteRequest() : newExecuteReliableWriteRequest();
      } else {
         return super.getNext();
      }
   }

   boolean hasMore() {
      if (!this.initialized) {
         return super.hasMore();
      } else {
         return !this.closed;
      }
   }

   void cancelQueue() {
      this.cancelled = true;
      super.cancelQueue();
   }
}
