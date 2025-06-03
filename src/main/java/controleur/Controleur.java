package controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import modele.Scenario;
import vue.VBoxAffichage;

public class Controleur implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {

        if (event.getSource() instanceof ToggleButton) {
            ToggleButton bouton = (ToggleButton) event.getSource();
            Object data = bouton.getUserData();

            if (data instanceof Scenario) {
                Scenario scenario = (Scenario) data;
                VBoxAffichage.setScenario(scenario);
                System.out.println("Toggle activé pour le scénario : " + scenario.getNom());
            }
        }

        if (event.getSource() instanceof Button) {
            Button bouton = (Button) event.getSource();
            Object data = bouton.getUserData();

            if (data instanceof Scenario) {
                Scenario scenario = (Scenario) data;
                VBoxAffichage.setScenario(scenario);

                System.out.println("Bouton cliqué pour le scénario : " + scenario.getNom());
            }




        }
    }
}
