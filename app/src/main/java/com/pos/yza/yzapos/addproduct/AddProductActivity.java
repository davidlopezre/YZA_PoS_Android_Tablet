package com.pos.yza.yzapos.addproduct;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.util.ActivityUtils;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        AddProductFragment fragment =
                (AddProductFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            // Create the fragment
            fragment = AddProductFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }
    }
}
