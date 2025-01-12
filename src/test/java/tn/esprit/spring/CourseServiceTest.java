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
        // Arrange: create a course object and set the name
        Course course = new Course();
        course.setName("Math");

        // Mock the repository behavior
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act: Add the course using the service
        courseService.addCourse(course);

        // Assert: Verify the repository save method was called with the correct course
        verify(courseRepository, times(1)).save(course);

        // Assert that the name of the course is still "Math" after the method is called
        assertNotNull("Course should not be null", course);
        assertEquals("Course name should be 'Math'", "Math", course.getName());
    }
}
