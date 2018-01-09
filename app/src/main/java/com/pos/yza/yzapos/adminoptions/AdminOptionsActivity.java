package com.pos.yza.yzapos.adminoptions;

import android.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pos.yza.yzapos.Injection;
import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.adminoptions.additem.AddItemFragment;
import com.pos.yza.yzapos.adminoptions.additem.AddItemPresenter;
import com.pos.yza.yzapos.adminoptions.item.ItemListFragment;
import com.pos.yza.yzapos.adminoptions.item.ItemListPresenter;
import com.pos.yza.yzapos.data.source.remote.ProductsRemoteDataSource;
import com.pos.yza.yzapos.util.ActivityUtils;

public class AdminOptionsActivity extends AppCompatActivity implements ItemListFragment.ItemListListener {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private DrawerLayout mDrawerLayout;

    private ItemListPresenter mItemListPresenter;

    private AddItemPresenter mAddItemPresenter;

    private ProductsRemoteDataSource mProductsRemoteDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle(R.string.admin_options);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ItemListFragment itemListFragment =
                (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (itemListFragment == null) {
            // Create the fragment
                itemListFragment = ItemListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), itemListFragment, R.id.contentFrame);
        }

        // Create the presenter
        mItemListPresenter = new ItemListPresenter(
                Injection.provideProductsRepository(getApplicationContext()),
                Injection.provideCategoriesRepository(getApplicationContext()),
                itemListFragment);

        if (savedInstanceState != null){

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        outState.putSerializable(CURRENT_FILTERING_KEY, mItemListPresenter.getFiltering());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_actions, menu);
        return true;
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
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.product_navigation_menu_item:
                                // Do nothing, we're already on that screen
                                break;
                            case R.id.category_navigation_menu_item:
//                                Intent intent =
//                                        new Intent(TasksActivity.this, StatisticsActivity.class);
//                                startActivity(intent);
                                break;
                            case R.id.staff_navigation_menu_item:
                                // Do nothing, we're already on that screen
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void addItem () {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        AddItemFragment mAddItemFragment = AddItemFragment.newInstance();
        mAddItemFragment.show(getSupportFragmentManager(),"additem");


//        AddItemFragment addItemFragment =
//                (AddItemFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameRight);
//        if (addItemFragment == null) {
//            // Create the fragment
//            addItemFragment = AddItemFragment.newInstance();
//            ActivityUtils.addFragmentToActivity(
//                    getSupportFragmentManager(), addItemFragment, R.id.contentFrameRight);
//        }

        // Create the presenter
        mAddItemPresenter = new AddItemPresenter(
                Injection.provideProductsRepository(getApplicationContext()), mAddItemFragment);
    }

}
