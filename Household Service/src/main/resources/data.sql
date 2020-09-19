INSERT INTO ADDRESS
VALUES (1, 'Sembehun', 'Sierra Leone', 21713, 'West Loop'),
       (2, 'Salybia', 'Trinidad and Tobago', 5641, 'Dangelo Trail'),
       (3, 'Vakaga', 'Central African Republic', 8632, 'Moriah Garden'),
       (4, 'Stará Paka', 'Czech Republic', 33, 'Královéhradecký kraj'),
       (5, 'Novi Sad', 'Serbia', 13, 'Danila Kiša'),
       (6, 'Novi Sad', 'Serbia', 23, 'Trg Despota Stefana');


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

INSERT INTO HOUSEHOLD
VALUES (13, 0.0, 'user1@user.com'),
       (14, 100000.0, 'user2@user.com'),
       (15, -50000.0, 'user3@user.com');

INSERT INTO TRANSACTION
VALUES ('Transaction', 16, 30000.0, DATE '2020-09-16', 'Bonus', 'INCOME', NULL, NULL),
       ('PeriodicalTransaction', 17, 30000.0, DATE '2020-09-16', 'Expenses', 'EXPENDITURE', NULL, 'MONTH'),
       ('MultipleTimesTransaction', 18, 20000.0, DATE '2020-09-16', 'Rent', 'INCOME', 5, NULL),
       ('Transaction', 19, 5000.0, DATE '2020-08-16', 'Transaction1', 'INCOME', NULL, NULL),
       ('Transaction', 20, 45000.0, DATE '2020-09-14', 'New dishwasher expense', 'EXPENDITURE', NULL, NULL);

INSERT INTO HOUSEHOLD_TRANSACTIONS
VALUES (13, 16),
       (13, 17),
       (14, 18),
       (15, 19),
       (15, 20);
