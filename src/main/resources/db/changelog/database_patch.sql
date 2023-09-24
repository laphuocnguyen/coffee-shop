--liquibase formatted sql

--changeset nguyen.la:1
-- Add user table
CREATE TABLE shop_user
(
    id         SERIAL    NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP    ,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP    ,
    password   varchar(100) NOT NULL,
    username   varchar(100) NOT NULL,
    primary key (id)
);

alter table shop_user
    add constraint user_username_unique unique (username);

INSERT INTO shop_user (id, username, password, created_at, updated_at)
VALUES (1, 'admin', '$2a$10$moKBAamsl/F3xfWViUkaMOHJ90gKaialb.CkE4X0dcodvjCvS5uJy', '2023-09-23 22:33:35.076', '2023-09-23 22:33:35.076');

-- Add customer table
CREATE TABLE customer
(
    id        SERIAL      NOT NULL,
    user_id   SERIAL      NOT NULL,
    lastname  VARCHAR(32),
    firstname VARCHAR(32),
    PRIMARY KEY (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES shop_user (id)
);

INSERT INTO customer (id, user_id, lastname, firstname)
VALUES (1, 1, 'James', 'Walker');

-- Add shop table
CREATE TABLE shop
(
    id                SERIAL NOT NULL,
    address           VARCHAR(32),
    queue_size        numeric,
    average_wait_time numeric,
    PRIMARY KEY (id)
);

INSERT INTO shop (id, address, queue_size, average_wait_time)
VALUES (1, 'ho chi minh', 3, 45);

-- Add shop menu_item
CREATE TABLE menu_item
(
    id      SERIAL NOT NULL,
    shop_id SERIAL NOT NULL,
    name    VARCHAR(32),
    price   numeric,
    PRIMARY KEY (id),
    CONSTRAINT fk_shop_id FOREIGN KEY (shop_id) REFERENCES shop (id)
);

INSERT INTO menu_item (id, shop_id, name, price)
VALUES (1, 1, 'tra vai hat sen', 25000),
       (2, 1, 'tra sua o long', 35000);

-- Add shop queue
CREATE TABLE queue
(
    id               SERIAL NOT NULL,
    shop_id          SERIAL NOT NULL,
    order_number     numeric,
    current_customer numeric,
    PRIMARY KEY (id),
    CONSTRAINT fk_shop_id FOREIGN KEY (shop_id) REFERENCES shop (id)
);
INSERT INTO queue (id, shop_id, order_number, current_customer)
VALUES (1, 1, 1, 0),
       (2, 1, 2, 0);

-- Add customer_queue
CREATE TABLE customer_order
(
    id          SERIAL NOT NULL,
    customer_id SERIAL NOT NULL,
    shop_id     SERIAL NOT NULL,
    total       numeric,
    status      VARCHAR(32),
    PRIMARY KEY (id),
    CONSTRAINT fk_shop_id FOREIGN KEY (shop_id) REFERENCES shop (id),
    CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer (id)
);

-- Add customer_queue
CREATE TABLE order_item
(
    id           SERIAL NOT NULL,
    order_id     SERIAL NOT NULL,
    menu_item_id SERIAL NOT NULL,
    price        numeric,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES customer_order (id),
    CONSTRAINT fk_menu_item_id FOREIGN KEY (menu_item_id) REFERENCES menu_item (id)
);

-- Add customer_queue
CREATE TABLE customer_queue
(
    id           SERIAL NOT NULL,
    queue_id     SERIAL NOT NULL,
    customer_id  SERIAL NOT NULL,
    order_number numeric,
    order_id     SERIAL NOT NULL,
    status       VARCHAR(32),
    PRIMARY KEY (id),
    CONSTRAINT fk_queue_id FOREIGN KEY (queue_id) REFERENCES queue (id),
    CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer (id),
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES customer_order (id)
);
