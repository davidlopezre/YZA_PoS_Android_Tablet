package com.pos.yza.yzapos.adminoptions.addstaff;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pos.yza.yzapos.adminoptions.additem.AddItemContract;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.ProductProperty;
import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.data.source.StaffRepository;

import java.util.ArrayList;

/**
 * Created by Dlolpez on 31/12/17.
 */

public class AddStaffPresenter implements AddStaffContract.Presenter {
    private final AddStaffContract.View mAddStaffView;

    private final StaffRepository mStaffRepository;

    public AddStaffPresenter(@NonNull StaffRepository staffRepository,
                             @NonNull AddStaffContract.View view){
        mStaffRepository = staffRepository;
        mAddStaffView = view;

        mAddStaffView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void confirmStaffMember(String name, String surname, String phone, String email,
                                   String address) {
        Log.i("saveStaff", "in presenter");
        Staff staffMember = new Staff(name, surname, phone, email, address);
        Log.i("saveStaff", "name is " + name);
        Log.i("saveStaff", staffMember.toString());
        mStaffRepository.saveStaff(staffMember);
        mAddStaffView.confirmStaffMemberFeedback();
    }
}
