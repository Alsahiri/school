package ru.hogwarts.school.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


import static java.util.Arrays.stream;
import static org.springframework.util.StringUtils.*;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private static Long idCount = 1L;

    @Override
    public Faculty add(Faculty faculty) {
        faculties.put(idCount++, faculty);
        return faculty;
    }

    @Override
    public Faculty get(Long id) {
        if ((!faculties.containsKey(id))) {
            throw new EntityFoundException("Факультет с id =" + id + "не существует");
        }
        return faculties.get(id);
    }

    @Override
    public Faculty update(Faculty faculty) {
        if ((!faculties.containsKey(faculty.getId()))) {
            throw new EntityFoundException("Факультет с id =" + faculty.getId() + "не существует");
        }
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty remove(Long id) {
        if ((!faculties.containsKey(id))) {
            throw new EntityFoundException("Факультет с id =" + id + "не существует");
        }
        return faculties.remove(id);
    }

    @Override
    public Collection<Faculty> getAll() {
        return faculties.values();
    }

    @Override
    public Collection<Faculty> getFacultyByColor(String color) {
        if (!hasText(color)) {
            throw new IncorrectArgumentException("Требуется указать корректный цвет для поиска факультета");
        }
        return faculties.values().stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }


}
