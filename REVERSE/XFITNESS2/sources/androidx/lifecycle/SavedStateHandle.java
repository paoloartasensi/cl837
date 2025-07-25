package androidx.lifecycle;

import android.annotation.SuppressLint;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import androidx.savedstate.SavedStateRegistry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class SavedStateHandle {
    private static final Class[] ACCEPTABLE_CLASSES;
    private static final String KEYS = "keys";
    private static final String VALUES = "values";
    private final Map<String, SavingStateLiveData<?>> mLiveDatas;
    final Map<String, Object> mRegular;
    private final SavedStateRegistry.b mSavedStateProvider;

    static {
        Class[] clsArr = new Class[29];
        clsArr[0] = Boolean.TYPE;
        clsArr[1] = boolean[].class;
        clsArr[2] = Double.TYPE;
        clsArr[3] = double[].class;
        clsArr[4] = Integer.TYPE;
        clsArr[5] = int[].class;
        clsArr[6] = Long.TYPE;
        clsArr[7] = long[].class;
        clsArr[8] = String.class;
        clsArr[9] = String[].class;
        clsArr[10] = Binder.class;
        clsArr[11] = Bundle.class;
        clsArr[12] = Byte.TYPE;
        clsArr[13] = byte[].class;
        clsArr[14] = Character.TYPE;
        clsArr[15] = char[].class;
        clsArr[16] = CharSequence.class;
        clsArr[17] = CharSequence[].class;
        clsArr[18] = ArrayList.class;
        clsArr[19] = Float.TYPE;
        clsArr[20] = float[].class;
        clsArr[21] = Parcelable.class;
        clsArr[22] = Parcelable[].class;
        clsArr[23] = Serializable.class;
        clsArr[24] = Short.TYPE;
        clsArr[25] = short[].class;
        clsArr[26] = SparseArray.class;
        clsArr[27] = Build.VERSION.SDK_INT >= 21 ? Size.class : Integer.TYPE;
        clsArr[28] = Build.VERSION.SDK_INT >= 21 ? SizeF.class : Integer.TYPE;
        ACCEPTABLE_CLASSES = clsArr;
    }

    public SavedStateHandle(Map<String, Object> map) {
        this.mLiveDatas = new HashMap();
        this.mSavedStateProvider = new SavedStateRegistry.b() {
            public Bundle saveState() {
                Set<String> keySet = SavedStateHandle.this.mRegular.keySet();
                ArrayList arrayList = new ArrayList(keySet.size());
                ArrayList arrayList2 = new ArrayList(arrayList.size());
                for (String next : keySet) {
                    arrayList.add(next);
                    arrayList2.add(SavedStateHandle.this.mRegular.get(next));
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(SavedStateHandle.KEYS, arrayList);
                bundle.putParcelableArrayList(SavedStateHandle.VALUES, arrayList2);
                return bundle;
            }
        };
        this.mRegular = new HashMap(map);
    }

    static SavedStateHandle createHandle(Bundle bundle, Bundle bundle2) {
        if (bundle == null && bundle2 == null) {
            return new SavedStateHandle();
        }
        HashMap hashMap = new HashMap();
        if (bundle2 != null) {
            for (String str : bundle2.keySet()) {
                hashMap.put(str, bundle2.get(str));
            }
        }
        if (bundle == null) {
            return new SavedStateHandle(hashMap);
        }
        ArrayList parcelableArrayList = bundle.getParcelableArrayList(KEYS);
        ArrayList parcelableArrayList2 = bundle.getParcelableArrayList(VALUES);
        if (parcelableArrayList == null || parcelableArrayList2 == null || parcelableArrayList.size() != parcelableArrayList2.size()) {
            throw new IllegalStateException("Invalid bundle passed as restored state");
        }
        for (int i2 = 0; i2 < parcelableArrayList.size(); i2++) {
            hashMap.put((String) parcelableArrayList.get(i2), parcelableArrayList2.get(i2));
        }
        return new SavedStateHandle(hashMap);
    }

    private <T> MutableLiveData<T> getLiveDataInternal(String str, boolean z, T t) {
        SavingStateLiveData savingStateLiveData;
        MutableLiveData<T> mutableLiveData = this.mLiveDatas.get(str);
        if (mutableLiveData != null) {
            return mutableLiveData;
        }
        if (this.mRegular.containsKey(str)) {
            savingStateLiveData = new SavingStateLiveData(this, str, this.mRegular.get(str));
        } else if (z) {
            savingStateLiveData = new SavingStateLiveData(this, str, t);
        } else {
            savingStateLiveData = new SavingStateLiveData(this, str);
        }
        this.mLiveDatas.put(str, savingStateLiveData);
        return savingStateLiveData;
    }

    private static void validateValue(Object obj) {
        if (obj != null) {
            Class[] clsArr = ACCEPTABLE_CLASSES;
            int length = clsArr.length;
            int i2 = 0;
            while (i2 < length) {
                if (!clsArr[i2].isInstance(obj)) {
                    i2++;
                } else {
                    return;
                }
            }
            throw new IllegalArgumentException("Can't put value with type " + obj.getClass() + " into saved state");
        }
    }

    public boolean contains(String str) {
        return this.mRegular.containsKey(str);
    }

    public <T> T get(String str) {
        return this.mRegular.get(str);
    }

    public <T> MutableLiveData<T> getLiveData(String str) {
        return getLiveDataInternal(str, false, (Object) null);
    }

    public Set<String> keys() {
        return Collections.unmodifiableSet(this.mRegular.keySet());
    }

    public <T> T remove(String str) {
        T remove = this.mRegular.remove(str);
        SavingStateLiveData remove2 = this.mLiveDatas.remove(str);
        if (remove2 != null) {
            remove2.detach();
        }
        return remove;
    }

    /* access modifiers changed from: package-private */
    public SavedStateRegistry.b savedStateProvider() {
        return this.mSavedStateProvider;
    }

    public <T> void set(String str, T t) {
        validateValue(t);
        MutableLiveData mutableLiveData = this.mLiveDatas.get(str);
        if (mutableLiveData != null) {
            mutableLiveData.setValue(t);
        } else {
            this.mRegular.put(str, t);
        }
    }

    public <T> MutableLiveData<T> getLiveData(String str, @SuppressLint({"UnknownNullness"}) T t) {
        return getLiveDataInternal(str, true, t);
    }

    static class SavingStateLiveData<T> extends MutableLiveData<T> {
        private SavedStateHandle mHandle;
        private String mKey;

        SavingStateLiveData(SavedStateHandle savedStateHandle, String str, T t) {
            super(t);
            this.mKey = str;
            this.mHandle = savedStateHandle;
        }

        /* access modifiers changed from: package-private */
        public void detach() {
            this.mHandle = null;
        }

        public void setValue(T t) {
            SavedStateHandle savedStateHandle = this.mHandle;
            if (savedStateHandle != null) {
                savedStateHandle.mRegular.put(this.mKey, t);
            }
            super.setValue(t);
        }

        SavingStateLiveData(SavedStateHandle savedStateHandle, String str) {
            this.mKey = str;
            this.mHandle = savedStateHandle;
        }
    }

    public SavedStateHandle() {
        this.mLiveDatas = new HashMap();
        this.mSavedStateProvider = new SavedStateRegistry.b() {
            public Bundle saveState() {
                Set<String> keySet = SavedStateHandle.this.mRegular.keySet();
                ArrayList arrayList = new ArrayList(keySet.size());
                ArrayList arrayList2 = new ArrayList(arrayList.size());
                for (String next : keySet) {
                    arrayList.add(next);
                    arrayList2.add(SavedStateHandle.this.mRegular.get(next));
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(SavedStateHandle.KEYS, arrayList);
                bundle.putParcelableArrayList(SavedStateHandle.VALUES, arrayList2);
                return bundle;
            }
        };
        this.mRegular = new HashMap();
    }
}
