package com.pos.yza.yzapos.newtransaction.payment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.newtransaction.OnFragmentInteractionListener;
import com.pos.yza.yzapos.util.Formatters;

import static com.google.common.base.Preconditions.checkNotNull;

public class PaymentFragment extends Fragment implements PaymentContract.View {

    PaymentContract.Presenter mPresenter;

    private OnFragmentInteractionListener mListener;
    private TextView numOfItemsView;
    private TextView totalDueView;
    private TextView changeView;

    double amount = 0.0;

    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(@NonNull PaymentContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_payment, container, false);

        this.numOfItemsView = root.findViewById(R.id.num_of_items);
        this.totalDueView = root.findViewById(R.id.total_due);
        this.changeView = root.findViewById(R.id.change_to_give);

        final EditText editTextAmount = root.findViewById(R.id.edittext_amount);
        editTextAmount.setSelectAllOnFocus(true);
        editTextAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    double payment = Double.parseDouble(editTextAmount.getText().toString());
                    mPresenter.setPaymentAmount(payment);
                }
                return false;
            }
        });
        editTextAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("focus", "focus lost");
                    double payment = Double.parseDouble(editTextAmount.getText().toString());
                    mPresenter.setPaymentAmount(payment);
                } else {
                    Log.d("focus", "focus");
                }
            }
        });

        Button next = root.findViewById(R.id.button_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    amount = Double.parseDouble(editTextAmount.getText().toString());
                    mPresenter.confirmPayment(amount);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showConfirmPayment() {
        mListener.onFragmentMessage("PAYMENT", amount);
    }

    @Override
    public void setNumOfItems(int numOfItems) {
        String toDisplay = getString(R.string.num_of_items) + ": " + numOfItems;
        numOfItemsView.setText(toDisplay);
    }

    @Override
    public void setTotalDue(double totalDue) {
        String toDisplay = getString(R.string.total_due) + ": " + Formatters.amountFormat.format(totalDue);
        totalDueView.setText(toDisplay);
    }

    @Override
    public void setChange(double change) {
        String toDisplay = getString(R.string.change_to_give) + ": " + Formatters.amountFormat.format(change);
        changeView.setText(toDisplay);
    }


}
