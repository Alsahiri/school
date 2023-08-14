package ru.hogwarts.school.service.impl;

import ru.hogwarts.school.exception.EntityFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private static Long idCount = 1L;

    @Override
    public Student add(Student student) {
        students.put(idCount++, student);
        return student;
    }

    @Override
    public Student get(Long id) {
        if ((!students.containsKey(id))) {
            throw new EntityFoundException("Студент с id =" + id + "не существует");
        }
        return students.get(id);
    }

    @Override
    public Student update(Student student) {
        if ((!students.containsKey(student.getId()))) {
            throw new EntityFoundException("Студент с id =" + student.getId() + "не существует");
        }
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student remove(Long id) {
        if ((!students.containsKey(id))) {
            throw new EntityFoundException("Студент с id =" + id + "не существует");
        }
        return students.remove(id);
    }

    @Override
    public Collection<Student> getAll() {
        return students.values();
    }

    @Override
    public Collection<Student> getStudentByAge(Integer age) {
        if (age <= 10 || age >= 100) {
            throw new IncorrectArgumentException("Требуется указать корректный воззраст для поиска студента");
        }
        return students.values().stream()
                .filter(s -> s.getAge().equals(age))
                .collect(Collectors.toList());
    }
}
