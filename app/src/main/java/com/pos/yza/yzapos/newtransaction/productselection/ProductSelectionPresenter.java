package com.pos.yza.yzapos.newtransaction.productselection;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.source.ProductsDataSource;
import com.pos.yza.yzapos.data.source.ProductsRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Lopez on 18/1/18.
 */

public class ProductSelectionPresenter implements ProductSelectionContract.Presenter {

    private final ProductSelectionContract.View mProductSelectionView;

    ProductCategory mCategory;
    ProductsRepository mProductRepository;

    public ProductSelectionPresenter(@NonNull ProductSelectionContract.View paymentView,
                                     @NonNull ProductsRepository productRepository,
                                     @NonNull ProductCategory category) {
        this.mProductSelectionView = checkNotNull(paymentView);
        this.mProductRepository = checkNotNull(productRepository);
        this.mCategory = checkNotNull(category);
        mProductSelectionView.setPresenter(this);
    }

    @Override
    public void start() {
        loadProducts();
    }

    private void loadProducts() {
        mProductRepository.getProductsByCategory(mCategory, new ProductsDataSource.LoadProductsCallback() {
            @Override
            public void onProductsLoaded(List<Product> products) {
                mProductSelectionView.showProducts(products);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
