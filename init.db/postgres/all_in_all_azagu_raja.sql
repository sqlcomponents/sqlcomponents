DROP TABLE IF EXISTS all_in_all_azagu_raja;

CREATE TABLE all_in_all_azagu_raja (
  id INT NOT NULL,
  role_id INT NOT NULL,
  grant_date TIMESTAMP,
  PRIMARY KEY (id, role_id)
);