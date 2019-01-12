package com.pos.yza.yzapos.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.ProductProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dlolpez on 27/12/17.
 */

public class ProductsRepository implements ProductsDataSource{

    private static ProductsRepository INSTANCE = null;

    private final ProductsDataSource mProductsRemoteDataSource;

    // Prevent direct instantiation.
    private ProductsRepository(@NonNull ProductsDataSource productsRemoteDataSource) {
        mProductsRemoteDataSource = checkNotNull(productsRemoteDataSource);
//      mTasksLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param productsRemoteDataSource the backend data source
     * @return the {@link ProductsDataSource} instance
     */
    public static ProductsRepository getInstance(ProductsDataSource productsRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ProductsRepository(productsRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(ProductsDataSource)} (ProductsDataSource, ProductsDataSource)}
     * to create a new instance next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static ProductsRepository mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    @Override
    public void getProducts(@NonNull final LoadProductsCallback callback) {
        checkNotNull(callback);

        mProductsRemoteDataSource.getProducts(new LoadProductsCallback() {
            @Override
            public void onProductsLoaded(List<Product> products) {
                callback.onProductsLoaded(products);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getProduct(@NonNull String productId, @NonNull GetProductCallback callback) {

    }

    @Override
    public void getProductsByCategory(ProductCategory category,
                                      @NonNull final LoadProductsCallback callback) {
        checkNotNull(callback);

        mProductsRemoteDataSource.getProductsByCategory(category, new LoadProductsCallback() {
            @Override
            public void onProductsLoaded(List<Product> products) {
                callback.onProductsLoaded(products);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveProduct(@NonNull Product product) {
        Log.i("saveItem", "in repo");
        mProductsRemoteDataSource.saveProduct(product);
    }

    @Override
    public void refreshProducts() {

    }

    @Override
    public void deleteAllProducts() {

    }

    @Override
    public void deleteProduct(@NonNull String productId) {
        Log.i("deleteItem", "in repo");
        mProductsRemoteDataSource.deleteProduct(productId);
    }

    @Override
    public void editProduct(@NonNull String productId, @NonNull HashMap<String,String> edits,
                            @NonNull List<ProductProperty> propertyEdits){
        mProductsRemoteDataSource.editProduct(productId, edits, propertyEdits);
    }

}
