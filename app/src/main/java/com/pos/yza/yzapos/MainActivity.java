package com.pos.yza.yzapos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pos.yza.yzapos.adminoptions.AdminOptionsActivity;
import com.pos.yza.yzapos.newtransaction.NewTransactionActivity;
import com.pos.yza.yzapos.data.source.remote.ProductsRemoteDataSource;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.source.ProductsRepository;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
