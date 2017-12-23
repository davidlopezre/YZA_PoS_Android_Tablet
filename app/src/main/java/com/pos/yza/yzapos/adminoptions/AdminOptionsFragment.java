package com.pos.yza.yzapos.adminoptions;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pos.yza.yzapos.R;

import java.util.ArrayList;

public class AdminOptionsFragment extends Fragment {
    private ArrayAdapter<String> mListAdapter;

    public AdminOptionsFragment(){

    }

    public static AdminOptionsFragment newInstance(){
        return new AdminOptionsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        String[] items = {"hello", "its", "me"};
        mListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_admin_options, container,
                false);

        ListView listView = (ListView) root.findViewById(R.id.list_view);
        listView.setAdapter(mListAdapter);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return root;
    }
}
