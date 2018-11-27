package com.pos.yza.yzapos.data.source;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.util.OnVolleyResponse;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dalzy Mendoza on 9/1/18.
 */

public interface StaffDataSource {

    interface LoadStaffCallback {

        void onStaffLoaded(List<Staff> staffList);

        void onDataNotAvailable();
    }

    interface ModifyStaffCallback {

        void onStaffModified();

        void onStaffNotModified();
    }

    void getAllStaff(@NonNull LoadStaffCallback callback);

    void saveStaff(@NonNull Staff staff);

    void refreshStaff();

    void deleteAllStaff();

    void deleteStaff(@NonNull ModifyStaffCallback callback, @NonNull String staffId);

    void editStaff(@NonNull String staffId, @NonNull HashMap<String,String> edits);
}

