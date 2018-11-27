package com.pos.yza.yzapos.adminoptions.addcategory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.util.DialogFragmentUtils;

import java.util.ArrayList;

public class AddCategoryFragment extends DialogFragment implements AddCategoryContract.View {
    private AddCategoryContract.Presenter mPresenter;

    private LinearLayout propertyLayout;

    public AddCategoryFragment(){

    }

    public static AddCategoryFragment newInstance(){
        return new AddCategoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull AddCategoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_add_category, container,
                false);

        propertyLayout = (LinearLayout) root.findViewById(R.id.layout_category_properties);


        final EditText name = (EditText) root.findViewById(R.id.name);

        Button buttonAddProperty = (Button) root.findViewById(R.id.button_add_property);
        buttonAddProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText_property = new EditText(getContext());
                editText_property.setHint("Enter property");
                propertyLayout.addView(editText_property);
            }
        });

        Button buttonConfirm = (Button) root.findViewById(R.id.button_add_item);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<CategoryProperty> properties = new ArrayList<CategoryProperty>();
                final int childCount = propertyLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    EditText property = (EditText) propertyLayout.getChildAt(i);
                    properties.add(new CategoryProperty(property.getText().toString()));
                }

                mPresenter.confirmCategory(name.getText().toString(), properties);
            }
        });

        return root;
    }

    @Override
    public void showFeedback() {
        DialogFragmentUtils.giveFeedback((DialogFragment) this, getContext(), "Category");
    }
}
