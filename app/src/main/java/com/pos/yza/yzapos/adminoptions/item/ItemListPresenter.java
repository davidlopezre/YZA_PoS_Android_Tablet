package com.pos.yza.yzapos.adminoptions.item;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.AdminOptionsDataSource;

/**
 * Created by Dlolpez on 31/12/17.
 */

public class ItemListPresenter implements ItemListContract.Presenter {
    private final ItemListContract.View mItemListView;

    private final AdminOptionsDataSource mAdminOptionsDataSource;

    public ItemListPresenter(@NonNull AdminOptionsDataSource adminOptionsDataSource,
                                 @NonNull ItemListContract.View view){
        mAdminOptionsDataSource = adminOptionsDataSource;
        mItemListView = view;

        mItemListView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadItems() {

    }

    @Override
    public void addNewItem() {
        mItemListView.showAddItem();
    }

    @Override
    public void deleteItem() {

    }

    @Override
    public void editItem() {

    }

    @Override
    public void openItemDetails() {

    }

    @Override
    public void setFiltering() {

    }
}
