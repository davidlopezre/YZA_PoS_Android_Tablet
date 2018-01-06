package com.pos.yza.yzapos.adminoptions.item;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.representations.Item;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.source.ProductsDataSource;
import com.pos.yza.yzapos.data.source.ProductsRepository;
import com.pos.yza.yzapos.data.source.remote.ProductsRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public class ItemListPresenter implements ItemListContract.Presenter {
    private final ItemListContract.View mItemListView;

    private final ProductsRepository mProductsRepository;

    private List mList;


    public ItemListPresenter(@NonNull ProductsRepository productsRepository,
                                 @NonNull ItemListContract.View view){
        mProductsRepository = productsRepository;
        mItemListView = view;

        mItemListView.setPresenter(this);
    }

    @Override
    public void start() {
        loadItems();
    }

    @Override
    public void loadItems() {
        ProductCategory category = new ProductCategory(1, new ArrayList<String>());
        mProductsRepository.getProductsByCategory(category, new ProductsDataSource.LoadProductsCallback() {
            @Override
            public void onProductsLoaded(List<Product> products) {
                mItemListView.showItems(new ArrayList<Item>(products));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
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
