-- Возраст студента не может быть меньше 16 лет.
alter table students
  add constraint age_check check (age >= 16);

-- Имена студентов должны быть уникальными и не равны нулю.
alter table students
  add constraint name_unique unique (name);

  ALTER TABLE students
  alter column name set not null;

-- Пара “значение названия” - “цвет факультета” должна быть уникальной.
alter table faculties
  add constraint name_color_unique unique (name, color);

--При создании студента без возраста ему автоматически должно присваиваться 20 лет.
  alter table students
  alter column name set default 20;
