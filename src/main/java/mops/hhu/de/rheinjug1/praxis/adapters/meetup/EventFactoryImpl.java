package mops.hhu.de.rheinjug1.praxis.adapters.meetup;

import static mops.hhu.de.rheinjug1.praxis.enums.MeetupTypeUtils.extractMeetupTypeFromString;

import java.time.*;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import mops.hhu.de.rheinjug1.praxis.domain.event.Event;
import mops.hhu.de.rheinjug1.praxis.domain.event.EventFactory;
import org.springframework.stereotype.Component;

@Component
public class EventFactoryImpl implements EventFactory {
  private final TimeFormatService timeFormatService;
  private EventResponseDTO dto;

  public EventFactoryImpl(final TimeFormatService timeFormatService) {
    this.timeFormatService = timeFormatService;
  }

  @Override
  public Event createFromDTO(final EventResponseDTO dto) {
    this.dto = dto;
    return Event.builder()
        .id(Long.parseLong(dto.getId()))
        .duration(formatDuration())
        .name(dto.getName())
        .status(dto.getStatus())
        .zonedDateTime(formatTime())
        .link(dto.getLink())
        .description(dto.getDescription())
        .meetupType(extractMeetupTypeFromString(dto.getName()))
        .build();
  }

  private String formatDuration() {
    final Duration duration = Duration.ofMillis(dto.getDuration());
    return timeFormatService.format(duration);
  }

  private String formatTime() {
    final Duration timeAsDuration = Duration.ofMillis(dto.getTime());
    final LocalDateTime dateTime =
        LocalDateTime.ofEpochSecond(timeAsDuration.toSeconds(), 0, ZoneOffset.UTC);
    final ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("UTC"));

    return timeFormatService.toLocalEventTimeString(zonedDateTime, dto.getGroup().getZoneId());
  }
}
