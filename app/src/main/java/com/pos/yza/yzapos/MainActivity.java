package com.pos.yza.yzapos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pos.yza.yzapos.adminoptions.AdminOptionsActivity;
import com.pos.yza.yzapos.data.source.PaymentsDataSource;
import com.pos.yza.yzapos.newtransaction.NewTransactionActivity;
import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.LineItem;
import com.pos.yza.yzapos.data.representations.Payment;
import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.StaffDataSource;
import com.pos.yza.yzapos.data.source.StaffRepository;
import com.pos.yza.yzapos.data.source.TransactionsDataSource;
import com.pos.yza.yzapos.data.source.remote.CategoriesRemoteDataSource;
import com.pos.yza.yzapos.data.source.remote.StaffRemoteDataSource;
import com.pos.yza.yzapos.data.source.remote.TransactionsRemoteDataSource;
import com.pos.yza.yzapos.newtransaction.NewTransactionActivity;
import com.pos.yza.yzapos.data.source.remote.ProductsRemoteDataSource;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.source.ProductsRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FOR DALZY
//        Log.i("paymentResponse", "in main");
//        PaymentsDataSource paymentsRepo = Injection.providePaymentsRepository(getApplicationContext());
//
//        paymentsRepo.getPaymentById("1", new PaymentsDataSource.GetPaymentCallback() {
//            @Override
//            public void onPaymentLoaded(Payment payment) {
//                Log.i("paymentResponse", "back to main");
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                Log.i("paymentResponse", "error back in main");
//            }
//        });
//
//        Payment payment = new Payment(new Date(), 20.0, 1, new Transaction(1));
//
//        Log.i("savePayment", "to save this: " + payment.toString());
//        paymentsRepo.savePayment(payment);

    }

    public void newTransaction(View view){
        Intent intent = new Intent(getApplicationContext(), NewTransactionActivity.class);
        startActivity(intent);
    }

    public void admin(View view){
        Intent intent = new Intent(getApplicationContext(), AdminOptionsActivity.class);
        startActivity(intent);
    }

}
