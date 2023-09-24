package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Locale.filter;

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

    @Override
    public List<String> getNamesByA() {
        logger.info("Was invoked method getNameByA for students");
        return studentRepository.findAll().stream()
                .filter(s -> s.getName().startsWith("A"))
                .map(n -> n.getName())
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageAgeByStream() {
        logger.info("Was invoked method getAverageAgeByStream for students");
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0f);
    }

    @Override
    public void printStudents() {
        List<Student> students = studentRepository.findAll();

        if (students.size() >= 6) {
            students.subList(0, 2).forEach(this::printsStudentName);

            printStudents(students.subList(2, 4));
            printStudents(students.subList(4, 6));
        }
    }

    @Override
    public void printStudentsSync() {
        List<Student> students = studentRepository.findAll();

        if (students.size() >= 6) {
            students.subList(0, 2).forEach(this::printsStudentNameSync);

            printStudentsSync(students.subList(2, 4));
            printStudentsSync(students.subList(4, 6));
        }
    }

    private void printsStudentName(Student student) {
        logger.info("Student " + student.getId() + " " + student.getName());
    }
    private synchronized void printsStudentNameSync(Student student) {
        logger.info("Student " + student.getId() + " " + student.getName());
    }

    private void printStudents(List<Student> students) {
        new Thread(() -> {
            students.forEach(this::printsStudentName);
        }).start();
    }
    private void printStudentsSync(List<Student> students) {
        new Thread(() -> {
            students.forEach(this::printsStudentNameSync);
        }).start();
    }

    private void checkAge(Integer age) {
        if (age <= 10 || age >= 100) {
            logger.warn("Incorrect student age = " + age);

            throw new IncorrectArgumentException("Требуется указать корректный воззраст для поиска студента");
        }
    }
}
