package com.google.gson;

public enum LongSerializationPolicy {
    DEFAULT {
        public i serialize(Long l) {
            return new l((Number) l);
        }
    },
    STRING {
        public i serialize(Long l) {
            return new l(String.valueOf(l));
        }
    };

    public abstract i serialize(Long l);
}
