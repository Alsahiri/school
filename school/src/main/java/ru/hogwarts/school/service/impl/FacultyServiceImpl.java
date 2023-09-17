package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        logger.info("Was invoked method add for faculties");

        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty get(Long id) {
        logger.info("Was invoked method get for faculties");

        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            return faculty.get();
        } else {
            logger.error("Incorrect faculty id = " + id);

            throw new EntityFoundException("Факультет с id = " + id + "не существует");
        }
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Was invoked method update for faculties");

        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty remove(Long id) {
        logger.info("Was invoked method remove for faculties");

        Faculty faculty = get(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Collection<Faculty> getAll() {
        logger.info("Was invoked method getAll for faculties");

        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getFacultyByColorOrName(String name, String color) {
        logger.info("Was invoked method getFacultyByColorOrName for faculties");

        if (!hasText(color) && !hasText(name)) {
            logger.warn("Incorrect faculty color = " + color, ", or name = " + name);

            throw new IncorrectArgumentException("Требуется указать корректный цвет для поиска факультета");
        }
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}
