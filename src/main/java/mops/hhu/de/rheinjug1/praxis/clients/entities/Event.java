package mops.hhu.de.rheinjug1.praxis.clients.entities;

@SuppressWarnings("PMD.TooManyFields")
public class Event {
  private int created;
  private int duration;
  private Fee fee;
  private String id;
  private String name;
  private boolean date_in_series_pattern;
  private String status;
  private int time;
  private String local_date;
  private String local_time;
  private int updated;
  private int utc_offset;
  private int waitlist_count;
  private int yes_rsvp_count;
  private Venue venue;
  private Group group;
  private String link;
  private String description;
  private String how_to_find_us;
  private String visibility;
  private boolean member_pay_fee;
}
