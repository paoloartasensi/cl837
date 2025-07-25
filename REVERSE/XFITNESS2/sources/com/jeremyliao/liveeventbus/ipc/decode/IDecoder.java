package com.jeremyliao.liveeventbus.ipc.decode;

import android.content.Intent;

public interface IDecoder {
    Object decode(Intent intent);
}
