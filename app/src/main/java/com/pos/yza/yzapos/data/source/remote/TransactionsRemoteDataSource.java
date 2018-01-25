package com.pos.yza.yzapos.data.source.remote;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.LineItem;
import com.pos.yza.yzapos.data.representations.Payment;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.ProductProperty;
import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.data.source.ProductsDataSource;
import com.pos.yza.yzapos.data.source.TransactionsDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dalzy Mendoza on 22/1/18.
 */

public class TransactionsRemoteDataSource implements TransactionsDataSource {

    private static TransactionsRemoteDataSource INSTANCE;

    public final static String TRANSACTION_ID = "transaction_id", CLIENT_FIRST_NAME = "client_name",
                               CLIENT_LAST_NAME = "client_surname", TRANSACTION_DATE_TIME = "date_time",
                               TRANSACTION_STATE = "state", TRANSACTION_AMOUNT = "amount",
                               TRANSACTION_BRANCH_ID = "branch_id", LINE_ITEMS = "items", PAYMENTS = "payments";

    public final static String LINE_ITEM_ID = "line_item_id", LINE_ITEM_AMOUNT = "amount",
                               LINE_ITEM_PRODUCT_ID = "product_id", LINE_ITEM_QUANTITY = "quantity";

    public final static String PAYMENT_ID = "payment_id", PAYMENT_DATE_TIME = "date_time",
                               PAYMENT_STATE = "state", PAYMENT_AMOUNT = "amount",
                               PAYMENT_BRANCH_ID = "branch_id";


    private final String ROOT = "http://35.197.185.80:8000/";
    private final String TRANSACTIONS = "transaction/";

    private RequestQueue mRequestQueue;


    public static TransactionsRemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TransactionsRemoteDataSource();
            INSTANCE.mRequestQueue = INSTANCE.getRequestQueue(context);
        }
        return INSTANCE;
    }

    private TransactionsRemoteDataSource() {}

    public RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        Log.d("transactionResponse","added");
        mRequestQueue.add(req);
    }


    /**********************************************************************************************/
    /********************************* SERVER REQUEST METHODS *************************************/
    /**********************************************************************************************/


    @Override
    public void getTransactions(@NonNull final TransactionsDataSource.LoadTransactionsCallback callback){
        checkNotNull(callback);

        List<Transaction> list = null;
        TransactionsRemoteDataSource.JSONArrayResponseListener responseListener =
                new TransactionsRemoteDataSource.JSONArrayResponseListener(callback);

        Uri builtUri = Uri.parse(ROOT + TRANSACTIONS)
                .buildUpon()
                .build();
        Log.d("transactionResponse",builtUri.toString());

        JsonArrayRequest jsObjRequest = new JsonArrayRequest (Request.Method.GET,
                builtUri.toString(), null, responseListener,
                new TransactionsRemoteDataSource.ErrorListener());
        addToRequestQueue(jsObjRequest);
    }

    public void getTransactionById(@NonNull String transactionId, @NonNull GetTransactionCallback callback){

    }

    public void saveTransaction(@NonNull Transaction transaction){

    }

    public void refreshTransactions(){

    }

    public void deleteAllTransactions(){

    }

    public void deleteAllTransactionsByBranch(){

    }

    public void cancelTransaction(@NonNull String transactionId){

    }

    public void refundTransaction(@NonNull String transactionId){

    }


    /**********************************************************************************************/
    /************************************* LISTENER CLASSES ***************************************/
    /**********************************************************************************************/


    private class JSONArrayResponseListener implements Response.Listener<JSONArray> {
        TransactionsDataSource.LoadTransactionsCallback callback;

        public JSONArrayResponseListener(TransactionsDataSource.LoadTransactionsCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(JSONArray response) {
            Log.d("transactionResponse", "onResponse");
            ArrayList<Transaction> transactions = new ArrayList<>();

            for (int i = 0; i < response.length(); i++){

                try {
                    JSONObject object = response.getJSONObject(i);

                    /** Create transaction with basic attributes **/

                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSz");
                    String dateTimeString = object.getString(TRANSACTION_DATE_TIME).replace("Z", "UTC");
                    Date dateTime = format.parse(dateTimeString);
                    Transaction.Status status = Transaction.getStatus(object.getString(TRANSACTION_STATE));

                    Transaction transaction = new Transaction(object.getInt(TRANSACTION_ID),
                                                              object.getString(CLIENT_FIRST_NAME),
                                                              object.getString(CLIENT_LAST_NAME),
                                                              dateTime,
                                                              object.getInt(TRANSACTION_BRANCH_ID),
                                                              status,
                                                              object.getDouble(TRANSACTION_AMOUNT));

                    /** Process line items **/

                    JSONArray itemsJson = object.getJSONArray(LINE_ITEMS);
                    ArrayList<LineItem> items = new ArrayList<>();

                    for (int j = 0; j < itemsJson.length(); j++) {
                        JSONObject itemObject = itemsJson.getJSONObject(j);
                        LineItem lineItem = new LineItem(itemObject.getInt(LINE_ITEM_ID),
                                                         itemObject.getDouble(LINE_ITEM_AMOUNT),
                                                         transaction,
                                                         itemObject.getInt(LINE_ITEM_PRODUCT_ID));
                        items.add(lineItem);
                    }

                    transaction.setLineItems(items);

                    /** Process payments **/

                    JSONArray paymentsJson = object.getJSONArray(PAYMENTS);
                    ArrayList<Payment> payments = new ArrayList<>();

                    for (int j = 0; j < paymentsJson.length(); j++) {
                        JSONObject paymentObject = paymentsJson.getJSONObject(j);

                        String paymentDateTimeString = object.getString(PAYMENT_DATE_TIME).replace("Z", "UTC");
                        Date paymentDateTime = format.parse(dateTimeString);

                        Payment.State state = Payment.getState(object.getString(PAYMENT_STATE));

                        Payment payment = new Payment(paymentObject.getInt(PAYMENT_ID),
                                                      paymentDateTime,
                                                      paymentObject.getDouble(PAYMENT_AMOUNT),
                                                      paymentObject.getInt(PAYMENT_BRANCH_ID),
                                                      transaction,
                                                      state);
                        payments.add(payment);
                    }

                    transaction.setPayments(payments);

                    Log.d("transactionResponse", "Processed transaction:\n" + transaction.toString());

                    transactions.add(transaction);
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
            }

            callback.onTransactionsLoaded(transactions);
        }

    }

    private class ErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            // TODO Auto-generated method stub
            Log.e("ERROR", "Error occurred ", error);
        }
    }
}


