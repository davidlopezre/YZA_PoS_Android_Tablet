package com.pos.yza.yzapos.newtransaction.customerdetails;

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
import com.pos.yza.yzapos.newtransaction.cart.CartContract;

import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;

public class CustomerDetailsFragment extends Fragment implements CustomerDetailsContract.View {

    CustomerDetailsContract.Presenter mPresenter;

    private OnFragmentInteractionListener mListener;

    private HashMap<String,String> customer = new HashMap<>();

    public CustomerDetailsFragment() {
        // Required empty public constructor
    }

    public static CustomerDetailsFragment newInstance() {
        return new CustomerDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mListAdapter = new TasksAdapter(new ArrayList<Task>(0), mItemListener);

    }

    @Override
    public void setPresenter(@NonNull CustomerDetailsContract.Presenter presenter) {
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
        View root = inflater.inflate(R.layout.fragment_customer, container, false);

        final EditText editTextFirstName = root.findViewById(R.id.editText_firstname);

        final EditText editTextSurname = root.findViewById(R.id.editText_surname);

        Button next = (Button) root.findViewById(R.id.button_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customer.put("firstname", editTextFirstName.getText().toString());
                customer.put("surname", editTextSurname.getText().toString());
                mPresenter.goToPayment();
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
    public void showPayment() {
        mListener.onFragmentMessage("CUSTOMER_DETAILS", customer);
    }
}
