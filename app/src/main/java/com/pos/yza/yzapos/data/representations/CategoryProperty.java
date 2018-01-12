package com.pos.yza.yzapos.data.representations;

import com.pos.yza.yzapos.data.source.remote.CategoriesRemoteDataSource;

import java.util.HashMap;

/**
 * Created by Dlolpez on 11/1/18.
 */

public class CategoryProperty {
    int id;
    String name;

    public CategoryProperty (int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryProperty (String name) {
        this.id = -1;
        this.name = name;
    }

    public HashMap<String,String> toHashMapNoId(){
        HashMap<String,String> toReturn = new HashMap<String, String>();
        toReturn.put(CategoriesRemoteDataSource.CATEGORY_PROPERTY_NAME, name);
        return toReturn;
    }

    public String getName() {
        return name;
    }

    public int getId(){
        return id;
    }
}
