package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SkierServicesImplTest {

    @InjectMocks
    private SkierServicesImpl skierServices;

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private IPisteRepository pisteRepository;

    @Mock
    private ICourseRepository courseRepository;

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void retrieveAllSkiers() {
        List<Skier> skiers = new ArrayList<>();
        skiers.add(new Skier());
        when(skierRepository.findAll()).thenReturn(skiers);

        List<Skier> result = skierServices.retrieveAllSkiers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(skierRepository, times(1)).findAll();
    }

    @Test
    void addSkier() {
        Subscription subscription = new Subscription();
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        subscription.setStartDate(LocalDate.of(2024, 1, 1));

        Skier skier = new Skier();
        skier.setSubscription(subscription);

        when(skierRepository.save(any(Skier.class))).thenReturn(skier);

        Skier result = skierServices.addSkier(skier);

        assertNotNull(result);
        assertEquals(LocalDate.of(2025, 1, 1), result.getSubscription().getEndDate());
        verify(skierRepository, times(1)).save(skier);
    }

    @Test
    void assignSkierToSubscription() {
        Skier skier = new Skier();
        Subscription subscription = new Subscription();

        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(subscriptionRepository.findById(2L)).thenReturn(Optional.of(subscription));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);

        Skier result = skierServices.assignSkierToSubscription(1L, 2L);

        assertNotNull(result);
        assertEquals(subscription, result.getSubscription());
        verify(skierRepository, times(1)).findById(1L);
        verify(subscriptionRepository, times(1)).findById(2L);
        verify(skierRepository, times(1)).save(skier);
    }

    @Test
    void addSkierAndAssignToCourse() {
        Skier skier = new Skier();
        Course course = new Course();
        Registration registration = new Registration();
        registration.setSkier(skier);
        registration.setCourse(course);

        skier.setRegistrations(Set.of(registration));

        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        when(courseRepository.getById(1L)).thenReturn(course);

        Skier result = skierServices.addSkierAndAssignToCourse(skier, 1L);

        assertNotNull(result);
        assertEquals(course, registration.getCourse());
        verify(skierRepository, times(1)).save(skier);
        verify(courseRepository, times(1)).getById(1L);
    }

    @Test
    void removeSkier() {
        doNothing().when(skierRepository).deleteById(1L);

        skierServices.removeSkier(1L);

        verify(skierRepository, times(1)).deleteById(1L);
    }

    @Test
    void retrieveSkier() {
        Skier skier = new Skier();
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));

        Skier result = skierServices.retrieveSkier(1L);

        assertNotNull(result);
        assertEquals(skier, result);
        verify(skierRepository, times(1)).findById(1L);
    }

    @Test
    void assignSkierToPiste() {
        Skier skier = new Skier();
        Piste piste = new Piste();

        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(pisteRepository.findById(2L)).thenReturn(Optional.of(piste));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);

        Skier result = skierServices.assignSkierToPiste(1L, 2L);

        assertNotNull(result);
        assertTrue(result.getPistes().contains(piste));
        verify(skierRepository, times(1)).findById(1L);
        verify(pisteRepository, times(1)).findById(2L);
        verify(skierRepository, times(1)).save(skier);
    }

    @Test
    void retrieveSkiersBySubscriptionType() {
        List<Skier> skiers = new ArrayList<>();
        skiers.add(new Skier());
        when(skierRepository.findBySubscription_TypeSub(TypeSubscription.ANNUAL)).thenReturn(skiers);

        List<Skier> result = skierServices.retrieveSkiersBySubscriptionType(TypeSubscription.ANNUAL);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(skierRepository, times(1)).findBySubscription_TypeSub(TypeSubscription.ANNUAL);
    }
}
