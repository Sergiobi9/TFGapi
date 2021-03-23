package com.example.tfg.Helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    private static SimpleDateFormat timePattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");

    public static boolean getDateIsAfter(String concertDate, String currentDate){

        Date currentToDate;
        Date concertToDate;
        try {
            currentToDate = timePattern.parse(currentDate);
            concertToDate = timePattern.parse(concertDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return currentToDate.before(concertToDate);
    }

    public static String getTimestamp(){
        TimeZone tz = Calendar.getInstance().getTimeZone();
        timePattern.setTimeZone(tz);
        return timePattern.format(new Date());
    }
}
