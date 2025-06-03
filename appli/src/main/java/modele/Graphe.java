package modele;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


public class Graphe {
    private TreeMap<Integer, Set<Integer>> mapSommetsVoisins;


    //voisin sortant
    public Graphe(int [][] tableauVoisins) {
        mapSommetsVoisins = new TreeMap<>();
        for (int i = 0; i < tableauVoisins.length; i++) {
            TreeSet<Integer> voisins = new TreeSet<>();
            mapSommetsVoisins.put(i, voisins);
            for (int j = 0; j < tableauVoisins[i].length; j++) {
                voisins.add(tableauVoisins[i][j]);
            }
        }
    }

    public Set getSommets(){
        return mapSommetsVoisins.keySet();
    }

    public int getOrdre(){
        return getSommets().size();
    }

    public int degre(int indice){
        return mapSommetsVoisins.get(indice).size();
    }

    public int taille(){
        int taille = 0;
        for (int i=0; i<getOrdre(); i++){
            taille += degre(i);
        }
        return taille/2;
    }

    public int degreMaximale(){
        int degree = 0;
        for (int i=0; i< getOrdre(); i++){
            if (degree < degre(i)){
                degree = degre(i);
            }
        }
        return degree;
    }

    public int degreMinimale(){
        int degree = getOrdre();
        for (int i=0; i<getOrdre(); i++){
            if (degree > degre(i)){
                degree = degre(i);
            }
        }
        return degree;
    }


    public TreeMap degreEntrant(){
        TreeMap<Integer, Integer> degresEntrant = new TreeMap<>();
        for (int sommet : mapSommetsVoisins.keySet()){
            degresEntrant.put(sommet, 0);
        }

        for (int sommet : mapSommetsVoisins.keySet()){
            for (int voisin : mapSommetsVoisins.get(sommet)){
                degresEntrant.put(voisin, degresEntrant.get(voisin)+1);
            }
        }
        return degresEntrant;
    }

    public ArrayList<Integer> degreEntrantZero(){
        ArrayList<Integer> degresEntrantZero = new ArrayList<>();
        for (int sommet : mapSommetsVoisins.keySet()){
            if(degreEntrant().get(sommet).equals(0)){
                degresEntrantZero.add(sommet);
            }
        }
        return degresEntrantZero;
    }

    public ArrayList<Integer> triToploogique(){
        ArrayList<Integer> sources = degreEntrantZero();
        ArrayList<Integer> resultat = new ArrayList<>();
        TreeMap<Integer, Integer> degresEntrant = degreEntrant();
        while (!sources.isEmpty()){
            int source = sources.remove(0);
            resultat.add(source);
            for (int voisin : mapSommetsVoisins.get(source)){
                degresEntrant.put(voisin, degresEntrant.get(voisin)-1);
                if (degresEntrant.get(voisin) == 0){
                    sources.add(voisin);
                }
            }
        }
        return resultat;
    }

    public String toString(){
        String sommetsVoisins = "ordre : " + getOrdre() + "\n" + "taille : " + taille() + "\n";
        for (int i=0; i<getOrdre(); i++){
            sommetsVoisins += "sommet " + i + ", degré = " + degre(i) + " Voisins : " + mapSommetsVoisins.get(i) + "\n";
        }
        return sommetsVoisins;
    }
}
