package com.pos.yza.yzapos.data.source;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Dlolpez on 6/1/18.
 */

public interface CategoriesDataSource {
    interface LoadCategoriesCallback {

        void onCategoriesLoaded(List<ProductCategory> categories);

        void onDataNotAvailable();
    }

    interface GetCategoryCallback {

        void onCategoryLoaded(ProductCategory category);

        void onDataNotAvailable();
    }

    void getCategories(@NonNull LoadCategoriesCallback callback);

    void saveCategory(@NonNull ProductCategory category);

    void refreshCategory();

    void deleteAllCategories();

    void deleteCategory(@NonNull String productId);

}
