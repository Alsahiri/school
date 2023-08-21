package ru.hogwarts.school.service.impl;

import ru.hogwarts.school.exception.EntityFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student add(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student get(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new EntityFoundException("Факультет с id = " + id + "не существует");
        }
    }

    @Override
    public Student update(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student remove(Long id) {
        Student student = get(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getStudentByAge(Integer startAge, Integer endAge) {
        checkAge(startAge);
        checkAge(endAge);
        return studentRepository.findStudentsByAgeBetween(startAge, endAge);
    }
    private void checkAge(Integer age) {
        if (age <= 10 || age >= 100) {
            throw new IncorrectArgumentException("Требуется указать корректный воззраст для поиска студента");
        }
    }
}
