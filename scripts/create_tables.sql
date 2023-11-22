CREATE EXTENSION pgcrypto;
-- Creation of operation_type table
CREATE TYPE operation_types AS ENUM ('PAYMENT', 'CASH', 'INSTALMENT', 'WITHDRAW');

CREATE TABLE IF NOT EXISTS operation_type (
  id INT NOT NULL,
  description OPERATION_TYPES NOT NULL,

  PRIMARY KEY (id)
);

-- Creation of account table
CREATE TABLE IF NOT EXISTS account (
  id SERIAL PRIMARY KEY,
  document_number BYTEA,
  hashed_document_number BYTEA NOT NULL UNIQUE,
  created_at TIMESTAMPTZ NOT NULL
);

-- Creation of transaction table
CREATE TABLE IF NOT EXISTS transaction (
  id SERIAL PRIMARY KEY,
  account_id INT NOT NULL,
  operation_type INT NOT NULL,
  amount DECIMAL NOT NULL,
  event_date TIMESTAMPTZ NOT NULL,

  CONSTRAINT fk_account
    FOREIGN KEY(account_id) 
	    REFERENCES account(id),

  CONSTRAINT fk_operation_type
    FOREIGN KEY(operation_type) 
	    REFERENCES operation_type(id)
);
