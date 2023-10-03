create table car
(
  id SERIAL PRIMARY KEY,
  brand text not null
  model text not null,
  price integer not null
);

insert into car(brand, model, price) VALUES ('BMW', 'M5', 5000);
insert into car(brand, model, price) VALUES ('Mercedes', 'AMG', 4000);
insert into car(brand, model, price) VALUES ('Honda', 'CRV', 1000);

create table person
(
id SERIAL PRIMARY KEY,
  name text not null
  age integer not null,
  driver_license boolean,
  car_id integer references car (id)
);

insert into person(name, age, driver_license, car_id)
values ('Ivan Ivanov', 25, true, 1),
       ('Petr Petrov', 20, true, 2),
       ('Semen Semenov', 30, true, 3),
       ('Anton Antonov', 45, false, 3);
