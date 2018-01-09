package com.pos.yza.yzapos.data.representations;

import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public interface Item {
    public List<String> showEditableFields();
    public String getName();
    public int getId();
}
