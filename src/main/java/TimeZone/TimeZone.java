package TimeZone;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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



}
