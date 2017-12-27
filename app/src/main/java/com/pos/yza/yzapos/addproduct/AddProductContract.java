package com.pos.yza.yzapos.addproduct;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

/**
 * Created by Dlolpez on 27/12/17.
 */

public interface AddProductContract {
    interface View extends BaseView<Presenter> {

        void showCategoryProperties();

    }

    interface Presenter extends BasePresenter {

        void createNewProduct();

        void cancel();

    }
}
