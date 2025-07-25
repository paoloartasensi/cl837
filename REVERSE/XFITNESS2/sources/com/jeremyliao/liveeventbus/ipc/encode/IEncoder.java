package com.jeremyliao.liveeventbus.ipc.encode;

import android.content.Intent;

public interface IEncoder {
    void encode(Intent intent, Object obj);
}
