package com.pos.yza.yzapos.newtransaction.productselection;

import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

/**
 * Created by Dlolpez on 18/1/18.
 */

public interface ProductSelectionContract {
    interface View extends BaseView<ProductSelectionContract.Presenter> {
        void showProducts(List<Product> products);

    }

    interface Presenter extends BasePresenter {


    }
}
