package com.example.tfg.Helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Helpers {

    public static boolean isNotNull(Object entity){
        return entity != null;
    }

    public static String getTimestamp(){
        TimeZone tz = Calendar.getInstance().getTimeZone();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        df.setTimeZone(tz);
        return df.format(new Date());
    }
}
