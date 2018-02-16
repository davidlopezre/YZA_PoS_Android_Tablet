package com.pos.yza.yzapos.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.data.source.StaffDataSource;
import com.pos.yza.yzapos.util.AppExecutors;

import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dalzy Mendoza on 16/2/18.
 * Concrete implementation of a data source as a db.
 */

public class StaffLocalDataSource implements StaffDataSource {

    private static volatile StaffLocalDataSource INSTANCE;

    private StaffDao mStaffDao;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private StaffLocalDataSource(@NonNull AppExecutors appExecutors,
                                 @NonNull StaffDao staffDao) {
        mAppExecutors = appExecutors;
        mStaffDao = staffDao;
    }

    public static StaffLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                   @NonNull StaffDao staffDao) {
        if (INSTANCE == null) {
            synchronized (StaffLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new StaffLocalDataSource(appExecutors, staffDao);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Note: {@link LoadStaffCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getAllStaff(@NonNull final LoadStaffCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Staff> tasks = mStaffDao.getStaff();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (tasks.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onStaffLoaded(tasks);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    /**
     * Note: {@link GetStaffCallback#onDataNotAvailable()} is fired if the {@link Staff} isn't
     * found.
     */
    @Override
    public void getStaffById(@NonNull final String staffId, @NonNull final GetStaffCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Staff staff = mStaffDao.getStaffById(staffId);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (staff != null) {
                            callback.onStaffLoaded(staff);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveStaff(@NonNull final Staff staff) {
        checkNotNull(staff);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mStaffDao.insertStaff(staff);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }


    @Override
    public void refreshStaff() {
        // Not required because the {@link StaffRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteAllStaff() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mStaffDao.deleteStaff();
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void deleteStaff(@NonNull final String staffId) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mStaffDao.deleteStaffById(staffId);
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    // need to work for lastName, phoneNumber, email, homeAddress

    @Override
    public void editStaff(@NonNull final String staffId, @NonNull final HashMap<String,String> edits){
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mStaffDao.updateFirstName(staffId, edits.get("firstName"));
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @VisibleForTesting
    static void clearInstance() {
        INSTANCE = null;
    }

}
