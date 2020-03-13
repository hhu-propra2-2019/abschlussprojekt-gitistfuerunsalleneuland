DROP TABLE IF EXISTS rheinjug1.receipt_signature;
CREATE TABLE `rheinjug1`.`receipt_signature` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `meetup_id` INT NOT NULL,
  `signature` VARCHAR(2000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

DROP TABLE IF EXISTS rheinjug1.accepted_submission;
CREATE TABLE `rheinjug1`.`accepted_submission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `meetup_id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `min_io_link` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

DROP TABLE IF EXISTS rheinjug1.event;

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

