package com.chileaf.cl831.sample.dfu;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.chileaf.cl831.sample.R;


/**
 * When cancel button is pressed during uploading this fragment shows uploading cancel dialog
 */
public class UploadCancelFragment extends DialogFragment {

    private static final String TAG = "UploadCancelFragment";

    private CancelFragmentListener mListener;

    public interface CancelFragmentListener {
        void onCancelUpload();
    }

    public static UploadCancelFragment getInstance() {
        return new UploadCancelFragment();
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        try {
            mListener = (CancelFragmentListener) context;
        } catch (final ClassCastException e) {
            Log.d(TAG, "The parent Activity must implement CancelFragmentListener interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setTitle(R.string.dfu_confirmation_dialog_title).setMessage(R.string.dfu_upload_dialog_cancel_message).setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, whichButton) -> {
                    final LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getActivity());
                    final Intent pauseAction = new Intent(DfuService.BROADCAST_ACTION);
                    pauseAction.putExtra(DfuService.EXTRA_ACTION, DfuService.ACTION_ABORT);
                    manager.sendBroadcast(pauseAction);
                    mListener.onCancelUpload();
                }).setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).create();
    }

    @Override
    public void onCancel(final DialogInterface dialog) {
        final LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getActivity());
        final Intent pauseAction = new Intent(DfuService.BROADCAST_ACTION);
        pauseAction.putExtra(DfuService.EXTRA_ACTION, DfuService.ACTION_RESUME);
        manager.sendBroadcast(pauseAction);
    }
}
