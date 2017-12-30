package com.pos.yza.yzapos.adminoptions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.Product;

import java.util.List;

public class AdminOptionsFragment extends Fragment implements AdminOptionsContract.View {
    private ArrayAdapter<String> mListAdapter;
    private AdminOptionsContract.Presenter mPresenter;

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

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull AdminOptionsContract.Presenter presenter) {
        mPresenter = presenter;
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
        fab.setImageResource(R.drawable.ic_add2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert(mPresenter != null);
                mPresenter.addNewProduct();
            }
        });
        return root;
    }

    @Override
    public void showProducts(List<Product> products) {

    }

    @Override
    public void showProductDetailsUi(String productId) {

    }

    @Override
    public void showFilteringPopUpMenu() {

    }

    @Override
    public void showAddProduct() {
//        Bundle bundle = new Bundle();
//        AddProductFragment addProductFragment =
//                (AddProductFragment) getActivity().getSupportFragmentManager().
//                        findFragmentById(R.id.frame_add);
//        if (addProductFragment == null) {
//            // Create the fragment
//            addProductFragment = addProductFragment.newInstance();
//            ActivityUtils.addFragmentToActivity(
//                    getActivity().getSupportFragmentManager(), addProductFragment, R.id.frame_add);
//        }


    }

    @Override
    public void showEditProduct() {

    }
}
