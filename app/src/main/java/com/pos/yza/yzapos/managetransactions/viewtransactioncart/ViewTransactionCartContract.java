package com.pos.yza.yzapos.managetransactions.viewtransactioncart;

import com.pos.yza.yzapos.data.representations.LineItem;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

public interface ViewTransactionCartContract {
    interface View extends BaseView<ViewTransactionCartContract.Presenter> {
        void setLineItems(List<LineItem> lineItems);
    }

    interface Presenter extends BasePresenter {

    }

}
