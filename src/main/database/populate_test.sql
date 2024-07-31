DELETE FROM food_delivery.admin WHERE true;
DELETE FROM food_delivery.user WHERE true;
DELETE FROM food_delivery.restaurant WHERE true;
DELETE FROM food_delivery.order_content WHERE true;
DELETE FROM food_delivery.product WHERE true;
DELETE FROM food_delivery.order WHERE true;
DELETE FROM food_delivery.category WHERE true;


INSERT INTO food_delivery.restaurant(id,name,description,city,address) VALUES(1,'gialloarancio','test','abano terme','Via diaz 116');
INSERT INTO food_delivery.admin(username,password,id_restaurant) VALUES('admingiallo','gialloarancio',1);

INSERT INTO food_delivery.category(name) VALUES ('antipasti');
INSERT INTO food_delivery.category(name) VALUES ('primi piatti');
INSERT INTO food_delivery.category(name) VALUES ('carne');
INSERT INTO food_delivery.category(name) VALUES ('pesce');
INSERT INTO food_delivery.category(name) VALUES ('contorni');
INSERT INTO food_delivery.category(name) VALUES ('bevande');
INSERT INTO food_delivery.category(name) VALUES ('dessert');

INSERT INTO food_delivery.product(name,id_restaurant,description,price,available,category,photo,photo_type) VALUES ('pasta pomodoro',1,'pasta al pomodoro',6,true,'primi piatti',NULL,NULL);
INSERT INTO food_delivery.product(name,id_restaurant,description,price,available,category,photo,photo_type) VALUES ('gnocchi pomodoro',1,'gnocchi al pomodoro',6,true,'primi piatti',NULL,NULL);
INSERT INTO food_delivery.product(name,id_restaurant,description,price,available,category,photo,photo_type) VALUES ('pasta ragu',1,'pasta al ragu',6,true,'primi piatti',NULL,NULL);
INSERT INTO food_delivery.product(name,id_restaurant,description,price,available,category,photo,photo_type) VALUES ('gnocchi ragu',1,'gnocchi al ragu',6,false,'primi piatti',NULL,NULL);
INSERT INTO food_delivery.product(name,id_restaurant,description,price,available,category,photo,photo_type) VALUES ('summer dish',1,'tomato, mozzarella and prosciutto',5,false,'antipasti',NULL,NULL);
INSERT INTO food_delivery.product(name,id_restaurant,description,price,available,category,photo,photo_type) VALUES ('sagra dish',1,'ribs, pork belly, sausages and fries',10,true,'carne',NULL,NULL);
INSERT INTO food_delivery.product(name,id_restaurant,description,price,available,category,photo,photo_type) VALUES ('ribs',1,'3 ribs with fries',7,true,'carne',NULL,NULL);
INSERT INTO food_delivery.product(name,id_restaurant,description,price,available,category,photo,photo_type) VALUES ('water',1,'water',1.10,false,'bevande',NULL,NULL);
INSERT INTO food_delivery.product(name,id_restaurant,description,price,available,category,photo,photo_type) VALUES ('fries',1,'fries',2.50,true,'contorni',NULL,NULL);


INSERT INTO food_delivery.order(client_name, client_surname, phone_number, email, id_admin ,order_date, collection_date, silverware, box_type) VALUES ('matteo','carpentieri','3457765884','marco@gmail.com',1,CURRENT_DATE, CURRENT_DATE, FALSE,1);



