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
import com.pos.yza.yzapos.data.source.ProductsDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Lopez on 26/12/17.
 */

public class ProductsRemoteDataSource implements ProductsDataSource {

    private static ProductsRemoteDataSource INSTANCE;

    public final static String PRODUCT_PROPERTY_ID = "product_property_id",
    PRODUCT_PROPERTY_VALUE = "value",
                               CATEGORY_PROPERTY_ID = "category_property";

//    private final String ROOT = "http://35.197.185.80:8000/";
    private final String ROOT = "http://localhost:8000/";
    private final String PRODUCTS = "products/";

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


    /**********************************************************************************************/
    /********************************* SERVER REQUEST METHODS *************************************/
    /**********************************************************************************************/


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

        // Create product with basic details

        HashMap<String,String> params = new HashMap<String, String>();
        params.put("name", product.getName());
        params.put("unit_price", product.getUnitPrice().toString());
        params.put("unit_of_measure", product.getUnitMeasure());
        params.put("category", Integer.toString(product.getCategory().getId()));

        JSONObject paramsJson = new JSONObject(params);

        try {

            // Add product properties

            JSONArray propertiesJson = new JSONArray();
            for(ProductProperty property: product.getProperties()){
                JSONObject propertyJson = new JSONObject(property.toHashMap());

                propertiesJson.put(propertyJson);
            }

            paramsJson.put("properties", propertiesJson);
            Log.i("saveItem", paramsJson.toString());

            // Send product to server

            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                    builtUri.toString(), paramsJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("saveItem", "success");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("saveItem", "Error occurred ", error);
                }
            });

            addToRequestQueue(jsObjRequest);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

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

        Log.i("deleteItem", builtUri.toString());

        DeleteJsonObjectRequest jsObjRequest = new DeleteJsonObjectRequest(
                Request.Method.DELETE, builtUri.toString(),

                null,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("deleteItem", "success");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("deleteItem", "Error occurred ", error);
                }
            });

        addToRequestQueue(jsObjRequest);
    }


    @Override
    public void editProduct(@NonNull String productId, @NonNull HashMap<String,String> edits){
        Log.i("editProduct", "in remote data source");
        Uri builtUri = Uri.parse(ROOT + PRODUCTS + productId + "/")
                .buildUpon()
                .build();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.PATCH, builtUri.toString(),
                new JSONObject(edits),new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("editProduct", "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("editProduct", "Error occurred ", error);
            }
        });

        addToRequestQueue(jsObjRequest);
    }


    /**********************************************************************************************/
    /************************************* LISTENER CLASSES ***************************************/
    /**********************************************************************************************/


    private class JSONArrayResponseListener implements Response.Listener<JSONArray> {
        LoadProductsCallback callback;

        public JSONArrayResponseListener(LoadProductsCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(JSONArray response) {
            Log.d("productResponse", "onResponse");
            ArrayList<Product> products = new ArrayList<Product>();

            for (int i = 0; i < response.length(); i++){

                try {
                    JSONObject object = response.getJSONObject(i);

                    int id = object.getInt("product_id");
                    String name = object.getString("name");
                    Double unitPrice = object.getDouble("unit_price");
                    String unitOfMeasure = object.getString("unit_of_measure");

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
