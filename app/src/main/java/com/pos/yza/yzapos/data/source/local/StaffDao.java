package com.pos.yza.yzapos.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pos.yza.yzapos.data.representations.Staff;

import java.util.List;


/**
 * Data Access Object for the staff table.
 */
@Dao
public interface StaffDao {

    /**
     * Select all staff from the staff table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM Staff")
    List<Staff> getStaff();

    /**
     * Select a staff by id.
     *
     * @param staffId the staff id.
     * @return the staff with staffId.
     */
    @Query("SELECT * FROM Staff WHERE staff_id = :staffId")
    Staff getStaffById(String staffId);

    /**
     * Insert a staff in the database. If the staff already exists, replace it.
     *
     * @param staff the staff to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStaff(Staff staff);

    /**
     * Update a staff.
     *
     * @param staff staff to be updated
     * @return the number of staff updated. This should always be 1.
     */
    @Update
    int updateStaff(Staff staff);

    /**
     * Update the first name of a staff
     *
     * @param staffId    id of the staff
     * @param firstName  first name to be updated
     */
    @Query("UPDATE Staff SET name = :firstName WHERE staff_id = :staffId")
    void updateFirstName(String staffId, String firstName);

    // add update for lastName, phoneNumber, email, homeAddress

    /**
     * Delete a staff by id.
     *
     * @return the number of staff deleted. This should always be 1.
     */
    @Query("DELETE FROM Staff WHERE staff_id = :staffId")
    int deleteStaffById(String staffId);

    /**
     * Delete all staff.
     */
    @Query("DELETE FROM Staff")
    void deleteStaff();

}

