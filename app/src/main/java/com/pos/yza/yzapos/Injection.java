package com.pos.yza.yzapos;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.source.CategoriesRepository;
import com.pos.yza.yzapos.data.source.ProductsRepository;
import com.pos.yza.yzapos.data.source.remote.CategoriesRemoteDataSource;
import com.pos.yza.yzapos.data.source.remote.ProductsRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dlolpez on 6/1/18.
 */

public class Injection {

        public static ProductsRepository provideProductsRepository(@NonNull Context context) {
            checkNotNull(context);
            return ProductsRepository.getInstance(ProductsRemoteDataSource.getInstance(context));
        }

        public static CategoriesRepository provideCategoriesRepository(@NonNull Context context) {
            checkNotNull(context);
            return CategoriesRepository.getInstance(CategoriesRemoteDataSource.getInstance(context));
        }
}