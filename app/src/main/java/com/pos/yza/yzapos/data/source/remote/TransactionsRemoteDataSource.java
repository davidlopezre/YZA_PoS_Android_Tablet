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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pos.yza.yzapos.DeleteJsonObjectRequest;
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
import java.util.HashMap;
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
                               TRANSACTION_BRANCH_ID = "branch_id", LINE_ITEMS = "items", PAYMENTS = "payments",
                               TRANSACTION_CANCEL = "CANCEL", TRANSACTION_REFUND = "REFUND";

    public final static String LINE_ITEM_ID = "line_item_id", LINE_ITEM_AMOUNT = "amount",
                               LINE_ITEM_PRODUCT_ID = "product_id", LINE_ITEM_QUANTITY = "quantity";

    public final static String PAYMENT_ID = "payment_id", PAYMENT_DATE_TIME = "date_time",
                               PAYMENT_STATE = "state", PAYMENT_AMOUNT = "amount",
                               PAYMENT_BRANCH_ID = "branch_id";


//    private final String ROOT = "http://35.197.185.80:8000/";
    private final String ROOT = "http://localhost:8000/";
    private final String TRANSACTIONS = "transaction/";
    private final String DELETE_OLD_TRANSACTIONS = "delete-old-transactions/";

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
        checkNotNull(callback);

        Uri builtUri = Uri.parse(ROOT + TRANSACTIONS + transactionId + "/")
                .buildUpon()
                .build();

        TransactionsRemoteDataSource.JSONObjectResponseListener responseListener =
                new TransactionsRemoteDataSource.JSONObjectResponseListener(callback);

        Log.d("transactionResponse",builtUri.toString());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest (Request.Method.GET,
                builtUri.toString(), null, responseListener,
                new TransactionsRemoteDataSource.ErrorListener());
        addToRequestQueue(jsObjRequest);

    }

    public void saveTransaction(@NonNull Transaction transaction){
        Log.i("saveTransaction", "in remote data source");
        Uri builtUri = Uri.parse(ROOT + TRANSACTIONS)
                .buildUpon()
                .build();

        // Create transaction with basic details

        HashMap<String,String> params = new HashMap<String, String>();
        params.put(CLIENT_FIRST_NAME, transaction.getClientFirstName());
        params.put(CLIENT_LAST_NAME, transaction.getClientSurname());
        params.put(TRANSACTION_BRANCH_ID, transaction.getBranchId() + "");
        params.put(TRANSACTION_AMOUNT, transaction.getAmount() + "" );

        JSONObject paramsJson = new JSONObject(params);

        try {

            // Add lineitems

            JSONArray itemsJson = new JSONArray();
            for(LineItem lineItem : transaction.getLineItems()){
                JSONObject itemJson = new JSONObject(lineItem.toHashMap());
                itemsJson.put(itemJson);
            }
            paramsJson.put(LINE_ITEMS, itemsJson);
            Log.i("saveTransaction", itemsJson.toString());

            // Add payments

            JSONArray paymentsJson = new JSONArray();
            for(Payment payment : transaction.getPayments()){
                JSONObject paymentJson = new JSONObject(payment.toHashMap());
                paymentsJson.put(paymentJson);
            }
            paramsJson.put(PAYMENTS, paymentsJson);
            Log.i("saveTransaction", paymentsJson.toString());

            // Send transation to server

            Log.i("saveTransaction", paramsJson.toString());

            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                    builtUri.toString(), paramsJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("saveTransaction", "success");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("saveTransaction", "Error occurred ", error);
                }
            });

            addToRequestQueue(jsObjRequest);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void refreshTransactions(){

    }

    public void deleteOldTransactions(){
        Log.i("deleteOldTrans", "in remote data source");
        Uri builtUri = Uri.parse(ROOT + DELETE_OLD_TRANSACTIONS)
                .buildUpon()
                .build();

        Log.i("deleteOldTrans", builtUri.toString());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,
                builtUri.toString(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // placeholder
                    }
                },
                new TransactionsRemoteDataSource.ErrorListener());

//        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
//                builtUri.toString(), new JSONObject(), new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.i("deleteOldTrans", "success");
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("deleteOldTrans", "Error occurred ", error);
//            }
//        });

        addToRequestQueue(jsObjRequest);
    }

    public void deleteAllTransactionsByBranch(){

    }

    public void cancelTransaction(@NonNull String transactionId){
        Log.i("cancelTrans", "in remote data source");
        Uri builtUri = Uri.parse(ROOT + TRANSACTIONS + transactionId + "/")
                .buildUpon()
                .build();

        HashMap<String,String> edits = new HashMap<>();
        edits.put(TRANSACTION_STATE, TRANSACTION_CANCEL);

        Log.i("cancelTrans", edits.toString());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.PATCH, builtUri.toString(),
                new JSONObject(edits),new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("cancelTrans", "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("cancelTrans", "Error occurred ", error);
            }
        });

        addToRequestQueue(jsObjRequest);
    }

    public void refundTransaction(@NonNull String transactionId){
        Log.i("refundTrans", "in remote data source");
        Uri builtUri = Uri.parse(ROOT + TRANSACTIONS + transactionId + "/")
                .buildUpon()
                .build();

        HashMap<String,String> edits = new HashMap<>();
        edits.put(TRANSACTION_STATE, TRANSACTION_REFUND);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.PATCH, builtUri.toString(),
                new JSONObject(edits),new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("refundTrans", "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("refundTrans", "Error occurred ", error);
            }
        });

        addToRequestQueue(jsObjRequest);
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
                                                         itemObject.getInt(LINE_ITEM_QUANTITY),
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

    private class JSONObjectResponseListener implements Response.Listener<JSONObject> {
        TransactionsDataSource.GetTransactionCallback callback;

        public JSONObjectResponseListener(TransactionsDataSource.GetTransactionCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(JSONObject response) {
            Log.d("getTransactionResponse", "onResponse");

            Transaction transaction;

            try{

                /** Create transaction with basic attributes **/

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSz");
                String dateTimeString = response.getString(TRANSACTION_DATE_TIME).replace("Z", "UTC");
                Date dateTime = format.parse(dateTimeString);
                Transaction.Status status = Transaction.getStatus(response.getString(TRANSACTION_STATE));

                transaction = new Transaction(response.getInt(TRANSACTION_ID),
                                              response.getString(CLIENT_FIRST_NAME),
                                              response.getString(CLIENT_LAST_NAME),
                                              dateTime,
                                              response.getInt(TRANSACTION_BRANCH_ID),
                                              status,
                                              response.getDouble(TRANSACTION_AMOUNT));

                /** Process line items **/

                JSONArray itemsJson = response.getJSONArray(LINE_ITEMS);
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

                JSONArray paymentsJson = response.getJSONArray(PAYMENTS);
                ArrayList<Payment> payments = new ArrayList<>();

                for (int j = 0; j < paymentsJson.length(); j++) {
                    JSONObject paymentObject = paymentsJson.getJSONObject(j);

                    String paymentDateTimeString = response.getString(PAYMENT_DATE_TIME).replace("Z", "UTC");
                    Date paymentDateTime = format.parse(dateTimeString);

                    Payment.State state = Payment.getState(response.getString(PAYMENT_STATE));

                    Payment payment = new Payment(paymentObject.getInt(PAYMENT_ID),
                            paymentDateTime,
                            paymentObject.getDouble(PAYMENT_AMOUNT),
                            paymentObject.getInt(PAYMENT_BRANCH_ID),
                            transaction,
                            state);
                    payments.add(payment);
                }

                transaction.setPayments(payments);

                Log.d("getTransactionResponse", "Processed transaction:\n" + transaction.toString());

                callback.onTransactionLoaded(transaction);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }
            catch (ParseException e){
                e.printStackTrace();
            }

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


