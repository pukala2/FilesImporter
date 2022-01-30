DROP TABLE IF EXISTS tags;

CREATE TABLE tags (
  tag_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id int NOT NULL,
  movie_id int NOT NULL,
  tag varchar(100) NOT NULL,
  timestamp timestamp NOT NULL
);