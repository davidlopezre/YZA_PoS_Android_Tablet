package com.pos.yza.yzapos.adminoptions.editcategory;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class EditCategoryFragment extends DialogFragment implements EditCategoryContract.View {

    private EditCategoryContract.Presenter mPresenter;

    public EditCategoryFragment() {

    }

    public static EditCategoryFragment newInstance() {
        return new EditCategoryFragment();
    }

    @Override
    public void setPresenter(@NonNull EditCategoryContract.Presenter presenter) {
        mPresenter = presenter;
    }


}
