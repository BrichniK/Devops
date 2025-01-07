package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServicesImplTest {

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addInstructor() {
        // Arrange
        Instructor instructor = new Instructor();
        instructor.setFirstName("John");
        instructor.setLastName("Doe");
        instructor.setDateOfHire(LocalDate.of(2023, 1, 15));
        instructor.setCourses(new HashSet<>()); // Pas de cours assignés pour cet exemple

        when(instructorRepository.save(instructor)).thenReturn(instructor);

        // Act
        Instructor result = instructorServices.addInstructor(instructor);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(LocalDate.of(2023, 1, 15), result.getDateOfHire());
        assertTrue(result.getCourses().isEmpty());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void retrieveAllInstructors() {
        // Arrange
        List<Instructor> instructors = Arrays.asList(new Instructor(), new Instructor());
        when(instructorRepository.findAll()).thenReturn(instructors);

        // Act
        List<Instructor> result = instructorServices.retrieveAllInstructors();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    void updateInstructor() {
        // Arrange
        Instructor instructor = new Instructor();
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        // Act
        Instructor result = instructorServices.updateInstructor(instructor);

        // Assert
        assertNotNull(result);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void retrieveInstructor() {
        // Arrange
        Long instructorId = 1L;
        Instructor instructor = new Instructor();
        when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(instructor));

        // Act
        Instructor result = instructorServices.retrieveInstructor(instructorId);

        // Assert
        assertNotNull(result);
        verify(instructorRepository, times(1)).findById(instructorId);
    }

    @Test
    void addInstructorAndAssignToCourse() {
        // Arrange
        Long courseId = 1L;
        Course course = new Course();
        course.setNumCourse(courseId);
        course.setLevel(3);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course.setSupport(Support.SKI);
        course.setPrice(200f);
        course.setTimeSlot(2);

        Instructor instructor = new Instructor();
        instructor.setFirstName("Jane");
        instructor.setLastName("Smith");
        instructor.setDateOfHire(LocalDate.of(2023, 6, 1));

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(instructorRepository.save(any(Instructor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Instructor result = instructorServices.addInstructorAndAssignToCourse(instructor, courseId);

        // Assert
        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals(LocalDate.of(2023, 6, 1), result.getDateOfHire());
        assertEquals(1, result.getCourses().size());
        assertTrue(result.getCourses().contains(course));
        verify(courseRepository, times(1)).findById(courseId);
        verify(instructorRepository, times(1)).save(instructor);
    }
}
