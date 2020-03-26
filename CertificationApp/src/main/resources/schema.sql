DROP TABLE IF EXISTS receipts.receipt;
CREATE TABLE receipts.receipt (
  
  id INT NOT NULL,
  signature VARCHAR(2000) NOT NULL,
  meetup_type ENUM('ENTWICKELBAR', 'RHEINJUG') NOT NULL,
  
  PRIMARY KEY (id),
  UNIQUE INDEX `signature__UNIQUE` (`signature`)
);
