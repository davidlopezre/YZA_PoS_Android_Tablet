package com.pos.yza.yzapos.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by beyondinfinity on 2/1/19.
 */

public class Formatters {
    public static DecimalFormat amountFormat = new DecimalFormat("₱###,###,###,##0.00");
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static String capitalise(String msg) {
        msg = msg.substring(0,1).toUpperCase() + msg.substring(1).toLowerCase();
        return msg;
    }
}
