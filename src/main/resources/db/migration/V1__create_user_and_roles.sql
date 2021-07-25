drop table if exists users cascade;
CREATE TABLE users
(
    id        BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(255)        not null,
    lastname  VARCHAR(255)        not null,
    date      DATE                not null,
    password  VARCHAR(255)        not null,
    email     VARCHAR(255) unique not null,
    phone     VARCHAR(255) unique not null
);

INSERT INTO users (firstname, lastname, date, password, email, phone)
VALUES ('admin', 'admin', '01.01.2000', '$2y$12$2ITRQJb/fKRUMnyZkwyA6OPOl5jyERiK6x/n7Xait/esoBmZfi6Ne',
        'admin@corp.com', '+74950000000'); --login/pas: admin@corp.com / admin


drop table if exists roles cascade;
CREATE TABLE roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) unique
);

create
unique index role_name on roles (name);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_SUPER_ADMIN');


drop table if exists user_roles cascade;
CREATE TABLE user_roles
(
    user_id BIGSERIAL REFERENCES users (id) ON DELETE CASCADE,
    role_id BIGSERIAL REFERENCES roles (id)
);

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 2);
