package com.pos.yza.yzapos.adminoptions.additem;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.ProductProperty;
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.CategoriesRepository;
import com.pos.yza.yzapos.data.source.ProductsRepository;
import com.pos.yza.yzapos.data.source.remote.ProductsRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public class AddItemPresenter implements AddItemContract.Presenter {
    private final AddItemContract.View mAddItemView;

    private final ProductsRepository mProductsRepository;

    private final CategoriesRepository mCategoriesRepository;

    public AddItemPresenter(@NonNull ProductsRepository productsRepository,
                            @NonNull CategoriesRepository categoriesRepository,
                            @NonNull AddItemContract.View view){
        mProductsRepository = productsRepository;
        mCategoriesRepository = categoriesRepository;
        mAddItemView = view;

        mAddItemView.setPresenter(this);
    }

    @Override
    public void start() {
        loadCategories();
    }

    @Override
    public void confirmItem(ProductCategory category, String unitOfMeasure, String unitPrice,
                            ArrayList<ProductProperty> properties) {
        Log.i("saveItem", "in presenter");
        Double newUnitPrice = Double.parseDouble(unitPrice);
        Product product = new Product(newUnitPrice, unitOfMeasure, category, properties);
        mProductsRepository.saveProduct(product);
    }

    @Override
    public void changeItemProperties() {

    }

    public void loadCategories() {
        mCategoriesRepository.getCategories(new CategoriesDataSource.LoadCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<ProductCategory> categories) {
                Log.d("categoriesResponse", "inside load categories");
                for (ProductCategory c : categories) {
                    Log.d("categoriesResponse", "Category: " + c.getName());
                    Log.d("categoriesResponse", "Properties: " + c.detailString());
                }

                processCategories(categories);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void processCategories(List<ProductCategory> categories) {
        if (categories.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            processEmptyCategories();
        } else {
            // Show the list of tasks
            mAddItemView.showCategories(categories);
            // Set the filter label's text.
//            showFilterLabel();
        }
    }

    private void processEmptyCategories() {

    }
}
