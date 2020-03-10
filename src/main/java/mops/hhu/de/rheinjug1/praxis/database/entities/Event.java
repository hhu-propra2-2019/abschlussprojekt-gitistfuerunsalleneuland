package mops.hhu.de.rheinjug1.praxis.database.entities;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

@ToString
@Getter
@Setter
public class Event {

  @Id
  private long id;
  private final String duration;
  private final String name;
  private final String status;
  private final String zonedDateTime;
  private final String link;
  private final String description;

  public Event(
      final Duration duration,
      final long id,
      final String name,
      final String status,
      final ZonedDateTime zonedDateTime,
      final String link,
      final String description) {
    this.duration = format(duration);
    this.id = id;
    this.name = name;
    this.status = status;
    this.zonedDateTime = ZonedDateTimetoString(zonedDateTime);
    this.link = link;
    this.description = description;
  }

  public void setId(long id){
    this.id=id;
  }

  private String format(Duration duration){
    String durationString = "" + duration.toHoursPart() + ":" + duration.toMinutesPart();
    return durationString;
  }



  private String ZonedDateTimetoString(ZonedDateTime zonedDateTime){
    Long OffsetHours = OffsetToHours(zonedDateTime.getOffset());
    zonedDateTime = zonedDateTime.plusHours(OffsetHours);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy");
    return zonedDateTime.format(formatter);
  }

  private Long OffsetToHours(ZoneOffset Offset){
    String OffsetString = Offset.toString();
    int index = OffsetString.lastIndexOf(":");
    String converted = OffsetString.substring(0,index);
    return Long.parseLong(converted);
  }
}
