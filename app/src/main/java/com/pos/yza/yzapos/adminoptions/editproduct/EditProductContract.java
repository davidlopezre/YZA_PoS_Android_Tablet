package com.pos.yza.yzapos.adminoptions.editproduct;

import android.widget.EditText;

import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

public interface EditProductContract {
    interface View extends BaseView<Presenter> {
        List<EditText> getPropertyEditTexts();
        void showFeedback();
    }

    interface Presenter extends BasePresenter {
        String getProductName();
        Product getProduct();
        String getProductCategoryName();
        int getProductPropertyId(int categoryPropertyId);
        String getProductPropertyValue(int categoryPropertyId);
        void saveProduct(String unitOfMeasure, String unitPrice);
    }
}
