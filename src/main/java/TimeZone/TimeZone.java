package TimeZone;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeZone {
    public static String getCurrentTimeZone(){
        ZonedDateTime currentTimeZone = ZonedDateTime.now(
                ZoneId.systemDefault()
        );

        return String.valueOf(currentTimeZone.getZone());
    }

    public static LocalDate changeStringToDate(String conDate){
        LocalDate newDate = LocalDate.parse(conDate);
        return newDate;
    }

    public static LocalDateTime changeStringToDateTime(String date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime newDate = LocalDateTime.parse(date, dtf);
        return newDate;
    }

}
