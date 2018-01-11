package com.pos.yza.yzapos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pos.yza.yzapos.adminoptions.AdminOptionsActivity;
import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.StaffDataSource;
import com.pos.yza.yzapos.data.source.StaffRepository;
import com.pos.yza.yzapos.data.source.remote.StaffRemoteDataSource;
import com.pos.yza.yzapos.newtransaction.NewTransactionActivity;
import com.pos.yza.yzapos.data.source.remote.ProductsRemoteDataSource;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.source.ProductsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FOR DALZY

//        StaffDataSource staffRepo = Injection.provideStaffRepository(getApplicationContext());
//
//        staffRepo.getAllStaff(new StaffDataSource.LoadStaffCallback() {
//            @Override
//            public void onStaffLoaded(List<Staff> staff) {
//                for(Staff s: staff){
//                    Log.i("staffRemoteRequest",s.getName());
//                }
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        });
//
//        Staff staffTest = new Staff("Minion", "Master", "1111",
//                                    "11@11.com", "Pixar");
//
//        staffRepo.saveStaff(staffTest);
//
//        staffRepo.deleteStaff("2");
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
