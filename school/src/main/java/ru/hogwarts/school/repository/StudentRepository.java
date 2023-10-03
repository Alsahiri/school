package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentsByAgeBetween(Integer startAge, Integer endAge);

    @Query(nativeQuery = true, value = "selest count(*) from students")
    Integer getCount();
    @Query(nativeQuery = true, value = "selest avg(age) from students")
    Float getAverageAge();

    @Query(nativeQuery = true, value = "selest * from students order by id desc limit 5")
    List<Student> getLastFive();
}
