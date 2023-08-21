package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
@Tag(name = "API для работы со студентами")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping()
    @Operation(summary = "Создание студента")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student addedStudent = service.add(student);
        return ResponseEntity.ok(addedStudent);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение студента")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = service.get(id);
        return ResponseEntity.ok(student);
    }

    @PutMapping
    @Operation(summary = "Изменение студента")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = service.update(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление студента")
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        Student student = service.remove(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("all")
    @Operation(summary = "Получение всех студентов")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("age")
    @Operation(summary = "Получение студентов по возрасту")
    public ResponseEntity<Collection<Student>> getFacultyByColor(@RequestParam Integer startAge,
                                                                 @RequestParam Integer endAge) {
        return ResponseEntity.ok(service.getStudentByAge(startAge, endAge));
    }

    @GetMapping("faculty/{studentId}")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long studentId) {
        Faculty faculty = service.get(studentId).getFaculty();
        return ResponseEntity.ok(faculty);
    }
}
