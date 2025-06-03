package modele;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GrapheTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testTriTopologiqueSimple() {
        // Graphe: 0 -> 1 -> 2
        int[][] voisins = {
                {1},  // sommet 0
                {2},  // sommet 1
                {}    // sommet 2
        };
        Graphe g = new Graphe(voisins);
        List<Integer> tri = g.triToploogique();

        assertEquals(List.of(0, 1, 2), tri, "Le tri topologique doit être [0, 1, 2]");
    }

    @AfterEach
    void tearDown() {
    }
}