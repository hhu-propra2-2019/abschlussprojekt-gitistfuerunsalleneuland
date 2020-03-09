DROP TABLE IF EXISTS rheinjug1.receipt;
CREATE TABLE `rheinjug1`.`receipt` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `used` TINYINT NOT NULL,
  `hash` CHAR(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

CREATE TABLE `rheinjug1`.`accepted_submission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `meetup_id` INT NOT NULL,
  `keycloak_id` INT NOT NULL,
  `min_io_link` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
