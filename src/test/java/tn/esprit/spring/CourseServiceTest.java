package tn.esprit.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private CourseServicesImpl courseService;

    @Test
    void testAddCourse() {
        // Arrange: mock the repository behavior
        Course course = new Course();
        course.setName("Math");
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act: Add the course using the service
        courseService.addCourse(course);

        // Assert: Verify the result
        verify(courseRepository, times(1)).save(course);
    }
}
