package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.AfterCallback;
import no.nordicsemi.android.ble.callback.BeforeCallback;
import no.nordicsemi.android.ble.callback.FailCallback;
import no.nordicsemi.android.ble.callback.InvalidRequestCallback;
import no.nordicsemi.android.ble.callback.PhyCallback;
import no.nordicsemi.android.ble.callback.SuccessCallback;

public final class PhyRequest extends SimpleValueRequest<PhyCallback> implements Operation {
   public static final int PHY_LE_1M_MASK = 1;
   public static final int PHY_LE_2M_MASK = 2;
   public static final int PHY_LE_CODED_MASK = 4;
   public static final int PHY_OPTION_NO_PREFERRED = 0;
   public static final int PHY_OPTION_S2 = 1;
   public static final int PHY_OPTION_S8 = 2;
   private final int txPhy;
   private final int rxPhy;
   private final int phyOptions;

   PhyRequest(@NonNull Request.Type type) {
      super(type);
      this.txPhy = 0;
      this.rxPhy = 0;
      this.phyOptions = 0;
   }

   PhyRequest(@NonNull Request.Type type, int txPhy, int rxPhy, int phyOptions) {
      super(type);
      if ((txPhy & -8) > 0) {
         txPhy = 1;
      }

      if ((rxPhy & -8) > 0) {
         rxPhy = 1;
      }

      if (phyOptions < 0 || phyOptions > 2) {
         phyOptions = 0;
      }

      this.txPhy = txPhy;
      this.rxPhy = rxPhy;
      this.phyOptions = phyOptions;
   }

   @NonNull
   PhyRequest setRequestHandler(@NonNull RequestHandler requestHandler) {
      super.setRequestHandler(requestHandler);
      return this;
   }

   @NonNull
   public PhyRequest setHandler(@Nullable Handler handler) {
      super.setHandler(handler);
      return this;
   }

   @NonNull
   public PhyRequest done(@NonNull SuccessCallback callback) {
      super.done(callback);
      return this;
   }

   @NonNull
   public PhyRequest fail(@NonNull FailCallback callback) {
      super.fail(callback);
      return this;
   }

   @NonNull
   public PhyRequest invalid(@NonNull InvalidRequestCallback callback) {
      super.invalid(callback);
      return this;
   }

   @NonNull
   public PhyRequest before(@NonNull BeforeCallback callback) {
      super.before(callback);
      return this;
   }

   @NonNull
   public PhyRequest then(@NonNull AfterCallback callback) {
      super.then(callback);
      return this;
   }

   @NonNull
   public PhyRequest with(@NonNull PhyCallback callback) {
      super.with(callback);
      return this;
   }

   void notifyPhyChanged(@NonNull BluetoothDevice device, int txPhy, int rxPhy) {
      this.handler.post(() -> {
         if (this.valueCallback != null) {
            try {
               ((PhyCallback)this.valueCallback).onPhyChanged(device, txPhy, rxPhy);
            } catch (Throwable var5) {
               Log.e(TAG, "Exception in Value callback", var5);
            }
         }

      });
   }

   void notifyLegacyPhy(@NonNull BluetoothDevice device) {
      this.handler.post(() -> {
         if (this.valueCallback != null) {
            try {
               ((PhyCallback)this.valueCallback).onPhyChanged(device, 1, 1);
            } catch (Throwable var3) {
               Log.e(TAG, "Exception in Value callback", var3);
            }
         }

      });
   }

   int getPreferredTxPhy() {
      return this.txPhy;
   }

   int getPreferredRxPhy() {
      return this.rxPhy;
   }

   int getPreferredPhyOptions() {
      return this.phyOptions;
   }
}
