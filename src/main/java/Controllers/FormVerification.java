package Controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormVerification {

    public static boolean verifyEndStartDates(String start, String end){
        end = end.substring(0, 16).replace(" ", "T");
        start = start.substring(0, 16).replace(" ", "T");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        if(startDate.isBefore(endDate)){
            return true;
        }
        return false;
    }

    public static boolean verifyStartDate(String start){
        start = start.substring(0, 16).replace(" ", "T");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime now = LocalDateTime.now();

        if(startDate.isAfter(now)){
            return true;
        }
        return false;
    }
}
