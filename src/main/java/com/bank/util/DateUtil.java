package com.bank.util;

import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
public class DateUtil {
    
    public static final String SIMPLE_DATE_TIME_FORMAT = "dd/MM/yyyy hh:mm";
    public static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";


    public static String getDateAsString(Date date , String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return sf.format(date);
    }

    
}
