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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.source.ProductsDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dlolpez on 26/12/17.
 */

public class ProductsRemoteDataSource implements ProductsDataSource {

    private static ProductsRemoteDataSource INSTANCE;

    final String ROOT = "http://35.197.185.80:8000/";
    final String PRODUCTS = "products/";

    private RequestQueue mRequestQueue;

    public static ProductsRemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ProductsRemoteDataSource();
            INSTANCE.mRequestQueue = INSTANCE.getRequestQueue(context);
        }
        return INSTANCE;
    }

    private ProductsRemoteDataSource() {}

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


    @Override
    public void getProductsByCategory(ProductCategory category,
                                               @NonNull final LoadProductsCallback callback){
        checkNotNull(callback);

        List<Product> list = null;
        JSONArrayResponseListener responseListener = new JSONArrayResponseListener(callback);

        Uri builtUri = Uri.parse(ROOT + PRODUCTS)
                .buildUpon()
                .appendQueryParameter("category", Integer.toString(category.getId()))
                .build();

        Log.d("requestTest",builtUri.toString());

        JsonArrayRequest jsObjRequest = new JsonArrayRequest (Request.Method.GET,
                builtUri.toString(), null, responseListener, new ErrorListener());
        addToRequestQueue(jsObjRequest);

    }

    public void addProduct(Product product){

    }

    private class JSONArrayResponseListener implements Response.Listener<JSONArray> {
        LoadProductsCallback callback;

        public JSONArrayResponseListener(LoadProductsCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(JSONArray response) {
            Log.d("requestTest", "onResponse");
            ArrayList<Product> products = new ArrayList<Product>();
           for (int i = 0; i < response.length(); i++){
               try {
                   JSONObject object = response.getJSONObject(i);
                   int id = object.getInt("product_id");
                   String name = object.getString("name");
                   Double unitPrice = object.getDouble("unit_price");
                   String unitOfMeasure = object.getString("unit_of_measure");
                   products.add(new Product(id, unitPrice, unitOfMeasure, ""));
                   Log.d("requestTest", name);
               }catch (JSONException e) {
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

    @Override
    public void getProducts(@NonNull LoadProductsCallback callback) {
        checkNotNull(callback);

        List<Product> list = null;
        JSONArrayResponseListener responseListener = new JSONArrayResponseListener(callback);

        Uri builtUri = Uri.parse(ROOT + PRODUCTS)
                .buildUpon()
                .build();

        Log.d("requestTest",builtUri.toString());

        JsonArrayRequest jsObjRequest = new JsonArrayRequest (Request.Method.GET,
                builtUri.toString(), null, responseListener, new ErrorListener());
        addToRequestQueue(jsObjRequest);

    }

    @Override
    public void getProduct(@NonNull String productId, @NonNull GetProductCallback callback) {

    }

    @Override
    public void saveProduct(@NonNull final Product product) {
        Log.i("saveItem", "in remote data source");
        Uri builtUri = Uri.parse(ROOT + PRODUCTS)
                .buildUpon()
                .build();

        HashMap<String,String> params = new HashMap<String, String>();
        params.put("name", product.getName());
        params.put("unit_price", product.getUnitPrice().toString());
        params.put("unit_of_measure", product.getUnitMeasure());
        params.put("category", Integer.toString(1));

        try {
            JSONObject paramsJson = new JSONObject(params);
            paramsJson.put("properties", new JSONArray());

            Log.i("saveItem", paramsJson.toString());

            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                    builtUri.toString(), paramsJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("success", "success");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERROR", "Error occurred ", error);
                }
            });
            addToRequestQueue(jsObjRequest);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        HashMap<String, String> editMap = new HashMap<String, String>();
        editMap.put("name", "FantasticEdit");
        editProduct("4", editMap);

    }

    @Override
    public void refreshProducts() {

    }

    @Override
    public void deleteAllProducts() {

    }

    @Override
    public void deleteProduct(@NonNull String productId) {
        Log.i("deleteItem", "in remote data source");
        Uri builtUri = Uri.parse(ROOT + PRODUCTS + productId + "/")
                .buildUpon()
                .build();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.DELETE, builtUri.toString(),
                null,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("success", "success");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERROR", "Error occurred ", error);
                }
            });

        addToRequestQueue(jsObjRequest);
    }


    // NOT YET WORKING! NEEDS REWORK ON THE SERVER
    @Override
    public void editProduct(@NonNull String productId, @NonNull HashMap<String,String> edits){
        Log.i("editItem", "in remote data source");
        Uri builtUri = Uri.parse(ROOT + PRODUCTS + productId + "/")
                .buildUpon()
                .build();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.PATCH, builtUri.toString(),
                new JSONObject(edits),new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("success", "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", "Error occurred ", error);
            }
        });

        addToRequestQueue(jsObjRequest);
    }
}
