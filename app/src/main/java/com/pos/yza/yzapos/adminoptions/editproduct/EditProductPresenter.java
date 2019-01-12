package com.pos.yza.yzapos.adminoptions.editproduct;

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
import com.pos.yza.yzapos.data.source.remote.ProductsRemoteDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditProductPresenter implements EditProductContract.Presenter{

    private ProductsRepository mProductsRepository;
    private CategoriesRepository mCategoriesRepository;
    private EditProductContract.View mEditProductView;
    private Product mProduct;

    private final String TAG = "EDIT_PROD_PRES";

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

    public int getProductPropertyId(int categoryPropertyId) {
        return mProduct.getProductPropertyId(categoryPropertyId);
    }

    public String getProductPropertyValue(int categoryPropertyId){
        return mProduct.getProductPropertyValue(categoryPropertyId);
    }

    @Override
    public void saveProduct(String unitOfMeasure, String unitPrice) {
        HashMap<String, String> editMap = new HashMap<>();
        editMap.put(ProductsRemoteDataSource.UNIT_OF_MEASURE, unitOfMeasure);
        editMap.put(ProductsRemoteDataSource.UNIT_PRICE, unitPrice);

        List<ProductProperty> properties = getFormProperties();
        mProductsRepository.editProduct(Integer.toString(mProduct.getId()), editMap,
                                        properties);

    }

    private List<ProductProperty> getFormProperties() {
        List<ProductProperty> propertyEdits = new ArrayList<>();
        for (EditText propertyAnswer : mEditProductView.getPropertyEditTexts()) {
            ProductProperty propertyEdit = new ProductProperty(propertyAnswer.getId(),
                    propertyAnswer.getText().toString());
            propertyEdits.add(propertyEdit);
            Log.i(TAG, "Adding to form props: " + propertyEdit);
        }
        return propertyEdits;
    }
}
