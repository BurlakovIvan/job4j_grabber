CREATE TABLE post(
     id SERIAL PRIMARY KEY,
     name VARCHAR(250),
     text TEXT,
     link VARCHAR(500) UNIQUE,
     created TIMESTAMP
);
     