package com.pos.yza.yzapos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pos.yza.yzapos.adminoptions.AdminOptionsActivity;
import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.StaffDataSource;
import com.pos.yza.yzapos.data.source.StaffRepository;
import com.pos.yza.yzapos.data.source.remote.CategoriesRemoteDataSource;
import com.pos.yza.yzapos.data.source.remote.StaffRemoteDataSource;
import com.pos.yza.yzapos.newtransaction.NewTransactionActivity;
import com.pos.yza.yzapos.data.source.remote.ProductsRemoteDataSource;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.source.ProductsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

////        FOR DALZY
//        Log.i("saveCategory", "in main");
//        CategoriesDataSource catsRepo = Injection.provideCategoriesRepository(getApplicationContext());

//        ArrayList<CategoryProperty> properties = new ArrayList<CategoryProperty>();
//        properties.add(new CategoryProperty("CatProp1"));
//        ProductCategory catTest = new ProductCategory("MainTest", properties);
//
//        catsRepo.saveCategory(catTest);

//        catsRepo.deleteCategory("4");
//
//        HashMap<String,String> staffEdits = new HashMap<String,String>();
//
//        staffEdits.put("name", "Majestic Edit");
//
//        staffRepo.editStaff("3", staffEdits);

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
