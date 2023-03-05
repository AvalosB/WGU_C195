package Controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormVerification {

    public static boolean verifyEndStartDates(String start, String end){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(start, dtf);
        LocalDateTime endDate = LocalDateTime.parse(end, dtf);
        if(startDate.isBefore(endDate)){
            return true;
        }
        return false;
    }

    public static boolean verifyStartDate(String start){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(start, dtf);
        LocalDateTime now = LocalDateTime.now();

        if(startDate.isAfter(now)){
            return true;
        }
        return false;
    }
}
