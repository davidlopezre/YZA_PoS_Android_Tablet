package com.pos.yza.yzapos.data.source;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;

import java.util.List;

/**
 * Created by Dlolpez on 6/1/18.
 */

public interface ProductsDataSource {
    interface LoadProductsCallback {

        void onProductsLoaded(List<Product> products);

        void onDataNotAvailable();
    }

    interface GetProductCallback {

        void onProductLoaded(Product product);

        void onDataNotAvailable();
    }

    void getProducts(@NonNull LoadProductsCallback callback);

    void getProduct(@NonNull String productId, @NonNull GetProductCallback callback);

    void getProductsByCategory(ProductCategory category, @NonNull LoadProductsCallback callback);

    void saveProduct(@NonNull Product product);

    void refreshProducts();

    void deleteAllProducts();

    void deleteProduct(@NonNull String productId);
}
