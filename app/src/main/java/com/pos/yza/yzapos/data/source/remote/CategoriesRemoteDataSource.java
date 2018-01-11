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
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.ProductsDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dlolpez on 9/1/18.
 */

public class CategoriesRemoteDataSource implements CategoriesDataSource {

    private static CategoriesRemoteDataSource INSTANCE;

    final String ROOT = "http://35.197.185.80:8000/";
    final String PRODUCTS = "product_categories/";

    private RequestQueue mRequestQueue;

    public static CategoriesRemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CategoriesRemoteDataSource();
            INSTANCE.mRequestQueue = INSTANCE.getRequestQueue(context);
        }
        return INSTANCE;
    }

    private CategoriesRemoteDataSource() {}

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
    public void getCategories(@NonNull LoadCategoriesCallback callback) {
        checkNotNull(callback);

        JSONArrayResponseListener responseListener = new JSONArrayResponseListener(callback);

        Uri builtUri = Uri.parse(ROOT + PRODUCTS)
                .buildUpon()
                .build();

        Log.d("requestTest",builtUri.toString());

        JsonArrayRequest jsObjRequest = new JsonArrayRequest (Request.Method.GET,
                builtUri.toString(), null, responseListener, new ErrorListener());
        addToRequestQueue(jsObjRequest);

    }

    private class JSONArrayResponseListener implements Response.Listener<JSONArray> {
        LoadCategoriesCallback callback;

        public JSONArrayResponseListener(LoadCategoriesCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(JSONArray response) {
            Log.d("categoriesResponse", "onResponse");
            ArrayList<ProductCategory> categories = new ArrayList<ProductCategory>();

            for (int i = 0; i < response.length(); i++){
                try {

                    JSONObject object = response.getJSONObject(i);
                    String name = object.getString("name");
                    Log.d("categoriesResponse", "Added category " + name);
                    int id = object.getInt("product_category_id");

                    JSONArray array = object.getJSONArray("properties");
                    ArrayList<CategoryProperty> properties = new ArrayList<CategoryProperty>();
                    Log.d("categoriesResponse", array.toString());

                    for (int j = 0; j < array.length(); j++) {
                        JSONObject propertyObject = array.getJSONObject(j);
                        CategoryProperty categoryProperty =
                                new CategoryProperty(propertyObject.getInt("category_property_id"),
                                        propertyObject.getString("name"));

                        properties.add(categoryProperty);
                        Log.d("categoriesResponse", "Added property " + categoryProperty.getName() );

                    }

                    categories.add(new ProductCategory(id, name, properties));

                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            callback.onCategoriesLoaded(categories);
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
    public void saveCategory(@NonNull ProductCategory category) {

    }

    @Override
    public void refreshCategory() {

    }

    @Override
    public void deleteAllCategories() {

    }

    @Override
    public void deleteCategory(@NonNull String productId) {

    }
}
