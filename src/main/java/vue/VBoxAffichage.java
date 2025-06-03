package vue;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.*;

import java.util.List;

public class VBoxAffichage extends VBox {

    private static Scenario scenario;
    private static CarteFrance carte;
    private static ScrollPane scrollPane;
    private static VBox contenuGlobal;

    public VBoxAffichage() {

        carte = Donnees.lireCarte("data/distances.txt");
        List<Membre> membres = Donnees.lireMembres("data/membres_APPLI.txt");
        scenario = Donnees.lireScenario("data/scenario_1.txt", membres);

        contenuGlobal = new VBox(20);
        contenuGlobal.setFillWidth(true);

        scrollPane = new ScrollPane(contenuGlobal);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(600);

        this.getChildren().add(scrollPane);

        ajouterBoutons();
    }

    public static void setScenario(Scenario s) {
        scenario = s;
        contenuGlobal.getChildren().clear();

        ajouterBoutons();
    }

    private static void ajouterBoutons() {
        contenuGlobal.getChildren().clear();

        Label lblScenar= new Label(scenario.getNom()) ;

        Button btnTopo = new Button("Afficher Tri Topologique");
        VBox vboxTopo = new VBox(5);
        btnTopo.setOnAction(e -> {
            vboxTopo.getChildren().clear();
            vboxTopo.getChildren().add(new Label("Tri Topologique :"));
            List<String> resultTopo = Algorithmes.algoTriTopologique(scenario);
            vboxTopo.getChildren().add(new Label(resultTopo.toString()));
        });

        Button btnHeur = new Button("Afficher Heuristique");
        VBox vboxHeur = new VBox(5);
        btnHeur.setOnAction(e -> {
            vboxHeur.getChildren().clear();
            vboxHeur.getChildren().add(new Label("Heuristique :"));
            ResultatParcours resultHeur = Algorithmes.algoHeuristique(scenario, carte);
            vboxHeur.getChildren().add(new Label(resultHeur.toString()));
        });


        Label lblBest = new Label("Entrez K : ") ;
        TextField txtBest = new TextField();
        txtBest.setText("2");

        Button btnKBest = new Button("Afficher les K meilleures solutions");
        VBox vboxKBest = new VBox(5);
        btnKBest.setOnAction(e -> {
            int k = Integer.parseInt(txtBest.getText());
            vboxKBest.getChildren().clear();
            vboxKBest.getChildren().add(new Label(k + " meilleures solutions :"));

            List<ResultatParcours> resultK = Algorithmes.algoKMeilleuresSolutions(scenario, carte, k);
            int i = 1;
            for (ResultatParcours solution : resultK) {
                vboxKBest.getChildren().add(new Label("Solution " + i++ + " : " + solution.toString()));
            }
        });

        HBox hboxBest = new HBox(5);
        hboxBest.getChildren().addAll(lblBest, txtBest);

        contenuGlobal.getChildren().addAll(
                lblScenar,
                btnTopo, vboxTopo,
                btnHeur, vboxHeur,
                hboxBest, btnKBest, vboxKBest
        );
    }
}
