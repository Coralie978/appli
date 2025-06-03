package vue;

import controleur.Controleur;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import modele.Donnees;
import modele.Membre;
import modele.Scenario;

import java.util.ArrayList;
import java.util.List;

public class VBoxRoot extends VBox {
    private static List<Scenario> scenarios;
    private static List<String> scenariosNoms;
    private static VBox contenuScroll;


    public VBoxRoot() {

        List<Membre> membres = Donnees.lireMembres("data/membres_APPLI.txt");

        scenarios = new ArrayList<>();
        scenariosNoms = new ArrayList<>() ;

        Label titre = new Label("Lecture");
        titre.setId("titre");
        ScrollPane scrollPaneScenar = new ScrollPane();
        contenuScroll = new VBox();
        scrollPaneScenar.setContent(contenuScroll);
        scrollPaneScenar.setId("scrollPane");
        scrollPaneScenar.setFitToWidth(false);
        scrollPaneScenar.setPrefWidth(350);
        scrollPaneScenar.setMinWidth(350);

        for(int i = 0; i <= 8; i++) {
            String path = "data/scenario_" + i + ".txt";
            Scenario scenario = Donnees.lireScenario(path,membres);
            scenario.setNom("Scenario " + i);
            this.ajouterScenarioNom(scenario.getNom());
            this.ajouterScenario(scenario);
        }


        this.getChildren().add(titre);
        this.getChildren().add(scrollPaneScenar);
    }


    public static void ajouterScenario(Scenario scenario){
        scenarios.add(scenario);
        System.out.println(scenario.getNom());
        Button boutonSuivant = new Button(scenario.getNom());
        boutonSuivant.setPrefWidth(300);
        boutonSuivant.setPrefHeight(80);
        boutonSuivant.setId("boutons");

        boutonSuivant.setUserData(scenario);
        boutonSuivant.setOnAction(new Controleur());

        contenuScroll.getChildren().add(boutonSuivant);
    }

    public static void ajouterScenarioNom(String noms){
        scenariosNoms.add(noms);
    }

    public static List<String> getScenariosNoms() {
        return scenariosNoms;
    }
    public static List<Scenario> getScenarios() {
        return scenarios;
    }
    @Override
    public String toString() {
        return "scenario : " + this.toString();
    }

}
