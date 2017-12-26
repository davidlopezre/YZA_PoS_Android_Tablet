package com.pos.yza.yzapos.adminoptions;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.DataSource;

/**
 * Created by Dlolpez on 23/12/17.
 */

public class AdminOptionsPresenter implements AdminOptionsContract.Presenter {

    private final AdminOptionsContract.View mAdminOptionsView;

    private final DataSource mDataSource;

    public AdminOptionsPresenter(@NonNull DataSource dataSource,
                                 @NonNull AdminOptionsContract.View view){
        mDataSource = dataSource;
        mAdminOptionsView = view;

        mAdminOptionsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadProducts();
    }

    @Override
    public void loadProducts() {

    }

    @Override
    public void addNewProduct() {

    }

    @Override
    public void deleteProduct() {

    }

    @Override
    public void editProduct() {

    }

    @Override
    public void openProductDetails() {

    }

    @Override
    public void setFiltering() {

    }
}
