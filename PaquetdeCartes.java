import java.util.ArrayList;
import java.util.Collection;

public class PaquetdeCartes{
    private ArrayList<Carte> tresor; //28
    private ArrayList<Carte> inondations; //24
    private ArrayList<Carte> defausseTresors;
    private ArrayList<Carte> defausseInondations;
    public PaquetdeCartes(){
        tresor = new ArrayList<Carte>();
        for(int i = 0; i < 5; i++){
            tresor.add(new Carte(TypeCarte.tresor_feu));
            tresor.add(new Carte(TypeCarte.tresor_terre));
            tresor.add(new Carte(TypeCarte.tresor_eau));
            tresor.add(new Carte(TypeCarte.tresor_air));
        }
        for(int i = 0; i < 3; i++){
            tresor.add(new Carte(TypeCarte.montee_des_eaux));
            tresor.add(new Carte(TypeCarte.helicoptere));
        }
        for(int i = 0; i < 2; i++) tresor.add(new Carte(TypeCarte.sacs_de_sable));


        inondations = new ArrayList<Carte>();

        for(int i = 0; i < 24; i++) inondations.add(new Carte(TypeCarte.inondation, i));
            defausseTresors = new ArrayList<Carte>();
            defausseInondations = new ArrayList<Carte>();
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
        if carte.type ==  
    }

    public void replacerAuSommet(){    
        
    }
}