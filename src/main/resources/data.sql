DROP TABLE IF EXISTS tags;

CREATE TABLE tags (
  tag_id int NOT NULL AUTO_INCREMENT,
  user_id int NOT NULL,
  movie_id int NOT NULL,
  tag varchar(50) NOT NULL,
  time_stamp timestamp NOT NULL
);