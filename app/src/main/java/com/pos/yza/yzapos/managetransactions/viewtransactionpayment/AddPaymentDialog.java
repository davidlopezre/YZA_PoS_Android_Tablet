package com.pos.yza.yzapos.managetransactions.viewtransactionpayment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.newtransaction.cart.RemoveLineItemDialog;
import com.pos.yza.yzapos.util.Formatters;

public class AddPaymentDialog extends DialogFragment {

    public static final String BUNDLE_BALANCE = "BUNDLE_BALANCE";
    private double payment = 0;

    public interface DialogClickListener {
        void onDialogPositiveClick(double value);
        void onDialogNegativeClick();
    }

    // Use this instance of the interface to deliver action events
    AddPaymentDialog.DialogClickListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mListener = (AddPaymentDialog.DialogClickListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement DialogClickListener interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        Double balance;
        if (bundle != null) {
            balance = Double.parseDouble(bundle.getString(BUNDLE_BALANCE, getString(R.string.quantity_dialog_default_clicked)));
        } else {
            balance = 0.0;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_payment, null);
        builder.setView(dialogView);

        final TextView balanceView = dialogView.findViewById(R.id.balance);
        balanceView.setText(getString(R.string.balance) + ": " +
                Formatters.amountFormat.format(balance));

        final TextView changeView = dialogView.findViewById(R.id.change_to_give);
        changeView.setText(getString(R.string.change_to_give) + ": " +
                Formatters.amountFormat.format(0));

        final EditText editTextAmount = dialogView.findViewById(R.id.edittext_amount);
        editTextAmount.setSelectAllOnFocus(true);
        editTextAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    payment = Double.parseDouble(editTextAmount.getText().toString());
                    double change = Math.max(payment - balance, 0);
                    changeView.setText(getString(R.string.change_to_give) + ": " +
                                       Formatters.amountFormat.format(change));
                }
                return false;
            }
        });
        editTextAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("focus", "focus lost");
                    payment = Double.parseDouble(editTextAmount.getText().toString());
                    double change = Math.max(payment - balance, 0);;
                    changeView.setText(getString(R.string.change_to_give) + ": " +
                            Formatters.amountFormat.format(change));
                } else {
                    Log.d("focus", "focus");
                }
            }
        });

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onDialogPositiveClick(payment);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onDialogNegativeClick();
            }
        });

        // Create the AlertDialog object and return it
        Dialog dialog = builder.create();
        if (dialog == null) {
            super.setShowsDialog(false);
        }

        return dialog;
    }

}
