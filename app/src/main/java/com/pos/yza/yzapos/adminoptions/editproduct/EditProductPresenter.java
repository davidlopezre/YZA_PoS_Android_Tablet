package com.pos.yza.yzapos.adminoptions.editproduct;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pos.yza.yzapos.SessionStorage;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.ProductProperty;
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.CategoriesRepository;
import com.pos.yza.yzapos.data.source.ProductsRepository;
import com.pos.yza.yzapos.data.source.remote.ProductsRemoteDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditProductPresenter implements EditProductContract.Presenter{

    private ProductsRepository mProductsRepository;
    private CategoriesRepository mCategoriesRepository;
    private EditProductContract.View mEditProductView;
    private Product mProduct;

    public EditProductPresenter(@NonNull ProductsRepository productsRepository,
                                @NonNull CategoriesRepository categoriesRepository,
                                @NonNull EditProductContract.View view,
                                @NonNull Product product){
        mProductsRepository = productsRepository;
        mCategoriesRepository = categoriesRepository;
        mEditProductView = view;
        mEditProductView.setPresenter(this);
        mProduct = product;
    }

    public void start(){
        loadCategories();
    }

    public String getProductName() {
        return mProduct.getName();
    }

    public Product getProduct() {
        return mProduct;
    }

    public String getProductCategoryName(){
        return mProduct.getCategory().getName();
    }

    public String getProductPropertyValue(int categoryPropertyId){
        return mProduct.getProductPropertyValue(categoryPropertyId);
    }

    public void loadCategories() {
        processCategories(SessionStorage.getAllCategories());
    }

    private void processCategories(List<ProductCategory> categories) {
        if (categories.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            processEmptyCategories();
        } else {
            // Show the list of tasks
            mEditProductView.showCategories(categories);
            // Set the filter label's text.
//            showFilterLabel();
        }
    }

    private void processEmptyCategories() {

    }

    @Override
    public void saveProduct(ProductCategory category, String unitOfMeasure, String unitPrice,
                            ArrayList<ProductProperty> properties) {
        HashMap<String, String> editMap = new HashMap<>();
        editMap.put(ProductsRemoteDataSource.CATEGORY_PROPERTY_ID, category.getId() + "");
        editMap.put(ProductsRemoteDataSource.UNIT_OF_MEASURE, unitOfMeasure);
        editMap.put(ProductsRemoteDataSource.UNIT_PRICE, unitPrice);



        mProductsRepository.editProduct(Integer.toString(mProduct.getId()), editMap);

    }
}
