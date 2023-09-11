-- получить информацию обо всех студентах (достаточно получить только имя и возраст студента) школы Хогвартс вместе с названиями факультетов.
select s.name, s.age, f.name
from students s
join faculties f on s.facilty_id = f.id;



-- получить только тех студентов, у которых есть аватарки.
select s.name, a.file_path
from students s
join avatar a on s.id = a.student_is;