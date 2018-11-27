package com.pos.yza.yzapos.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.util.OnVolleyResponse;

import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dalzy Mendoza on 9/1/18.
 */

public class StaffRepository implements StaffDataSource {

    private static StaffRepository INSTANCE = null;
    private static Context mContext;
    private final StaffDataSource mStaffRemoteDataSource;
    private RequestQueue mRequestQueue;

    // Prevent direct instantiation.
    private StaffRepository(@NonNull StaffDataSource staffRemoteDataSource) {
        mStaffRemoteDataSource = checkNotNull(staffRemoteDataSource);
    //  mTasksLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param staffRemoteDataSource the backend data source
     * @return the {@link ProductsDataSource} instance
     */
    public static StaffRepository getInstance(StaffDataSource staffRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new StaffRepository(staffRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(StaffDataSource)} (StaffDataSource, StaffDataSource)}
     * to create a new instance next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void getAllStaff(@NonNull final LoadStaffCallback callback) {
        checkNotNull(callback);

        mStaffRemoteDataSource.getAllStaff(new LoadStaffCallback() {

            @Override
            public void onStaffLoaded(List<Staff> staff) {
                callback.onStaffLoaded(staff);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveStaff(@NonNull Staff staff) {
        Log.i("saveStaff", "in repo");
        mStaffRemoteDataSource.saveStaff(staff);
    }

    @Override
    public void refreshStaff() {

    }

    @Override
    public void deleteAllStaff() {

    }

    @Override
    public void deleteStaff(@NonNull final ModifyStaffCallback callback, @NonNull String staffId) {
        Log.i("deleteStaff", "in repo");
        checkNotNull(callback);

        mStaffRemoteDataSource.deleteStaff(new ModifyStaffCallback() {
            @Override
            public void onStaffModified() {
                callback.onStaffModified();
            }

            @Override
            public void onStaffNotModified() {
                callback.onStaffNotModified();
            }
        }, staffId);
    }

    @Override
    public void editStaff(@NonNull String staffId, @NonNull HashMap<String,String> edits){
        mStaffRemoteDataSource.editStaff(staffId, edits);
    }

}

