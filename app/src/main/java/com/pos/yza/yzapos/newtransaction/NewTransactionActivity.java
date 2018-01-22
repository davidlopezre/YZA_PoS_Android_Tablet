package com.pos.yza.yzapos.newtransaction;

import android.icu.text.IDNA;
import android.security.keystore.UserNotAuthenticatedException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.newtransaction.cart.CartFragment;
import com.pos.yza.yzapos.newtransaction.cart.CartPresenter;
import com.pos.yza.yzapos.util.ActivityUtils;

public class NewTransactionActivity extends AppCompatActivity
        implements OnFragmentInteractionListener{

    private CartPresenter mCartPresenter;

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
                    getSupportFragmentManager(), cartFragment, R.id.contentFrame);
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
        if (TAG.equals("CART")){

        }else if (TAG.equals("CATEGORY_SELECTION")) {

        }else if (TAG.equals("PRODUCT_SELECTION")){

        }else if (TAG.equals("PAYMENT")){

        }else if (TAG.equals("CUSTOMER_DETAILS")){

        }else{

        }
    }
}
