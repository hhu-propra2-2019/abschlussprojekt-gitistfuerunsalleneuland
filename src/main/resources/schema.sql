DROP TABLE IF EXISTS rheinjug1.receipt;
CREATE TABLE `rheinjug1`.`receipt` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `used` TINYINT NOT NULL,
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
  PRIMARY KEY (`id`)
  );

