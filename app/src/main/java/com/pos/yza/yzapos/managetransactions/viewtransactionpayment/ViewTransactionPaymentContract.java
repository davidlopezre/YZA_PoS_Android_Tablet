package com.pos.yza.yzapos.managetransactions.viewtransactionpayment;

import com.pos.yza.yzapos.data.representations.Payment;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

public interface ViewTransactionPaymentContract {
    interface View extends BaseView<ViewTransactionPaymentContract.Presenter> {
        void setPayments(List<Payment> payments);
    }

    interface Presenter extends BasePresenter {
        double getTotalDue();
        double getBalance();
        double getTotalPaid();
        void makePayment(double payment);
        void reloadTransaction();
    }
}
