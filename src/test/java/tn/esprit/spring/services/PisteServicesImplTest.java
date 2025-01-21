package tn.esprit.spring.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PisteServicesImplTest {

    @Mock
    private IPisteRepository pisteRepository;

    @InjectMocks
    private PisteServicesImpl pisteServices;

    @Test
    void retrieveAllPistes_WhenRepositoryHasData_ShouldReturnList() {
        // Arrange
        Piste mockPiste = new Piste();
        List<Piste> expected = Collections.singletonList(mockPiste);
        when(pisteRepository.findAll()).thenReturn(expected);

        // Act
        List<Piste> result = pisteServices.retrieveAllPistes();

        // Assert
        assertFalse(result.isEmpty(), "La liste ne devrait pas être vide");
        assertSame(expected, result, "Doit retourner la liste du repository");
        verify(pisteRepository, times(1)).findAll();
    }

    @Test
    void addPiste_ShouldReturnSavedPiste() {
        // Arrange
        Piste mockPiste = new Piste();
        when(pisteRepository.save(any(Piste.class))).thenReturn(mockPiste);

        // Act
        Piste result = pisteServices.addPiste(mockPiste);

        // Assert
        assertNotNull(result, "La piste sauvegardée ne devrait pas être null");
        assertSame(mockPiste, result, "Doit retourner la même piste envoyée au repository");
        verify(pisteRepository, times(1)).save(mockPiste);
    }
    @Test
    void removePiste_ShouldCallDeleteMethod() {
        // Arrange
        Long pisteId = 1L;
        doNothing().when(pisteRepository).deleteById(pisteId);

        // Act
        pisteServices.removePiste(pisteId);

        // Assert
        verify(pisteRepository, times(1)).deleteById(pisteId);
        verifyNoMoreInteractions(pisteRepository);
    }

    @Test
    void retrievePiste_WhenExists_ShouldReturnPiste() {
        // Arrange
        Long pisteId = 1L;
        Piste expectedPiste = new Piste();
        when(pisteRepository.findById(pisteId)).thenReturn(Optional.of(expectedPiste));

        // Act
        Piste result = pisteServices.retrievePiste(pisteId);

        // Assert
        assertNotNull(result);
        assertSame(expectedPiste, result);
        verify(pisteRepository, times(1)).findById(pisteId);
    }

    @Test
    void retrievePiste_WhenNotExists_ShouldReturnNull() {
        // Arrange
        Long pisteId = 99L;
        when(pisteRepository.findById(pisteId)).thenReturn(Optional.empty());

        // Act
        Piste result = pisteServices.retrievePiste(pisteId);

        // Assert
        assertNull(result);
        verify(pisteRepository, times(1)).findById(pisteId);
    }
}