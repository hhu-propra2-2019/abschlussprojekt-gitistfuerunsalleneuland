DROP TABLE IF EXISTS rheinjug1.quittung;
CREATE TABLE `rheinjug1`.`quittung` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `used` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
