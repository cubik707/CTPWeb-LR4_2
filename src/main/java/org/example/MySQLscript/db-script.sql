DROP SCHEMA IF EXISTS carshop;
CREATE SCHEMA carshop;

USE
carshop;

CREATE TABLE `companies`
(
    `company_id`      int         NOT NULL AUTO_INCREMENT primary key,
    `company_name`    varchar(45) NOT NULL,
    `company_country` varchar(45) NOT NULL
);

CREATE TABLE `cars`
(
    `car_id`           int         NOT NULL AUTO_INCREMENT primary key,
    `name`             varchar(45) NOT NULL,
    `year`             int         NOT NULL,
    `distance`         int                  DEFAULT NULL,
    `fuel`             varchar(45) NOT NULL DEFAULT 'Бензин',
    `fuel_consumption` varchar(45) NOT NULL,
    `price`            int         NOT NULL,
    `company_id`       int         NOT NULL,
    FOREIGN KEY (`company_id`) REFERENCES companies (`company_id`)
        ON delete cascade
);

CREATE TABLE `people`
(
    `person_id` int         NOT NULL primary key auto_increment,
    `surname`   varchar(45) NOT NULL,
    `name`      varchar(45) NOT NULL,
    `age`       int         NOT NULL,
    `phone`     varchar(45) NOT NULL,
    `mail`      varchar(45) NOT NULL
);

CREATE TABLE `users`
(
    `user_id`   int         NOT NULL primary key auto_increment,
    `login`     varchar(45) NOT NULL,
    `password`  varchar(45) NOT NULL,
    `role`      varchar(45) not null DEFAULT 'User',
    `person_id` int,

    constraint `fk_user_person` foreign key (`person_id`) references `people` (`person_id`)
        on delete cascade
);


-- Вставка данных в таблицу компаний
INSERT INTO companies (company_name, company_country)
VALUES ('Toyota', 'Japan'),
       ('Ford', 'USA'),
       ('BMW', 'Germany'),
       ('Hyundai', 'South Korea');

-- Вставка данных в таблицу машин
INSERT INTO cars (name, year, distance, fuel, fuel_consumption, price, company_id)
VALUES ('Camry', 2020, 15000, 'Gasoline', '7.5 L/100 km', 30000, 1),   -- Toyota
       ('Corolla', 2019, 20000, 'Gasoline', '6.5 L/100 km', 20000, 1), -- Toyota
       ('Mustang', 2021, 5000, 'Gasoline', '10.0 L/100 km', 55000, 2), -- Ford
       ('F-150', 2020, 25000, 'Diesel', '9.0 L/100 km', 40000, 2),     -- Ford
       ('3 Series', 2018, 30000, 'Gasoline', '6.8 L/100 km', 35000, 3),-- BMW
       ('5 Series', 2021, 10000, 'Diesel', '7.2 L/100 km', 45000, 3),  -- BMW
       ('Elantra', 2022, 1000, 'Gasoline', '6.0 L/100 km', 22000, 4),  -- Hyundai
       ('Santa Fe', 2019, 12000, 'Diesel', '8.5 L/100 km', 33000, 4);
-- Hyundai

-- Вставка данных в таблицу людей
INSERT INTO people (surname, name, age, phone, mail)
VALUES ('Ivanov', 'Ivan', 35, '+1234567890', 'ivanov@example.com'),
       ('Petrov', 'Petr', 40, '+0987654321', 'petrov@example.com'),
       ('Sidorov', 'Sidr', 28, '+1230984567', 'sidorov@example.com'),
       ('Kim', 'Hyun', 32, '+9876543210', 'kim@example.com');

-- Вставка данных в таблицу пользователей
INSERT INTO users (login, password, role, person_id)
VALUES ('ivan123', 'password1', 'ADMIN', 1), -- Связан с Ivanov
       ('petr456', 'password2', 'USER', 2),  -- Связан с Petrov
       ('sidr789', 'password3', 'USER', 3),  -- Связан с Sidorov
       ('kim999', 'password4', 'GUEST', 4); -- Связан с Kim