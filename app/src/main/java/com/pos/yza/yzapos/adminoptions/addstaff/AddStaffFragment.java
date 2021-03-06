package com.pos.yza.yzapos.adminoptions.addstaff;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.util.DialogFragmentUtils;

public class AddStaffFragment extends DialogFragment implements AddStaffContract.View {
    private AddStaffContract.Presenter mPresenter;

    public AddStaffFragment(){

    }

    public static AddStaffFragment newInstance(){
        return new AddStaffFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull AddStaffContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_add_staff, container,
                false);

        final EditText editTextName = (EditText) root.findViewById(R.id.name);
        final EditText editTextSurName = (EditText) root.findViewById(R.id.surname);
        final EditText editTextPhone = (EditText) root.findViewById(R.id.phone_number);
        final EditText editTextEmail = (EditText) root.findViewById(R.id.email);
        final EditText editTextAddress = (EditText) root.findViewById(R.id.home_address);

        Button button = (Button) root.findViewById(R.id.button_add_staff);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String surname = editTextSurName.getText().toString();
                String phone = editTextPhone.getText().toString();
                String email = editTextEmail.getText().toString();
                String address = editTextAddress.getText().toString();
                mPresenter.confirmStaffMember(name, surname, phone, email, address);
            }
        });

        return root;
    }

    @Override
    public void showFeedback() {
        DialogFragmentUtils.giveCreatedFeedback(this, getContext(), "Staff");
    }
}
