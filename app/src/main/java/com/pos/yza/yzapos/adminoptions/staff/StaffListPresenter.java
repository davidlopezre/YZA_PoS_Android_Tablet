package com.pos.yza.yzapos.adminoptions.staff;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.data.source.StaffDataSource;
import com.pos.yza.yzapos.data.source.StaffRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public class StaffListPresenter implements StaffListContract.Presenter {
    private final StaffListContract.View mStaffListView;

    private final StaffRepository mStaffRepository;

    public StaffListPresenter(@NonNull StaffRepository staffRepository,
                              @NonNull StaffListContract.View view){
        mStaffRepository = staffRepository;
        mStaffListView = view;

        mStaffListView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadStaff() {
        mStaffRepository.getAllStaff(new StaffDataSource.LoadStaffCallback() {
            @Override
            public void onStaffLoaded(List<Staff> staffList) {
                mStaffListView.showStaff(new ArrayList<Staff>(staffList));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void addNewStaffMember() {

    }

    @Override
    public void deleteStaff(Staff member) {

    }

    @Override
    public void editStaff() {

    }

}
