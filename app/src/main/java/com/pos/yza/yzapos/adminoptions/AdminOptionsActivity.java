package com.pos.yza.yzapos.adminoptions;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.pos.yza.yzapos.Injection;
import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.adminoptions.addcategory.AddCategoryFragment;
import com.pos.yza.yzapos.adminoptions.addcategory.AddCategoryPresenter;
import com.pos.yza.yzapos.adminoptions.addproduct.AddProductFragment;
import com.pos.yza.yzapos.adminoptions.addproduct.AddProductPresenter;
import com.pos.yza.yzapos.adminoptions.addstaff.AddStaffFragment;
import com.pos.yza.yzapos.adminoptions.addstaff.AddStaffPresenter;
import com.pos.yza.yzapos.adminoptions.category.CategoryListFragment;
import com.pos.yza.yzapos.adminoptions.category.CategoryListPresenter;
import com.pos.yza.yzapos.adminoptions.editproduct.EditProductFragment;
import com.pos.yza.yzapos.adminoptions.editproduct.EditProductPresenter;
import com.pos.yza.yzapos.adminoptions.product.ProductListFragment;
import com.pos.yza.yzapos.adminoptions.product.ProductListPresenter;
import com.pos.yza.yzapos.adminoptions.staff.StaffListFragment;
import com.pos.yza.yzapos.adminoptions.staff.StaffListPresenter;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.source.remote.ProductsRemoteDataSource;
import com.pos.yza.yzapos.util.ActivityUtils;

public class AdminOptionsActivity extends AppCompatActivity
        implements ProductListFragment.ProductListListener, StaffListFragment.StaffListListener,
        CategoryListFragment.CategoryListListener {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private DrawerLayout mDrawerLayout;

    private ProductListPresenter mProductListPresenter;

    private AddProductPresenter mAddProductPresenter;

    private AddStaffPresenter mAddStaffPresenter;

    private AddCategoryPresenter mAddCategoryPresenter;

    private EditProductPresenter mEditProductPresenter;

    boolean isDrawerLocked;

    private StaffListPresenter mStaffListPresenter;

    private CategoryListPresenter mCategoryListPresenter;

    private ProductsRemoteDataSource mProductsRemoteDataSource;

    private Toolbar toolbar;

    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);

        // Set up the toolbar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle(R.string.products);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);

        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }

        ProductListFragment productListFragment =
                (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (productListFragment == null) {
            // Create the fragment
                productListFragment = ProductListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), productListFragment, R.id.contentFrame, "product",
                    false);
        }

        // Create the presenter
        mProductListPresenter = new ProductListPresenter(
                Injection.provideProductsRepository(getApplicationContext()),
                Injection.provideCategoriesRepository(getApplicationContext()),
                productListFragment);

        if (savedInstanceState != null){

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        outState.putSerializable(CURRENT_FILTERING_KEY, mProductListPresenter.getFiltering());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        // TODO (1) : Stop creating so many instances of the fragments, create one and try to find with tag
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.product_navigation_menu_item:
                                toolbar.setTitle(R.string.products);
                                // Do nothing, we're already on that screen
                                ProductListFragment productFragment = ProductListFragment.newInstance();
                                ActivityUtils.replaceFragmentInActivity(
                                        getSupportFragmentManager(), productFragment, R.id.contentFrame, "", false);
                                // Create the presenter
                                mProductListPresenter = new ProductListPresenter(Injection.provideProductsRepository(getApplicationContext()),
                                        Injection.provideCategoriesRepository(getApplicationContext()), productFragment);
                                break;
                            case R.id.category_navigation_menu_item:
                                toolbar.setTitle(R.string.product_categories);
                                CategoryListFragment categoryListFragment = CategoryListFragment.newInstance();
                                ActivityUtils.replaceFragmentInActivity(
                                        getSupportFragmentManager(), categoryListFragment, R.id.contentFrame, "", false);
                                // Create the presenter
                                mCategoryListPresenter = new CategoryListPresenter(
                                        Injection.provideCategoriesRepository(getApplicationContext()), categoryListFragment);
                                break;
                            case R.id.staff_navigation_menu_item:
                                toolbar.setTitle(R.string.staff);
                                StaffListFragment staffListFragment = StaffListFragment.newInstance();
                                    ActivityUtils.replaceFragmentInActivity(
                                            getSupportFragmentManager(), staffListFragment, R.id.contentFrame, "", false);
                                // Create the presenter
                                mStaffListPresenter = new StaffListPresenter(
                                        Injection.provideStaffRepository(getApplicationContext()), staffListFragment);
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        int size = mNavigationView.getMenu().size();
                        for (int i = 0; i < size; i++) {
                            mNavigationView.getMenu().getItem(i).setChecked(false);
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void addProduct() {

        AddProductFragment mAddProductFragment = AddProductFragment.newInstance();
        mAddProductFragment.show(getSupportFragmentManager(),"additem");

        // Create the presenter
        mAddProductPresenter = new AddProductPresenter(
                Injection.provideProductsRepository(getApplicationContext()),
                Injection.provideCategoriesRepository(getApplicationContext()),
                mAddProductFragment);
    }

    @Override
    public void addStaff() {

        AddStaffFragment mAddStaffFragment = AddStaffFragment.newInstance();
        mAddStaffFragment.show(getSupportFragmentManager(),"addstaff");

        // Create the presenter
        mAddStaffPresenter = new AddStaffPresenter(
                Injection.provideStaffRepository(getApplicationContext()),
                mAddStaffFragment);
    }

    @Override
    public void addCategory() {

        AddCategoryFragment mAddCategoryFragment = AddCategoryFragment.newInstance();
        mAddCategoryFragment.show(getSupportFragmentManager(),"addcategory");

        // Create the presenter
        mAddCategoryPresenter = new AddCategoryPresenter(
                Injection.provideCategoriesRepository(getApplicationContext()),
                mAddCategoryFragment);
    }

    @Override
    public void editProduct(Product product) {

        EditProductFragment mEditProductFragment = EditProductFragment.newInstance();
        mEditProductFragment.show(getSupportFragmentManager(),"edititem");

        // Create the presenter
        mEditProductPresenter = new EditProductPresenter(
                Injection.provideProductsRepository(getApplicationContext()),
                Injection.provideCategoriesRepository(getApplicationContext()),
                mEditProductFragment,
                product);

    }
}
