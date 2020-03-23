package mops.hhu.de.rheinjug1.praxis.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class FormatService {

  public static String dateTimeFormat = "HH:mm - dd.MM.yyyy";

  public String toLocalDateString(final String date) {
    final LocalDateTime time = LocalDateTime.parse(date, getDateTimePattern());
    final DateTimeFormatter formatte = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    return time.format(formatte);
  }

  public static DateTimeFormatter getDateTimePattern() {
    return DateTimeFormatter.ofPattern(dateTimeFormat);
  }
}
