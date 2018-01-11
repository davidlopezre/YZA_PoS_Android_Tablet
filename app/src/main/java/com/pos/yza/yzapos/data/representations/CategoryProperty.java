package com.pos.yza.yzapos.data.representations;

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

    public String getName() {
        return name;
    }

    public int getId(){
        return id;
    }
}
