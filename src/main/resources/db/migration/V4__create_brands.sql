drop table if exists brands cascade;
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
       ('Epson'),
       ('Canon');

drop table if exists brands_products cascade;
create table brands_products
(
    product_id BIGSERIAL references products (id) on delete cascade,
    brand_id   BIGSERIAL references brands (id) on delete cascade

);

insert into brands_products (product_id, brand_id)
VALUES (1, 1),
       (2, 2),
       (3, 4),
       (4, 2),
       (5, 1),
       (6, 5),
       (7, 3),
       (8, 6);
