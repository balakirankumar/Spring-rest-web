CREATE TABLE IF NOT EXISTS rest.role (
  id               VARCHAR(36) PRIMARY KEY,
  name        VARCHAR(50),
  description  VARCHAR(150),
  created_by       VARCHAR(36) NOT NULL references rest.user_profile(id),
  created_date     TIMESTAMP    NOT NULL DEFAULT current_timestamp,
  modified_by      VARCHAR(36)  references rest.user_profile(id),
  modified_date    TIMESTAMP
);


CREATE TABLE IF NOT EXISTS rest.user_profile_role (
  id               VARCHAR(36) PRIMARY KEY,
  userprofile_id        VARCHAR(36) NOT NULL references rest.user_profile(id),
  role_id  VARCHAR(36) NOT NULL references rest.role(id),
  created_by       VARCHAR(36) NOT NULL references rest.user_profile(id),
  created_date     TIMESTAMP    NOT NULL DEFAULT current_timestamp,
  modified_by      VARCHAR(36)  references rest.user_profile(id),
  modified_date    TIMESTAMP
);