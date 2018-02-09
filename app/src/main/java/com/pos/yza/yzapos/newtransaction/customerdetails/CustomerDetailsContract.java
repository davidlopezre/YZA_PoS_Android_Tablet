package com.pos.yza.yzapos.newtransaction.customerdetails;

import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

/**
 * Created by Dlolpez on 18/1/18.
 */

public interface CustomerDetailsContract {
    interface View extends BaseView<CustomerDetailsContract.Presenter> {
        void showPayment();

    }

    interface Presenter extends BasePresenter {
        void goToPayment();
        void confirm();

    }
}
