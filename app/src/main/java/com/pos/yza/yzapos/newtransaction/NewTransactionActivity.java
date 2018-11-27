package com.pos.yza.yzapos.newtransaction;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.pos.yza.yzapos.Injection;
import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.LineItem;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.source.TransactionsRepository;
import com.pos.yza.yzapos.newtransaction.cart.CartFragment;
import com.pos.yza.yzapos.newtransaction.cart.CartActions;
import com.pos.yza.yzapos.newtransaction.cart.CartPresenter;
import com.pos.yza.yzapos.newtransaction.categoryselection.CategorySelectionFragment;
import com.pos.yza.yzapos.newtransaction.categoryselection.CategorySelectionPresenter;
import com.pos.yza.yzapos.newtransaction.customerdetails.CustomerDetailsFragment;
import com.pos.yza.yzapos.newtransaction.customerdetails.CustomerDetailsPresenter;
import com.pos.yza.yzapos.newtransaction.payment.PaymentFragment;
import com.pos.yza.yzapos.newtransaction.payment.PaymentPresenter;
import com.pos.yza.yzapos.newtransaction.productselection.ProductSelectionFragment;
import com.pos.yza.yzapos.newtransaction.productselection.ProductSelectionPresenter;
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
    private CategorySelectionPresenter mCategorySelectionPresenter;
    private ProductSelectionPresenter mProductSelectionPresenter;

    private NewTransaction transaction = new NewTransaction();

    private TransactionsRepository mTransactionsRepository;

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
                    getSupportFragmentManager(), cartFragment, R.id.contentFrame, CART,
                    false);
        }

        // Create the presenter
        mCartPresenter = new CartPresenter(cartFragment);
        // Initialise the repo
        mTransactionsRepository = Injection.provideTransactionsRepository(this);

    }

    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    }

    @Override
    public void onFragmentMessage(String TAG, Object data) {
        switch (TAG) {
            case CART:
                handleCartActions(mCartPresenter.getAction(), data);
                break;
            case CATEGORY_SELECTION:
                handleCategorySelectionActions((ProductCategory)data);
                break;
            case PRODUCT_SELECTION:
                handleProductSelectionActions((LineItem)data);
                break;
            case PAYMENT:
                completeTransaction(data);
                break;
            case CUSTOMER_DETAILS:
                handleCustomerDetailsActions(data);
                break;
            default:
                break;
        }

    }

    private void handleCustomerDetailsActions(Object data){
        transaction.setCustomerDetails(data);
        PaymentFragment paymentFragment =
                (PaymentFragment) getSupportFragmentManager().findFragmentByTag(PAYMENT);
        if (paymentFragment == null) {
            // Create the fragment
            paymentFragment = PaymentFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), paymentFragment,
                    R.id.contentFrame, PAYMENT, true);
        }

        // Create the presenter
        mPaymentPresenter = new PaymentPresenter(paymentFragment);
    }

    private void handleCartActions(CartActions action, Object data) {
        switch (action) {
            case ADD_PRODUCT:

                CategorySelectionFragment categorySelectionFragment =
                        (CategorySelectionFragment) getSupportFragmentManager().
                                findFragmentByTag(CATEGORY_SELECTION);

                if (categorySelectionFragment == null) {
                    categorySelectionFragment = CategorySelectionFragment.newInstance();
                    ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                            categorySelectionFragment, R.id.contentFrame, CATEGORY_SELECTION,
                            true);
                }

                mCategorySelectionPresenter =
                        new CategorySelectionPresenter(categorySelectionFragment,
                                Injection.provideCategoriesRepository(this));

                break;
            case GO_TO_CUSTOMER_DETAILS:
                transaction.setCart(data);
                CustomerDetailsFragment customerDetailsFragment =
                        (CustomerDetailsFragment) getSupportFragmentManager().
                                findFragmentByTag(CUSTOMER_DETAILS);

                if (customerDetailsFragment == null) {
                    // Create the fragment
                    customerDetailsFragment = CustomerDetailsFragment.newInstance();
                    ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                            customerDetailsFragment, R.id.contentFrame, CUSTOMER_DETAILS,
                            true);
                }
                // Create the presenter
                mCustomerDetailsPresenter = new CustomerDetailsPresenter(customerDetailsFragment);

                break;
            default:
                break;
        }

    }

    private void handleCategorySelectionActions(ProductCategory category) {
        Log.i("CATEGORY_SELECTION", "In New Transactions Activity");
        ProductSelectionFragment productSelectionFragment =
                (ProductSelectionFragment) getSupportFragmentManager().
                        findFragmentByTag(PRODUCT_SELECTION);

        if (productSelectionFragment == null) {
            // Create the fragment
            productSelectionFragment = ProductSelectionFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                    productSelectionFragment, R.id.contentFrame, PRODUCT_SELECTION, true);
        }
        // Create the presenter
        mProductSelectionPresenter = new ProductSelectionPresenter(productSelectionFragment,
                Injection.provideProductsRepository(this), category);

    }

    private void handleProductSelectionActions(LineItem data) {
        Log.i("PRODUCT_SELECTION", "In New Transactions Activity");
        mCartPresenter.addLineItem(data);
        // Remove the fragment from activity
//        CartFragment cartFragment =
//                (CartFragment) getSupportFragmentManager().
//                        findFragmentByTag(CART);

        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        
    }

    private void completeTransaction(Object data) {
        transaction.setPayment(data);
        mTransactionsRepository.saveTransaction(transaction.createTransaction());

    }
}
