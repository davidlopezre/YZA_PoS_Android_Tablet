package com.pos.yza.yzapos.managetransactions.viewtransaction;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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
import com.pos.yza.yzapos.util.Formatters;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ViewTransactionFragment extends Fragment
                                     implements ViewTransactionContract.View {

    ViewTransactionContract.Presenter mPresenter;
    LineItemAdapter adapter;
    private static final String TAG = "VIEW_TRANS_FRAG";

    TextView totalView;

    public ViewTransactionFragment() {
        // Required empty public constructor
    }

    public static ViewTransactionFragment newInstance() {
        return new ViewTransactionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new LineItemAdapter(new ArrayList<>(0));

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public void setPresenter(@NonNull ViewTransactionContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_view_transaction_cart, container, false);

        ListView listView = root.findViewById(R.id.list_view);
        listView.setAdapter(adapter);


        Button viewPayments = root.findViewById(R.id.button_view_payments);
        viewPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mPresenter.goToCustomerDetails();
            }
        });

        TextView totalAmount = root.findViewById(R.id.totalAmt);
        totalAmount.setText("Total: " + Formatters.amountFormat.format(adapter.getTotalAmt()));
        this.totalView = totalAmount;

        return root;
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

        @Override
        public LineItem getItem(int i) {
            return lineItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public double getTotalAmt() {
            double amount = 0;
            for(LineItem lineItem: lineItems) {
                amount += lineItem.getAmount();
            }
            return amount;
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

            TextView label = rowView.findViewById(R.id.title);
            label.setText(lineItem.toString());
            label.setTextSize(20);

            TextView amount = rowView.findViewById(R.id.amount);

            amount.setText(Formatters.amountFormat.format(lineItem.getAmount()));
            amount.setTextSize(20);

            return rowView;
        }

        public void initLineItems(List<LineItem> lineItems) {
            this.lineItems = lineItems;
            notifyDataSetChanged();
        }
    }

    public void setLineItems(List<LineItem> lineItems) {
        adapter.initLineItems(lineItems);
        Log.i(TAG, "adapter reset");
        totalView.setText("Total: " + Formatters.amountFormat.format(adapter.getTotalAmt()));
    }
}
