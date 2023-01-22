drop table if exists admin_block cascade;
CREATE TABLE admin_block
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) unique
);

insert into admin_block (title)
values ('Product management'),
       ('User settings'),
       ('Orders');

drop table if exists admin_url cascade;
create table admin_url
(
    id     BIGSERIAL primary key,
    action VARCHAR(255) unique,
    url    VARCHAR(255) unique
);

insert into admin_url (action, url)
values ('Add new product', '/admin/new_product'),
       ('Add new category', '/admin/new_category'),
       ('Show all products', '/admin/products'),
       ('Add new user', '/admin/new_user'),
       ('Show all orders', '/admin/orders');


drop table if exists admin_block_url cascade;
create table admin_block_url
(
    admin_block_id BIGSERIAL references admin_block (id) ON DELETE CASCADE,
    admin_url_id   BIGSERIAL references admin_url (id)
);

insert into admin_block_url(admin_block_id, admin_url_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (3, 5);
