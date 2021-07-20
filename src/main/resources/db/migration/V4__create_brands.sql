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
    brand_id   BIGSERIAL REFERENCES brands (id) on delete cascade,
    product_id BIGSERIAL references products (id) on delete cascade
);

insert into brands_products (brand_id, product_id)
VALUES (1, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5),
       (5, 6),
       (3, 7);