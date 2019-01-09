package com.pos.yza.yzapos.managetransactions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.pos.yza.yzapos.Injection;
import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.managetransactions.viewtransactioncart.ViewTransactionCartFragment;
import com.pos.yza.yzapos.managetransactions.viewtransactionpayment.ViewTransactionPaymentFragment;
import com.pos.yza.yzapos.managetransactions.viewtransactionpayment.ViewTransactionPaymentPresenter;
import com.pos.yza.yzapos.util.ActivityUtils;
import com.pos.yza.yzapos.managetransactions.viewtransactioncart.ViewTransactionCartPresenter;

public class ManageTransactionsActivity extends AppCompatActivity
        implements OnFragmentInteractionListener{

    private ManageTransactionsPresenter mManageTransactionsPresenter;
    private ViewTransactionCartPresenter mViewTransactionCartPresenter;
    private ViewTransactionPaymentPresenter mViewTransactionPaymentPresenter;

    private final String MGMTTRANSACTIONS = "MANAGE_TRANSACTIONS";
    private static final String VIEW_TRANSACTION_FRAG = "VIEW_TRANSACTION_FRAG";
    public static final String VIEW_TRANSACTION_CLICK = "VIEW_TRANSACTION_CLICK";
    public static final String VIEW_PAYMENTS = "VIEW_PAYMENTS";
    public static final String VIEW_PAYMENTS_FRAG = "VIEW_PAYMENTS_FRAG";
    private final String TAG = "MANAGE_TRANS_ACTIVITY";

    private Transaction chosenTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_transactions);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle(R.string.manage_transactions);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ManageTransactionsFragment manageTransactionsFragment =
                (ManageTransactionsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (manageTransactionsFragment == null) {
            // Create the fragment
            manageTransactionsFragment = ManageTransactionsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), manageTransactionsFragment, R.id.contentFrame,
                    MGMTTRANSACTIONS,false);
        }

        // Create the presenter
        mManageTransactionsPresenter =
                new ManageTransactionsPresenter(Injection.provideTransactionsRepository(this),
                        Injection.providePaymentsRepository(this),
                        manageTransactionsFragment);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onFragmentMessage(String TAG, Object data) {
        switch(TAG) {
            case MGMTTRANSACTIONS:
                viewTransaction((Transaction) data);
                break;
            case VIEW_TRANSACTION_FRAG:
                viewPayments();
            default:
                break;
        }
    }

    public void viewTransaction(Transaction transaction) {

        chosenTransaction = transaction;

        ViewTransactionCartFragment viewTransactionCartFragment =
                (ViewTransactionCartFragment) getSupportFragmentManager().findFragmentByTag(VIEW_TRANSACTION_FRAG);

        if (viewTransactionCartFragment == null) {
            viewTransactionCartFragment = ViewTransactionCartFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                    viewTransactionCartFragment, R.id.contentFrame, VIEW_TRANSACTION_FRAG,
                    true);
        }

        mViewTransactionCartPresenter =
                new ViewTransactionCartPresenter(
                        Injection.provideTransactionsRepository(this),
                        Injection.providePaymentsRepository(this),
                        viewTransactionCartFragment,
                        transaction);

        Log.i(TAG, "opening to view " + transaction.toString());

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(transaction.getToolbarTitle());
    }

    public void viewPayments() {
        ViewTransactionPaymentFragment viewTransactionPaymentFragment =
                (ViewTransactionPaymentFragment) getSupportFragmentManager().findFragmentByTag(VIEW_PAYMENTS_FRAG);

        if (viewTransactionPaymentFragment == null) {
            viewTransactionPaymentFragment = ViewTransactionPaymentFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                    viewTransactionPaymentFragment, R.id.contentFrame, VIEW_PAYMENTS_FRAG,
                    true);
        }

        mViewTransactionPaymentPresenter =
                new ViewTransactionPaymentPresenter(
                        Injection.provideTransactionsRepository(this),
                        Injection.providePaymentsRepository(this),
                        viewTransactionPaymentFragment,
                        chosenTransaction);

        Log.i(TAG, "opening to view payments" + chosenTransaction.toString());
    }
}
