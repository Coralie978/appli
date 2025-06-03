package modele;

import java.util.HashMap;
import java.util.Map;

public class CarteFrance {
    private Map<String, Map<String, Integer>> distances;

    public CarteFrance() {
        distances = new HashMap<>();
    }

    public void ajouterDistance(String ville1, String ville2, int distance) {
        // Ajout bidirectionnel (symétrique)
        distances.computeIfAbsent(ville1, k -> new HashMap<>()).put(ville2, distance);
        distances.computeIfAbsent(ville2, k -> new HashMap<>()).put(ville1, distance);
    }

    public int getDistance(String ville1, String ville2) {
        if (distances.containsKey(ville1) && distances.get(ville1).containsKey(ville2)) {
            return distances.get(ville1).get(ville2);
        } else {
            throw new IllegalArgumentException("Distance inconnue entre " + ville1 + " et " + ville2);
        }
    }

    public boolean contientVille(String ville) {
        return distances.containsKey(ville);
    }

    public Map<String, Integer> getVoisins(String ville) {
        return distances.getOrDefault(ville, new HashMap<>());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String ville : distances.keySet()) {
            sb.append(ville).append(" : ").append(distances.get(ville)).append("\n");
        }
        return sb.toString();
    }
}
