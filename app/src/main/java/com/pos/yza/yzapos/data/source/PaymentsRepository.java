package com.pos.yza.yzapos.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.pos.yza.yzapos.data.representations.Payment;
import com.pos.yza.yzapos.data.representations.Transaction;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by beyondinfinity on 9/2/18.
 */

public class PaymentsRepository implements PaymentsDataSource {

    private static PaymentsRepository INSTANCE = null;
    private static Context mContext;
    private final PaymentsDataSource mPaymentsRemoteDataSource;
    private RequestQueue mRequestQueue;

    // Prevent direct instantiation.
    private PaymentsRepository(@NonNull PaymentsDataSource paymentsRemoteDataSource) {
        mPaymentsRemoteDataSource = checkNotNull(paymentsRemoteDataSource);
        //  mTasksLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param paymentsRemoteDataSource the backend data source
     * @return the {@link ProductsDataSource} instance
     */
    public static PaymentsRepository getInstance(PaymentsDataSource paymentsRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PaymentsRepository(paymentsRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(PaymentsDataSource)} (PaymentsDataSource, PaymentsDataSource)}
     * to create a new instance next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

//    public void getPaymentsByTransaction(@NonNull String transactionId,
//                                         @NonNull final LoadPaymentsCallback callback){
//        checkNotNull(callback);
//
//        mPaymentsRemoteDataSource.getPaymentsByTransaction(transactionId, new LoadPaymentsCallback(){
//            @Override
//            public void onPaymentsLoaded(List<Payment> payments) {
//                callback.onPaymentsLoaded(payments);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                callback.onDataNotAvailable();
//            }
//        });
//
//    }

    public void getPaymentById(@NonNull String paymentId,
                               @NonNull final GetPaymentCallback callback){
        checkNotNull(callback);

        mPaymentsRemoteDataSource.getPaymentById(paymentId, new PaymentsDataSource.GetPaymentCallback(){
            @Override
            public void onPaymentLoaded(Payment payment) {
                callback.onPaymentLoaded(payment);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    public void savePayment(@NonNull Payment payment){
        Log.i("savePayment", "in repo");
        mPaymentsRemoteDataSource.savePayment(payment);
    }

    public void refundPayment(@NonNull String paymentId){
        Log.i("refundPayment", "in repo");
        mPaymentsRemoteDataSource.refundPayment(paymentId);
    }
}
