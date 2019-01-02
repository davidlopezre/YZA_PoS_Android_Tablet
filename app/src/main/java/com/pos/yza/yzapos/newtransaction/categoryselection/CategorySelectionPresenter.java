package com.pos.yza.yzapos.newtransaction.categoryselection;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.SessionStorage;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.CategoriesRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Lopez on 18/1/18.
 */

public class CategorySelectionPresenter implements CategorySelectionContract.Presenter {

    private final CategorySelectionContract.View mCategorySelectionView;

    private CategoriesRepository mCategoriesRepository;


    public CategorySelectionPresenter(@NonNull CategorySelectionContract.View mCategorySelectionView,
                                      @NonNull CategoriesRepository mCategoriesRepository) {
        this.mCategorySelectionView = checkNotNull(mCategorySelectionView);
        this.mCategoriesRepository = checkNotNull(mCategoriesRepository);
        mCategorySelectionView.setPresenter(this);
    }

    @Override
    public void start() {
        loadCategories();
    }

    private void loadCategories(){
        processCategories(SessionStorage.getAllCategories());
    }

    private void processCategories(List<ProductCategory> categories) {
        mCategorySelectionView.showCategories(categories);
    }
}
