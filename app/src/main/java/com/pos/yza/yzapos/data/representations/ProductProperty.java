package com.pos.yza.yzapos.data.representations;

import com.pos.yza.yzapos.data.source.remote.ProductsRemoteDataSource;

import java.util.HashMap;

/**
 * Created by David Lopez on 11/1/18.
 */

public class ProductProperty {
    int productPropertyId;
    int categoryPropertyId;
    String value;

    public ProductProperty(int categoryPropertyId, String value) {
        this.productPropertyId = -1;
        this.categoryPropertyId = categoryPropertyId;
        this.value = value;
    }

    public ProductProperty(int productPropertyId, int categoryPropertyId, String value) {
        this.productPropertyId = productPropertyId;
        this.categoryPropertyId = categoryPropertyId;
        this.value = value;
    }

    public int getProductPropertyId() {
        return productPropertyId;
    }

    public int getCategoryPropertyId() {
        return categoryPropertyId;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString(){
        return "id: " + productPropertyId + " category: " + categoryPropertyId +
                " value: " + value;
    }

    public HashMap<String,String> toHashMap(){
        HashMap<String,String> toReturn = new HashMap<String, String>();
        toReturn.put(ProductsRemoteDataSource.PRODUCT_PROPERTY_ID, productPropertyId + "");
        toReturn.put(ProductsRemoteDataSource.PRODUCT_PROPERTY_VALUE, value);
        toReturn.put(ProductsRemoteDataSource.CATEGORY_PROPERTY_ID, categoryPropertyId + "");

        return toReturn;
    }
}
