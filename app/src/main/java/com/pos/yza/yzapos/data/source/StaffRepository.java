package com.pos.yza.yzapos.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.pos.yza.yzapos.data.representations.Staff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dalzy Mendoza on 9/1/18.
 */

public class StaffRepository implements StaffDataSource {

    private static StaffRepository INSTANCE = null;
    private static Context mContext;
    private final StaffDataSource mStaffRemoteDataSource;
    private final StaffDataSource mStaffLocalDataSource;
    private RequestQueue mRequestQueue;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, Staff> mCachedStaff;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    private StaffRepository(@NonNull StaffDataSource staffRemoteDataSource,
                            @NonNull StaffDataSource staffLocalDataSource) {
        mStaffRemoteDataSource = checkNotNull(staffRemoteDataSource);
        mStaffLocalDataSource = checkNotNull(staffLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param staffRemoteDataSource the backend data source
     * @return the {@link ProductsDataSource} instance
     */
    public static StaffRepository getInstance(StaffDataSource staffRemoteDataSource,
                                              StaffDataSource staffLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new StaffRepository(staffRemoteDataSource, staffLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(StaffDataSource, StaffDataSource)}
     * to create a new instance next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void getAllStaff(@NonNull final LoadStaffCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (mCachedStaff != null && !mCacheIsDirty) {
            callback.onStaffLoaded(new ArrayList<>(mCachedStaff.values()));
            return;
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getStaffFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mStaffLocalDataSource.getAllStaff(new LoadStaffCallback() {
                @Override
                public void onStaffLoaded(List<Staff> staff) {
                    refreshCache(staff);
                    callback.onStaffLoaded(new ArrayList<>(mCachedStaff.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getStaffFromRemoteDataSource(callback);
                }
            });
        }

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
    public void getStaffById(@NonNull String staffId, @NonNull final GetStaffCallback callback){
        checkNotNull(callback);

        mStaffRemoteDataSource.getStaffById(staffId, new GetStaffCallback() {

            @Override
            public void onStaffLoaded(Staff staff) {
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
    public void deleteStaff(@NonNull String staffId) {
        Log.i("deleteStaff", "in repo");
        mStaffRemoteDataSource.deleteStaff(staffId);
    }

    @Override
    public void editStaff(@NonNull String staffId, @NonNull HashMap<String,String> edits){
        mStaffRemoteDataSource.editStaff(staffId, edits);
    }

    private void getStaffFromRemoteDataSource(@NonNull final LoadStaffCallback callback) {
        mStaffRemoteDataSource.getAllStaff(new LoadStaffCallback() {
            @Override
            public void onStaffLoaded(List<Staff> staff) {
                refreshCache(staff);
                refreshLocalDataSource(staff);
                callback.onStaffLoaded(new ArrayList<>(mCachedStaff.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Staff> staff) {
        if (mCachedStaff == null) {
            mCachedStaff = new LinkedHashMap<>();
        }
        mCachedStaff.clear();
        for (Staff aStaff : staff) {
            mCachedStaff.put(aStaff.getStaffId()+"", aStaff);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Staff> staff) {
        mStaffLocalDataSource.deleteAllStaff();
        for (Staff aStaff : staff) {
            mStaffLocalDataSource.saveStaff(aStaff);
        }
    }

}

