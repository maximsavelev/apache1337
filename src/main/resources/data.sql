INSERT INTO companies (id, company_name, address, phone)
VALUES (1, 'ТСЖ', 'Чикаго', 222333);

INSERT INTO addresses (id, city, street, house, building, apartment)
VALUES (default, 'Самара', 'улица Мичурина', '56', 'A', '11'),
       (default, 'Самара', 'улица Пушкина', '1', null, '21');

INSERT INTO users (id, phone, email, first_name, second_name, patronymic, address_id, company_id)
VALUES (default, 79999999999, 'arturp@mail.ru', 'Артур', 'Пирожков', 'Петрович', 1, 1),
       (default, 79222222222, 'ivanivanov@gmail.com', 'Иван', 'Иванов', 'Иванович', 2, 1);

INSERT INTO user_roles (user_role, user_id)
VALUES ('USER', 1),
       ('USER', 2);
       
INSERT INTO services (id, service_name)
VALUES (default, 'Электричество'),
       (default, 'Холодная вода'),
       (default, 'Горячая вода'),
       (default, 'Газ');

INSERT INTO meters (id, meter_name, user_id, service_id)
VALUES (default, 'газ.юз2', 1, 4),
       (default, 'х.вод.юз2', 1, 2),
       (default, 'эл.энерг.', 1, 1),
       (default, 'х.вод.юз3', 2, 2);

INSERT INTO meter_reading (id, meter_id, meter_reading_date, meter_reading_value)
VALUES (default, 1, '2023-02-28 17:30:45.123456', 500),
       (default, 2, '2023-02-28 17:30:45.123456', 600),
       (default, 3, '2021-02-28 17:30:45.123456', 1000),
       (default, 4, '2021-02-06 17:30:45.123456', 800);

INSERT INTO requests (id, title, request_date, address, comment, status, client_id)
VALUES (default, 'Подключение счетчиков', '2021-10-15 12:22:17.000000', 1, 'Просьба, подключить по данному адресу счетчики', 3, 1);


