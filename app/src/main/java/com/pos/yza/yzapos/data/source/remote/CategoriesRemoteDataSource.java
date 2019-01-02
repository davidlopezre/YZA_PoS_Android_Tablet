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
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.ProductProperty;
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.ProductsDataSource;
import com.pos.yza.yzapos.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Lopez on 9/1/18.
 */

public class CategoriesRemoteDataSource implements CategoriesDataSource {

    private static CategoriesRemoteDataSource INSTANCE;

    public final static String CATEGORY_NAME = "name",
                               CATEGORY_PROPERTIES = "properties",
                               CATEGORY_PROPERTY_NAME = "name";

//    private final String ROOT = "http://35.197.185.80:8000/";
//    private final String ROOT = "http://localhost:8000/";
    private final String ROOT = Constants.APIADDRESS;
    private final String CATEGORIES = "product_categories/";

    private RequestQueue mRequestQueue;

    public static CategoriesRemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CategoriesRemoteDataSource();
            INSTANCE.mRequestQueue = INSTANCE.getRequestQueue(context);
        }
        Log.i("catRemoteSource", "created");
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


    /**********************************************************************************************/
    /********************************* SERVER REQUEST METHODS *************************************/
    /**********************************************************************************************/


    @Override
    public void getCategories(@NonNull LoadCategoriesCallback callback) {
        checkNotNull(callback);

        JSONArrayResponseListener responseListener = new JSONArrayResponseListener(callback);

        Uri builtUri = Uri.parse(ROOT + CATEGORIES)
                .buildUpon()
                .build();

        Log.d("requestTest",builtUri.toString());

        JsonArrayRequest jsObjRequest = new JsonArrayRequest (Request.Method.GET,
                builtUri.toString(), null, responseListener, new ErrorListener());
        addToRequestQueue(jsObjRequest);

    }

    @Override
    public void saveCategory(@NonNull ProductCategory category) {
        Log.i("saveCategory", "in remote data source");
        Uri builtUri = Uri.parse(ROOT + CATEGORIES).buildUpon().build();

        HashMap<String,String> params = new HashMap<String, String>();
        params.put("name", category.getName());

        JSONObject paramsJson = new JSONObject(params);

        try {

            // Add category properties

            JSONArray propertiesJson = new JSONArray();
            for(CategoryProperty property: category.getPropertyList()){
                JSONObject propertyJson = new JSONObject(property.toHashMapNoId());
                propertiesJson.put(propertyJson);
            }

            paramsJson.put(CATEGORY_PROPERTIES, propertiesJson);
            Log.i("saveCategory", paramsJson.toString());

            // Send product to server

            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                    builtUri.toString(), paramsJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("saveCategory", "success");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("saveCategory", "Error occurred ", error);
                }
            });

            addToRequestQueue(jsObjRequest);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void refreshCategory() {

    }

    @Override
    public void deleteAllCategories() {

    }

    @Override
    public void deleteCategory(@NonNull String categoryId) {
        Log.i("deleteCategory", "in remote data source");
        Uri builtUri = Uri.parse(ROOT + CATEGORIES + categoryId + "/").buildUpon().build();

        Log.i("deleteCategory", builtUri.toString());
        DeleteJsonObjectRequest jsObjRequest = new DeleteJsonObjectRequest(
                Request.Method.DELETE, builtUri.toString(),
                null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("deleteCategory", "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("deleteCategory", "Error occurred ", error);
            }
        });

        addToRequestQueue(jsObjRequest);
    }


    /**********************************************************************************************/
    /************************************* LISTENER CLASSES ***************************************/
    /**********************************************************************************************/


    private class JSONArrayResponseListener implements Response.Listener<JSONArray> {
        LoadCategoriesCallback callback;

        public JSONArrayResponseListener(LoadCategoriesCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(JSONArray response) {
            ArrayList<ProductCategory> categories = new ArrayList<ProductCategory>();

            for (int i = 0; i < response.length(); i++){

                try {

                    JSONObject object = response.getJSONObject(i);
                    String name = object.getString("name");
                    int id = object.getInt("product_category_id");
                    Log.d("categoriesResponse", "Added category " + name);

                    JSONArray array = object.getJSONArray("properties");
                    ArrayList<CategoryProperty> properties = new ArrayList<CategoryProperty>();

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

}
