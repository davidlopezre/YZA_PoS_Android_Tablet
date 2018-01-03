package com.pos.yza.yzapos.data.representations;

import java.util.List;

/**
 * Created by Dlolpez on 27/12/17.
 */

public class ProductCategory {
    private int id;
    private List<String> propertyList;

    public int getId() {
        return id;
    }

    public List<String> getPropertyList() {return propertyList;}

    public ProductCategory(int id, List<String> propertyList) {
        this.id = id;
        this.propertyList = propertyList;
    }
}

