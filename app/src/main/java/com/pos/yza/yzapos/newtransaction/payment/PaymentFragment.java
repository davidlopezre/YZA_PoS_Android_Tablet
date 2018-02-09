package com.pos.yza.yzapos.newtransaction.payment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.newtransaction.OnFragmentInteractionListener;
import com.pos.yza.yzapos.newtransaction.customerdetails.CustomerDetailsContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class PaymentFragment extends Fragment implements PaymentContract.View {

    PaymentContract.Presenter mPresenter;

    private OnFragmentInteractionListener mListener;

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
//        mListAdapter = new TasksAdapter(new ArrayList<Task>(0), mItemListener);

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

        final EditText editTextAmount = root.findViewById(R.id.edittext_amount);

        Button next = root.findViewById(R.id.button_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = Double.parseDouble(editTextAmount.getText().toString());
                mPresenter.confirmPayment();
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
}
