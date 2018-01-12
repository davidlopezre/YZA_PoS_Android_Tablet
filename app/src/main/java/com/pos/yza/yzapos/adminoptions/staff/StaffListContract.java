package com.pos.yza.yzapos.adminoptions.staff;

import com.pos.yza.yzapos.data.representations.Item;
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
        void showAddStaff();
        void showEditStaff();
        void showDeleteStaff();
    }

    interface Presenter extends BasePresenter {
        void loadStaff();
        void addNewStaffMember();
        void deleteStaff(Staff member);
        void editStaff();
    }
}
