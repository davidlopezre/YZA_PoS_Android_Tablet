package com.pos.yza.yzapos.newtransaction.productselection;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.Item;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.newtransaction.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductSelectionFragment extends Fragment implements ProductSelectionContract.View {

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
        public Item getItem(int i) {
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
                rowView = inflater.inflate(R.layout.list_item,
                        viewGroup, false);
            }
            final Product product = products.get(i);

            TextView label = (TextView) rowView.findViewById(R.id.title);
            label.setText(product.getName());

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onFragmentMessage("PRODUCT_SELECTION", product);
                }
            });

            return rowView;
        }
    }
}