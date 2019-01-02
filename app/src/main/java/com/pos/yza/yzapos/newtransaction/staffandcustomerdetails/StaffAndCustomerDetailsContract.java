package com.pos.yza.yzapos.newtransaction.staffandcustomerdetails;

import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

/**
 * Created by Dlolpez on 18/1/18.
 */

public interface StaffAndCustomerDetailsContract {
    interface View extends BaseView<StaffAndCustomerDetailsContract.Presenter> {
        void showPayment();
        void setUpSpinnerAdapter(List<Staff> content);

    }

    interface Presenter extends BasePresenter {
        void goToPayment();
        void confirm();

    }
}
