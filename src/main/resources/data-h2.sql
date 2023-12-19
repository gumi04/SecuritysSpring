-- CREACIÓN DE MODULOS
INSERT INTO module (name, base_path) VALUES ('PRODUCT', '/products');
INSERT INTO module (name, base_path) VALUES ('CATEGORY', '/categories');
INSERT INTO module (name, base_path) VALUES ('CUSTOMER', '/customers');
INSERT INTO module (name, base_path) VALUES ('AUTH', '/auth');
INSERT INTO module (name, base_path) VALUES ('PERMISSION', '/permissions');


-- CREACIÓN DE OPERACIONES
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PRODUCTS','', 'GET', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_PRODUCT','/[0-9]*', 'GET', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_PRODUCT','', 'POST', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_PRODUCT','/[0-9]*', 'PUT', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DISABLE_ONE_PRODUCT','/[0-9]*/disabled', 'PUT', false, 1);

INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_CATEGORIES','', 'GET', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_CATEGORY','/[0-9]*', 'GET', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_CATEGORY','', 'POST', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_CATEGORY','/[0-9]*', 'PUT', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DISABLE_ONE_CATEGORY','/[0-9]*/disabled', 'PUT', false, 2);

INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_CUSTOMERS','', 'GET', false, 3);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('REGISTER_ONE','', 'POST', true, 3);

INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('AUTHENTICATE','/authenticate', 'POST', true, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('VALIDATE-TOKEN','/validate', 'GET', true, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_MY_PROFILE','/profile','GET', false, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('LOGOUT','/logout','POST', true, 4);

INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PERMISSIONS','','GET', false, 5);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_PERMISSION','/[0-9]*','GET', false, 5);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_PERMISSION','','POST', false, 5);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DELETE_ONE_PERMISSION','/[0-9]*','DELETE', false, 5);



-- CREACIÓN DE ROLES
INSERT INTO role (name) VALUES ('CUSTOMER');
INSERT INTO role (name) VALUES ('ASSISTANT_ADMINISTRATOR');
INSERT INTO role (name) VALUES ('ADMINISTRATOR');

-- CREACIÓN DE PERMISOS
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 15);

INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 1);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 2);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 4);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 6);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 7);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 9);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 15);

INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 1);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 2);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 3);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 4);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 5);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 6);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 7);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 8);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 9);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 10);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 15);

INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 17);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 18);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 19);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 20);


INSERT INTO "user" (username, name, password, role_id) VALUES ('nami', 'luis márquez', '$2a$10$9TU/nhqwqqNDfRx8Eg2Wvu9i/yd7UimLaLYigHaad8R/qLj7j4H72',1);
INSERT INTO "user" (username, name, password, role_id) VALUES ('robin', 'fulano pérez', '$2a$10$7Qsr4MRDTq4e.9ZjXgOvCu/0clEKlTGwtVlRMgi.wtB0AWiDISEx.',2);
INSERT INTO "user" (username, name, password, role_id) VALUES ('zoro', 'zoro', '$2a$10$Eb11qu4GEU3dVJmUtnfaVOuK7f8aaOYWNdNlxWGSC2VCR87ckD8nC',3);
INSERT INTO "user" (username, name, password, role_id) VALUES ('admin', 'admin', '$2a$10$Eb11qu4GEU3dVJmUtnfaVOuK7f8aaOYWNdNlxWGSC2VCR87ckD8nC',3);

insert into category (name, status) values ('Alcentra Capital Corp.','ENABLED');
insert into category (name, status) values ('RiverNorth Opportunities Fund, Inc.','ENABLED');
insert into category (name, status) values ('Ligand Pharmaceuticals Incorporated','ENABLED');
insert into category (name, status) values ('Uranium Resources, Inc.','ENABLED');
insert into category (name, status) values ('SodaStream International Ltd.','ENABLED');


insert into product (name, price, status, category_id) values ('Pepperoni Slices', 435.8, 'ENABLED', 5);
insert into product (name, price, status, category_id) values ('Shallots', 451.93, 'ENABLED', 1);
insert into product (name, price, status, category_id) values ('Ice Cream - Super Sandwich', 235.34, 'ENABLED', 2);
insert into product (name, price, status, category_id) values ('Wine - Fume Blanc Fetzer', 240.63, 'ENABLED', 2);
insert into product (name, price, status, category_id) values ('Coffee Swiss Choc Almond', 204.0, 'ENABLED', 5);
insert into product (name, price, status, category_id) values ('Wine - Beaujolais Villages', 348.78, 'ENABLED', 4);
insert into product (name, price, status, category_id) values ('Milk - Skim', 289.95, 'ENABLED', 2);
insert into product (name, price, status, category_id) values ('Swordfish Loin Portions', 309.16, 'ENABLED', 3);
insert into product (name, price, status, category_id) values ('Beer - Mill St Organic', 245.41, 'ENABLED', 3);
insert into product (name, price, status, category_id) values ('Octopus - Baby, Cleaned', 462.55, 'ENABLED', 3);
insert into product (name, price, status, category_id) values ('Grenadine', 466.05, 'ENABLED', 1);
insert into product (name, price, status, category_id) values ('Eggplant - Baby', 140.74, 'ENABLED', 2);
insert into product (name, price, status, category_id) values ('Wine - Alsace Gewurztraminer', 158.07, 'ENABLED', 3);
insert into product (name, price, status, category_id) values ('Water - Spring 1.5lit', 100.29, 'ENABLED', 1);
insert into product (name, price, status, category_id) values ('Cherries - Fresh', 196.74, 'ENABLED', 4);
insert into product (name, price, status, category_id) values ('Soup - Campbells Asian Noodle', 258.09, 'ENABLED', 1);
insert into product (name, price, status, category_id) values ('Salt - Table', 231.86, 'ENABLED', 2);
insert into product (name, price, status, category_id) values ('Grapes - Black', 207.73, 'ENABLED', 4);
insert into product (name, price, status, category_id) values ('Goat - Whole Cut', 450.62, 'ENABLED', 5);
insert into product (name, price, status, category_id) values ('Venison - Liver', 352.68, 'ENABLED', 1);
insert into product (name, price, status, category_id) values ('Veal - Inside', 157.96, 'ENABLED', 2);
insert into product (name, price, status, category_id) values ('Wine - Placido Pinot Grigo', 420.56, 'ENABLED', 1);
insert into product (name, price, status, category_id) values ('Wine - Fume Blanc Fetzer', 370.72, 'ENABLED', 4);
insert into product (name, price, status, category_id) values ('Cafe Royale', 110.45, 'ENABLED', 4);
insert into product (name, price, status, category_id) values ('Lettuce - Lolla Rosa', 231.34, 'ENABLED', 5);
insert into product (name, price, status, category_id) values ('Wine - White, French Cross', 208.58, 'ENABLED', 1);
insert into product (name, price, status, category_id) values ('Foil Wrap', 477.38, 'ENABLED', 5);
insert into product (name, price, status, category_id) values ('Amarula Cream', 324.82, 'ENABLED', 3);
insert into product (name, price, status, category_id) values ('Juice - V8 Splash', 126.35, 'ENABLED', 2);
insert into product (name, price, status, category_id) values ('Scrubbie - Scotchbrite Hand Pad', 362.03, 'ENABLED', 2);
insert into product (name, price, status, category_id) values ('Trueblue - Blueberry', 342.95, 'ENABLED', 1);
insert into product (name, price, status, category_id) values ('Fork - Plastic', 117.8, 'ENABLED', 3);
insert into product (name, price, status, category_id) values ('Onion - Dried', 392.02, 'ENABLED', 5);
insert into product (name, price, status, category_id) values ('Beef - Bresaola', 117.49, 'ENABLED', 1);
insert into product (name, price, status, category_id) values ('Brandy - Bar', 416.86, 'ENABLED', 3);
insert into product (name, price, status, category_id) values ('Basil - Seedlings Cookstown', 384.61, 'ENABLED', 3);
insert into product (name, price, status, category_id) values ('Lettuce - Radicchio', 122.38, 'ENABLED', 2);
insert into product (name, price, status, category_id) values ('Tomato - Peeled Italian Canned', 394.04, 'ENABLED', 5);
insert into product (name, price, status, category_id) values ('Chicken - Whole Roasting', 366.51, 'ENABLED', 1);
insert into product (name, price, status, category_id) values ('Onions - Green', 343.27, 'ENABLED', 2);
insert into product (name, price, status, category_id) values ('Pork - Inside', 467.13, 'ENABLED', 4);
insert into product (name, price, status, category_id) values ('Ecolab Crystal Fusion', 136.57, 'ENABLED', 5);
insert into product (name, price, status, category_id) values ('Wine - Blue Nun Qualitatswein', 140.93, 'ENABLED', 5);
insert into product (name, price, status, category_id) values ('Soup - Campbells, Cream Of', 284.24, 'ENABLED', 5);
insert into product (name, price, status, category_id) values ('Dill Weed - Fresh', 321.67, 'ENABLED', 3);
insert into product (name, price, status, category_id) values ('Pears - Bartlett', 365.77, 'ENABLED', 5);
insert into product (name, price, status, category_id) values ('Bread - 10 Grain', 431.24, 'ENABLED', 2);
insert into product (name, price, status, category_id) values ('Table Cloth 120 Round White', 146.35, 'ENABLED', 4);
insert into product (name, price, status, category_id) values ('Pasta - Cheese / Spinach Bauletti', 189.38, 'ENABLED', 3);
insert into product (name, price, status, category_id) values ('Lettuce - Frisee', 227.64, 'ENABLED', 3);