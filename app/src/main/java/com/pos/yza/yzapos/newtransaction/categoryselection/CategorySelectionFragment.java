package com.pos.yza.yzapos.newtransaction.categoryselection;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.newtransaction.MyRecyclerViewAdapter;
import com.pos.yza.yzapos.newtransaction.OnFragmentInteractionListener;
import com.pos.yza.yzapos.newtransaction.payment.PaymentContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class CategorySelectionFragment extends Fragment implements CategorySelectionContract.View,
        MyRecyclerViewAdapter.ItemClickListener{

    CategorySelectionContract.Presenter mPresenter;

    MyRecyclerViewAdapter<ProductCategory> adapter;

    private OnFragmentInteractionListener mListener;

    public CategorySelectionFragment() {
        // Required empty public constructor
    }

    public static CategorySelectionFragment newInstance() {
        return new CategorySelectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setPresenter(@NonNull CategorySelectionContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product_category_selection, container, false);

        // set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        int numberOfColumns = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        adapter = new MyRecyclerViewAdapter<ProductCategory>(getContext(), new ArrayList<ProductCategory>());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showCategories(List<ProductCategory> categories) {
        adapter.replaceData(categories);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("CATEGORY_SELECTION", "You clicked " + adapter.getItem(position) +
                " at position " + position);
        mListener.onFragmentMessage("CATEGORY_SELECTION", adapter.getItem(position));
    }
}
