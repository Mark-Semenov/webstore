
CREATE TABLE users
(
    id        BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(255),
    lastname  VARCHAR(255),
    age       SMALLINT,
    login     VARCHAR(255),
    password  VARCHAR(255),
    email     VARCHAR(255),
    phone     INTEGER
);

INSERT INTO users (login, password)
VALUES ('admin', '$2y$12$f06iCJFDF0z88mTLSVYFSOVACgG4ybjFk6bxvZhFFlggsBzVsLr4S');

CREATE TABLE roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_SUPER_ADMIN');

CREATE TABLE user_roles
(
    user_id BIGINT REFERENCES users (id) ON DELETE CASCADE,
    role_id BIGINT REFERENCES roles (id)
);

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 2);
