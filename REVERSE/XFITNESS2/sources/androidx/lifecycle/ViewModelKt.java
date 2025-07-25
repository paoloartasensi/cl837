package androidx.lifecycle;

import kotlin.jvm.internal.i;
import kotlinx.coroutines.b2;
import kotlinx.coroutines.g0;
import kotlinx.coroutines.k1;
import kotlinx.coroutines.u0;

/* compiled from: ViewModel.kt */
public final class ViewModelKt {
    private static final String JOB_KEY = "androidx.lifecycle.ViewModelCoroutineScope.JOB_KEY";

    public static final g0 getViewModelScope(ViewModel viewModel) {
        i.b(viewModel, "$this$viewModelScope");
        g0 g0Var = (g0) viewModel.getTag(JOB_KEY);
        if (g0Var != null) {
            return g0Var;
        }
        Object tagIfAbsent = viewModel.setTagIfAbsent(JOB_KEY, new CloseableCoroutineScope(b2.a((k1) null, 1, (Object) null).plus(u0.b().n())));
        i.a(tagIfAbsent, "setTagIfAbsent(JOB_KEY,\n…patchers.Main.immediate))");
        return (g0) tagIfAbsent;
    }
}
