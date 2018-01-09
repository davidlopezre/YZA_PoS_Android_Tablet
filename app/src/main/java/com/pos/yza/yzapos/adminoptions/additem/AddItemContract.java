package com.pos.yza.yzapos.adminoptions.additem;

import com.pos.yza.yzapos.data.representations.Item;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public interface AddItemContract {
    interface View extends BaseView<Presenter> {
        void showItemProperties();
    }

    interface Presenter extends BasePresenter {
        void confirmItem(String unitOfMeasure, String unitPrice);
        void changeItemProperties();
    }
}
