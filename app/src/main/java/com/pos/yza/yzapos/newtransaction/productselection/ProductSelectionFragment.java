package com.pos.yza.yzapos.newtransaction.productselection;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.newtransaction.OnFragmentInteractionListener;
import com.pos.yza.yzapos.newtransaction.payment.PaymentContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductSelectionFragment extends Fragment implements ProductSelectionContract.View {

    ProductSelectionContract.Presenter mPresenter;

    private OnFragmentInteractionListener mListener;

    public ProductSelectionFragment() {
        // Required empty public constructor
    }

    public static ProductSelectionFragment newInstance() {
        return new ProductSelectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mListAdapter = new TasksAdapter(new ArrayList<Task>(0), mItemListener);

    }

    @Override
    public void setPresenter(@NonNull ProductSelectionContract.Presenter presenter) {
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
        View root = inflater.inflate(R.layout.fragment_product_selection, container, false);

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

}
