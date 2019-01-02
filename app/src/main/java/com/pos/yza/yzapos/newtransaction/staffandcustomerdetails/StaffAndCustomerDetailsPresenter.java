package com.pos.yza.yzapos.newtransaction.staffandcustomerdetails;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pos.yza.yzapos.SessionStorage;
import com.pos.yza.yzapos.data.representations.Staff;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Lopez on 18/1/18.
 */

public class StaffAndCustomerDetailsPresenter implements StaffAndCustomerDetailsContract.Presenter {

    private final StaffAndCustomerDetailsContract.View mCartView;


    public StaffAndCustomerDetailsPresenter(@NonNull StaffAndCustomerDetailsContract.View mCartView) {
        this.mCartView = checkNotNull(mCartView);
        mCartView.setPresenter(this);
    }

    @Override
    public void start() {
        List<Staff> staff = SessionStorage.getInstance().getAllStaff();
        mCartView.setUpSpinnerAdapter(staff);
    }

    @Override
    public void goToPayment() {
        mCartView.showPayment();
    }

    @Override
    public void confirm() {

    }
}
