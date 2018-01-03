package com.pos.yza.yzapos.adminoptions.additem;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.adminoptions.item.ItemListContract;
import com.pos.yza.yzapos.data.AdminOptionsDataSource;

/**
 * Created by Dlolpez on 31/12/17.
 */

public class AddItemPresenter implements AddItemContract.Presenter {
    private final AddItemContract.View mAddItemView;

    private final AdminOptionsDataSource mAdminOptionsDataSource;

    public AddItemPresenter(@NonNull AdminOptionsDataSource adminOptionsDataSource,
                            @NonNull AddItemContract.View view){
        mAdminOptionsDataSource = adminOptionsDataSource;
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
