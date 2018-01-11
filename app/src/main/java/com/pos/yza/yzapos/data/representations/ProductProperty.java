package com.pos.yza.yzapos.data.representations;

/**
 * Created by David Lopez on 11/1/18.
 */

public class ProductProperty {
    int categoryPropertyId;
    String value;

    public ProductProperty(int categoryPropertyId, String value) {
        this.categoryPropertyId = categoryPropertyId;
        this.value = value;
    }

    public int getCategoryPropertyId() {
        return categoryPropertyId;
    }

    public String getValue() {
        return value;
    }
}
