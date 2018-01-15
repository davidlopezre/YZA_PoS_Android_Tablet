package com.pos.yza.yzapos.adminoptions.category;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.adminoptions.staff.StaffListContract;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.Staff;

import java.util.ArrayList;
import java.util.List;

public class CategoryListFragment extends Fragment implements CategoryListContract.View {
    private CategoryAdapter mListAdapter;
    private CategoryListContract.Presenter mPresenter;
    private CategoryListListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (CategoryListFragment.CategoryListListener) context;
        } catch (ClassCastException castException){

        }
    }

    public CategoryListFragment(){

    }

    public static CategoryListFragment newInstance(){
        return new CategoryListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mListAdapter = new CategoryAdapter(new ArrayList<ProductCategory>());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull CategoryListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_item, container,
                false);

        // Set up the items view
        ListView listView = (ListView) root.findViewById(R.id.list_view);
        listView.setAdapter(mListAdapter);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add);
        fab.setImageResource(R.drawable.ic_add2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert(mPresenter != null);
                mPresenter.addCategory();
            }
        });

        Spinner spinner = (Spinner) root.findViewById(R.id.spinner);
        // Apply the adapter to the spinner
//        spinner.setAdapter(mSpinnerAdapter);

        return root;
    }

    @Override
    public void showCategories(List<ProductCategory> categories) {
        mListAdapter.setList(categories);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAddCategory() {
        listener.addCategory();
    }

    @Override
    public void showEditCategory() {

    }

    @Override
    public void showDeleteCategory() {

    }

    private class CategoryAdapter extends BaseAdapter {

        private List<ProductCategory> mCategoryList;

        public CategoryAdapter(List<ProductCategory> mCategoryList) {
            this.mCategoryList = mCategoryList;
        }

        @Override
        public int getCount() {
            return mCategoryList.size();
        }

        public void setList(List<ProductCategory> categoryList){
            mCategoryList = categoryList;
        }

        @Override
        public ProductCategory getItem(int i) {
            return mCategoryList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.list_item,
                        viewGroup, false);
            }
            final ProductCategory category = getItem(i);

            TextView titleTV = (TextView) rowView.findViewById(R.id.title);
            titleTV.setText(category.getName());

            ImageButton deleteButton = (ImageButton) rowView.findViewById(R.id.button_del_item);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.deleteCategory(category);
                }
            });


            return rowView;
        }
    }

    public interface CategoryListListener{
        void addCategory();
    }
}
