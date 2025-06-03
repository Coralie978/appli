package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.Donnees;
import modele.Membre;
import modele.Scenario;
import modele.Vente;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HBoxRootCreation extends HBox {

    public HBoxRootCreation() {

        super();
        this.setSpacing(20);
        this.setPadding(new Insets(10));

        List<Membre> membres = Donnees.lireMembres("data/membres_APPLI.txt");


        List<Scenario> scenarios = new ArrayList<>();
        List<String> scenariosNoms = new ArrayList<>();
        List<String> scenariosVilles = new ArrayList<>();


        try {
            Path path = Paths.get("data/distances.txt");
            BufferedReader reader = Files.newBufferedReader(path);

            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parties = ligne.trim().split("\\s+");
                if (parties.length > 0) {
                    scenariosVilles.add(parties[0]);
                }
            }

            reader.close();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        for(int i = 0; i <= 8; i++) {
            String chemin = "data/scenario_" + i + ".txt";
            Scenario scenario = Donnees.lireScenario(chemin,membres);
            scenario.setNom("Scenario " + i);
            scenariosNoms.add(scenario.getNom());
            scenarios.add(scenario);
        }

        VBox vboxAffichage = new VBox();
        vboxAffichage.setStyle("-fx-padding: 10;");
        vboxAffichage.setSpacing(10);
        Label labelTitre = new Label("Scénario sélectionné :");
        Label labelAffichage = new Label("Aucun scénario sélectionné");
        vboxAffichage.getChildren().addAll(labelTitre, labelAffichage);




        ComboBox<String> comboBoxScenarios = new ComboBox<>();
        comboBoxScenarios.getItems().addAll(scenariosNoms);
        comboBoxScenarios.setVisible(true);

        comboBoxScenarios.setOnAction(e -> {
            String selected = comboBoxScenarios.getValue();
            Scenario scenarioSelectionne = null;
            for (Scenario s : scenarios) {
                if (s.getNom().equals(selected)) {
                    scenarioSelectionne = s;
                    break;
                }
            }
            labelAffichage.setText( scenarioSelectionne.toString());
        });

        VBox container = new VBox(10);

        HBox creationBox = new HBox(10);
        creationBox.setAlignment(Pos.CENTER_LEFT);
        Button btnCreer = new Button("Créer scénario");
        TextField nom = new TextField();
        Label labelNom = new Label("Nouveau scenario :");
        labelNom.setLabelFor(nom);

        btnCreer.setOnAction(e -> {
            Scenario nouveau = new Scenario();
            nouveau.setNom(nom.getText());
            scenarios.add(nouveau);
            scenariosNoms.add(nouveau.getNom());
            VBoxRoot.ajouterScenario(nouveau);
            VBoxRoot.ajouterScenarioNom(nouveau.getNom());



            comboBoxScenarios.getItems().add(nouveau.getNom());
        });

        creationBox.getChildren().addAll(labelNom, nom);

        Separator sep = new Separator();

        ComboBox<String> comboBoxMembre1 = new ComboBox<>();
        comboBoxMembre1.getItems().addAll(scenariosVilles);
        comboBoxMembre1.setVisible(true);

        TextField tfMembre1 = new TextField();
        tfMembre1.setPromptText("Entrée gauche");


        ComboBox<String> comboBoxMembre2 = new ComboBox<>();
        comboBoxMembre2.getItems().addAll(scenariosVilles);
        comboBoxMembre2.setVisible(true);

        TextField tfMembre2 = new TextField();
        tfMembre2.setPromptText("Entrée droite");

        Label lblMembre1 = new Label("Membre 1 : ");
        Label lblMembre2 = new Label("Membre 2 : ");

        Label Ville1 = new Label("Ville : ");
        Label Nom1 = new Label("Nom : ");

        Label Ville2 = new Label("Ville : ");
        Label Nom2 = new Label("Nom : ");

        HBox membre1 = new HBox(10, Nom1,  tfMembre1, Ville1,   comboBoxMembre1);
        HBox membre2 = new HBox(10, Nom2,  tfMembre2, Ville2, comboBoxMembre2);

        Button btnAjouterVente = new Button("Ajouter vente");
        btnAjouterVente.setOnAction(e -> {
            String nomChoisi = comboBoxScenarios.getValue();

            String valGauche = tfMembre1.getText();
            String valDroite = tfMembre2.getText();

            Vente vente = new Vente(new Membre(valGauche,comboBoxMembre1.getValue()), new Membre(valDroite,comboBoxMembre2.getValue()));

            for (Scenario s : scenarios) {
                if (s.getNom().equals(nomChoisi)) {
                    s.ajouterVente(vente);

                    labelAffichage.setText(s.toString());
                    break;
                }
            }

            tfMembre1.clear();
            tfMembre2.clear();
        });

        ScrollPane scrollPaneAffichage = new ScrollPane();
        scrollPaneAffichage.setContent(vboxAffichage);
        scrollPaneAffichage.setFitToWidth(true);
        scrollPaneAffichage.setPrefHeight(400);

        Label lblSelection = new Label("Selectionner scenario :");

        Label titre = new Label("Creation");
        titre.setId("titre");

        container.getChildren().addAll(
                titre,
                creationBox,
                btnCreer,
                sep,
                lblSelection,
                comboBoxScenarios,
                lblMembre1,
                membre1,
                lblMembre2,
                membre2,
                btnAjouterVente
        );

        this.getChildren().addAll(container,scrollPaneAffichage );
    }


}
