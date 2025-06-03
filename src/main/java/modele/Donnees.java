package modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Donnees {
    public static List<Membre> lireMembres(String cheminFichier) {
        List<Membre> membres = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parties = ligne.split(" ");
                if (parties.length == 2) {
                    String pseudo = parties[0].trim();
                    String ville = parties[1].trim();
                    membres.add(new Membre(pseudo, ville));
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture des membres : " + e.getMessage());
        }
        return membres;
    }

    public static CarteFrance lireCarte(String cheminFichier) {
        CarteFrance carte = new CarteFrance();
        List<String> villes = new ArrayList<>();
        List<int[]> distancesLignes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parties = ligne.trim().split("\\s+"); // split sur un ou plusieurs espaces
                if (parties.length < 2) continue; // ignore les lignes vides ou invalides

                String ville = parties[0];
                villes.add(ville);

                // On récupère les distances sous forme de tableau d'entiers
                int[] distances = new int[parties.length - 1];
                for (int i = 1; i < parties.length; i++) {
                    distances[i - 1] = Integer.parseInt(parties[i]);
                }
                distancesLignes.add(distances);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture des distances : " + e.getMessage());
            return carte; // retourne ce qu'on a pu lire
        }

        // Maintenant on a la liste des villes et la matrice des distances
        // On va ajouter toutes les distances dans la carte
        for (int i = 0; i < villes.size(); i++) {
            String villeDepart = villes.get(i);
            int[] dist = distancesLignes.get(i);
            for (int j = 0; j < dist.length; j++) {
                String villeArrivee = villes.get(j);
                int distance = dist[j];
                carte.ajouterDistance(villeDepart, villeArrivee, distance);
            }
        }

        return carte;
    }


    public static Scenario lireScenario(String cheminFichier, List<Membre> membres) {
        Scenario scenario = new Scenario();
        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parties = ligne.split("->");
                if (parties.length == 2) {
                    String pseudoVendeur = parties[0].trim();
                    String pseudoAcheteur = parties[1].trim();
                    Membre vendeur = trouverMembre(pseudoVendeur, membres);
                    Membre acheteur = trouverMembre(pseudoAcheteur, membres);
                    if (vendeur != null && acheteur != null) {
                        scenario.ajouterVente(new Vente(vendeur, acheteur));
                    } else {
                        System.err.println("Membre non trouvé : " + pseudoVendeur + " ou " + pseudoAcheteur);
                    }
                } else {
                    System.err.println("Format incorrect dans la ligne : " + ligne);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du scénario : " + e.getMessage());
        }
        return scenario;
    }


    private static Membre trouverMembre(String pseudo, List<Membre> membres) {
        for (Membre m : membres) {
            if (m.getPseudo().equalsIgnoreCase(pseudo)) {
                return m;
            }
        }
        return null;
    }

}
