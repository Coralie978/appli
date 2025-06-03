package modele;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VenteTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testAjoutVente() {
        Membre vendeur = new Membre("Alice", "Paris");
        Membre acheteur = new Membre("Bob", "Lyon");
        Vente vente = new Vente(vendeur, acheteur);
        Scenario scenario = new Scenario();
        scenario.ajouterVente(vente);

        assertEquals(1, scenario.getVentes().size(), "Le scénario devrait contenir une vente");
        assertEquals("Lyon", scenario.getVentes().get(0).getAcheteur().getVille(), "L'acheteur devrait habiter Lyon");
    }

    @AfterEach
    void tearDown() {
    }
}