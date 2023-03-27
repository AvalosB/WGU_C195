package Controllers;

import TimeZone.TimeZone;
import User.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

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

        Boolean overlapping;


        if(startDate.isAfter(now)){
            return true;
        }
        return false;
    }

    public static void overlappingAppointments(String start){
        Boolean overlapping = false;
        start = start.substring(0, 16).replace(" ", "T");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(start);
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        appointments = LogInController.user.getAppointments();

        appointments.forEach( (app) -> {
            String end = app.End + ":00";
            end = TimeZone.convertToLocalDateTimeToUTC(end);
            end = end.substring(0, 16).replace(" ", "T");
            LocalDateTime endDate = LocalDateTime.parse(end);
            System.out.println("Over Start: " + startDate);
            System.out.println("Over End: " +  endDate);
            if(startDate.isBefore(endDate)){

                AddAppointment.overlapping = true;
            }
        });

        System.out.println("Over" + AddAppointment.overlapping);
    }
}
