package com.pos.yza.yzapos.data.representations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Lopez on 27/12/17.
 */

public class ProductCategory {
    private int id;

    public String getName() {
        return name;
    }

    private String name;
    private List<CategoryProperty> propertyList;

    public int getId() {
        return id;
    }

    public List<CategoryProperty> getPropertyList() {return propertyList;}

    public ProductCategory(int id, String name, List<CategoryProperty> propertyList) {
        this.id = id;
        this.name = name;
        this.propertyList = propertyList;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String detailString(){
        String properties = "";
        for (CategoryProperty p : propertyList){
            properties = properties.toString() + p.getName() + " ";
        }
        return properties;
    }
}

