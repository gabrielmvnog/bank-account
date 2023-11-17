-- Creation of operation_type table
CREATE TYPE operation_types AS ENUM ('PAYMENT', 'CASH', 'INSTALMENT', 'WITHDRAW');

CREATE TABLE IF NOT EXISTS operation_type (
  id INT NOT NULL,
  description OPERATION_TYPES NOT NULL,
  PRIMARY KEY (id)
);
