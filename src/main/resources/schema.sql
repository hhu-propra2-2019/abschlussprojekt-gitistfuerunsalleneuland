DROP TABLE IF EXISTS rheinjug1.receipt;
CREATE TABLE `rheinjug1`.`receipt` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `used` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));


DROP TABLE IF EXISTS rheinjug1.event;
DROP TABLE IF exists rheinjug1.venue;
CREATE TABLE `rheinjug1`.`venue` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) ,
  `adresse` VARCHAR(45),
  `city` VARCHAR(45),
   PRIMARY KEY (`id`));


CREATE TABLE `rheinjug1`.`event` (
  `id` INT NOT NULL,
  `duration` TIME NULL,
  `name` VARCHAR(45) NULL,
  `status` VARCHAR(10) NULL,
  `zonedDateTime` DATETIME NULL,
  `venue_id` INT NOT NULL,
  `link` VARCHAR(100) NULL,
  `description` TEXT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (venue_id) REFERENCES rheinjug1.venue (id)
  ON UPDATE CASCADE ON DELETE CASCADE
  );

