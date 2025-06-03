package modele;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmesTest {

    @BeforeEach
    void setUp() {
    }

    private CarteFrance construireCarteTest() {
        CarteFrance carte = new CarteFrance();
        carte.ajouterDistance("Velizy", "Paris", 10);
        carte.ajouterDistance("Paris", "Lyon", 50);
        carte.ajouterDistance("Lyon", "Marseille", 60);
        carte.ajouterDistance("Velizy", "Marseille", 120);
        carte.ajouterDistance("Velizy", "Lyon", 70);
        carte.ajouterDistance("Lyon", "Lyon", 0);
        carte.ajouterDistance("Paris", "Marseille", 200);
        return carte;
    }

    private Scenario construireScenarioTest() {
        Membre a = new Membre("Alice", "Paris");
        Membre b = new Membre("Bob", "Lyon");
        Membre c = new Membre("Charlie", "Marseille");

        Vente v1 = new Vente(a, b); // Paris -> Lyon
        Vente v2 = new Vente(b, c); // Lyon -> Marseille

        Scenario s = new Scenario();
        s.ajouterVente(v1);
        s.ajouterVente(v2);
        return s;
    }

    @Test
    public void testAlgoTriTopologique() {
        Scenario scenario = construireScenarioTest();
        List<String> parcours = Algorithmes.algoTriTopologique(scenario);

        assertEquals("Velizy", parcours.get(0), "Le parcours doit commencer par Velizy");
        assertEquals("Velizy", parcours.get(parcours.size() - 1), "Le parcours doit finir par Velizy");
        assertTrue(parcours.indexOf("Paris") < parcours.indexOf("Lyon"), "Paris doit précéder Lyon");
        assertTrue(parcours.indexOf("Lyon") < parcours.indexOf("Marseille"), "Lyon doit précéder Marseille");
    }

    @Test
    public void testAlgoHeuristique() {
        Scenario scenario = construireScenarioTest();
        CarteFrance carte = construireCarteTest();
        ResultatParcours res = Algorithmes.algoHeuristique(scenario, carte);

        List<String> parcours = res.getParcours();
        int distance = res.getDistanceTotale();

        assertEquals("Velizy", parcours.get(0), "Le parcours doit commencer par Velizy");
        assertEquals("Velizy", parcours.get(parcours.size() - 1), "Le parcours doit finir par Velizy");
        assertTrue(parcours.indexOf("Paris") < parcours.indexOf("Lyon"), "Paris doit précéder Lyon");
        assertTrue(parcours.indexOf("Lyon") < parcours.indexOf("Marseille"), "Lyon doit précéder Marseille");
        assertTrue(distance > 0, "La distance doit être strictement positive");
    }

    @Test
    public void testAlgoKMeilleuresSolutions() {
        Scenario scenario = construireScenarioTest();
        CarteFrance carte = construireCarteTest();
        List<ResultatParcours> solutions = Algorithmes.algoKMeilleuresSolutions(scenario, carte, 2);

        assertEquals(2, solutions.size(), "Deux solutions sont attendues pour ce petit scénario");

        ResultatParcours rp = solutions.get(0);
        List<String> parcours = rp.getParcours();

        assertEquals("Velizy", parcours.get(0), "Le parcours doit commencer par Velizy");
        assertEquals("Velizy", parcours.get(parcours.size() - 1), "Le parcours doit finir par Velizy");
        assertTrue(parcours.contains("Paris"), "Le parcours doit contenir Paris");
        assertTrue(parcours.contains("Lyon"), "Le parcours doit contenir Lyon");
        assertTrue(parcours.contains("Marseille"), "Le parcours doit contenir Marseille");

        int dist = rp.getDistanceTotale();
        assertTrue(dist > 0, "La distance totale doit être strictement positive");
    }

    @AfterEach
    void tearDown() {
    }
}