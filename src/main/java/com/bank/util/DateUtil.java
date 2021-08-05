package com.bank.util;

import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@NoArgsConstructor
public class DateUtil {
    
    public static final String SIMPLE_DATE_TIME_FORMAT = "dd/MM/yyyy hh:mm";
    public static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";


    public static String getDateAsString(Date date , String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return sf.format(date);
    }

    public static Date getDateFromString(String date , String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        try {
            return sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getFormattedDate(Date date , String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        try {
            return sf.parse(sf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date convertToDate(LocalDate localdate) {
        return java.util.Date.from(localdate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }



    
}
