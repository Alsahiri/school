package ru.hogwarts.school.TestRestTemplate;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;
import static ru.hogwarts.school.TestConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplate {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testCreateStudent() {
        ResponseEntity<Student> createdStudentRs = createStudent(MOCK_STUDENT);
        assertThat(createdStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createdStudentRs.getBody().getName()).isEqualTo(MOCK_STUDENT.getName());
        assertThat(createdStudentRs.getBody().getAge()).isEqualTo(MOCK_STUDENT.getAge());
    }

    @Test
    public void testGetStudents() {
        ResponseEntity<Student> createdStudentRs = createStudent(MOCK_STUDENT);
        Student createdStudent = createdStudentRs.getBody();

        ResponseEntity<Student> studentGetRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + createdStudent.getId(), Student.class);

        Student getStudent = studentGetRs.getBody();
        assertThat(getStudent.getId()).isEqualTo(createdStudent.getId());
        assertThat(getStudent.getName()).isEqualTo(createdStudent.getName());
        assertThat(getStudent.getAge()).isEqualTo(createdStudent.getAge());


        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/student/age?statAge=10&endAge=20", String.class))
                .isNotNull();
        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/student/all", String.class))
                .isNotNull();
    }

    @Test
    public void testDeleteStudent() {
        ResponseEntity<Student> createdStudentRs = createStudent(MOCK_STUDENT);
        Student createdStudent = createdStudentRs.getBody();

        testRestTemplate.delete("http://localhost:" + port + "/student/" + createdStudent.getId(), Student.class);

        ResponseEntity<Student> studentGetRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + createdStudent.getId(), Student.class);
        Student getStudent = studentGetRs.getBody();
        assertThat(getStudent.getId()).isNull();
    }

    @Test
    public void testChangeStudent() {
        ResponseEntity<Student> createdStudentRs = createStudent(MOCK_STUDENT);

        Student createdStudent = createdStudentRs.getBody();
        createdStudent.setName(MOCK_STUDENT_NEW_NAME);

        ResponseEntity<Student> updatedStudentRs = testRestTemplate.exchange(
                "/student",
                HttpMethod.PUT,
                new HttpEntity<>(createdStudent),
                Student.class
        );
        assertThat(updatedStudentRs.getBody().getId()).isEqualTo(createdStudent.getId());
        assertThat(updatedStudentRs.getBody().getName()).isEqualTo(createdStudent.getName());
        assertThat(updatedStudentRs.getBody().getAge()).isEqualTo(createdStudent.getAge());
    }

    public ResponseEntity<Student> createStudent(Student student) {
        return testRestTemplate.postForEntity("http://localhost:" + port + "/student/", student, Student.class);
    }
}
