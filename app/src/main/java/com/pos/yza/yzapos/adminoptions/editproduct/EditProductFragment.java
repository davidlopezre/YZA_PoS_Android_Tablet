package com.pos.yza.yzapos.adminoptions.editproduct;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.util.Formatters;

import java.util.List;
import java.util.Locale;

public class EditProductFragment extends DialogFragment implements EditProductContract.View {
    private EditProductContract.Presenter mPresenter;
    private ArrayAdapter<ProductCategory> mSpinnerAdapter;
    private Spinner categorySpinner;
    private ProductCategory currentCategory;
    private LinearLayout propertyLayout;

    private final String TAG = "EDIT_PROD_FRAG";

    public EditProductFragment() {

    }

    public static EditProductFragment newInstance() {
        return new EditProductFragment();
    }

    public void setPresenter(@NonNull EditProductContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
        mSpinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_edit_product, container, false);
        TextView titleView = root.findViewById(R.id.dialog_title);
        titleView.setText(getString(R.string.edit) + " " + mPresenter.getProductName());

        propertyLayout = root.findViewById(R.id.layout_category_properties);

        Spinner spinner = root.findViewById(R.id.category_spinner);
        spinner.setAdapter(mSpinnerAdapter);

        spinner.setOnItemSelectedListener(new CategoryAdapterViewListener());

        categorySpinner = spinner;

        EditText unitMeasure = root.findViewById(R.id.unit_of_measure);
        unitMeasure.setText(mPresenter.getProduct().getUnitMeasure());
        EditText unitPrice = root.findViewById(R.id.unit_price);
        unitPrice.setText(String.format(Locale.getDefault(), "%.2f",mPresenter.getProduct().getUnitPrice()));

        Button saveButton = root.findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return root;
    }

    @Override
    public void showCategories(List<ProductCategory> categories) {
        mSpinnerAdapter.clear();
        mSpinnerAdapter.addAll(categories);
        initialiseForm();
    }

    private void initialiseForm() {
        ProductCategory productCategory = mPresenter.getProduct().getCategory();
        int position = findSpinnerPosition(categorySpinner, productCategory);
        categorySpinner.setSelection(position);
        Log.i(TAG, "setting spinner to category " + productCategory.getName());
        Log.i(TAG, "pos: " + position);
    }

    private int findSpinnerPosition(Spinner spinner, ProductCategory category) {
        Log.i(TAG, "in finding spinner pos");
        for (int i = 0; i < spinner.getCount(); i++) {
            ProductCategory category_i = (ProductCategory) spinner.getItemAtPosition(i);
            Log.i(TAG, "category name " + category_i.getName());
            if (category_i.getName().equals(category.getName())) {
                return i;
            }
        }
        return -1;
    }


    private class CategoryAdapterViewListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            currentCategory = (ProductCategory) adapterView.getItemAtPosition(i);
            Log.i("category_spinner", currentCategory.getName());
            propertyLayout.removeAllViews();
            boolean sameAsProductCategory = currentCategory.getName().equals(mPresenter.getProductCategoryName());

            for (CategoryProperty property : currentCategory.getPropertyList()) {
                TextView label_property = new TextView(getContext());
                label_property.setTextAppearance(getContext(), R.style.FormLabel);
                label_property.setPadding(0, 12, 0, 0);
                label_property.setText(Formatters.capitalise(property.getName()));

                EditText editText_property = new EditText(getContext());
                editText_property.setTextAppearance(getContext(), R.style.FormAnswer);
                editText_property.setId(property.getId());

                if (sameAsProductCategory) {
                    editText_property.setText(mPresenter.getProductPropertyValue(property.getId()));
                }

                propertyLayout.addView(label_property);
                propertyLayout.addView(editText_property);
                Log.i("category_spinner", "added " + property.getName());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }


}
