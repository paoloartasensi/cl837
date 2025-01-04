package no.nordicsemi.android.ble.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonMerger implements DataMerger {
   private String buffer = "";

   public boolean merge(@NonNull DataStream output, @Nullable byte[] lastPacket, int index) {
      output.write(lastPacket);
      this.buffer = this.buffer + new String(lastPacket);

      try {
         new JSONObject(this.buffer);
      } catch (JSONException var7) {
         try {
            new JSONArray(this.buffer);
         } catch (JSONException var6) {
            return false;
         }
      }

      this.reset();
      return true;
   }

   public void reset() {
      this.buffer = "";
   }
}
