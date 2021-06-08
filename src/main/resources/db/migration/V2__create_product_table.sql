CREATE TABLE products
(

    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255),
    description VARCHAR(255),
    status      VARCHAR(255),
    image       VARCHAR(255),
    price       NUMERIC,
    old_price   NUMERIC,
    discount    INTEGER,
    sale        BOOLEAN
);

INSERT INTO products (NAME, DESCRIPTION, STATUS, IMAGE, PRICE, OLD_PRICE, DISCOUNT, SALE)
VALUES ('Samsung Galaxy U21 Ultra', 'New smart phone model', 'in stock', 'samsung_galaxy.jfif', 1500, 1725, 15, true);

CREATE TABLE categories
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

INSERT INTO categories (name)
VALUES ('Phones'),
       ('Notebooks'),
       ('Printers');

CREATE TABLE product_category
(
    product_id BIGINT REFERENCES products (id) ON DELETE CASCADE,
    category_id BIGINT REFERENCES categories (id)
);

INSERT INTO product_category (product_id, category_id)
VALUES (1, 1);
