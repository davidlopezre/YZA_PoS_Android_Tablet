package com.pos.yza.yzapos.data.representations;

import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public final class Staff implements Item {

    int staffId;
    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    String homeAddress;

    public Staff(int staffId, String firstName,
                 String lastName, String phoneNumber,
                 String email, String homeAddress){

        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.homeAddress = homeAddress;
    }

    public Staff(String firstName,
                 String lastName, String phoneNumber,
                 String email, String homeAddress){

        this.staffId = -1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.homeAddress = homeAddress;
    }


    public int getStaffId() {
        return staffId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    @Override
    public List<String> showEditableFields() {
        return null;
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String toString() {
        return staffId +  " " + firstName + " " + lastName + " "
                + phoneNumber + " " + email + " " + homeAddress;
    }
}
