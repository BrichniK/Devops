package tn.esprit.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private CourseServicesImpl courseService;

    @Test
    public void testAddCourse() {
        // Arrange: create a course to add
        Course course = new Course("Math", "Basic", "Online", 100.0f, 10);

        // Mock the behavior of the courseRepository's save method
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act: call the method under test
        Course result = courseService.addCourse(course);

        // Assert: verify the result and interactions
        assertEquals("Math", result.getName());
        verify(courseRepository, times(1)).save(course);
    }
}
