package com.pos.yza.yzapos.newtransaction.quantity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.Product;

/**
 * Created by Dlolpez on 14/2/18.
 */

public class QuantityDialog extends DialogFragment {
    private static final String TAG = "Quantity Dialog";
    private static int MAX_QUANTITY = 10000;

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
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.number_picker_layout, null);

        builder.setTitle(clickedProduct);
        builder.setMessage(getString(R.string.select_quantity));
        builder.setView(dialogView);

        final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
        numberPicker.setMaxValue(MAX_QUANTITY);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(false);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.i(TAG, "onValueChange: from " + Integer.toString(i) +
                        "to " +  Integer.toString(i1));
            }
        });
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i(TAG, "onClick: " + numberPicker.getValue());
                mListener.onDialogPositiveClick(numberPicker.getValue());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i(TAG, "onClick: " + numberPicker.getValue());
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
