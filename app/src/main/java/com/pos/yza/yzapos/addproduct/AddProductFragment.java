package com.pos.yza.yzapos.addproduct;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pos.yza.yzapos.R;


public class AddProductFragment extends Fragment {


    public AddProductFragment() {
        // Required empty public constructor
    }

    public static AddProductFragment newInstance(){
        return new AddProductFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_add_item, container,
                false);
        return root;
    }

}
