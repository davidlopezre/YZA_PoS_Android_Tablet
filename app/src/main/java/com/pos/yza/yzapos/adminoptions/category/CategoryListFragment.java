package com.pos.yza.yzapos.adminoptions.category;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.adminoptions.staff.StaffListContract;
import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.util.Formatters;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

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
        View root = inflater.inflate(R.layout.fragment_admin_product_categories, container,
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

//            ImageButton imageEdit = rowView.findViewById(R.id.button_edit_item);
//            imageEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    showProductCategoryDetailsUi(category);
//                }
//            });

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showProductCategoryDetailsUi(category);
                }
            });

            return rowView;
        }
    }

    public interface CategoryListListener{
        void addCategory();
        void editCategory(ProductCategory category);
    }


    private void showProductCategoryDetailsUi(ProductCategory productCategory) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_productcategory_details, null);

        TextView nameView = popupView.findViewById(R.id.product_category_name);
        nameView.setText(productCategory.getName());
        TextView propertiesView = popupView.findViewById(R.id.category_properties);
        propertiesView.setText(getPropertiesDescription(productCategory));

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

//        Button editButton = popupView.findViewById(R.id.button_edit);
//        editButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.editCategory(productCategory);
//                popupWindow.dismiss();
//            }
//        });
    }

    private String getPropertiesDescription(ProductCategory category) {
        String description = "";
        List<CategoryProperty> properties = category.getPropertyList();
        for (int i = 0; i < properties.size(); i++) {
            if (i > 0) {
                description += ", " + properties.get(i).getName();
            }
            else {
                description += properties.get(i).getName();
            }
        }
        return description;
    }
}
