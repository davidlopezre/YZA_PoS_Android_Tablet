package com.pos.yza.yzapos.adminoptions.addproduct;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;

import com.pos.yza.yzapos.SessionStorage;
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

    private final String TAG = "ADD_PROD_PRES";

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
    public void confirmProduct(ProductCategory category, String unitOfMeasure, String unitPrice) {
        Log.i("saveItem", "in presenter");
        Double newUnitPrice = Double.parseDouble(unitPrice);
        ArrayList<ProductProperty> properties = getFormProperties();
        Product product = new Product(newUnitPrice, unitOfMeasure, category, properties);
        mProductsRepository.saveProduct(product);
        mAddProductView.showFeedback();
    }


    public void loadCategories() {
        mCategoriesRepository.getCategories(new CategoriesDataSource.LoadCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<ProductCategory> categories) {
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

    private ArrayList<ProductProperty> getFormProperties() {
        ArrayList<ProductProperty> propertyEdits = new ArrayList<>();
        for (EditText propertyAnswer : mAddProductView.getPropertyEditTexts()) {
            ProductProperty propertyEdit = new ProductProperty(propertyAnswer.getId(),
                    propertyAnswer.getText().toString());
            propertyEdits.add(propertyEdit);
            Log.i(TAG, "Adding to form props: " + propertyEdit);
        }
        return propertyEdits;
    }
}
