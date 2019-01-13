package com.pos.yza.yzapos.adminoptions.editproduct;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.util.DialogFragmentUtils;
import com.pos.yza.yzapos.util.Formatters;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EditProductFragment extends DialogFragment implements EditProductContract.View {
    private EditProductContract.Presenter mPresenter;
    private LinearLayout propertyLayout;
    private List<EditText> propertyAnswers;

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
        propertyAnswers = new ArrayList<>();
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

        initialiseForm();

        EditText unitMeasure = root.findViewById(R.id.unit_of_measure);
        unitMeasure.setText(mPresenter.getProduct().getUnitMeasure());
        EditText unitPrice = root.findViewById(R.id.unit_price);
        unitPrice.setText(String.format(Locale.getDefault(), "%.2f",mPresenter.getProduct().getUnitPrice()));

        Button saveButton = root.findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.saveProduct(unitMeasure.getText().toString(),
                                       unitPrice.getText().toString());
            }
        });


        return root;
    }

    private void initialiseForm() {
        ProductCategory productCategory = mPresenter.getProduct().getCategory();

        for (CategoryProperty categoryProperty : productCategory.getPropertyList()) {
            TextView label_property = new TextView(getContext());
            label_property.setTextAppearance(getContext(), R.style.FormLabel);
            label_property.setPadding(0, 12, 0, 0);
            label_property.setText(Formatters.capitalise(categoryProperty.getName()));

            EditText editText_property = (EditText) View.inflate(getContext(), R.layout.edittext_one_line_done, null);
            editText_property.setId(categoryProperty.getId());
            editText_property.setText(mPresenter.getProductPropertyValue(categoryProperty.getId()));

            Log.i(TAG, "catpropid: " + categoryProperty.getId() + " editviewid: " + editText_property.getId());

            propertyLayout.addView(label_property);
            propertyLayout.addView(editText_property);
            propertyAnswers.add(editText_property);

        }
        Log.i(TAG, "form initialised");
    }

    @Override
    public List<EditText> getPropertyEditTexts() {
        return propertyAnswers;
    }

    @Override
    public void showFeedback() {
        DialogFragmentUtils.giveEditedFeedback(this, getContext(), "Product");
    }

}
