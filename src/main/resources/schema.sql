DROP TABLE IF EXISTS meter_reading;
DROP TABLE IF EXISTS client_company;
DROP TABLE IF EXISTS requests;
DROP TABLE IF EXISTS request_statuses;
DROP TABLE IF EXISTS meters;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS addresses CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS companies;


CREATE TABLE companies
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    company_name VARCHAR(50) NOT NULL,
    address      TEXT        NOT NULL,
    phone        VARCHAR(11) NOT NULL
);

CREATE TABLE addresses
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    city      VARCHAR NOT NULL,
    street    VARCHAR NOT NULL,
    house     VARCHAR NOT NULL,
    building  VARCHAR,
    apartment VARCHAR NOT NULL
);

CREATE TABLE users
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    phone       VARCHAR(11) NOT NULL,
    email       VARCHAR     NOT NULL,
    first_name  TEXT        NOT NULL,
    second_name TEXT        NOT NULL,
    patronymic  TEXT        NOT NULL,
    address_id  INTEGER,
    company_id  INTEGER,
    FOREIGN KEY (company_id) REFERENCES companies (id),
    FOREIGN KEY (address_id) REFERENCES addresses (id),
    CONSTRAINT user_phone UNIQUE (phone)
);

CREATE TABLE user_roles
(
    user_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_role VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, user_role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE services
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    service_name VARCHAR NOT NULL
);

CREATE TABLE meters
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    meter_name VARCHAR NOT NULL,
    user_id    INTEGER,
    service_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services (id) ON DELETE CASCADE
);

CREATE TABLE meter_reading
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    meter_id            INTEGER   NOT NULL,
    meter_reading_date  TIMESTAMP NOT NULL,
    meter_reading_value INTEGER   NOT NULL,
    FOREIGN KEY (meter_id) REFERENCES meters (id) ON DELETE CASCADE
);



CREATE TABLE requests
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    title        VARCHAR(50) NOT NULL,
    request_date TIMESTAMP   NOT NULL,
    address      INTEGER     NOT NULL,
    comment      TEXT,
    status       INTEGER     NOT NULL,
    client_id    INTEGER     NOT NULL,
    file         VARCHAR(50),
    FOREIGN KEY (address) REFERENCES addresses (id),
    FOREIGN KEY (client_id) REFERENCES users (id) ON DELETE CASCADE
);

