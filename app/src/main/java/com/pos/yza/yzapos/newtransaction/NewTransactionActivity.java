package com.pos.yza.yzapos.newtransaction;

import android.icu.text.IDNA;
import android.security.keystore.UserNotAuthenticatedException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.adminoptions.staff.StaffListFragment;
import com.pos.yza.yzapos.newtransaction.cart.CartFragment;
import com.pos.yza.yzapos.newtransaction.cart.CartPresenter;
import com.pos.yza.yzapos.newtransaction.customerdetails.CustomerDetailsFragment;
import com.pos.yza.yzapos.newtransaction.customerdetails.CustomerDetailsPresenter;
import com.pos.yza.yzapos.newtransaction.payment.PaymentFragment;
import com.pos.yza.yzapos.newtransaction.payment.PaymentPresenter;
import com.pos.yza.yzapos.util.ActivityUtils;

public class NewTransactionActivity extends AppCompatActivity
        implements OnFragmentInteractionListener{

    private static final String CART = "CART";
    private static final String CATEGORY_SELECTION = "CATEGORY_SELECTION";
    private static final String PRODUCT_SELECTION = "PRODUCT_SELECTION";
    private static final String CUSTOMER_DETAILS = "CUSTOMER_DETAILS";
    private static final String PAYMENT = "PAYMENT";


    private CartPresenter mCartPresenter;
    private CustomerDetailsPresenter mCustomerDetailsPresenter;
    private PaymentPresenter mPaymentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle(R.string.new_transaction);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        CartFragment cartFragment =
                (CartFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (cartFragment == null) {
            // Create the fragment
            cartFragment = CartFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), cartFragment, R.id.contentFrame, CART);
        }

        // Create the presenter
        mCartPresenter = new CartPresenter(cartFragment);

    }

    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    }

    @Override
    public void onFragmentMessage(String TAG, Object data) {
        if (TAG.equals(CART)){
            CustomerDetailsFragment customerDetailsFragment =
                    (CustomerDetailsFragment) getSupportFragmentManager().findFragmentByTag(CUSTOMER_DETAILS);
            if (customerDetailsFragment == null) {
                // Create the fragment
                customerDetailsFragment = CustomerDetailsFragment.newInstance();
                ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), customerDetailsFragment, R.id.contentFrame, CUSTOMER_DETAILS);
            }

            // Create the presenter
            mCustomerDetailsPresenter = new CustomerDetailsPresenter(customerDetailsFragment);

        }else if (TAG.equals(CATEGORY_SELECTION)) {

        }else if (TAG.equals(PRODUCT_SELECTION)){

        }else if (TAG.equals(PAYMENT)){

        }else if (TAG.equals(CUSTOMER_DETAILS)){
            PaymentFragment paymentFragment =
                    (PaymentFragment) getSupportFragmentManager().findFragmentByTag(PAYMENT);
            if (paymentFragment == null) {
                // Create the fragment
                paymentFragment = PaymentFragment.newInstance();
                ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), paymentFragment, R.id.contentFrame, PAYMENT);
            }

            // Create the presenter
            mPaymentPresenter = new PaymentPresenter(paymentFragment);

        }else{

        }
    }
}
