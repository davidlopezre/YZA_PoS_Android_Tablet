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
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.data.source.ProductsDataSource;
import com.pos.yza.yzapos.data.source.StaffDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by beyondinfinity on 9/1/18.
 */

public class StaffRemoteDataSource implements StaffDataSource {

    private final String ID_STAFF = "staff_id", FIRST_NAME = "name",
                  LAST_NAME = "surname", PHONE_NUMBER = "phone_number",
                  EMAIL = "email", ADDRESS = "home_address";

    private final String ROOT = "http://35.197.185.80:8000/";
    private final String STAFF = "staff/";

    private static StaffRemoteDataSource INSTANCE;
    private RequestQueue mRequestQueue;

    private StaffRemoteDataSource() {}

    public static StaffRemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new StaffRemoteDataSource();
            INSTANCE.mRequestQueue = INSTANCE.getRequestQueue(context);
        }
        return INSTANCE;
    }

    public RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        Log.d("staffRemoteRequest","added to queue");
        mRequestQueue.add(req);
    }

    public void getAllStaff(@NonNull LoadStaffCallback callback){
        checkNotNull(callback);

        List<Staff> list = null;
        StaffRemoteDataSource.JSONArrayResponseListener responseListener =
                new StaffRemoteDataSource.JSONArrayResponseListener(callback);

        Uri builtUri = Uri.parse(ROOT + STAFF)
                .buildUpon()
                .build();

        Log.d("staffRemoteRequest",builtUri.toString());

        JsonArrayRequest jsObjRequest = new JsonArrayRequest (Request.Method.GET,
                builtUri.toString(), null, responseListener,
                new StaffRemoteDataSource.ErrorListener());

        addToRequestQueue(jsObjRequest);
    }

    public void saveStaff(@NonNull Staff staff) {}

    public void refreshStaff() {}

    public void deleteAllStaff() {}

    public void deleteStaff(@NonNull String staffId) {}

    public void editStaff(@NonNull String staffId, @NonNull HashMap<String,String> edits) {}

    private class JSONArrayResponseListener implements Response.Listener<JSONArray> {
        StaffDataSource.LoadStaffCallback callback;

        public JSONArrayResponseListener(StaffDataSource.LoadStaffCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(JSONArray response) {
            Log.d("staffRemoteRequest", "onResponse");

            ArrayList<Staff> staff = new ArrayList<Staff>();

            for (int i = 0; i < response.length(); i++){
                try {
                    JSONObject object = response.getJSONObject(i);

                    String staffId = object.getString(ID_STAFF);
                    String firstName = object.getString(FIRST_NAME);
                    String lastName = object.getString(LAST_NAME);
                    String phoneNumber = object.getString(PHONE_NUMBER);
                    String email = object.getString(EMAIL);
                    String homeAddress = object.getString(ADDRESS);

                    staff.add(new Staff(Integer.parseInt(staffId), firstName, lastName,
                                        phoneNumber, email, homeAddress));

                    Log.d("staffRemoteRequest", firstName + " " + lastName);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            callback.onStaffLoaded(staff);
        }

    }

    private class ErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            // TODO Auto-generated method stub
            Log.e("STAFF_ERROR", "Error occurred ", error);
        }
    }
}
