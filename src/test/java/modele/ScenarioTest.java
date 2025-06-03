package modele;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class ScenarioTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testAjoutVente() {
        Membre m1 = new Membre("Alice", "Paris");
        Membre m2 = new Membre("Bob", "Lyon");
        Vente v = new Vente(m1, m2);
        Scenario scenario = new Scenario();
        scenario.ajouterVente(v);

        assertEquals(1, scenario.getVentes().size(), "Il devrait y avoir une seule vente dans le scénario");
        assertEquals("Lyon", scenario.getVentes().get(0).getAcheteur().getVille(), "L'acheteur devrait habiter à Lyon");
    }

    @AfterEach
    void tearDown() {
    }
}