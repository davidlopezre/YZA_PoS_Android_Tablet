package com.pos.yza.yzapos.adminoptions.item;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.Item;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.CategoriesRepository;
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
    private final CategoriesRepository mCategoriesRepository;

    public ItemListPresenter(@NonNull ProductsRepository productsRepository,
                             @NonNull CategoriesRepository categoriesRepository,
                                 @NonNull ItemListContract.View view){
        mProductsRepository = productsRepository;
        mCategoriesRepository = categoriesRepository;
        mItemListView = view;

        mItemListView.setPresenter(this);
    }

    @Override
    public void start() {
        mCategoriesRepository.getCategories(new CategoriesDataSource.LoadCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<ProductCategory> categories) {
                ArrayList<String> categoryNames = new ArrayList<String>();
                for(ProductCategory c : categories){
                    categoryNames.add(c.getName());
                    Log.i("cat",c.getName());
                }
                mItemListView.setUpSpinnerAdapter(categoryNames);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        loadItems();
    }

    @Override
    public void loadItems() {
        ProductCategory category = new ProductCategory(1, "", new ArrayList<CategoryProperty>());
        mProductsRepository.getProducts(new ProductsDataSource.LoadProductsCallback() {
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
    public void deleteItem(Item item) {
        int id = item.getId();
        mProductsRepository.deleteProduct(Integer.toString(id));
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
