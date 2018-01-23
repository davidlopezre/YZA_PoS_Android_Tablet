package com.pos.yza.yzapos.data.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.pos.yza.yzapos.data.representations.Transaction;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dalzy Mendoza on 16/1/18.
 */

public class TransactionsRepository implements TransactionsDataSource {

    private static TransactionsRepository INSTANCE = null;
    private static Context mContext;
    private final TransactionsDataSource mTransactionRemoteDataSource;
    private RequestQueue mRequestQueue;

    // Prevent direct instantiation.
    private TransactionsRepository(@NonNull TransactionsDataSource transactionRemoteDataSource) {
        mTransactionRemoteDataSource = checkNotNull(transactionRemoteDataSource);
        //  mTasksLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param transactionRemoteDataSource the backend data source
     * @return the {@link ProductsDataSource} instance
     */
    public static TransactionsRepository getInstance(TransactionsDataSource transactionRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TransactionsRepository(transactionRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(TransactionsDataSource)} (TransactionsDataSource, TransactionsDataSource)}
     * to create a new instance next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }


    public void getTransactions(@NonNull final LoadTransactionsCallback callback){
        checkNotNull(callback);

        mTransactionRemoteDataSource.getTransactions(new LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
                callback.onTransactionsLoaded(transactions);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });

    }

    public void getTransactionById(@NonNull String transactionId, @NonNull GetTransactionCallback callback){

    }

    // Functions for Generating Report
    // void getTransactionsByBranch(Branch branch, @NonNull LoadTransactionsCallback callback);

    public void saveTransaction(@NonNull Transaction transaction){

    }

    public void refreshTransactions(){

    }

    public void deleteAllTransactions(){

    }

    public void deleteAllTransactionsByBranch(){

    }

    public void cancelTransaction(@NonNull String transactionId){

    }

    public void refundTransaction(@NonNull String transactionId){

    }


}


