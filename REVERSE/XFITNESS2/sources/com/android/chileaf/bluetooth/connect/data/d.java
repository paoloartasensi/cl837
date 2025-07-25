package com.android.chileaf.bluetooth.connect.data;

import java.io.ByteArrayOutputStream;

/* compiled from: DataStream */
public class d {
    private final ByteArrayOutputStream a = new ByteArrayOutputStream();

    public Data a() {
        return new Data(this.a.toByteArray());
    }
}
