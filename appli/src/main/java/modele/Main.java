package modele;
//package controleur;
import modele.*;
import java.util.List;

public class Main {
        public static void main(String[] args) {
            // Chargement des données
            List<Membre> membres = Donnees.lireMembres("data/membres_APPLI.txt");
            CarteFrance carte = Donnees.lireCarte("data/distances.txt");
            Scenario scenario0 = Donnees.lireScenario("data/scenario_0.txt", membres);
            Scenario scenario1 = Donnees.lireScenario("data/scenario_1.txt", membres);
            Scenario scenario4 = Donnees.lireScenario("data/scenario_4.txt", membres);
            Scenario scenario8 = Donnees.lireScenario("data/scenario_8.txt", membres);

            // Affichage simple pour vérifier que tout est bien lu
            System.out.println("Membres :");
            for (Membre m : membres) {
                System.out.println(m.getPseudo() + " - " + m.getVille());
            }

            System.out.println("\nVentes du scénario :");
            for (Vente v : scenario0.getVentes()) {
                System.out.println(v.getVendeur().getPseudo() + " → " + v.getAcheteur().getPseudo());
            }

            System.out.println("\nCarte chargée.");
            System.out.println(carte.toString());
/*
            System.out.println(Algorithmes.algoTriTopologique(scenario0));
            System.out.println(Algorithmes.algoHeuristique(scenario0,carte));

            System.out.println(Algorithmes.algoTriTopologique(scenario1));
            System.out.println(Algorithmes.algoHeuristique(scenario1,carte));


            System.out.println(Algorithmes.algoTriTopologique(scenario4));
            System.out.println(Algorithmes.algoHeuristique(scenario4,carte));
 */
            System.out.println(Algorithmes.algoTriTopologique(scenario8));
            System.out.println(Algorithmes.algoHeuristique(scenario4,carte));
            System.out.println(Algorithmes.algoKMeilleuresSolutions(scenario4,carte,3));
        }


}


