package modele;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class CarteFranceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testAjoutEtLectureDistance() {
        CarteFrance carte = new CarteFrance();
        carte.ajouterDistance("Paris", "Lyon", 500);
        assertEquals(500, carte.getDistance("Paris", "Lyon"), "La distance Paris-Lyon devrait être 500");
        assertEquals(500, carte.getDistance("Lyon", "Paris"), "La distance Lyon-Paris devrait être symétrique");
    }

    @Test
    public void testDistanceInconnue() {
        CarteFrance carte = new CarteFrance();
        carte.ajouterDistance("Paris", "Lyon", 500);
        assertThrows(IllegalArgumentException.class,
                () -> carte.getDistance("Paris", "Marseille"),
                "Une exception aurait dû être levée pour une distance inconnue entre Paris et Marseille"
        );
    }

    @AfterEach
    void tearDown() {
    }
}