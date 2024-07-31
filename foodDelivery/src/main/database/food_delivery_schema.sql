DROP SCHEMA IF EXISTS food_delivery CASCADE;

CREATE SCHEMA food_delivery;

CREATE TABLE food_delivery.restaurant(
	id SERIAL NOT NULL,         -- 4 Bytes from 1 to 2147483647
	name VARCHAR(50) NOT NULL,
	description VARCHAR(200),
	city VARCHAR(100),
	address VARCHAR(100),

	PRIMARY KEY (id)
);


CREATE TABLE food_delivery.admin(
	id SERIAL NOT NULL,
    username VARCHAR(20) NOT NULL UNIQUE,
	password CHAR(64) NOT NULL,          -- Using md5.
	id_restaurant INT NOT NULL,

	PRIMARY KEY (id),
	FOREIGN KEY (id_restaurant) REFERENCES food_delivery.restaurant(id)
);

CREATE TABLE food_delivery.user(
    id SERIAL NOT NULL,
    username VARCHAR(20) NOT NULL UNIQUE,
	password CHAR(64) NOT NULL,          -- Using md5.
    email VARCHAR(50),
    phone_number VARCHAR(20) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE food_delivery.order(
    id SERIAL NOT NULL,
    client_name VARCHAR(50) NOT NULL,
    client_surname VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    email VARCHAR(50),
    id_admin INT,                            -- NULL = not payed
    order_date DATE,
    order_time TIMESTAMP WITH TIME ZONE,
    collection_date DATE,
    silverware BOOL NOT NULL,
    box_type INT NOT NULL,
    user_id INT,    
                     
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES food_delivery.user(id)  -- Nullable foreign key for registered users
);



CREATE TABLE food_delivery.category(
    name VARCHAR(20) NOT NULL,

    PRIMARY KEY (name)
);


CREATE TABLE food_delivery.product(
    name VARCHAR(50) NOT NULL,
    id_restaurant INT NOT NULL,
    description VARCHAR(100),
    price REAL,					-- 4 bytes. NULL allowed in case of free items
    available BOOLEAN NOT NULL,
    category VARCHAR(50) NOT NULL,
    photo bytea,
    photo_type VARCHAR(10),

    PRIMARY KEY (name, id_restaurant),
    FOREIGN KEY (category) REFERENCES food_delivery.category(name),
    CHECK (price IS NULL OR price >=0)
);


CREATE TABLE food_delivery.order_content(
	id_restaurant INT NOT NULL,
    id_order INT NOT NULL,
    product_name VARCHAR(50) NOT NULL,
    price REAL DEFAULT 0,
    quantity SMALLINT DEFAULT 0,

    PRIMARY KEY (id_restaurant, id_order, product_name),
    FOREIGN KEY (id_order) REFERENCES food_delivery.order(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (product_name, id_restaurant) REFERENCES food_delivery.product(name, id_restaurant) ON UPDATE NO ACTION ON DELETE NO ACTION,
    CHECK (quantity>0)
);


CREATE TABLE food_delivery.user(
);




CREATE OR REPLACE FUNCTION f_price()
RETURNS TRIGGER AS $$
BEGIN
  UPDATE food_delivery.order_content SET price= (SELECT price FROM food_delivery.product WHERE name=NEW.product_name AND id_restaurant=NEW.id_restaurant) WHERE product_name=NEW.product_name AND id_restaurant=NEW.id_restaurant;
  RETURN NULL;
END; $$ LANGUAGE 'plpgsql';

CREATE TRIGGER trg_price AFTER INSERT
ON food_delivery.order_content
FOR EACH ROW
EXECUTE PROCEDURE f_price();


COMMENT ON TRIGGER trg_price ON food_delivery.order_content IS 'Trigger that after an insert on the table order_content updates the price with the current and correct price of the related product from the product table (needed if the price passed is not coherent with the real price of the product)';
