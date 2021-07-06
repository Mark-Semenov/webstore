CREATE TABLE brands
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) unique
);

insert into brands (title)
VALUES ('Samsung'),
       ('Apple'),
       ('Lenovo'),
       ('Huawei'),
       ('Epson');


create table brands_products
(
    brand_id   BIGINT REFERENCES brands (id) ON DELETE CASCADE,
    product_id BIGINT
);

insert into brands_products (brand_id, product_id)
VALUES (1, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5),
       (5, 6),
       (3, 7);