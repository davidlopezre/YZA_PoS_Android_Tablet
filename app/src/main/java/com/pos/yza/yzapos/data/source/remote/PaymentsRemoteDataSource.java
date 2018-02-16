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
import com.pos.yza.yzapos.data.representations.LineItem;
import com.pos.yza.yzapos.data.representations.Payment;
import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.data.source.PaymentsDataSource;
import com.pos.yza.yzapos.data.source.PaymentsRepository;
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
 * Created by Dalzy Mendoza on 9/2/18.
 */

public class PaymentsRemoteDataSource implements PaymentsDataSource {

    public final static String TRANSACTION_ID = "transaction_id", CLIENT_FIRST_NAME = "client_name",
            CLIENT_LAST_NAME = "client_surname", TRANSACTION_DATE_TIME = "date_time",
            TRANSACTION_STATE = "state", TRANSACTION_AMOUNT = "amount",
            TRANSACTION_BRANCH_ID = "branch_id", LINE_ITEMS = "items", TRANSACTION_PAYMENTS = "payments",
            TRANSACTION_CANCEL = "CANCEL", TRANSACTION_REFUND = "REFUND";

    public final static String LINE_ITEM_ID = "line_item_id", LINE_ITEM_AMOUNT = "amount",
            LINE_ITEM_PRODUCT_ID = "product_id", LINE_ITEM_QUANTITY = "quantity";

    public final static String PAYMENT_ID = "payment_id", PAYMENT_DATE_TIME = "date_time",
            PAYMENT_STATE = "state", PAYMENT_AMOUNT = "amount",
            PAYMENT_BRANCH_ID = "branch_id", PAYMENT_TRANSACTION = "transaction_id",
            PAYMENT_REFUND = "REFUND";


    private final String ROOT = "http://35.197.185.80:8000/";
    private final String PAYMENTS = "payment/";
    private final String ADD_PAYMENT = "add-payment/";

    private static PaymentsRemoteDataSource INSTANCE;
    private RequestQueue mRequestQueue;

    public static PaymentsRemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PaymentsRemoteDataSource();
            INSTANCE.mRequestQueue = INSTANCE.getRequestQueue(context);
        }
        return INSTANCE;
    }

    private PaymentsRemoteDataSource() {}

    public RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        Log.d("paymentResponse","added");
        mRequestQueue.add(req);
    }


    /**********************************************************************************************/
    /********************************* SERVER REQUEST METHODS *************************************/
    /**********************************************************************************************/

//    public void getPaymentsByTransaction(@NonNull String transactionId,
//                                         @NonNull final LoadPaymentsCallback callback){
//        checkNotNull(callback);
//
//        List<Payment> list = null;
//        PaymentsRemoteDataSource.JSONArrayResponseListener responseListener =
//                new PaymentsRemoteDataSource.JSONArrayResponseListener(callback);
//
//        Uri builtUri = Uri.parse(ROOT + PAYMENTS)
//                .buildUpon()
//                .build();
//        Log.d("paymentResponse",builtUri.toString());
//
//        JsonArrayRequest jsObjRequest = new JsonArrayRequest (Request.Method.GET,
//                builtUri.toString(), null, responseListener,
//                new PaymentsRemoteDataSource.ErrorListener());
//        addToRequestQueue(jsObjRequest);
//    }

    public void getPaymentById(@NonNull String paymentId,
                               @NonNull PaymentsRemoteDataSource.GetPaymentCallback callback){
        checkNotNull(callback);

        Uri builtUri = Uri.parse(ROOT + PAYMENTS + paymentId + "/")
                .buildUpon()
                .build();

        PaymentsRemoteDataSource.JSONObjectResponseListener responseListener =
                new PaymentsRemoteDataSource.JSONObjectResponseListener(callback);

        Log.d("paymentResponse",builtUri.toString());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest (Request.Method.GET,
                builtUri.toString(), null, responseListener,
                new PaymentsRemoteDataSource.ErrorListener());
        addToRequestQueue(jsObjRequest);
    }

    public void savePayment(@NonNull Payment payment){
        Log.i("savePayment", "in remote data source");
        Uri builtUri = Uri.parse(ROOT + ADD_PAYMENT)
                .buildUpon()
                .build();

        HashMap<String,String> params = new HashMap<String, String>();
        params.put(PAYMENT_AMOUNT, payment.getAmount() + "");
        params.put(PAYMENT_BRANCH_ID, payment.getBranchId() + "");
        params.put(PAYMENT_TRANSACTION, payment.getTransaction().getTransactionId() + "");

        JSONObject paramsJson = new JSONObject(params);

        Log.i("savePayment", paramsJson.toString());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                builtUri.toString(), paramsJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("savePayment", "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("savePayment", "Error occurred ", error);
            }
        });

        addToRequestQueue(jsObjRequest);
    }

    public void refundPayment(@NonNull String paymentId){
        Log.i("refundPayment", "in remote data source");
        Uri builtUri = Uri.parse(ROOT + PAYMENTS + paymentId + "/")
                .buildUpon()
                .build();

        HashMap<String,String> edits = new HashMap<>();
        edits.put(PAYMENT_STATE, PAYMENT_REFUND);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.PATCH, builtUri.toString(),
                new JSONObject(edits),new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("refundPayment", "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("refundPayment", "Error occurred ", error);
            }
        });

        addToRequestQueue(jsObjRequest);
    }


    /**********************************************************************************************/
    /************************************* LISTENER CLASSES ***************************************/
    /**********************************************************************************************/


    private class JSONArrayResponseListener implements Response.Listener<JSONArray> {
        PaymentsDataSource.LoadPaymentsCallback callback;

        public JSONArrayResponseListener(PaymentsDataSource.LoadPaymentsCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(JSONArray response) {
            Log.d("paymentResponse", "onResponse");
            ArrayList<Payment> payments = new ArrayList<>();

            for (int i = 0; i < response.length(); i++){

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSz");

                try {
                    JSONObject object = response.getJSONObject(i);

                    String paymentDateTimeString = object.getString(PAYMENT_DATE_TIME).replace("Z", "UTC");
                    Date paymentDateTime = format.parse(paymentDateTimeString);

                    Payment.State state = Payment.getState(object.getString(PAYMENT_STATE));

                    Payment payment = new Payment(object.getInt(PAYMENT_ID),
                            paymentDateTime,
                            object.getDouble(PAYMENT_AMOUNT),
                            object.getInt(PAYMENT_BRANCH_ID),
                            new Transaction(object.getJSONObject(PAYMENT_TRANSACTION).
                                                   getString(TRANSACTION_STATE)),
                            state);

                    Log.d("paymentResponse", "Processed payment:\n" + payment.toString());

                    payments.add(payment);
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
            }

            callback.onPaymentsLoaded(payments);
        }

    }

    private class JSONObjectResponseListener implements Response.Listener<JSONObject> {
        PaymentsDataSource.GetPaymentCallback callback;

        public JSONObjectResponseListener(PaymentsDataSource.GetPaymentCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(JSONObject response) {
            Log.d("getPaymentResponse", "onResponse");
            Payment payment;

            try{
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSz");
                String paymentDateTimeString = response.getString(PAYMENT_DATE_TIME).replace("Z", "UTC");
                Date paymentDateTime = format.parse(paymentDateTimeString);

                Payment.State state = Payment.getState(response.getString(PAYMENT_STATE));

                payment = new Payment(response.getInt(PAYMENT_ID),
                                      paymentDateTime,
                                      response.getDouble(PAYMENT_AMOUNT),
                                      response.getInt(PAYMENT_BRANCH_ID),
                                      new Transaction(response.getJSONObject(PAYMENT_TRANSACTION).
                                                      getString(TRANSACTION_STATE)),
                                      state);

                Log.d("getPaymentResponse", "Processed payment:\n" + payment.toString());

                callback.onPaymentLoaded(payment);
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
