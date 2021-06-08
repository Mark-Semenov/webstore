alter table users add constraint auth_data unique (login, email, phone);

alter table roles add constraint user_role unique (name);
create unique index role_name on roles (name);

alter table user_roles add primary key (user_id, role_id);

