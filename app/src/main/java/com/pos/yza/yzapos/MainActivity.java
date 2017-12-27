package com.pos.yza.yzapos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pos.yza.yzapos.adminoptions.AdminOptionsActivity;
import com.pos.yza.yzapos.createtransaction.NewTransactionActivity;
import com.pos.yza.yzapos.data.AdminOptionsDataSource;
import com.pos.yza.yzapos.data.ProductCategory;
import com.pos.yza.yzapos.data.RequestHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProductCategory category = new ProductCategory(2, new ArrayList<String>());
        RequestHandler rh = RequestHandler.getInstance(this);
        AdminOptionsDataSource dataSource = new AdminOptionsDataSource(rh);
        dataSource.getProductsByCategory(category);

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
