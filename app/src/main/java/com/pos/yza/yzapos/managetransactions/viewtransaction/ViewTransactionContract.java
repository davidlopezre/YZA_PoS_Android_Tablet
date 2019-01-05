package com.pos.yza.yzapos.managetransactions.viewtransaction;

import com.pos.yza.yzapos.data.representations.LineItem;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

public interface ViewTransactionContract {
    interface View extends BaseView<ViewTransactionContract.Presenter> {
        void setLineItems(List<LineItem> lineItems);
    }

    interface Presenter extends BasePresenter {

    }

}
