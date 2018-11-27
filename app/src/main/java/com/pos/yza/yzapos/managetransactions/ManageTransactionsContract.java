package com.pos.yza.yzapos.managetransactions;

import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

/**
 * Created by David Lopez on 22/02/18.
 */

public interface ManageTransactionsContract {
    interface View extends BaseView<Presenter> {
        void showTransactions(List<Transaction> transactions);
        void showCancellationFeedback();
        void showRefundFeedback();
        void addedPaymentFeedback();
    }

    interface Presenter extends BasePresenter {
        void loadTransactions();
        void cancelTransaction(String transactionId);
        void refundTransaction(String transactionId);
        void addPaymentToTransaction();
    }
}
