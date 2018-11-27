package com.pos.yza.yzapos.adminoptions.addproduct;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.ProductProperty;
import com.pos.yza.yzapos.util.DialogFragmentUtils;

import java.util.ArrayList;
import java.util.List;

public class AddProductFragment extends DialogFragment implements AddProductContract.View {
    private AddProductContract.Presenter mPresenter;

    private CategoryAdapter mSpinnerAdapter;

    private ProductCategory currentCategory;

    private LinearLayout propertyLayout;

    public AddProductFragment(){

    }

    public static AddProductFragment newInstance(){
        return new AddProductFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mSpinnerAdapter = new CategoryAdapter(getActivity(), android.R.layout.simple_list_item_1,
                new ArrayList<ProductCategory>());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull AddProductContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_add_item, container,
                false);

        Spinner spinner = (Spinner) root.findViewById(R.id.spinner);
        spinner.setAdapter(mSpinnerAdapter);

        propertyLayout = (LinearLayout) root.findViewById(R.id.layout_category_properties);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentCategory = (ProductCategory)adapterView.getItemAtPosition(i);
                Log.i("category_spinner", currentCategory.getName());
                propertyLayout.removeAllViews();
                for (int j = 0; j < currentCategory.getPropertyList().size(); j++) {
                    CategoryProperty property = currentCategory.getPropertyList().get(j);
                    EditText editText_property = new EditText(getContext());
                    editText_property.setHint(property.getName());
                    editText_property.setId(property.getId());
                    propertyLayout.addView(editText_property);
                    Log.i("category_spinner", "added " + property.getName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final EditText unitOfMeasure = (EditText) root.findViewById(R.id.unit_of_measure);
        final EditText unitPrice = (EditText) root.findViewById(R.id.unit_price);


        Button button = (Button) root.findViewById(R.id.button_add_item);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<ProductProperty> properties = new ArrayList<ProductProperty>();
                final int childCount = propertyLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    EditText property = (EditText) propertyLayout.getChildAt(i);
                    properties.add(new ProductProperty(property.getId(), property.getText().toString()));

                }

                mPresenter.confirmProduct(currentCategory, unitOfMeasure.getText().toString(),
                        unitPrice.getText().toString(), properties);
            }
        });

        return root;
    }

    @Override
    public void showProductProperties() {

    }

    @Override
    public void showCategories(List<ProductCategory> categories) {
        mSpinnerAdapter.replaceData(categories);
    }

    @Override
    public void showFeedback() {
        DialogFragmentUtils.giveFeedback(this, getContext(), "Product");
    }
}
