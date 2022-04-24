insert into category (id, description) values (1000, 'Comic Books');
insert into category (id, description) values (2000, 'Movies');
insert into category (id, description) values (3000, 'Books');

insert into supplier (id, name) values (1000, 'Panini Comics');
insert into supplier (id, name) values (2000, 'Amazon');

insert into product (id, name,fk_supplier, fk_category, quantity_available, created_at) values (1000,'Crise na Pain', 1000, 1000 , 10, CURRENT_TIMESTAMP);
insert into product (id, name,fk_supplier, fk_category, quantity_available, created_at) values (2000,'Interestelar', 2000, 2000 , 5, CURRENT_TIMESTAMP);
insert into product (id, name,fk_supplier, fk_category, quantity_available, created_at) values (3000,'Harry Potter', 2000, 3000 , 15, CURRENT_TIMESTAMP);