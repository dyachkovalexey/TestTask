CREATE TABLE DepDB
(
    ID SERIAL PRIMARY KEY NOT NULL,
    DepCode character varying(20) NOT NULL,
    DepJob character varying(100) NOT NULL,
    Description character varying(255) NOT NULL
);