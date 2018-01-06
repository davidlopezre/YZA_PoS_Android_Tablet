package com.pos.yza.yzapos.adminoptions.additem;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.source.remote.ProductsRemoteDataSource;

/**
 * Created by Dlolpez on 31/12/17.
 */

public class AddItemPresenter implements AddItemContract.Presenter {
    private final AddItemContract.View mAddItemView;

    private final ProductsRemoteDataSource mProductsRemoteDataSource;

    public AddItemPresenter(@NonNull ProductsRemoteDataSource productsRemoteDataSource,
                            @NonNull AddItemContract.View view){
        mProductsRemoteDataSource = productsRemoteDataSource;
        mAddItemView = view;

        mAddItemView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void confirmItem() {

    }

    @Override
    public void changeItemProperties() {

    }
}
