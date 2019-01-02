package com.pos.yza.yzapos.newtransaction.cart;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.pos.yza.yzapos.R;

/**
 * Created by beyondinfinity on 3/1/19.
 */

public class RemoveLineItemDialog extends DialogFragment {
    private static final String TAG = "RemoveLineItem Dialog";

    public interface DialogClickListener {
        public void onDialogPositiveClick(int value);
        public void onDialogNegativeClick();
    }

    // Use this instance of the interface to deliver action events
    DialogClickListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mListener = (DialogClickListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement DialogClickListener interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Getting the name of the product clicked, provided by the ProductSelectionFragment
        Bundle bundle = this.getArguments();
        String clickedProduct;
        if (bundle != null) {
            clickedProduct = bundle.getString("CLICKED", getString(R.string.quantity_dialog_default_clicked));
        } else {
            clickedProduct = getString(R.string.quantity_dialog_default_clicked);
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(clickedProduct);
        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onDialogPositiveClick(i);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onDialogNegativeClick();
            }
        });

        // Create the AlertDialog object and return it
        Log.i(TAG, "onCreateDialog ran");

        Dialog dialog = builder.create();
        if (dialog == null) {
            super.setShowsDialog(false);
        }

        return dialog;
    }
}

