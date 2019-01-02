package com.pos.yza.yzapos.newtransaction.cart;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.LineItem;
import com.pos.yza.yzapos.newtransaction.OnFragmentInteractionListener;
import com.pos.yza.yzapos.newtransaction.productselection.ProductSelectionFragment;
import com.pos.yza.yzapos.newtransaction.quantity.QuantityDialog;
import com.pos.yza.yzapos.util.Formatters;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class CartFragment extends Fragment implements CartContract.View,
        RemoveLineItemDialog.DialogClickListener {

    public static final String TAG = "CartFrag";

    CartContract.Presenter mPresenter;
    LineItemAdapter adapter;
    private OnFragmentInteractionListener mListener;
    private int chosenLineItemIndex;
    private TextView totalDueView;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new LineItemAdapter(new ArrayList<LineItem>(0));

    }

    @Override
    public void setPresenter(@NonNull CartContract.Presenter presenter) {
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
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        ListView listView = (ListView) root.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        Button add = (Button) root.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goToCategorySelection();
            }
        });

        Button next = (Button) root.findViewById(R.id.button_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goToCustomerDetails();
            }
        });

        TextView totalAmount = (TextView) root.findViewById(R.id.totalAmt);
        totalAmount.setText("Total: " + Formatters.amountFormat.format(adapter.getTotalAmt()));
        this.totalDueView = totalAmount;

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
    public void showProductsInCart() {

    }

    @Override
    public void addLineItemToAdapter(LineItem lineItem) {
        Log.i("CART", "Adding product to adapter");
        adapter.addLineItem(lineItem);
    }

    @Override
    public void showCategorySelection() {
        mListener.onFragmentMessage(getTag(), null);
    }

    @Override
    public void showCustomerDetails() {
        mListener.onFragmentMessage(getTag(), adapter.lineItems);
    }

    private class LineItemAdapter extends BaseAdapter {

        private List<LineItem> lineItems;

        public LineItemAdapter(List<LineItem> lineItems) {
            this.lineItems = lineItems;
        }

        @Override
        public int getCount() {
            return lineItems.size();
        }

        public double getTotalAmt() {
            double amount = 0;
            for(LineItem lineItem: lineItems) {
                amount += lineItem.getAmount();
            }
            return amount;
        }

        public void addLineItem(LineItem lineItem) {
            lineItems.add(lineItem);
            notifyDataSetChanged();
            Log.i("CART","Product added. New list is " + lineItem.toString());

        }

        public void removeLineItem(int i) {
            lineItems.remove(i);
            notifyDataSetChanged();
            Log.i("CART","Product removed");
            updateTotalDueView();
        }

        @Override
        public LineItem getItem(int i) {
            return lineItems.get(i);
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
                rowView = inflater.inflate(R.layout.lineitem,
                        viewGroup, false);
            }
            final LineItem lineItem = lineItems.get(i);

            TextView label = (TextView) rowView.findViewById(R.id.title);
            label.setText(lineItem.toString());
            label.setTextSize(20);

            TextView amount = (TextView) rowView.findViewById(R.id.amount);

            amount.setText(Formatters.amountFormat.format(lineItem.getAmount()));
            amount.setTextSize(20);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chosenLineItemIndex = i;
                    Log.i(TAG, "lineitem chosen is: " + adapter.getItem(chosenLineItemIndex));
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

    @Override
    public void onDialogPositiveClick(int value) {
        Log.i(TAG, "clicked removed on: " + Integer.toString(value));
        Log.i(TAG, "chosen LineItem: " + adapter.getItem(chosenLineItemIndex).toString());
        adapter.removeLineItem(chosenLineItemIndex);
    }

    @Override
    public void onDialogNegativeClick() {
    }

    private void updateTotalDueView() {
        totalDueView.setText("Total: " + Formatters.amountFormat.format(adapter.getTotalAmt()));
    }
}
