package TimeZone;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    public static String convertToLocalDateTimeToUTC(String dateStr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.parse(dateStr, dtf);
        Timestamp ts = Timestamp.valueOf(now);
        LocalDateTime ldt = ts.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utczdt = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime ldtIn = utczdt.toLocalDateTime();

        ZonedDateTime zdtOut = ldtIn.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtOutToLocalTZ = zdtOut.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));

        String output = ldtIn.toString().replace("T", " ");
        return output;
    }

    public static String convertToLocalDateTime(String dateStr) {
        Timestamp ts = Timestamp.valueOf(dateStr);
        LocalDateTime ldt = ts.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZonedDateTime utczdt = zdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime ldtIn = utczdt.toLocalDateTime();

        ZonedDateTime zdtOut = ldtIn.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime zdtOutToLocalTZ = zdtOut.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime ldtOutFinal = zdtOutToLocalTZ.toLocalDateTime();

        return ldtOutFinal.toString().substring(0, 16).replace("T", " ");
    }



}
