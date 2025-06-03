package modele;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private String nom;
    private List<Vente> ventes;


    public Scenario() {
        this.ventes = new ArrayList<>();


    }



    public void ajouterVente(Vente vente) {
        ventes.add(vente);
    }

    public List<Vente> getVentes() {
        return ventes;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getNom() + " :\n");
        for (Vente v : ventes) {
            sb.append("  ").append(v).append("\n");
        }
        return sb.toString();
    }


}
