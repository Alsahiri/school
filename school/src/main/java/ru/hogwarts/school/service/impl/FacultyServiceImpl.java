package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long idCount = 0;

    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculties.put(idCount++, faculty);
        return faculty;
    }

    @Override
    public Faculty getFaculty(long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        if ((!faculties.containsKey(faculty.getId()))) {
            return null;
        }
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty deleteFaculty(long id) {
        if ((!faculties.containsKey(id))) {
            return null;
        }
        return faculties.remove(id);
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        return faculties.values();
    }

    @Override
    public Collection<Faculty> getFacultyByColor(String color) {

    }


}
