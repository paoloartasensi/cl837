package no.nordicsemi.android.support.v18.scanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class ScanCallbackWrapperSet<W extends BluetoothLeScannerCompat.ScanCallbackWrapper> {
   @NonNull
   private final Set<W> wrappers = new HashSet();

   @NonNull
   public Set<W> values() {
      return this.wrappers;
   }

   boolean isEmpty() {
      return this.wrappers.isEmpty();
   }

   void add(@NonNull W wrapper) {
      this.wrappers.add(wrapper);
   }

   boolean contains(@NonNull ScanCallback callback) {
      Iterator var2 = this.wrappers.iterator();

      while(var2.hasNext()) {
         W wrapper = (BluetoothLeScannerCompat.ScanCallbackWrapper)var2.next();
         if (wrapper.scanCallback == callback) {
            return true;
         }

         if (wrapper.scanCallback instanceof UserScanCallbackWrapper) {
            UserScanCallbackWrapper callbackWrapper = (UserScanCallbackWrapper)wrapper.scanCallback;
            if (callbackWrapper.get() == callback) {
               return true;
            }
         }
      }

      return false;
   }

   @Nullable
   W get(@NonNull ScanCallback callback) {
      Iterator var2 = this.wrappers.iterator();

      while(var2.hasNext()) {
         W wrapper = (BluetoothLeScannerCompat.ScanCallbackWrapper)var2.next();
         if (wrapper.scanCallback == callback) {
            return wrapper;
         }

         if (wrapper.scanCallback instanceof UserScanCallbackWrapper) {
            UserScanCallbackWrapper callbackWrapper = (UserScanCallbackWrapper)wrapper.scanCallback;
            if (callbackWrapper.get() == callback) {
               return wrapper;
            }
         }
      }

      return null;
   }

   @Nullable
   W remove(@NonNull ScanCallback callback) {
      Iterator var2 = this.wrappers.iterator();

      while(var2.hasNext()) {
         W wrapper = (BluetoothLeScannerCompat.ScanCallbackWrapper)var2.next();
         if (wrapper.scanCallback == callback) {
            return wrapper;
         }

         if (wrapper.scanCallback instanceof UserScanCallbackWrapper) {
            UserScanCallbackWrapper callbackWrapper = (UserScanCallbackWrapper)wrapper.scanCallback;
            if (callbackWrapper.get() == callback) {
               this.wrappers.remove(wrapper);
               return wrapper;
            }
         }
      }

      this.cleanUp();
      return null;
   }

   private void cleanUp() {
      List<W> deadWrappers = new LinkedList();
      Iterator var2 = this.wrappers.iterator();

      BluetoothLeScannerCompat.ScanCallbackWrapper wrapper;
      while(var2.hasNext()) {
         wrapper = (BluetoothLeScannerCompat.ScanCallbackWrapper)var2.next();
         if (wrapper.scanCallback instanceof UserScanCallbackWrapper) {
            UserScanCallbackWrapper callbackWrapper = (UserScanCallbackWrapper)wrapper.scanCallback;
            if (callbackWrapper.isDead()) {
               deadWrappers.add(wrapper);
            }
         }
      }

      var2 = deadWrappers.iterator();

      while(var2.hasNext()) {
         wrapper = (BluetoothLeScannerCompat.ScanCallbackWrapper)var2.next();
         this.wrappers.remove(wrapper);
      }

   }
}
