package com.pos.yza.yzapos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pos.yza.yzapos.adminoptions.AdminOptionsActivity;
import com.pos.yza.yzapos.newtransaction.TransactionsActivity;

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
        Intent intent = new Intent(getApplicationContext(), TransactionsActivity.class);
        startActivity(intent);
    }

    public void admin(View view){
        Intent intent = new Intent(getApplicationContext(), AdminOptionsActivity.class);
        startActivity(intent);
    }

}
