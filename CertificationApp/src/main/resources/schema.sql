DROP TABLE IF EXISTS receipt;
CREATE TABLE receipt (
  
  id INT NOT NULL,
  signature VARBINARY(2000),
  
  PRIMARY KEY (id),
  UNIQUE INDEX id_UNIQUE (id ASC));
