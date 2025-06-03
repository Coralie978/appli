package modele;

import java.util.List;


public class ResultatParcours {
    private List<String> parcours;
    private int distanceTotale;

    public ResultatParcours(List<String> parcours, int distanceTotale) {
        this.parcours = parcours;
        this.distanceTotale = distanceTotale;
    }

    public List<String> getParcours() {
        return parcours;
    }

    public int getDistanceTotale() {
        return distanceTotale;
    }

    public String toString(){
        return Integer.toString(distanceTotale) + " Parcours : " + parcours.toString();
    }
}

