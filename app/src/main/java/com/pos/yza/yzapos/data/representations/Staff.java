package com.pos.yza.yzapos.data.representations;

import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public class Staff implements Item {
    @Override
    public List<String> showEditableFields() {
        return null;
    }

    @Override
    public String getTitleForList() {
        return null;
    }
}
