package com.pos.yza.yzapos.addproduct;

import android.support.annotation.NonNull;

/**
 * Created by Dlolpez on 27/12/17.
 */

public class AddProductPresenter implements AddProductContract.Presenter{

    private String category;

    private final AddProductContract.View mView;

    public AddProductPresenter(@NonNull AddProductContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void createNewProduct() {

    }

    @Override
    public void cancel() {

    }
}
