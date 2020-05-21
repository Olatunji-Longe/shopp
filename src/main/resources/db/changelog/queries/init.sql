set MODE MYSQL;

insert IGNORE into stores (name) values ('shopp');

insert IGNORE into addresses (building_no, street, city, zip_code, state, country) values ('28', 'Obasanjo Avenue', 'Victoria Island', '10001', 'Lagos', 'Nigeria');
insert IGNORE into addresses (building_no, street, city, zip_code, state, country) values ('7', 'St. Bellerin Drive', 'Lakeshore', 'V4N7G6', 'Ottawa', 'Canada');

insert IGNORE into store_addresses (store_id, address_id) values ((select id from stores where name='shopp'), (select id from addresses where zip_code='10001'));

insert IGNORE into carts (store_id) values ((select id from stores where name='shopp'));

insert IGNORE into users (first_name, last_name, email, cart_id) values ('Olatunji', 'Longe', 'olatunji@longe.com', (select ct.id from carts ct, stores st where ct.store_id=st.id and st.name='shopp'));

insert IGNORE into credentials (username, password, user_id) values ('olatunji.longe', 'secret', (select id from users where email='olatunji@longe.com'));

insert IGNORE into user_addresses (user_id, address_id) values ((select id from users where email='olatunji@longe.com'), (select id from addresses where zip_code='V4N7G6'));

insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('A-1234', 'title-1', 'author-1', 'english', 2004, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1352082529l/37781.jpg', 200.00);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('B-1234', 'title-2', 'author-2', 'french', 2001, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1520258755l/18512._SY475_.jpg', 5000.00);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('C-1234', 'title-3', 'author-3', 'yoruba', 2003, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1336967150l/18386.jpg', 1000.00);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('D-1234', 'title-4', 'author-4', 'english', 2007, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1428715580l/52036.jpg', 300.50);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('E-1234', 'title-5', 'author-1', 'yoruba', 2003, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1351051208l/1420.jpg', 450.00);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('F-1234', 'title-6', 'author-2', 'english', 2020, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1466865542l/18144590._SY475_.jpg', 200.00);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('G-1234', 'title-7', 'author-2', 'english', 2003, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1490528560l/4671._SY475_.jpg', 150.00);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('H-1234', 'title-8', 'author-4', 'french', 2018, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1572098085l/18135._SY475_.jpg', 750.00);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('I-1234', 'title-9', 'author-3', 'english', 2020, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1387151694l/17245.jpg', 350.00);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('J-1234', 'title-10', 'author-3', 'english', 2002, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1572261616l/52892857._SX318_SY475_.jpg', 60.00);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('K-1234', 'title-11', 'author-1', 'english', 2003, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1572261616l/52892857._SX318_SY475_.jpg', 34.70);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('L-1234', 'title-12', 'author-1', 'french', 2001, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1309211401l/6310.jpg', 400.00);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('M-1234', 'title-13', 'author-4', 'yoruba', 2003, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1389733983l/16322.jpg', 180.90);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('N-1234', 'title-14', 'author-3', 'english', 1996, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1507838927l/36402034._SY475_.jpg', 6000.00);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('O-1234', 'title-15', 'author-3', 'french', 1974, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1459795105l/12996._SY475_.jpg', 50.50);
insert IGNORE into books (isbn, title, author, language, year, image_url, price) values ('P-1234', 'title-16', 'author-2', 'yoruba', 1974, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1318116526l/51496.jpg', 95.00);

alter table cart_items drop constraint if exists constr_cart_items_quantity;
alter table cart_items add constraint constr_cart_items_quantity check(quantity > 0);

insert IGNORE into cart_items (book_id, quantity, cart_id, checkout_state, active) values ((select id from books where isbn='A-1234'), 3, (select ct.id from carts ct, users u where ct.id=u.cart_id and u.email='olatunji@longe.com'), 'QUEUED', true);
insert IGNORE into cart_items (book_id, quantity, cart_id, checkout_state, active) values ((select id from books where isbn='B-1234'), 1, (select ct.id from carts ct, users u where ct.id=u.cart_id and u.email='olatunji@longe.com'), 'QUEUED', true);
insert IGNORE into cart_items (book_id, quantity, cart_id, checkout_state, active) values ((select id from books where isbn='C-1234'), 2, (select ct.id from carts ct, users u where ct.id=u.cart_id and u.email='olatunji@longe.com'), 'QUEUED', true);
insert IGNORE into cart_items (book_id, quantity, cart_id, checkout_state, active) values ((select id from books where isbn='D-1234'), 4, (select ct.id from carts ct, users u where ct.id=u.cart_id and u.email='olatunji@longe.com'), 'QUEUED', true);
insert IGNORE into cart_items (book_id, quantity, cart_id, checkout_state, active) values ((select id from books where isbn='E-1234'), 3, (select ct.id from carts ct, users u where ct.id=u.cart_id and u.email='olatunji@longe.com'), 'QUEUED', true);




