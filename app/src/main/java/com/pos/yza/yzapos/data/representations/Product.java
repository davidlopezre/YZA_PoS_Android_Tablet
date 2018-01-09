package com.pos.yza.yzapos.data.representations;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Dlolpez on 26/12/17.
 */

public final class Product implements Item {
    Double unitPrice;
    String unitMeasure;
    HashMap<String, String> propertyMap;
    String category;

    public Product(Double unitPrice, String unitMeasure, String category){
        this.unitPrice = unitPrice;
        this.unitMeasure = unitMeasure;
        this.category = category;
    }

    public void setProperties(){}

    public Double getUnitPrice() {
        return unitPrice;
    }

    public String getCategory() {
        return category;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public HashMap<String, String> getPropertyMap() {
        return propertyMap;
    }

    @Override
    public List<String> showEditableFields() {
        return null;
    }

    @Override
    public String getName() {
        return category + Double.toString(unitPrice) + unitMeasure;
    }
}

