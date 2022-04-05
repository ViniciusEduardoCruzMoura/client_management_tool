CREATE TABLE user_table(
    id_user serial NOT NULL PRIMARY KEY,
    first_name character varying(50),
    last_name character varying(100),
    email character varying(50),
    password character varying(32)
);