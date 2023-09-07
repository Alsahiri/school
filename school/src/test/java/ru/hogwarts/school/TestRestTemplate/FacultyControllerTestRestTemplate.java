package ru.hogwarts.school.TestRestTemplate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static ru.hogwarts.school.TestConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRestTemplate {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testCreateFaculty() {
        ResponseEntity<Faculty> createdFacultyRs = createFaculty(MOCK_FACULTY);
        assertThat(createdFacultyRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createdFacultyRs.getBody().getName()).isEqualTo(MOCK_FACULTY.getName());
        assertThat(createdFacultyRs.getBody().getColor()).isEqualTo(MOCK_FACULTY.getColor());
    }

    @Test
    public void testGetFaculty() {
        ResponseEntity<Faculty> createdFacultyRs = createFaculty(MOCK_FACULTY);
        Faculty createdFaculty = createdFacultyRs.getBody();

        ResponseEntity<Faculty> facultyGetRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + createdFaculty.getId(), Faculty.class);

        Faculty getFaculty = facultyGetRs.getBody();
        assertThat(getFaculty.getId()).isEqualTo(createdFaculty.getId());
        assertThat(getFaculty.getName()).isEqualTo(createdFaculty.getName());
        assertThat(getFaculty.getColor()).isEqualTo(createdFaculty.getColor());


        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/find?name" + MOCK_FACULTY_NAME
                + "&color" + MOCK_FACULTY_COLOR, String.class))
                .isNotNull();
        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/all", String.class))
                .isNotNull();
    }

    @Test
    public void testDeleteFaculty() {
        ResponseEntity<Faculty> createdFacultyRs = createFaculty(MOCK_FACULTY);
        Faculty createdFaculty = createdFacultyRs.getBody();

        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + createdFaculty.getId(), Faculty.class);

        ResponseEntity<Faculty> facultyGetRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + createdFaculty.getId(), Faculty.class);
        Faculty getFaculty = facultyGetRs.getBody();
        assertThat(getFaculty.getId()).isNull();
    }

    @Test
    public void testChangeFaculty() {
        ResponseEntity<Faculty> createdFacultyRs = createFaculty(MOCK_FACULTY);

        Faculty createdFaculty = createdFacultyRs.getBody();
        createdFaculty.setName(MOCK_STUDENT_NEW_NAME);

        ResponseEntity<Faculty> updatedFacultyRs = testRestTemplate.exchange(
                "/faculty",
                HttpMethod.PUT,
                new HttpEntity<>(createdFaculty),
                Faculty.class
        );
        assertThat(updatedFacultyRs.getBody().getId()).isEqualTo(createdFaculty.getId());
        assertThat(updatedFacultyRs.getBody().getName()).isEqualTo(createdFaculty.getName());
        assertThat(updatedFacultyRs.getBody().getColor()).isEqualTo(createdFaculty.getColor());
    }

    public ResponseEntity<Faculty> createFaculty(Faculty faculty) {
        return testRestTemplate.postForEntity("http://localhost:" + port + "/faculty/", faculty, Faculty.class);
    }
}
