package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class Application2 extends Application {

    @Override
    public void start(Stage stage) {
        HBoxRootLecture rootLecture = new HBoxRootLecture();
        rootLecture.setId("rootLecture");

        HBoxRootCreation rootCreation = new HBoxRootCreation();
        rootCreation.setId("rootCreation");

        VBox container = new VBox();


        MenuBar menuBar = new MenuBar();

        Menu menuAppli = new Menu("Appli");
        MenuItem exitItem = new MenuItem("Quitter");
        exitItem.setOnAction(e -> stage.close());


        Menu menuAide = new Menu("Aide");
        MenuItem aboutItem = new MenuItem("À propos");
        aboutItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("À propos");
            alert.setHeaderText(null);
            alert.setContentText("Calendrier version 1.0");
            alert.showAndWait();
        });

        Menu menuCreation = new Menu("Création");
        MenuItem itemCreation = new MenuItem("Mode Création");
        itemCreation.setOnAction(e -> {
            container.getChildren().clear();
            container.getChildren().add(rootCreation);
        });

        Menu menuLecture = new Menu("Lecture");
        MenuItem itemLecture = new MenuItem("Mode Lecture");
        itemLecture.setOnAction(e -> {
            container.getChildren().clear();
            container.getChildren().add(rootLecture);
        });


        menuLecture.getItems().add(itemLecture);
        menuCreation.getItems().add(itemCreation);
        menuAppli.getItems().add(exitItem);
        menuAide.getItems().add(aboutItem);
        menuBar.getMenus().addAll(menuAppli, menuAide, menuLecture, menuCreation);


        VBox root = new VBox();
        root.getChildren().addAll(menuBar, container);
        container.getChildren().add(rootLecture);



        Scene scene = new Scene(root, 1200, 500);
        File[] fichiersCss = new File("css").listFiles();
        for (File fichier : fichiersCss) {
            scene.getStylesheets().add(fichier.toURI().toString());
        }
        stage.setTitle("Appli");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}