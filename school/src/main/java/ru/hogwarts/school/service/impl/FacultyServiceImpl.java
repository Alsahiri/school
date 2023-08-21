package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


import static java.util.Arrays.stream;
import static org.springframework.util.StringUtils.*;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty get(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            return faculty.get();
        } else {
            throw new EntityFoundException("Факультет с id = " + id + "не существует");
        }
    }

    @Override
    public Faculty update(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty remove(Long id) {
        Faculty faculty = get(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getFacultyByColorOrName(String color, String name) {
        if (!hasText(color) && !hasText(name)) {
            throw new IncorrectArgumentException("Требуется указать корректный цвет для поиска факультета");
        }
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}
