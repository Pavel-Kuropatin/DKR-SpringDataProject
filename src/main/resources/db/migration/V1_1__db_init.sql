-- carts
create table carts(
    id      bigserial not null constraint carts_pk primary key
);

alter table carts owner to username;

create unique index carts_id_uindex
    on carts (id);

-- users
create table users(
    id         bigserial    not null constraint users_pk primary key,
    cart_id    bigint       not null constraint users_cart_id_fk references carts,
    login      varchar(20)  not null,
    password   varchar(100) not null,
    name       varchar(100) not null,
    surname    varchar(100) not null,
    gender     varchar(9)   not null,
    birth_date date         not null,
    phone      varchar(13)  not null,
    email      varchar(50)  not null
);

alter table users owner to username;

create unique index users_id_uindex
    on users (id);

create unique index users_cart_id_uindex
    on users (cart_id);

create unique index users_login_uindex
    on users (login);

create unique index users_phone_uindex
    on users (phone);

create unique index users_email_uindex
    on users (email);

-- addresses
create table addresses(
    id bigserial not null constraint addresses_pk primary key
);

alter table addresses owner to username;

create unique index addresses_id_uindex
    on addresses (id);

-- products
create table products(
    id           bigserial     not null constraint products_pk primary key,
    name         varchar(255)  not null,
    description  varchar(2000) not null,
    price        numeric       not null,
    is_available boolean       not null
);

alter table products owner to username;

create unique index products_id_uindex
    on products (id);

create index products_name_index
    on products (name);

create index products_price_index
    on products (price);

create index products_is_available_index
    on products (is_available);

-- orders
create table orders(
    id         bigserial   not null constraint orders_pk primary key,
    user_id    bigint      not null constraint orders_user_id_fk references users,
    status     varchar(11) not null,
    order_date date        not null
);

alter table orders owner to username;

create unique index orders_id_uindex
    on orders (id);

create index orders_user_id_index
    on orders (user_id);

create index orders_status_index
    on orders (status);

create index orders_order_date_index
    on orders (order_date);

-- items
create table items(
    id         bigserial not null constraint items_pk            primary key,
    product_id bigint    not null constraint items_product_id_fk references products,
    cart_id    bigint             constraint items_cart_id_fk    references carts,
    order_id   bigint             constraint items_order_id_fk   references orders,
    quantity   integer
);

alter table items owner to username;

create unique index items_id_uindex
    on items (id);

create index items_cart_id_index
    on items (cart_id);

create index items_order_id_index
    on items (order_id);

create index items_product_id_index
    on items (product_id);