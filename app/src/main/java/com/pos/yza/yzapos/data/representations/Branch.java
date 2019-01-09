package com.pos.yza.yzapos.data.representations;

/**
 * Created by beyondinfinity on 22/1/18.
 */

public class Branch {
    int branchId;
    String name;
    String address;

    public Branch(int branchId, String name, String address){
        this.branchId = branchId;
        this.name = name;
        this.address = address;
    }

    public int getBranchId() {
        return branchId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
