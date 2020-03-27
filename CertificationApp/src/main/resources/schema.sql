DROP TABLE IF EXISTS receipt;
CREATE TABLE receipt (
  
  id INT NOT NULL,
  signature text,
  
  PRIMARY KEY (id),
  UNIQUE INDEX id_UNIQUE (id ASC));
