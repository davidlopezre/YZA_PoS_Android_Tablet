package com.pos.yza.yzapos.managetransactions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.util.Formatters;

import java.util.ArrayList;
import java.util.List;

public class ManageTransactionsFragment extends Fragment implements ManageTransactionsContract.View {
    private TransactionsAdapter mListAdapter;
    private ManageTransactionsContract.Presenter mPresenter;
    private OnFragmentInteractionListener mListener;
    private EditText transIDSearchView;
    private View parentLayout;
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

        transIDSearchView = root.findViewById(R.id.search_transaction_id);
        transIDSearchView.setSelectAllOnFocus(true);
        transIDSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    searchTransactionById();
                }
                return false;
            }
        });
        transIDSearchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("focus", "focus lost");
                    searchTransactionById();
                } else {
                    Log.d("focus", "focus");
                }
            }
        });

        final Button searchButton = root.findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTransactionById();
            }
        });

        ListView listView = root.findViewById(R.id.list_view);
        listView.setAdapter(mListAdapter);

        parentLayout = root;
        return root;
    }

    private void searchTransactionById() {
        try {
            int transIDSearch = Integer.parseInt(transIDSearchView.getText().toString());
            mPresenter.searchTransactionById(transIDSearch);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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

            TextView titleTV = rowView.findViewById(R.id.title);
            titleTV.setText(transaction.getToolbarTitle());

            TextView balanceView = rowView.findViewById(R.id.balance);
            balanceView.setText(getString(R.string.balance) + ": " +
                    Formatters.amountFormat.format(transaction.getBalance()));

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onFragmentMessage(getTag(), transaction);
                }
            });
            return rowView;
        }

        public void setTransactions(List<Transaction> mTransactions) {
            this.mTransactions = mTransactions;
            notifyDataSetChanged();
        }
    }

    public interface ItemListListener{
        void addItem();
    }

    @Override
    public void setTransactions(List<Transaction> transactions) {
        mListAdapter.setTransactions(transactions);
    }

    @Override
    public void showSnackBar(String msg) {
        Snackbar mySnackbar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_LONG);
        mySnackbar.show();
    }


}
