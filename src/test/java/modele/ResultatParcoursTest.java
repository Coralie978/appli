package modele;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultatParcoursTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testResultatParcours() {
        List<String> villes = List.of("Velizy", "Paris", "Lyon", "Velizy");
        ResultatParcours rp = new ResultatParcours(villes, 1000);

        assertEquals(1000, rp.getDistanceTotale(), "La distance totale du parcours devrait être 1000");
        assertEquals(4, rp.getParcours().size(), "Le parcours devrait contenir 4 villes");
    }

    @AfterEach
    void tearDown() {
    }
}