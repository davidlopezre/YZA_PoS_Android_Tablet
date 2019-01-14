package com.pos.yza.yzapos.data.source;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pos.yza.yzapos.data.representations.Branch;
import com.pos.yza.yzapos.data.representations.ProductProperty;
import com.pos.yza.yzapos.data.representations.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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

    public void getTransactionById(@NonNull String transactionId, @NonNull final GetTransactionCallback callback){
        checkNotNull(callback);

        mTransactionRemoteDataSource.getTransactionById(transactionId, new GetTransactionCallback(){
            @Override
            public void onTransactionLoaded(Transaction transaction) {
                callback.onTransactionLoaded(transaction);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    // Functions for Generating Report
    // void getTransactionsByBranch(Branch branch, @NonNull LoadTransactionsCallback callback);

    public void saveTransaction(@NonNull Transaction transaction){
        Log.i("saveTransaction", "in repo");
        mTransactionRemoteDataSource.saveTransaction(transaction);
    }

    public void saveTransaction(@NonNull Transaction transaction, Response.Listener<JSONObject> listener){
        Log.i("saveTransaction", "in repo");
        mTransactionRemoteDataSource.saveTransaction(transaction, listener);
    }

    public void refreshTransactions(){

    }

    public void deleteOldTransactions(){
        Log.i("deleteOldTrans", "in repo");
        mTransactionRemoteDataSource.deleteOldTransactions();
    }

    public void deleteAllTransactionsByBranch(){

    }

    public void cancelTransaction(@NonNull String transactionId){
        Log.i("cancelTrans", "in repo");
        mTransactionRemoteDataSource.cancelTransaction(transactionId);
    }

    public void refundTransaction(@NonNull String transactionId){
        Log.i("refundTrans", "in repo");
        mTransactionRemoteDataSource.refundTransaction(transactionId);
    }

    public void sendReport(@NonNull Branch branch, @NonNull int year, @NonNull int month, @NonNull int day) {
        Log.i("sendReport", "in repo");
        mTransactionRemoteDataSource.sendReport(branch, year, month, day);
    }


}


