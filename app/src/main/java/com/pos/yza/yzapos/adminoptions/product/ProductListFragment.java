package com.pos.yza.yzapos.adminoptions.product;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment implements ProductListContract.View {
    private ProductAdapter mListAdapter;
    private ProductListContract.Presenter mPresenter;
    private ArrayAdapter<ProductCategory> mSpinnerAdapter;
    private ProductListListener listener;

    private final String TAG = "ADMIN_PROD_LIST_FRAG";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ProductListListener) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    public ProductListFragment(){

    }

    public static ProductListFragment newInstance(){
        return new ProductListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mListAdapter = new ProductAdapter(new ArrayList<Product>());
        mSpinnerAdapter =
                new ArrayAdapter<>(getActivity(),
                        R.layout.spinner_item);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull ProductListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void setUpSpinnerAdapter(List<ProductCategory> content){
        mSpinnerAdapter.clear();
        mSpinnerAdapter.addAll(content);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_item, container,
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
                mPresenter.addNewProduct();
            }
        });

        Spinner spinner = (Spinner) root.findViewById(R.id.spinner);
        spinner.setAdapter(mSpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ProductCategory productCategory = mSpinnerAdapter.getItem(position);
                mPresenter.loadProductsByCategory(productCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return root;
    }

    @Override
    public void showProducts(List<Product> products) {
        mListAdapter.setList(products);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProductDetailsUi(String productId) {

    }

    @Override
    public void showFilteringPopUpMenu() {

    }

    @Override
    public void showAddProduct() {
        listener.addProduct();
    }

    @Override
    public void showEditProduct() {

    }

    private class ProductAdapter extends BaseAdapter {

        private List<Product> mProducts;

        public ProductAdapter(List<Product> products) {
            this.mProducts = products;
        }

        @Override
        public int getCount() {
            return mProducts.size();
        }

        public void setList(List<Product> products){
            mProducts = products;
        }

        @Override
        public Product getItem(int i) {
            return mProducts.get(i);
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
            final Product product = getItem(i);

            TextView titleTV = (TextView) rowView.findViewById(R.id.title);
            titleTV.setText(product.getName());

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chosenLineItemIndex = i;
                    Log.i(TAG, "product chosen is: " + adapter.getItem(chosenLineItemIndex));
                    DialogFragment dialog = new RemoveLineItemDialog();
                    // Create a new bundle to pass the product name to the QuantityDialog fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("CLICKED", adapter.getItem(chosenLineItemIndex).toString());
                    dialog.setArguments(bundle);
                    dialog.setTargetFragment(CartFragment.this, 0);
                    dialog.show(getFragmentManager(), "dialog");

                }
            });

            return rowView;
        }
    }

    public interface ProductListListener {
        void addProduct();
    }

    @Override
    public ProductCategory getChosenProductCategory() {
        if (mSpinnerAdapter.getCount() > 0){
            return mSpinnerAdapter.getItem(1);
        }
        return null;
    }
}
