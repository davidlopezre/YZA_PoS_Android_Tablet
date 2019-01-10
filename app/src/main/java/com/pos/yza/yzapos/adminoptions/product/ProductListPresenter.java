package com.pos.yza.yzapos.adminoptions.product;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.CategoriesRepository;
import com.pos.yza.yzapos.data.source.ProductsDataSource;
import com.pos.yza.yzapos.data.source.ProductsRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public class ProductListPresenter implements ProductListContract.Presenter {
    private final ProductListContract.View mItemListView;

    private final ProductsRepository mProductsRepository;
    private final CategoriesRepository mCategoriesRepository;

    public ProductListPresenter(@NonNull ProductsRepository productsRepository,
                                @NonNull CategoriesRepository categoriesRepository,
                                @NonNull ProductListContract.View view){
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
                mItemListView.setUpSpinnerAdapter(categories);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        loadProductsByCategory(mItemListView.getChosenProductCategory());
    }

    @Override
    public void loadProductsByCategory(ProductCategory category) {
        if (category == null) {
            mProductsRepository.getProducts(new ProductsDataSource.LoadProductsCallback() {
                @Override
                public void onProductsLoaded(List<Product> products) {
                    mItemListView.showProducts(new ArrayList<Product>(products));
                }

                @Override
                public void onDataNotAvailable() {

                }
            });
        }
        else {
            mProductsRepository.getProductsByCategory(category, new ProductsDataSource.LoadProductsCallback() {
                @Override
                public void onProductsLoaded(List<Product> products) {
                    mItemListView.showProducts(new ArrayList<Product>(products));
                }

                @Override
                public void onDataNotAvailable() {

                }
            });
        }
    }

    @Override
    public void addNewProduct() {
        mItemListView.showAddProduct();
    }

    @Override
    public void deleteProduct(Product product) {
        int id = product.getId();
        mProductsRepository.deleteProduct(Integer.toString(id));
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
