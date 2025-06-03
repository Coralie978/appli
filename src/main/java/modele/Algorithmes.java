package modele;

import java.util.*;

public class Algorithmes {

    /**
     * Algorithme basé sur un tri topologique :
     * Respecte toutes les contraintes vendeur → acheteur
     * Calcule un ordre valide des visites en construisant un graphe d'antériorité
     */
    public static List<String> algoTriTopologique(Scenario scenario) {
        Set<String> villes = new HashSet<>();
        List<Vente> ventes = scenario.getVentes();

        // 1. Associer chaque "ville+" et "ville-" à un entier
        Map<String, Integer> sommetVersIndice = new HashMap<>();
        Map<Integer, String> indiceVersSommet = new HashMap<>();
        int index = 0;
        for (Vente v : ventes) {
            String vPlus = v.getVendeur().getVille() + "+";
            String vMoins = v.getAcheteur().getVille() + "-";
            villes.add(v.getVendeur().getVille());
            villes.add(v.getAcheteur().getVille());
            if (!sommetVersIndice.containsKey(vPlus)) {
                sommetVersIndice.put(vPlus, index);
                indiceVersSommet.put(index++, vPlus);
            }
            if (!sommetVersIndice.containsKey(vMoins)) {
                sommetVersIndice.put(vMoins, index);
                indiceVersSommet.put(index++, vMoins);
            }
        }

        // 2. Création du graphe orienté
        List<Integer>[] voisins = new ArrayList[index];
        for (int i = 0; i < index; i++) voisins[i] = new ArrayList<>();

        for (Vente v : ventes) {
            int from = sommetVersIndice.get(v.getVendeur().getVille() + "+");
            int to = sommetVersIndice.get(v.getAcheteur().getVille() + "-");
            voisins[from].add(to);
        }

        // 3. Ajouter les arcs internes ville+ → ville- si les deux existent
        for (String ville : villes) {
            String plus = ville + "+";
            String moins = ville + "-";
            if (sommetVersIndice.containsKey(plus) && sommetVersIndice.containsKey(moins)) {
                int a = sommetVersIndice.get(plus);
                int b = sommetVersIndice.get(moins);
                voisins[a].add(b);
            }
        }

        // 4. Création et tri topologique
        int[][] grapheVoisins = new int[index][];
        for (int i = 0; i < index; i++) {
            grapheVoisins[i] = voisins[i].stream().mapToInt(Integer::intValue).toArray();
        }

        Graphe g = new Graphe(grapheVoisins);
        List<Integer> ordre = g.triToploogique();

        // 5. Extraction de l’ordre des villes avec répétition si nécessaire
        List<String> parcours = new ArrayList<>();
        parcours.add("Velizy");
        for (int i : ordre) {
            String sommet = indiceVersSommet.get(i);
            String ville = sommet.substring(0, sommet.length() - 1);
            parcours.add(ville);
        }
        parcours.add("Velizy");

        return parcours;
    }
    /**
     * Algorithme basé sur un tri topologique amélioré avec heuristique :
     * Parmi les sommets accessibles, choisit celui dont la ville est la plus proche du point actuel
     */
    public static ResultatParcours algoHeuristique(Scenario scenario, CarteFrance carte) {
        Set<String> villes = new HashSet<>();
        List<Vente> ventes = scenario.getVentes();

        // 1. Associer chaque "ville+" et "ville-" à un entier
        Map<String, Integer> sommetVersIndice = new HashMap<>();
        Map<Integer, String> indiceVersSommet = new HashMap<>();
        int index = 0;
        for (Vente v : ventes) {
            String vPlus = v.getVendeur().getVille() + "+";
            String vMoins = v.getAcheteur().getVille() + "-";
            villes.add(v.getVendeur().getVille());
            villes.add(v.getAcheteur().getVille());
            if (!sommetVersIndice.containsKey(vPlus)) {
                sommetVersIndice.put(vPlus, index);
                indiceVersSommet.put(index++, vPlus);
            }
            if (!sommetVersIndice.containsKey(vMoins)) {
                sommetVersIndice.put(vMoins, index);
                indiceVersSommet.put(index++, vMoins);
            }
        }

        // 2. Création du graphe orienté
        List<Integer>[] voisins = new ArrayList[index];
        for (int i = 0; i < index; i++) voisins[i] = new ArrayList<>();

        for (Vente v : ventes) {
            int from = sommetVersIndice.get(v.getVendeur().getVille() + "+");
            int to = sommetVersIndice.get(v.getAcheteur().getVille() + "-");
            voisins[from].add(to);
        }

        // 3. Ajouter les arcs internes ville+ → ville- si les deux existent
        for (String ville : villes) {
            String plus = ville + "+";
            String moins = ville + "-";
            if (sommetVersIndice.containsKey(plus) && sommetVersIndice.containsKey(moins)) {
                int a = sommetVersIndice.get(plus);
                int b = sommetVersIndice.get(moins);
                voisins[a].add(b);
            }
        }

        // 4. Initialisation des degrés entrants
        int[] degresEntrants = new int[index];
        for (int i = 0; i < index; i++) {
            for (int voisin : voisins[i]) {
                degresEntrants[voisin]++;
            }
        }

        // 5. Tri topologique avec heuristique de distance
        List<String> parcours = new ArrayList<>();
        parcours.add("Velizy");
        String villeActuelle = "Velizy";

        Set<Integer> disponibles = new HashSet<>();
        for (int i = 0; i < index; i++) {
            if (degresEntrants[i] == 0) disponibles.add(i);
        }

        while (!disponibles.isEmpty()) {
            // Choisir le sommet avec ville la plus proche
            int suivant = -1;
            int minDistance = Integer.MAX_VALUE;
            for (int s : disponibles) {
                String ville = indiceVersSommet.get(s);
                ville = ville.substring(0, ville.length() - 1);
                int dist = carte.getDistance(villeActuelle, ville);
                if (dist < minDistance) {
                    minDistance = dist;
                    suivant = s;
                }
            }

            // Ajouter au parcours seulement si ce n'est pas la même ville que la dernière
            String sommet = indiceVersSommet.get(suivant);
            String ville = sommet.substring(0, sommet.length() - 1);
            if (!parcours.get(parcours.size() - 1).equals(ville)) {
                parcours.add(ville);
            }
            villeActuelle = ville;
            disponibles.remove(suivant);

            for (int voisin : voisins[suivant]) {
                degresEntrants[voisin]--;
                if (degresEntrants[voisin] == 0) disponibles.add(voisin);
            }
        }

        parcours.add("Velizy");
        int distanceTotale = calculDistance(parcours, carte);
        return new ResultatParcours(parcours, distanceTotale);
    }

    public static List<ResultatParcours> algoKMeilleuresSolutions(Scenario scenario, CarteFrance carte, int k) {
        Set<String> villes = new HashSet<>();
        List<Vente> ventes = scenario.getVentes();

        Map<String, Integer> sommetVersIndice = new HashMap<>();
        Map<Integer, String> indiceVersSommet = new HashMap<>();
        int index = 0;
        for (Vente v : ventes) {
            String vPlus = v.getVendeur().getVille() + "+";
            String vMoins = v.getAcheteur().getVille() + "-";
            villes.add(v.getVendeur().getVille());
            villes.add(v.getAcheteur().getVille());
            if (!sommetVersIndice.containsKey(vPlus)) {
                sommetVersIndice.put(vPlus, index);
                indiceVersSommet.put(index++, vPlus);
            }
            if (!sommetVersIndice.containsKey(vMoins)) {
                sommetVersIndice.put(vMoins, index);
                indiceVersSommet.put(index++, vMoins);
            }
        }

        List<Integer>[] voisins = new ArrayList[index];
        for (int i = 0; i < index; i++) voisins[i] = new ArrayList<>();
        for (Vente v : ventes) {
            int from = sommetVersIndice.get(v.getVendeur().getVille() + "+");
            int to = sommetVersIndice.get(v.getAcheteur().getVille() + "-");
            voisins[from].add(to);
        }
        for (String ville : villes) {
            String plus = ville + "+";
            String moins = ville + "-";
            if (sommetVersIndice.containsKey(plus) && sommetVersIndice.containsKey(moins)) {
                int a = sommetVersIndice.get(plus);
                int b = sommetVersIndice.get(moins);
                voisins[a].add(b);
            }
        }

        int[] degresEntrantsInit = new int[index];
        for (int i = 0; i < index; i++) {
            for (int voisin : voisins[i]) {
                degresEntrantsInit[voisin]++;
            }
        }

        class Etat implements Comparable<Etat> {
            List<Integer> parcoursSommets;
            int[] degresEntrants;
            String villeActuelle;
            int distanceCumulee;

            Etat(List<Integer> p, int[] degs, String ville, int dist) {
                parcoursSommets = p;
                degresEntrants = degs;
                villeActuelle = ville;
                distanceCumulee = dist;
            }

            @Override
            public int compareTo(Etat other) {
                return Integer.compare(this.distanceCumulee, other.distanceCumulee);
            }
        }

        PriorityQueue<Etat> pq = new PriorityQueue<>();
        Set<String> dejaVus = new HashSet<>();

        Etat etatInitial = new Etat(new ArrayList<>(), degresEntrantsInit.clone(), "Velizy", 0);
        pq.add(etatInitial);

        List<ResultatParcours> resultats = new ArrayList<>();
        final int LIMITE_ETATS = 20000;
        final int MAX_SUIVANTS = 5;

        while (!pq.isEmpty() && resultats.size() < k) {
            Etat courant = pq.poll();

            boolean tousVisites = courant.parcoursSommets.size() == index;
            if (tousVisites) {
                List<String> parcoursVilles = new ArrayList<>();
                parcoursVilles.add("Velizy");
                String villePrec = "Velizy";
                for (int s : courant.parcoursSommets) {
                    String sommet = indiceVersSommet.get(s);
                    String ville = sommet.substring(0, sommet.length() - 1);
                    if (!ville.equals(villePrec)) {
                        parcoursVilles.add(ville);
                        villePrec = ville;
                    }
                }
                parcoursVilles.add("Velizy");
                int distTot = calculDistance(parcoursVilles, carte);
                resultats.add(new ResultatParcours(parcoursVilles, distTot));
                continue;
            }

            Set<Integer> disponibles = new HashSet<>();
            for (int i = 0; i < index; i++) {
                if (courant.degresEntrants[i] == 0 && !courant.parcoursSommets.contains(i)) {
                    disponibles.add(i);
                }
            }

            List<Integer> meilleursSuivants = disponibles.stream()
                    .sorted(Comparator.comparingInt(s -> carte.getDistance(
                            courant.villeActuelle,
                            indiceVersSommet.get(s).substring(0, indiceVersSommet.get(s).length() - 1)
                    )))
                    .limit(MAX_SUIVANTS)
                    .toList();

            for (int suivant : meilleursSuivants) {
                String sommetSuivant = indiceVersSommet.get(suivant);
                String villeSuivante = sommetSuivant.substring(0, sommetSuivant.length() - 1);
                int distSupplementaire = carte.getDistance(courant.villeActuelle, villeSuivante);
                int nouvelleDistance = courant.distanceCumulee + distSupplementaire;

                int[] newDegres = courant.degresEntrants.clone();
                for (int voisin : voisins[suivant]) newDegres[voisin]--;

                List<Integer> newParcours = new ArrayList<>(courant.parcoursSommets);
                newParcours.add(suivant);

                String cleEtat = newParcours.toString() + "@" + villeSuivante;
                if (dejaVus.contains(cleEtat)) continue;
                dejaVus.add(cleEtat);

                if (pq.size() > LIMITE_ETATS) break;

                pq.add(new Etat(newParcours, newDegres, villeSuivante, nouvelleDistance));
            }
        }

        return resultats;
    }


    /**
     * Calcule la distance totale d’un parcours
     */
    public static int calculDistance(List<String> parcours, CarteFrance carte) {
        int distanceTotale = 0;
        for (int i = 0; i < parcours.size() - 1; i++) {
            String ville1 = parcours.get(i);
            String ville2 = parcours.get(i + 1);
            distanceTotale += carte.getDistance(ville1, ville2);
        }
        return distanceTotale;
    }
}


