package com.pos.yza.yzapos.adminoptions.additem;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.source.ProductsRepository;
import com.pos.yza.yzapos.data.source.remote.ProductsRemoteDataSource;

/**
 * Created by Dlolpez on 31/12/17.
 */

public class AddItemPresenter implements AddItemContract.Presenter {
    private final AddItemContract.View mAddItemView;

    private final ProductsRepository mProductsRepository;

    public AddItemPresenter(@NonNull ProductsRepository productsRepository,
                            @NonNull AddItemContract.View view){
        mProductsRepository = productsRepository;
        mAddItemView = view;

        mAddItemView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void confirmItem(String unitOfMeasure, String unitPrice) {
        Log.i("saveItem", "in presenter");
        mProductsRepository.saveProduct(new Product(42.0, "lt", ""));
    }

    @Override
    public void changeItemProperties() {

    }
}
