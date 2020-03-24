package mops.hhu.de.rheinjug1.praxis.adapters.database.submission;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import mops.hhu.de.rheinjug1.praxis.domain.TimeFormatService;
import org.springframework.data.annotation.Id;

@Getter
@EqualsAndHashCode
public class SubmissionDTO {
    @Id private Long id;
    private Long meetupId;
    private String email;
    private String name;
    private String minIoLink;
    private boolean accepted;
    private String acceptanceDateTime;
    private TimeFormatService timeFormatService;
}