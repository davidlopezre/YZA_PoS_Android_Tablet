package com.pos.yza.yzapos.data;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Dlolpez on 26/12/17.
 */

public class AdminOptionsDataSource {

    RequestHandler requestHandler;


    public AdminOptionsDataSource(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    final String ROOT = "http://35.197.185.80:8000";
    final String PRODUCTS = "/products";

    public List<Product> getProductsByCategory(ProductCategory category){
        List<Product> list = null;
        JSONArrayResponseListener responseListener = new JSONArrayResponseListener();
//        JSONObject parameters = new JSONObject();
//        try {
//            parameters.put("category", category.getId());
//        } catch (JSONException e){
//            Log.i("getProductsByCategory", "JsonException");
//            return new ArrayList<Product>();
//        }

        Uri builtUri = Uri.parse(ROOT + PRODUCTS)
                .buildUpon()
                .appendQueryParameter("category", Integer.toString(category.getId()))
                .build();

        Log.d("requestTest",builtUri.toString());

        JsonArrayRequest jsObjRequest = new JsonArrayRequest (Request.Method.GET,
                builtUri.toString(), null, responseListener, new ErrorListener());
        requestHandler.addToRequestQueue(jsObjRequest);
        return list;
    }

    public void addNewProduct(Product product){

    }

    private class JSONArrayResponseListener implements Response.Listener<JSONArray> {

        @Override
        public void onResponse(JSONArray response) {
            Log.d("requestTest", "onResponse");
           for (int i = 0; i < response.length(); i++){
               try {
                   JSONObject object = response.getJSONObject(i);
                   String name = object.getString("name");
                   Log.d("requestTest", name);
               }catch (JSONException e) {
                   e.printStackTrace();
               }
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
