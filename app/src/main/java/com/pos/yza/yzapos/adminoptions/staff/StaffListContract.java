package com.pos.yza.yzapos.adminoptions.staff;

import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public interface StaffListContract {
    interface View extends BaseView<Presenter> {
        void showStaff(List<Staff> staffList);
        void showAddStaffMember();
        void showEditStaffMember();
        void showDeleteStaffMember();
    }

    interface Presenter extends BasePresenter {
        void loadStaff();
        void addStaffMember();
        void deleteStaffMember(Staff member);
        void editStaffMember();
    }
}
