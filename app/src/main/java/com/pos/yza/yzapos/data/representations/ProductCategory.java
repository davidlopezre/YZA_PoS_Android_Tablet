package com.pos.yza.yzapos.data.representations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dlolpez on 27/12/17.
 */

public class ProductCategory {
    private int id;

    public String getName() {
        return name;
    }

    private String name;
    private List<String> propertyList;

    public int getId() {
        return id;
    }

    public List<String> getPropertyList() {return propertyList;}

    public ProductCategory(int id, String name, List<String> propertyList) {
        this.id = id;
        this.name = name;
        this.propertyList = propertyList;
    }

    public ArrayList<String> getNameList(List<ProductCategory> categories){
        ArrayList<String> categoryNames = new ArrayList<String>();
        for(ProductCategory c : categories){
            categoryNames.add(c.name);
        }
        return categoryNames;
    }
}

