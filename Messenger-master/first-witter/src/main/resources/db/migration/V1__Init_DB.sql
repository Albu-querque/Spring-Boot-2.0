create sequence hibernate_sequence start 1 increment 1

create table messages (
    id  bigserial not null,
    filename varchar(255),
    tag varchar(255),
    text varchar(2048) not null,
    user_id int8,
    primary key (id)
)

create table user_roles (
    user_id int8 not null,
    roles varchar(255)
)

create table users (id int8 not null,
    activation_code varchar(255),
    active boolean not null,
    email varchar(255) not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
)

alter table users add constraint email_unique unique (email)
alter table users add constraint username_unique unique (username)
alter table messages add constraint messages_user_fk foreign key (user_id) references users
alter table user_roles add constraint roles_user_fk foreign key (user_id) references users