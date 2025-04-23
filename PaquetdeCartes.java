import java.util.ArrayList;
import java.util.Collection;

public class PaquetdeCartes{
    private ArrayList<Carte> tresor; //28
    private ArrayList<Carte> inondations; //24
    private ArrayList<Carte> defausse_tresor;
    private  ArrayList<Carte> defausse_inondations;
    public PaquetdeCartes(){
        tresor = new ArrayList<Carte>();
        inondations = new ArrayList<Carte>();
        defausse_tresor = new ArrayList<Carte>();
        defausse_inondations = new ArrayList<>();
    }

    public void melanger_tresor(){
        Collection.shuffle(tresor);
    }

    public void melanger_inondations(){
        Collection.shuffle(inondations);
    }

    public Carte tirerCarte_tresor(){
        carte = tresor.get(27);
        tresor.remove(27);
        return carte;
    }
    
    public Carte tireerCarte_inondations(){
        carte = inondations.get(inondations.size() - 1);
        inondations.remove(inondations.size() - 1);
        return carte;

    }

    // Cette méthode dépose la carte dans la défausse
    public void poser(Carte carte){
        defausse.add(carte);
    }

    public void melangerDefausse(){
        Collection.shuffle(defausse);
    }

    public void replacerAuSommet(){    
        
    }
}