package no.nordicsemi.android.ble;

import android.os.Handler;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Deque;
import java.util.LinkedList;
import no.nordicsemi.android.ble.callback.AfterCallback;
import no.nordicsemi.android.ble.callback.BeforeCallback;
import no.nordicsemi.android.ble.callback.FailCallback;
import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;

public class RequestQueue extends TimeoutableRequest {
   @NonNull
   private final Deque<Request> requests = new LinkedList();

   RequestQueue() {
      super(Request.Type.SET);
   }

   @NonNull
   RequestQueue setRequestHandler(@NonNull RequestHandler requestHandler) {
      super.setRequestHandler(requestHandler);
      return this;
   }

   @NonNull
   public RequestQueue setHandler(@Nullable Handler handler) {
      super.setHandler(handler);
      return this;
   }

   @NonNull
   public RequestQueue done(@NonNull SuccessCallback callback) {
      super.done(callback);
      return this;
   }

   @NonNull
   public RequestQueue fail(@NonNull FailCallback callback) {
      super.fail(callback);
      return this;
   }

   @NonNull
   public RequestQueue invalid(@NonNull InvalidRequestCallback callback) {
      super.invalid(callback);
      return this;
   }

   @NonNull
   public RequestQueue before(@NonNull BeforeCallback callback) {
      super.before(callback);
      return this;
   }

   @NonNull
   public RequestQueue then(@NonNull AfterCallback callback) {
      super.then(callback);
      return this;
   }

   @NonNull
   public RequestQueue timeout(@IntRange(from = 0L) long timeout) {
      super.timeout(timeout);
      return this;
   }

   @NonNull
   public RequestQueue add(@NonNull Operation operation) {
      if (operation instanceof Request) {
         Request request = (Request)operation;
         if (request.enqueued) {
            throw new IllegalStateException("Request already enqueued");
         } else {
            request.internalFail(this::notifyFail);
            this.requests.add(request);
            request.enqueued = true;
            return this;
         }
      } else {
         throw new IllegalArgumentException("Operation does not extend Request");
      }
   }

   void addFirst(@NonNull Request request) {
      this.requests.addFirst(request);
   }

   @IntRange(
      from = 0L
   )
   public int size() {
      return this.requests.size();
   }

   public boolean isEmpty() {
      return this.requests.isEmpty();
   }

   public void cancel() {
      this.cancelQueue();
      super.cancel();
   }

   @Nullable
   Request getNext() {
      try {
         return (Request)this.requests.remove();
      } catch (Exception var2) {
         return null;
      }
   }

   boolean hasMore() {
      return !this.finished && !this.requests.isEmpty();
   }

   void cancelQueue() {
      this.requests.clear();
   }
}
