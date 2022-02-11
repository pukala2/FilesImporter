CREATE TABLE tags (
  tag_id int PRIMARY KEY NOT NULL,
  user_id int NOT NULL,
  movie_id int NOT NULL,
  tag varchar(100) NOT NULL,
  timestamp timestamp NOT NULL
);