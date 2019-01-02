package com.pos.yza.yzapos.newtransaction.productselection;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.LineItem;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.newtransaction.OnFragmentInteractionListener;
import com.pos.yza.yzapos.newtransaction.quantity.QuantityDialog;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductSelectionFragment extends Fragment implements ProductSelectionContract.View,
        QuantityDialog.DialogClickListener {

    public static final String TAG = "ProductSelectionFrag";

    private Product chosenProduct;

    ProductSelectionContract.Presenter mPresenter;

    private OnFragmentInteractionListener mListener;

    public ProductAdapter adapter;

    public ProductSelectionFragment() {
        // Required empty public constructor
    }

    public static ProductSelectionFragment newInstance() {
        return new ProductSelectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ProductAdapter(new ArrayList<Product>());

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

        // Set up tasks view
        ListView listView = (ListView) root.findViewById(R.id.list_view);
        listView.setAdapter(adapter);


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
    public void showProducts(List<Product> products) {
        adapter.setList(products);
    }

    private class ProductAdapter extends BaseAdapter {

        private List<Product> products;

        public ProductAdapter(List<Product> products) {
            this.products = products;
        }

        @Override
        public int getCount() {
            return products.size();
        }

        public void setList(List<Product> items){
            products = items;
            notifyDataSetChanged();
        }

        @Override
        public Product getItem(int i) {
            return products.get(i);
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
                rowView = inflater.inflate(R.layout.list_product,
                        viewGroup, false);
            }
            final Product product = products.get(i);

            TextView label = (TextView) rowView.findViewById(R.id.title);
            Log.i(TAG, "name of prod to display " + product.getName());
            label.setText(product.getName());

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chosenProduct = product;
                    Log.i(TAG, "product chosen is: " + product);
                    DialogFragment dialog = new QuantityDialog();
                    // Create a new bundle to pass the product name to the QuantityDialog fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("CLICKED", product.getName());
                    dialog.setArguments(bundle);
                    dialog.setTargetFragment(ProductSelectionFragment.this, 0);
                    dialog.show(getFragmentManager(), "dialog");

                }
            });

            return rowView;
        }
    }


    @Override
    public void onDialogPositiveClick(int value) {
        Log.i(TAG, "clicked done on: " + Integer.toString(value));
        // Create the LineItem
        LineItem lineItem = new LineItem(value, value * chosenProduct.getUnitPrice(), chosenProduct.getId());
        Log.i(TAG, "new LineItem: " + lineItem.toString());
        mListener.onFragmentMessage("PRODUCT_SELECTION", lineItem);
    }

    @Override
    public void onDialogNegativeClick() {
        chosenProduct = null;
    }
}
