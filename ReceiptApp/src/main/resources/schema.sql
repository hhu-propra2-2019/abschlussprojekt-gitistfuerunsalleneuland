DROP TABLE IF EXISTS rheinjug1.signature_record;
DROP TABLE IF EXISTS rheinjug1.submission;
DROP TABLE IF EXISTS rheinjug1.event;
DROP VIEW IF EXISTS rheinjug1.chart_data;

CREATE TABLE `rheinjug1`.`event` (
  `id` INT NOT NULL,
  `duration` VARCHAR(10) NULL,
  `name` VARCHAR(100) NULL,
  `status` VARCHAR(10) NULL,
  `zoned_date_time` VARCHAR(30) NULL,
  `link` VARCHAR(100) NULL,
  `description` TEXT NULL,
  `meetup_type` ENUM('ENTWICKELBAR', 'RHEINJUG') NOT NULL,
  PRIMARY KEY (`id`)
  );

CREATE TABLE `rheinjug1`.`signature_record` (
  `meetup_id` INT NOT NULL,
  `signature` VARBINARY(2000) NOT NULL,
  PRIMARY KEY (`signature`)
  );

alter table signature_record
	add constraint signature_record__event__meetup_id_fk
		foreign key (meetup_id) references event (id);

CREATE TABLE `rheinjug1`.`submission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `meetup_id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `min_io_link` VARCHAR(2000) NOT NULL,
  `accepted` CHAR(1) NOT NULL,
  `acceptance_date_time` VARCHAR(30) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `meetup_id__email__UNIQUE` (`meetup_id`,`email`)
  );

alter table submission
	add constraint submission_event_id_fk
		foreign key (meetup_id) references event (id);

CREATE VIEW rheinjug1.chart_data AS
SELECT e.zoned_date_time,COUNT(s.meetup_id) AS submissions,COUNT(s.accepted) AS accepted,COUNT(sr.meetup_id) AS receipts
FROM rheinjug1.event e
LEFT JOIN rheinjug1.submission s ON s.meetup_id = e.id
LEFT JOIN rheinjug1.signature_record sr ON sr.meetup_id = e.id
WHERE e.status="past"
GROUP BY e.zoned_date_time;
