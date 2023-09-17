-- liquibase formatted sql

-- changeset ysudarkin:1
create index student_name_index on students(name);

-- changeset ysudarkin:2
create index faculty_name_color_index on faculties(name, color);
