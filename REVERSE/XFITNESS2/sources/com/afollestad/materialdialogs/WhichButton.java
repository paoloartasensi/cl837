package com.afollestad.materialdialogs;

/* compiled from: WhichButton.kt */
public enum WhichButton {
    POSITIVE(0),
    NEGATIVE(1),
    NEUTRAL(2);
    
    public static final a Companion = null;
    private final int index;

    /* compiled from: WhichButton.kt */
    public static final class a {
        private a() {
        }

        public final WhichButton a(int i2) {
            if (i2 == 0) {
                return WhichButton.POSITIVE;
            }
            if (i2 == 1) {
                return WhichButton.NEGATIVE;
            }
            if (i2 == 2) {
                return WhichButton.NEUTRAL;
            }
            throw new IndexOutOfBoundsException(i2 + " is not an action button index.");
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    static {
        Companion = new a((f) null);
    }

    private WhichButton(int i2) {
        this.index = i2;
    }

    public final int getIndex() {
        return this.index;
    }
}
