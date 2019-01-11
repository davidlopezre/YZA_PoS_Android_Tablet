package com.pos.yza.yzapos.newtransaction.staffandcustomerdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.newtransaction.OnFragmentInteractionListener;

import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class StaffAndCustomerDetailsFragment extends Fragment implements StaffAndCustomerDetailsContract.View {

    StaffAndCustomerDetailsContract.Presenter mPresenter;
    private OnFragmentInteractionListener mListener;
    private HashMap<String,String> staffAndCustomer = new HashMap<>();
    private ArrayAdapter<Staff> mSpinnerAdapter;

    public StaffAndCustomerDetailsFragment() {
        // Required empty public constructor
    }

    public static StaffAndCustomerDetailsFragment newInstance() {
        return new StaffAndCustomerDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mListAdapter = new TasksAdapter(new ArrayList<Task>(0), mItemListener);
        mSpinnerAdapter = new ArrayAdapter<>(getActivity(),
                        R.layout.spinner_item);
    }

    @Override
    public void setPresenter(@NonNull StaffAndCustomerDetailsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public void setUpSpinnerAdapter(List<Staff> content){
        if (mSpinnerAdapter.getCount() == 0) {
            mSpinnerAdapter.addAll(content);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_staff_and_customer, container, false);

        Spinner spinner = (Spinner) root.findViewById(R.id.staff_selection);
        spinner.setAdapter(mSpinnerAdapter);

        final EditText editTextFirstName = root.findViewById(R.id.editText_firstname);

        final EditText editTextSurname = root.findViewById(R.id.editText_surname);

        Button next = (Button) root.findViewById(R.id.button_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                staffAndCustomer.put("firstname", editTextFirstName.getText().toString());
                staffAndCustomer.put("surname", editTextSurname.getText().toString());
                Staff staff = (Staff) spinner.getSelectedItem();
                staffAndCustomer.put("staff_id", staff.getStaffId() + "");
                Log.i("staff selected", staffAndCustomer.get("staff_id"));
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
        mListener.onFragmentMessage("CUSTOMER_DETAILS", staffAndCustomer);
    }
}
