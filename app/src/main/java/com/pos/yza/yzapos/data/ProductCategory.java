package com.pos.yza.yzapos.data;

import java.util.List;

/**
 * Created by Dlolpez on 27/12/17.
 */

public class ProductCategory {
    int id;
    List<String> propertyList;

    public int getId() {
        return id;
    }

    public ProductCategory(int id, List<String> propertyList) {
        this.id = id;
        this.propertyList = propertyList;

    }
}
