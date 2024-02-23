package com.example.Analytics;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DateConverter {
    public LocalDate dateToLocalDate(Date date){
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Instant localDateToInstant(LocalDate localDate){
        return localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
    }
    public Date stringToDate(String dateString){
        Date parsedDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            parsedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return  parsedDate;
    }
}
