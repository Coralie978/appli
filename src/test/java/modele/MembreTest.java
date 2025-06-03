package modele;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MembreTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testGetVilleEtPseudo() {
        Membre membre = new Membre("Alice", "Paris");
        assertEquals("Alice", membre.getPseudo(), "Le pseudo doit être Alice");
        assertEquals("Paris", membre.getVille(), "La ville doit être Paris");
    }

    @AfterEach
    void tearDown() {
    }
}