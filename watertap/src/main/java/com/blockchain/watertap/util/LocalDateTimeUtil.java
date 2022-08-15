package com.blockchain.watertap.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class LocalDateTimeUtil {

    private static final String DAYS_AGO = " days ago";
    private static final String HOURS_AGO = " hours ago";
    private static final String MINUTES_AGO = " minutes ago";
    private static final String SECONDS_AGO = " seconds ago";

    public static String calTimeDiff(LocalDateTime startTime,LocalDateTime endTime){
        Duration duration = Duration.between(startTime,endTime);
        Long days = duration.toDays();
        StringBuffer sb = new StringBuffer();
        if(days > 1){
            sb.append(days);
            sb.append(DAYS_AGO);
        }else{
            Long hours = duration.toHours();
            if(hours > 1){
                sb.append(hours);
                sb.append(HOURS_AGO);
            }else{
                Long minutes = duration.toMinutes();
                if(minutes > 1){
                    sb.append(minutes);
                    sb.append(MINUTES_AGO);
                }else{
                    Long millis = duration.toMillis();
                    sb.append(millis / 1000);
                    sb.append(SECONDS_AGO);
                }
            }
        }
        return sb.toString();
    }
}
