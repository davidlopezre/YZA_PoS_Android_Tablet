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
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.ProductProperty;
import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.data.source.ProductsDataSource;
import com.pos.yza.yzapos.data.source.TransactionsDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                               CLIENT_LAST_NAME = "client_surname", DATE_TIME = "date_time",
                               STATE = "state", TRANSACTION_AMOUNT = "amount",
                               BRANCH_ID = "branch_id";

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
        Log.d("requestTest","added");
        mRequestQueue.add(req);
    }


    /**********************************************************************************************/
    /********************************* SERVER REQUEST METHODS *************************************/
    /**********************************************************************************************/


    @Override
    public void getTransactions(@NonNull final TransactionsDataSource.LoadTransactionsCallback callback){
        checkNotNull(callback);

        List<Product> list = null;
        ProductsRemoteDataSource.JSONArrayResponseListener responseListener = new ProductsRemoteDataSource.JSONArrayResponseListener(callback);

        Uri builtUri = Uri.parse(ROOT + PRODUCTS)
                .buildUpon()
                .appendQueryParameter("category", Integer.toString(category.getId()))
                .build();

        Log.d("requestTest",builtUri.toString());

        JsonArrayRequest jsObjRequest = new JsonArrayRequest (Request.Method.GET,
                builtUri.toString(), null, responseListener, new ProductsRemoteDataSource.ErrorListener());
        addToRequestQueue(jsObjRequest);

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

                    int id = object.getInt(TRANSACTION_ID);
                    String firstName = object.getString(CLIENT_FIRST_NAME);
                    String lastName = object.getString(CLIENT_FIRST_NAME);

//                    String string = "January 2, 2010";
//                    DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
//                    Date date = format.parse(string);
//                    https://stackoverflow.com/questions/4216745/java-string-to-date-conversion
                    Date dateTime = new Date(Date.parse(object.getString(DATE_TIME)));



                    int categoryId = object.getInt("category");
                    ProductCategory category = new ProductCategory(categoryId,"", new ArrayList<CategoryProperty>());

                    JSONArray propertiesJson = object.getJSONArray("properties");
                    ArrayList<ProductProperty> properties = new ArrayList<ProductProperty>();

                    for (int j = 0; j < propertiesJson.length(); j++) {
                        JSONObject propertyObject = propertiesJson.getJSONObject(j);
                        ProductProperty productProperty = new ProductProperty(propertyObject.getInt(PRODUCT_PROPERTY_ID),
                                propertyObject.getInt(CATEGORY_PROPERTY_ID),
                                propertyObject.getString(PRODUCT_PROPERTY_VALUE));
                        properties.add(productProperty);
                    }

                    Product product = new Product(id, unitPrice, unitOfMeasure, category, properties);
                    Log.d("productResponse", product.toString());

                    products.add(product);


                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            callback.onProductsLoaded(products);
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


