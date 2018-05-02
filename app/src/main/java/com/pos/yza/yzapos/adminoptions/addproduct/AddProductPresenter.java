package com.pos.yza.yzapos.adminoptions.addproduct;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.ProductProperty;
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.CategoriesRepository;
import com.pos.yza.yzapos.data.source.ProductsRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public class AddProductPresenter implements AddProductContract.Presenter {
    private final AddProductContract.View mAddProductView;

    private final ProductsRepository mProductsRepository;

    private final CategoriesRepository mCategoriesRepository;

    public AddProductPresenter(@NonNull ProductsRepository productsRepository,
                               @NonNull CategoriesRepository categoriesRepository,
                               @NonNull AddProductContract.View view){
        mProductsRepository = productsRepository;
        mCategoriesRepository = categoriesRepository;
        mAddProductView = view;

        mAddProductView.setPresenter(this);
    }

    @Override
    public void start() {
        loadCategories();
    }

    @Override
    public void confirmProduct(ProductCategory category, String unitOfMeasure, String unitPrice,
                               ArrayList<ProductProperty> properties) {
        Log.i("saveItem", "in presenter");
        Double newUnitPrice = Double.parseDouble(unitPrice);
        Product product = new Product(newUnitPrice, unitOfMeasure, category, properties);
        mProductsRepository.saveProduct(product);
        mAddProductView.showFeedback();
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
            mAddProductView.showCategories(categories);
            // Set the filter label's text.
//            showFilterLabel();
        }
    }

    private void processEmptyCategories() {

    }
}
