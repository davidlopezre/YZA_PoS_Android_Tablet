package com.pos.yza.yzapos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class Parsers {

    public static Date parseDjangoDateTime(String djangoDateTime) {
        int index = djangoDateTime.indexOf(".");
        if (index > 0)
            djangoDateTime = djangoDateTime.substring(0, index);

        String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            Date dateTime = sdf.parse(djangoDateTime);
            return dateTime;
        }
        catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

}
