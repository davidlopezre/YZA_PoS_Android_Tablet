package com.pos.yza.yzapos.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dlolpez on 27/12/17.
 */

public class CategoriesRepository implements CategoriesDataSource{

    private static CategoriesRepository INSTANCE = null;

    private final CategoriesDataSource mCategoriesRemoteDataSource;

    // Prevent direct instantiation.
    private CategoriesRepository(@NonNull CategoriesDataSource categoriesRemoteDataSource) {
        mCategoriesRemoteDataSource = checkNotNull(categoriesRemoteDataSource);
//      mTasksLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param categoriesRemoteDataSource the backend data source
     * @return the {@link ProductsDataSource} instance
     */
    public static CategoriesRepository getInstance(CategoriesDataSource categoriesRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CategoriesRepository(categoriesRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(CategoriesDataSource)} (CategoriesDataSource)}
     * to create a new instance next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static CategoriesRepository mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    @Override
    public void getCategories(@NonNull final LoadCategoriesCallback callback) {
        checkNotNull(callback);

        mCategoriesRemoteDataSource.getCategories(new LoadCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<ProductCategory> categories) {
                callback.onCategoriesLoaded(categories);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveCategory(@NonNull ProductCategory category) {
        Log.i("saveCategory", "in repo");
        mCategoriesRemoteDataSource.saveCategory(category);
    }

    @Override
    public void refreshCategory() {

    }

    @Override
    public void deleteAllCategories() {

    }

    @Override
    public void deleteCategory(@NonNull String categoryId) {
        Log.i("deleteCategory", "in repo");
        mCategoriesRemoteDataSource.deleteCategory(categoryId);
    }

}
