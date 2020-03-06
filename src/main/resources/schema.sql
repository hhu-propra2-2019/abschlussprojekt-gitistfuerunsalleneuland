DROP TABLE IF EXISTS rheinjug1.receipt;
CREATE TABLE `rheinjug1`.`receipt` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `used` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
