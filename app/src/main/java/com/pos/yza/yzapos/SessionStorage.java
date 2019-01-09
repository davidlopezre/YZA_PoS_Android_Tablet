package com.pos.yza.yzapos;

import android.app.Application;

import com.google.common.collect.Maps;
import com.pos.yza.yzapos.data.representations.Branch;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.CategoriesRepository;
import com.pos.yza.yzapos.data.source.ProductsDataSource;
import com.pos.yza.yzapos.data.source.ProductsRepository;
import com.pos.yza.yzapos.data.source.StaffDataSource;
import com.pos.yza.yzapos.data.source.StaffRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by beyondinfinity on 23/12/18.
 */

public class SessionStorage extends Application {
    private static SessionStorage instance;

    private static Map<Integer,ProductCategory> categoryMap;
    private static Map<Integer,Product> productMap;
    private static Map<Integer,Staff> staffMap;
    private static Branch branch;

    public static SessionStorage getInstance()
    {
        if (instance == null) {
            synchronized(SessionStorage.class) {
                if (instance == null)
                    instance = new SessionStorage();
            }
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.getCategories();
        this.getStaff();
        this.getInitBranch();
    }

    private void getCategories() {
        CategoriesRepository mCategoriesRepo = Injection.provideCategoriesRepository(this);
        mCategoriesRepo.getCategories(new CategoriesDataSource.LoadCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<ProductCategory> categories) {
                categoryMap = Maps.uniqueIndex(categories, ProductCategory::getId);
                getProducts();
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void getStaff() {
        StaffRepository mStaffRepo = Injection.provideStaffRepository(this);
        mStaffRepo.getAllStaff(new StaffDataSource.LoadStaffCallback() {
            @Override
            public void onStaffLoaded(List<Staff> staff) {
                staffMap = Maps.uniqueIndex(staff, Staff::getStaffId);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void getProducts() {
        ProductsRepository mProductsRepo = Injection.provideProductsRepository(this);
        mProductsRepo.getProducts(new ProductsDataSource.LoadProductsCallback() {
            @Override
            public void onProductsLoaded(List<Product> products) {
                for(Product product: products){
                    Integer categoryId = product.getCategory().getId();
                    ProductCategory category = categoryMap.get(categoryId);
                    product.setCategory(category);
                    category.addProduct(product);

                }
                productMap = Maps.uniqueIndex(products, Product::getId);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    public void getInitBranch() {
        branch = new Branch(1, "TESTBRANCH", "TESTADD");
    }

    public static Branch getBranch() {
        return branch;
    }

    public static Product getProduct(int id) {
        return productMap.get(id);
    }

    public static List<Product> getProductsByCategory(int categoryId){
        return categoryMap.get(categoryId).getProducts();
    }

    public static List<ProductCategory> getAllCategories() {
        return new ArrayList<ProductCategory>(categoryMap.values());
    }

    public static List<Staff> getAllStaff() {
        return new ArrayList<Staff>(staffMap.values());
    }

    public static Staff getStaffById(int id) {
        return staffMap.get(id);
    }




}
