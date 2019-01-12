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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.util.DialogFragmentUtils;
import com.pos.yza.yzapos.util.Formatters;

import java.util.ArrayList;
import java.util.List;

public class AddProductFragment extends DialogFragment implements AddProductContract.View {
    private AddProductContract.Presenter mPresenter;
    private ArrayAdapter<ProductCategory> mSpinnerAdapter;
    private ProductCategory currentCategory;
    private LinearLayout propertyLayout;
    private List<EditText> propertyAnswers;

    public AddProductFragment(){

    }

    public static AddProductFragment newInstance(){
        return new AddProductFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
        mSpinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item);
        propertyAnswers = new ArrayList<>();

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
        View root = inflater.inflate(R.layout.fragment_add_product, container,
                false);

        Spinner spinner = root.findViewById(R.id.spinner);
        spinner.setAdapter(mSpinnerAdapter);

        propertyLayout = root.findViewById(R.id.layout_category_properties);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentCategory = (ProductCategory) adapterView.getItemAtPosition(i);
                Log.i("category_spinner", currentCategory.getName());
                propertyLayout.removeAllViews();
                propertyAnswers.clear();
                for (CategoryProperty property : currentCategory.getPropertyList()) {
                    TextView label_property = new TextView(getContext());
                    label_property.setTextAppearance(getContext(), R.style.FormLabel);
                    label_property.setPadding(0, 12, 0, 0);
                    label_property.setText(Formatters.capitalise(property.getName()));

                    EditText editText_property = (EditText) View.inflate(getContext(), R.layout.edittext_one_line_done, null);
                    editText_property.setId(property.getId());

                    propertyLayout.addView(label_property);
                    propertyLayout.addView(editText_property);
                    propertyAnswers.add(editText_property);
                    Log.i("category_spinner", "added " + property.getName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final EditText unitOfMeasure = root.findViewById(R.id.unit_of_measure);
        final EditText unitPrice = root.findViewById(R.id.unit_price);

        Button button = root.findViewById(R.id.button_add_item);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.confirmProduct(currentCategory, unitOfMeasure.getText().toString(),
                        unitPrice.getText().toString());
            }
        });

        return root;
    }

    @Override
    public void showProductProperties() {

    }

    @Override
    public void showCategories(List<ProductCategory> categories) {
        mSpinnerAdapter.clear();
        mSpinnerAdapter.addAll(categories);
    }

    @Override
    public void showFeedback() {
        DialogFragmentUtils.giveCreatedFeedback(this, getContext(), "Product");
    }

    @Override
    public List<EditText> getPropertyEditTexts() {
        return propertyAnswers;
    }
}
