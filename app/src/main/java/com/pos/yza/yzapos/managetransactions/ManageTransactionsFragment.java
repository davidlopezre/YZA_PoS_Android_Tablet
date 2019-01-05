package com.pos.yza.yzapos.managetransactions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.managetransactions.viewtransaction.ViewTransactionFragment;

import java.util.ArrayList;
import java.util.List;

public class ManageTransactionsFragment extends Fragment implements ManageTransactionsContract.View {
    private TransactionsAdapter mListAdapter;
    private ManageTransactionsContract.Presenter mPresenter;
    private OnFragmentInteractionListener mListener;
//    private ProductListListener listener;

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

    public ManageTransactionsFragment(){

    }

    public static ManageTransactionsFragment newInstance(){
        return new ManageTransactionsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mListAdapter = new TransactionsAdapter(new ArrayList<Transaction>());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.manage_transactions);
    }

    @Override
    public void setPresenter(@NonNull ManageTransactionsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_manage_transactions, container,
                false);

        // Set up the items view
        ListView listView = (ListView) root.findViewById(R.id.list_view);
        listView.setAdapter(mListAdapter);


        return root;
    }



    @Override
    public void showTransactions(List<Transaction> items) {
        mListAdapter.setList(items);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCancellationFeedback() {

    }

    @Override
    public void showRefundFeedback() {

    }

    @Override
    public void addedPaymentFeedback() {

    }

    private class TransactionsAdapter extends BaseAdapter {

        private List<Transaction> mTransactions;

        public TransactionsAdapter(List<Transaction> transactions) {
            this.mTransactions = transactions;
        }

        @Override
        public int getCount() {
            return mTransactions.size();
        }

        public void setList(List<Transaction> transactions){
            mTransactions = transactions;
        }

        @Override
        public Transaction getItem(int i) {
            return mTransactions.get(i);
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
                rowView = inflater.inflate(R.layout.transaction_manage,
                        viewGroup, false);
            }
            final Transaction transaction = getItem(i);

            final String transactionId = Integer.toString(transaction.getTransactionId());

            TextView titleTV = (TextView) rowView.findViewById(R.id.title);
            titleTV.setText(transaction.toString());

            Button refundButton = (Button) rowView.findViewById(R.id.button_refund);
            refundButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.refundTransaction(transactionId);
                }
            });

            Button cancelButton = (Button) rowView.findViewById(R.id.button_cancel);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.cancelTransaction(transactionId);
                }
            });

            Button addPaymentButton = (Button) rowView.findViewById(R.id.button_add_payment);
            addPaymentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.addPaymentToTransaction();
                }
            });

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onFragmentMessage(ManageTransactionsActivity.VIEW_TRANSACTION_CLICK,
                                                transaction);
                }
            });
            return rowView;
        }
    }

    public interface ItemListListener{
        void addItem();
    }




}
