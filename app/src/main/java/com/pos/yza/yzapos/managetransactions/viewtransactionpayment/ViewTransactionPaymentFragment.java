package com.pos.yza.yzapos.managetransactions.viewtransactionpayment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.PluralsRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.Payment;
import com.pos.yza.yzapos.newtransaction.cart.RemoveLineItemDialog;
import com.pos.yza.yzapos.util.Formatters;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ViewTransactionPaymentFragment extends Fragment
        implements ViewTransactionPaymentContract.View,
                   AddPaymentDialog.DialogClickListener {

    ViewTransactionPaymentContract.Presenter mPresenter;
    PaymentAdapter mPaymentAdapter;
    TextView balanceView;
    TextView totalPaidView;

    private final String TAG = "VIEW_TRANS_PAY_FRAG";

    public ViewTransactionPaymentFragment() {
        mPaymentAdapter = new PaymentAdapter(new ArrayList<>());
    }

    public static ViewTransactionPaymentFragment newInstance() {
        return new ViewTransactionPaymentFragment();
    }

    @Override
    public void setPresenter(@NonNull ViewTransactionPaymentContract.Presenter presenter) {
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
        View root = inflater.inflate(R.layout.fragment_view_transaction_payments, container, false);

        TextView totalDueView = root.findViewById(R.id.total_due);
        totalDueView.setText(getString(R.string.total_due) + ": " + Formatters.amountFormat.format(mPresenter.getTotalDue()));
        totalPaidView = root.findViewById(R.id.total_paid);
        totalPaidView.setText(getString(R.string.total_paid) + ": " + Formatters.amountFormat.format(mPresenter.getTotalPaid()));
        balanceView = root.findViewById(R.id.balance);
        balanceView.setText(getString(R.string.balance) + ": " + Formatters.amountFormat.format(mPresenter.getBalance()));

        ListView listView = root.findViewById(R.id.list_view);
        listView.setAdapter(mPaymentAdapter);

        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        Button addPaymentButton = root.findViewById(R.id.button_add_payment);
        addPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new AddPaymentDialog();
                Bundle bundle = new Bundle();
                bundle.putString(AddPaymentDialog.BUNDLE_BALANCE, Double.toString(mPresenter.getBalance()));
                dialog.setArguments(bundle);
                dialog.setTargetFragment(ViewTransactionPaymentFragment.this, 0);
                dialog.show(getFragmentManager(), "dialog");
            }
        });
        return root;
    }

    private class PaymentAdapter extends BaseAdapter {

        private List<Payment> payments;

        public PaymentAdapter(List<Payment> payments) {
            this.payments = payments;
        }

        @Override
        public int getCount() {
            return payments.size();
        }

        @Override
        public Payment getItem(int i) {
            return payments.get(i);
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
                rowView = inflater.inflate(R.layout.list_payment,
                        viewGroup, false);
            }
            final Payment payment = payments.get(i);

            TextView label = rowView.findViewById(R.id.title);
            label.setText(Formatters.amountFormat.format(payment.getAmount()));
            label.setTextSize(20);

            TextView amount = rowView.findViewById(R.id.date);
            Log.i(TAG, payment.getDateTime().toString());
            amount.setText(Formatters.dateFormat.format(payment.getDateTime()));
            amount.setTextSize(20);

            return rowView;
        }

        public void initLineItems(List<Payment> payments) {
            this.payments = payments;
            notifyDataSetChanged();
        }
    }

    @Override
    public void setPayments(List<Payment> payments) {
        mPaymentAdapter.initLineItems(payments);
        updateBalanceAndTotalPaid();
    }

    private void updateBalanceAndTotalPaid() {
        totalPaidView.setText(getString(R.string.total_paid) + ": " + Formatters.amountFormat.format(mPresenter.getTotalPaid()));
        balanceView.setText(getString(R.string.balance) + ": " + Formatters.amountFormat.format(mPresenter.getBalance()));
    }

    @Override
    public void onDialogPositiveClick(double payment) {
        mPresenter.makePayment(payment);
        mPresenter.reloadTransaction();
    }

    @Override
    public void onDialogNegativeClick() {

    }

}
