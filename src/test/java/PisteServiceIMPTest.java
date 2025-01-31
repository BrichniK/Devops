import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.Optional;

@ExtendWith(MockitoExtension.class) // Active Mockito avec JUnit 5
public class PisteServiceIMPTest {

    @Mock
    private IPisteRepository pisteRepository;  // Mock du repository

    @InjectMocks
    private PisteServicesImpl pisteServices;  // Injecte le mock dans le service

    private Piste piste;

    @BeforeEach
    void setUp() {
        // Initialisation de la piste pour les tests
        piste = new Piste(1L, "Piste 1", Color.RED, 500, 30, null);
    }

    /** Test de l'ajout d'une piste */
    @Test
    void testAjouterPiste() {
        when(pisteRepository.save(piste)).thenReturn(piste);

        Piste result = pisteServices.addPiste(piste);

        assertNotNull(result);
        assertEquals(piste.getNamePiste(), result.getNamePiste());
        verify(pisteRepository, times(1)).save(piste);
    }

    @Test
    void testRetrievePiste_Existant() {
        // Arrange : préparation des données
        Piste expectedPiste = new Piste(1L, "Piste 1", Color.RED, 500, 30, null);
        when(pisteRepository.findById(1L)).thenReturn(Optional.of(expectedPiste));  // Simuler une piste existante dans le repository

        // Act : appel de la méthode
        Piste result = pisteServices.retrievePiste(1L);

        // Assert : vérifier que la piste retournée est correcte
        assertNotNull(result, "La piste récupérée ne doit pas être nulle.");
        assertEquals(expectedPiste.getNumPiste(), result.getNumPiste(), "Les IDs des pistes doivent être égaux.");
        assertEquals(expectedPiste.getNamePiste(), result.getNamePiste(), "Les noms des pistes doivent être égaux.");

        // Vérification que la méthode findById a été appelée une seule fois avec l'ID 1
        verify(pisteRepository, times(1)).findById(1L);
    }



    @Test
    void testRetrievePiste_Inexistant() {
        // Arrange : préparation des données
        when(pisteRepository.findById(99L)).thenReturn(Optional.empty());  // Simuler une piste non trouvée dans le repository

        // Act : appel de la méthode
        Piste result = pisteServices.retrievePiste(99L);

        // Assert : vérifier que le résultat est nul
        assertNull(result, "La piste récupérée doit être nulle.");

        // Vérification que la méthode findById a été appelée une seule fois avec l'ID 99
        verify(pisteRepository, times(1)).findById(99L);
    }

    @Test
    void testRemovePiste() {
        // Arrange : préparation des données
        Long numPiste = 1L;

        // Act : appel de la méthode
        pisteServices.removePiste(numPiste);

        // Assert : vérifier que deleteById a été appelé avec le bon ID
        verify(pisteRepository, times(1)).deleteById(numPiste);
    }




}
