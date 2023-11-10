SET search_path TO rest;

CREATE TABLE IF NOT EXISTS rest.recipe (
  id               VARCHAR(36) PRIMARY KEY,
  name        VARCHAR(50),
  description  VARCHAR(150),
  image_path VARCHAR(250),
  created_by       VARCHAR(36) NOT NULL references rest.user_profile(id),
  created_date     TIMESTAMP    NOT NULL DEFAULT current_timestamp,
  modified_by      VARCHAR(36) references rest.user_profile(id),
  modified_date    TIMESTAMP
);

CREATE TABLE IF NOT EXISTS rest.ingredient (
  id               VARCHAR(36) PRIMARY KEY,
  name        VARCHAR(50),
  amount  VARCHAR(50),
  recipe_id VARCHAR(36) NOT NULL references rest.recipe (id),
  created_by       VARCHAR(36) NOT NULL,
  created_date     TIMESTAMP    NOT NULL DEFAULT current_timestamp,
  modified_by      VARCHAR(36) references rest.user_profile(id),
  modified_date    TIMESTAMP
);