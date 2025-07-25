package com.jeremyliao.liveeventbus.ipc.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.jeremyliao.liveeventbus.ipc.IpcConst;
import com.jeremyliao.liveeventbus.ipc.decode.IDecoder;
import com.jeremyliao.liveeventbus.ipc.decode.ValueDecoder;
import com.jeremyliao.liveeventbus.ipc.json.JsonConverter;

public class LebIpcReceiver extends BroadcastReceiver {
    private IDecoder decoder;

    public LebIpcReceiver(JsonConverter jsonConverter) {
        this.decoder = new ValueDecoder(jsonConverter);
    }

    public void onReceive(Context context, Intent intent) {
        if (IpcConst.ACTION.equals(intent.getAction())) {
            try {
                String stringExtra = intent.getStringExtra(IpcConst.KEY);
                Object decode = this.decoder.decode(intent);
                if (stringExtra != null) {
                    LiveEventBus.get(stringExtra).post(decode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setJsonConverter(JsonConverter jsonConverter) {
        this.decoder = new ValueDecoder(jsonConverter);
    }
}
