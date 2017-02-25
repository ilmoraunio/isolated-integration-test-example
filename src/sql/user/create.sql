INSERT INTO users (username, password)
VALUES ((:username)::varchar(200), (:password)::text);