package com.pos.yza.yzapos.data.source;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.representations.Payment;
import com.pos.yza.yzapos.data.representations.Transaction;

import java.util.List;

/**
 * Created by Dalzy Mendoza on 9/2/18.
 */

public interface PaymentsDataSource {

    interface LoadPaymentsCallback {
        void onPaymentsLoaded(List<Payment> payments);
        void onDataNotAvailable();
    }

    interface GetPaymentCallback {
        void onPaymentLoaded(Payment payment);
        void onDataNotAvailable();
    }

    void getPaymentById(@NonNull String paymentId, @NonNull GetPaymentCallback callback);

//    void getPaymentsByTransaction(@NonNull String transactionId, @NonNull PaymentsDataSource.LoadPaymentsCallback callback);

    void savePayment(@NonNull Payment payment);

    void refundPayment(@NonNull String paymentId);


}
