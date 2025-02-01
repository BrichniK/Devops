package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationServicesImplTest {

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private ICourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void addRegistrationAndAssignToSkier() {
        // Arrange
        Registration registration = new Registration();
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(registrationRepository.save(registration)).thenReturn(registration);

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(skier, result.getSkier());
        verify(skierRepository, times(1)).findById(1L);
        verify(registrationRepository, times(1)).save(registration);
    }

    @Test
    void assignRegistrationToCourse() {
        // Arrange
        Registration registration = new Registration();
        Course course = new Course();
        course.setNumCourse(1L);
        when(registrationRepository.findById(1L)).thenReturn(Optional.of(registration));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(registrationRepository.save(registration)).thenReturn(registration);

        // Act
        Registration result = registrationServices.assignRegistrationToCourse(1L, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(course, result.getCourse());
        verify(registrationRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(1L);
        verify(registrationRepository, times(1)).save(registration);
    }

    @Test
    void addRegistrationAndAssignToSkierAndCourse() {
        // Arrange
        Registration registration = new Registration();
        registration.setNumWeek(1);

        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setDateOfBirth(LocalDate.of(2000, 1, 1));

        Course course = new Course();
        course.setNumCourse(1L);
        course.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);

        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(1, 1L, 1L)).thenReturn(0L);
        when(registrationRepository.countByCourseAndNumWeek(course, 1)).thenReturn(0L);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(skier, result.getSkier());
        assertEquals(course, result.getCourse());
        verify(skierRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(1L);
        verify(registrationRepository, times(1)).save(registration);
    }


    @Test
    void numWeeksCourseOfInstructorBySupport() {
        // Arrange
        Long instructorId = 1L;
        Support support = Support.SKI;
        List<Integer> weeks = Arrays.asList(1, 2, 3);

        when(registrationRepository.numWeeksCourseOfInstructorBySupport(instructorId, support)).thenReturn(weeks);

        // Act
        List<Integer> result = registrationServices.numWeeksCourseOfInstructorBySupport(instructorId, support);

        // Assert
        assertNotNull(result);
        assertEquals(weeks, result);
        verify(registrationRepository, times(1)).numWeeksCourseOfInstructorBySupport(instructorId, support);
    }

    @Test
    void staticTestForRegistration() {
        // Créer des objets statiques pour simuler les entités
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setFirstName("John");
        skier.setLastName("Doe");
        skier.setDateOfBirth(LocalDate.of(1990, 1, 1));
        skier.setCity("New York");

        Course course = new Course();
        course.setNumCourse(1L);
        course.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);
        course.setSupport(Support.SKI);

        // Créer une inscription statique
        Registration registration = new Registration();
        registration.setNumWeek(1);
        registration.setSkier(skier);
        registration.setCourse(course);

        // Vérifications statiques
        assertNotNull(registration);
        assertEquals(1, registration.getNumWeek());
        assertEquals("John", registration.getSkier().getFirstName());
        assertEquals("Doe", registration.getSkier().getLastName());
        assertEquals(TypeCourse.COLLECTIVE_ADULT, registration.getCourse().getTypeCourse());
        assertEquals(Support.SKI, registration.getCourse().getSupport());
    }



}
