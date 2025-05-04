package tn.esprit.sporty.Service;

import tn.esprit.sporty.Entity.TrainingSession;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CalendarUtils {

    public static String formatDate(Date date) {
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
    }

    public static String buildICalendarContent(TrainingSession session) {
        String start = formatDate(session.getStartDate());
        String end = formatDate(session.getEndDate());

        return "BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "PRODID:-//YourCompany//YourApp//EN\n" +
                "BEGIN:VEVENT\n" +
                "UID:" + session.getId() + "@yourapp.com\n" +
                "DTSTAMP:" + start + "\n" +
                "DTSTART:" + start + "\n" +
                "DTEND:" + end + "\n" +
                "SUMMARY:" + session.getName() + "\n" +
                "DESCRIPTION:Session d'entraînement\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR";
    }
}
