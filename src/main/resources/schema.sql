DROP TABLE IF EXISTS rheinjug1.receipt_signature;
CREATE TABLE `rheinjug1`.`receipt_signature` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `used` TINYINT NOT NULL,
  `signature` CHAR(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

DROP TABLE IF EXISTS rheinjug1.accepted_submission;
CREATE TABLE `rheinjug1`.`accepted_submission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `meetup_id` INT NOT NULL,
  `keycloak_id` INT NOT NULL,
  `min_io_link` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
