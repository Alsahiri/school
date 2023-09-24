package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
@Tag(name = "API для работы с факультетами")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService facultyService) {
        this.service = facultyService;
    }


    @PostMapping()
    @Operation(summary = "Создание факультета")
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        Faculty addedFaculty = service.add(faculty);
        return ResponseEntity.ok(addedFaculty);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение факультета")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = service.get(id);
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    @Operation(summary = "Изменение факультета")
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = service.update(faculty);
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление факультета")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable Long id) {
        Faculty faculty = service.remove(id);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("all")
    @Operation(summary = "Получение всех факультетов")
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("find")
    @Operation(summary = "Получение факультета по цвету")
    public ResponseEntity<Collection<Faculty>> getFacultyByColororName(@RequestParam(required = false) String name,
                                                                       @RequestParam(required = false) String color) {
        return ResponseEntity.ok(service.getFacultyByColorOrName(name, color));
    }

    @GetMapping("students/{facultyId}")
    @Operation(summary = "Получение студентов факультета")
    public ResponseEntity<Collection<Student>> getFacultyStudents(@PathVariable Long facultyId) {
        List<Student> students = service.get(facultyId).getStudents();
        return ResponseEntity.ok(students);
    }
    @GetMapping("longest-name")
    @Operation(summary = "Получение самого длинного названия факультета")
    public ResponseEntity<String> getLongestName() {
        return ResponseEntity.ok(service.getLongestName());
    }
}
