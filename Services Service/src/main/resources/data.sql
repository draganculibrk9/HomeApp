INSERT INTO ADDRESS
VALUES (1, 'Sembehun', 'Sierra Leone', 21713, 'West Loop'),
       (2, 'Salybia', 'Trinidad and Tobago', 5641, 'Dangelo Trail'),
       (3, 'Vakaga', 'Central African Republic', 8632, 'Moriah Garden'),
       (4, 'Stará Paka', 'Czech Republic', 33, 'Královéhradecký kraj'),
       (5, 'Novi Sad', 'Serbia', 13, 'Danila Kiša'),
       (6, 'Novi Sad', 'Serbia', 23, 'Trg Despota Stefana'),
       (13, 'Singapore', 'Singapore', 71, 'Jalan Lekar'),
       (14, 'Yakimovo', 'Bulgaria', 9693, 'Crist Ferry'),
       (15, 'United Arab Emirates', 'Ras al-Khaimah', 137, 'Jebel Jais'),
       (16, 'Novi Sad', 'Serbia', 63, 'Bulevar Oslobođenja');

INSERT INTO CONTACT
VALUES (17, 'catering@service.com', '+387 590046743', 'www.catering.com', 13),
       (18, 'repairs@service.com', '+375 590047743', 'www.repairs.com', 14),
       (19, 'hygiene@service.com', '+394 590866743', 'www.hygiene.com', 15),
       (20, 'catering1@service.com', '+887 595346743', 'www.catering1.com', 16);

INSERT INTO USER
VALUES (7, FALSE, 'user1@user.com', 'User1', 'User1', '$2a$10$f8ZpJpFXq8j.Jygg/s44zuA1YGIEPGpEjwnmuKAt0IAvzktJ8eFe2',
        '+381 549964367', 'USER', 1),
       (8, FALSE, 'user2@user.com', 'User2', 'User2', '$2a$10$f8ZpJpFXq8j.Jygg/s44zuA1YGIEPGpEjwnmuKAt0IAvzktJ8eFe2',
        '+381 549964167', 'USER', 2),
       (9, TRUE, 'user3@user.com', 'User3', 'User3', '$2a$10$f8ZpJpFXq8j.Jygg/s44zuA1YGIEPGpEjwnmuKAt0IAvzktJ8eFe2',
        '+381 549964361', 'USER', 3),
       (10, FALSE, 'serviceadmin1@user.com', 'ServiceAdmin1', 'ServiceAdmin1',
        '$2a$10$f8ZpJpFXq8j.Jygg/s44zuA1YGIEPGpEjwnmuKAt0IAvzktJ8eFe2',
        '+381 539964367', 'SERVICE_ADMINISTRATOR', 4),
       (11, FALSE, 'serviceadmin2@user.com', 'ServiceAdmin2', 'ServiceAdmin2',
        '$2a$10$f8ZpJpFXq8j.Jygg/s44zuA1YGIEPGpEjwnmuKAt0IAvzktJ8eFe2',
        '+381 549364367', 'SERVICE_ADMINISTRATOR', 5),
       (12, TRUE, 'serviceadmin3@user.com', 'ServiceAdmin3', 'ServiceAdmin3',
        '$2a$10$f8ZpJpFXq8j.Jygg/s44zuA1YGIEPGpEjwnmuKAt0IAvzktJ8eFe2',
        '+381 519964367', 'SERVICE_ADMINISTRATOR', 6);

INSERT INTO SERVICE
VALUES (21, 'serviceadmin1@user.com', 'Catering', 17),
       (22, 'serviceadmin1@user.com', 'Repairs', 18),
       (23, 'serviceadmin2@user.com', 'Hygiene', 19),
       (24, 'serviceadmin3@user.com', 'Catering1', 20);

INSERT INTO ACCOMMODATION
VALUES (25, TRUE, 'Bolognese', 600.0, 'CATERING'),
       (26, TRUE, 'Meatballs', 800.0, 'CATERING'),
       (27, FALSE, 'Sofa cleaning', 1300.0, 'HYGIENE'),
       (28, TRUE, 'General repairs', 2000.0, 'REPAIRS'),
       (29, TRUE, 'Cookies', 1300.0, 'CATERING');

INSERT INTO SERVICE_ACCOMMODATIONS
VALUES (21, 25),
       (21, 26),
       (22, 27),
       (23, 28),
       (24, 29);

INSERT INTO ACCOMMODATION_REQUEST
VALUES (30, DATE '2020-09-16', 13, DATE '2020-09-20', 'PENDING', 25),
       (31, DATE '2020-09-10', 13, DATE '2020-09-11', 'PENDING', 26),
       (32, DATE '2020-08-16', 14, DATE '2020-09-13', 'REJECTED', 28),
       (33, DATE '2020-09-16', 14, DATE '2020-09-20', 'ACCEPTED', 29),
       (34, DATE '2020-09-16', 15, DATE '2020-09-30', 'PENDING', 25),
       (35, DATE '2020-09-16', 13, DATE '2020-09-20', 'PENDING', 26),
       (36, DATE '2020-09-16', 13, DATE '2020-09-20', 'REJECTED', 27);

