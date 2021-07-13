CREATE TABLE carts
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGSERIAL references users (id),
    discount   BIGSERIAL,
    totalSum   BIGSERIAL
);

CREATE TABLE product_in_cart
(
    id    BIGSERIAL primary key,
    product_id BIGSERIAL references products (id),
    count      BIGSERIAL
);

CREATE TABLE carts_products
(
    cart_id    BIGSERIAL references carts (id),
    product_in_cart_id BIGSERIAL references product_in_cart (id)
);
