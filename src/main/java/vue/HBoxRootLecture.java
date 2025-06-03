package vue;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public class HBoxRootLecture extends HBox {
    private static VBoxRoot scenarios = new VBoxRoot();
    private static VBoxAffichage affichage = new VBoxAffichage();





    public HBoxRootLecture() {
        scenarios.setId("scenarios");
        affichage.setId("affichage");
        ScrollPane scrollPaneAffichage = new ScrollPane(affichage);
        scrollPaneAffichage .setFitToHeight(true);
        this.getChildren().addAll(scenarios,scrollPaneAffichage );
    }



}
