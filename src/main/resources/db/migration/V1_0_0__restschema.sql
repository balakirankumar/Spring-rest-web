CREATE SCHEMA IF NOT EXISTS rest;

SET search_path TO rest;


CREATE TABLE IF NOT EXISTS rest.user_profile (
  id               VARCHAR(36) PRIMARY KEY,
  email  VARCHAR(50) NOT NULL,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  birth_date TIMESTAMP,
  created_by       VARCHAR(36),
  created_date     TIMESTAMP    NOT NULL DEFAULT current_timestamp,
  modified_by      VARCHAR(36) references rest.user_profile(id),
  modified_date    TIMESTAMP
);

CREATE TABLE IF NOT EXISTS rest.post (
  id               VARCHAR(36) PRIMARY KEY,
  title        VARCHAR(50),
  description  VARCHAR(150),
  user_id VARCHAR(36) references rest.user_profile(id),
  created_by       VARCHAR(36) NOT NULL,
  created_date     TIMESTAMP    NOT NULL DEFAULT current_timestamp,
  modified_by      VARCHAR(36)  references rest.user_profile(id),
  modified_date    TIMESTAMP
);