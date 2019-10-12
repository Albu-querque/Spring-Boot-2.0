insert into users (id, username, password, email, active)
values (1, 'albuquerque', '123', 'albuquerque.maks@gmail.com', true);

insert into user_roles (user_id, roles)
values (1, 'USER'), (1, 'ADMIN');