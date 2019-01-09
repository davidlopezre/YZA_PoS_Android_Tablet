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

}
