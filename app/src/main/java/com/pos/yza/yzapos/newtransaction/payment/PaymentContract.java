package com.pos.yza.yzapos.newtransaction.payment;

import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

/**
 * Created by Dlolpez on 18/1/18.
 */

public interface PaymentContract {
    interface View extends BaseView<PaymentContract.Presenter> {
        void showConfirmPayment();
        void setNumOfItems(int numOfItems);
        void setTotalDue(double totalDue);
        void setChange(double change);
    }

    interface Presenter extends BasePresenter {
        void confirmPayment(Double amount);
        void setPaymentAmount(Double amount);
    }
}
