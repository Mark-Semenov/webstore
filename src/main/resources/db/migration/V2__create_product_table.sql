drop table if exists products cascade;
CREATE TABLE products
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255),
    description VARCHAR(255),
    status      VARCHAR(255),
    image       VARCHAR(255),
    price       NUMERIC,
    old_price   NUMERIC,
    count       INTEGER,
    sale        BOOLEAN,
    brand_id    BIGSERIAL references brands (id) on delete cascade
);
VALUES ('Samsung'),
       ('Apple'),
       ('Lenovo'),
       ('Huawei'),
       ('Epson');

INSERT INTO products (name, description, status, image, price, old_price, count, sale, brand_id)
VALUES ('Samsung Galaxy U21 Ultra',
        '6.8" (3200x1440) 120 Гц, оперативная память: 12 ГБ, память: 128 ГБ, 4 камеры: 108 МП, 12 МП, 10 МП, 10 МП',
        'in stock', 'galaxy-s21.jpg', 1500, 1725, 15, true, 1),
       ('Iphone 12', '6.1" (2532x1170), оперативная память: 4 ГБ, память: 128 ГБ, двойная камера: 12 МП, 12 МП',
        'in stock', 'iphone-12-pro-max.jpg', 1450, 1550, 25, true, 2),
       ('Huawei P40 lite', '6/128GB', 'in stock', 'huawei_p40.jpg', 1700, 1800, 19, true, 4),
       ('Mac Book Pro 16', 'Intel Core i9 9880H 2.3ГГц, 16ГБ, 1ТБ SSD, Radeon Pro 5500M - 4096 Мб', 'in stock',
        'macbook_pro_2019.jpg', 3150, 3500, 8, true, 2),
       ('Samsung galaxy A52', '6.5" (2400x1080) 90 Гц оперативная память: 4 ГБ память: 128 ГБ', 'in stock',
        'samsung-a-52.jpg', 1450, 1550, 12, true, 1),
       ('Epson L805', 'струйный, цветной, A4, Скорость печать (ч/б) до 37 стр/мин, формата А4', 'in stock', 'epson.jpg',
        3200, 3600, 35, true, 5),
       ('LENOVO IdeaPad S340-14IIL', '14", IPS, Intel Core i5 1035G1 1.0ГГц, 8ГБ, 256ГБ SSD, Intel UHD Graphics',
        'in stock', 'lenovo-ideapad.jpg', 1900, 2300, 18, true, 3);

drop table if exists categories cascade;
CREATE TABLE categories
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) unique
);

INSERT INTO categories (name)
VALUES ('Phones'),
       ('Notebooks'),
       ('Printers');


drop table if exists product_category cascade;
CREATE TABLE product_category
(
    product_id  BIGSERIAL REFERENCES products (id) ON DELETE CASCADE,
    category_id BIGSERIAL REFERENCES categories (id)
);

INSERT INTO product_category (product_id, category_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 2),
       (5, 1),
       (6, 3),
       (7, 2);

drop table if exists carts cascade;
CREATE TABLE carts
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGSERIAL references users (id) on delete cascade
);

drop table if exists product_in_cart cascade;
CREATE TABLE product_in_cart
(
    id         BIGSERIAL primary key,
    product_id BIGSERIAL references products (id),
    count      BIGSERIAL
);

drop table if exists carts_products cascade;
CREATE TABLE carts_products
(
    cart_id            BIGSERIAL references carts (id) on delete cascade,
    product_in_cart_id BIGSERIAL references product_in_cart (id)
);

drop table if exists orders cascade;
CREATE TABLE orders
(
    id        BIGSERIAL PRIMARY KEY,
    user_id   BIGSERIAL references users (id) on delete cascade,
    address   VARCHAR(255),
    total_sum numeric
);



