package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student add(Student student) {
        logger.info("Was invoked method add for students");
        return studentRepository.save(student);
    }

    @Override
    public Student get(Long id) {
        logger.info("Was invoked method get for students");

        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            logger.error("Student not found, id = " + id);

            throw new EntityFoundException("Факультет с id = " + id + "не существует");
        }
    }

    @Override
    public Student update(Student student) {
        logger.info("Was invoked method update for students");

        return studentRepository.save(student);
    }

    @Override
    public Student remove(Long id) {
        logger.info("Was invoked method remove for students");

        Student student = get(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Collection<Student> getAll() {
        logger.info("Was invoked method getAll for students");

        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getStudentByAge(Integer startAge, Integer endAge) {
        logger.info("Was invoked method getStudentByAge for students");

        checkAge(startAge);
        checkAge(endAge);
        return studentRepository.findStudentsByAgeBetween(startAge, endAge);
    }

    @Override
    public Integer getCount() {
        logger.info("Was invoked method getCount for students");

        return studentRepository.getCount();
    }

    @Override
    public Float getAverageAge() {
        logger.info("Was invoked method getAverageAge for students");
        return studentRepository.getAverageAge();
    }

    @Override
    public List<Student> getLastFive() {
        logger.info("Was invoked method getLastFive for students");
        return studentRepository.getLastFive();
    }

    private void checkAge(Integer age) {
        if (age <= 10 || age >= 100) {
            logger.warn("Incorrect student age = " + age);

            throw new IncorrectArgumentException("Требуется указать корректный воззраст для поиска студента");
        }
    }
}
