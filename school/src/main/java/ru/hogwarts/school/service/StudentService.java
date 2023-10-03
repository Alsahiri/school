package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    Student add(Student student);

    Student get(Long id);

    Student update(Student student);

    Student remove(Long id);

    Collection<Student> getAll();

    Collection<Student> getStudentByAge(Integer startAge, Integer endAge);
    Integer getCount();
    Float getAverageAge();

    List<Student> getLastFive();

    List<String> getNamesByA();

    Double getAverageAgeByStream();

    void printStudents();

    void printStudentsSync();

}
