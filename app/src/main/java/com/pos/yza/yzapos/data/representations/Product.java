package com.pos.yza.yzapos.data.representations;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Dlolpez on 26/12/17.
 */

public final class Product {
    int id;
    Double unitPrice;
    String unitMeasure;
    ArrayList<ProductProperty> properties;
    ProductCategory category;

    public Product(int id, Double unitPrice, String unitMeasure, ProductCategory category,
                   ArrayList<ProductProperty> properties){
        this.id = id;
        this.unitPrice = unitPrice;
        this.unitMeasure = unitMeasure;
        this.category = category;
        this.properties = properties;
    }

    public Product(Double unitPrice, String unitMeasure, ProductCategory category,
                   ArrayList<ProductProperty> properties){
        this.unitPrice = unitPrice;
        this.unitMeasure = unitMeasure;
        this.category = category;
        this.properties = properties;
    }

    public void setProperties(){}

    public int getId() {return id;}

    public Double getUnitPrice() {
        return unitPrice;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public ArrayList<ProductProperty> getProperties() {
        return properties;
    }

    public String getProductPropertyValue(int categoryPropertyId) {
        for (ProductProperty productProperty : properties) {
            if (productProperty.getCategoryPropertyId() == categoryPropertyId){
                return productProperty.getValue();
            }
        }
        return "";
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getName() {
        String name = category.getName();
        for (ProductProperty property : properties){
            name += " " + property.getValue();
        }
        return name.trim();
    }

    @Override
    public String toString(){
        String toReturn = getName() + "\n properties: ";

        for (ProductProperty property: properties) {
            toReturn += property;
        }

        return toReturn;
    }
}

