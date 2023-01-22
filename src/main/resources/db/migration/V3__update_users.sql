alter table users
    add column cart_id BIGSERIAL
        references carts (id) on update cascade on delete cascade;