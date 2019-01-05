package com.pos.yza.yzapos.managetransactions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.pos.yza.yzapos.Injection;
import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.managetransactions.viewtransaction.ViewTransactionFragment;
import com.pos.yza.yzapos.util.ActivityUtils;
import com.pos.yza.yzapos.managetransactions.viewtransaction.ViewTransactionPresenter;

public class ManageTransactionsActivity extends AppCompatActivity
        implements OnFragmentInteractionListener{

    private ManageTransactionsPresenter mManageTransactionsPresenter;
    private ViewTransactionPresenter mViewTransactionPresenter;

    private final String MGMTTRANSACTIONS = "MANAGE_TRANSACTIONS";
    private static final String VIEW_TRANSACTION_FRAG = "VIEW_TRANSACTION_FRAG";
    public static final String VIEW_TRANSACTION_CLICK = "VIEW_TRANSACTION_CLICK";
    private final String TAG = "MANAGE_TRANS_ACTIVITY";

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
            case VIEW_TRANSACTION_CLICK:
                viewTransaction((Transaction) data);
                break;
            default:
                break;
        }
    }

    public void viewTransaction(Transaction transaction) {
        ViewTransactionFragment viewTransactionFragment =
                (ViewTransactionFragment) getSupportFragmentManager().findFragmentByTag(VIEW_TRANSACTION_FRAG);

        if (viewTransactionFragment == null) {
            viewTransactionFragment= ViewTransactionFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                    viewTransactionFragment, R.id.contentFrame, VIEW_TRANSACTION_FRAG,
                    true);
        }

        mViewTransactionPresenter =
                new ViewTransactionPresenter(
                        Injection.provideTransactionsRepository(this),
                        Injection.providePaymentsRepository(this),
                        viewTransactionFragment,
                        transaction);

        Log.i(TAG, "opening to view " + transaction.toString());

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(transaction.getToolbarTitle());
    }
}
