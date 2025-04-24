import java.util.ArrayList;
import java.util.Collections;

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
        Collections.shuffle(tresor);
    }

    public void melanger_inondations(){
        Collections.shuffle(inondations);
    }

    public Carte tirerCarte_tresor(){
        Carte carte = tresor.get(tresor.size()-1);
        tresor.remove(tresor.size() - 1);
        return carte;
    }
    
    public Carte tireerCarte_inondations(){
        Carte carte = inondations.get(inondations.size() - 1);
        inondations.remove(inondations.size() - 1);
        return carte;

    }

    // Cette méthode dépose la carte dans la défausse
    public void poser(Carte carte){
        if (carte.getTypeCarte() == TypeCarte.inondation){
            defausseInondations.add(carte);
        }
        else{
            defausseTresors.add(carte);
        }
    }

    public void replacerAuSommet_tresors(){    
        for(int i = 0; i<defausseTresors.size(); i++){
            tresor.add(defausseTresors.get( i )) ;
        }
        defausseTresors.clear();
    }

    public void replacerAuSommet_inondations(){    
        for(int i = 0; i<defausseInondations.size(); i++){
            tresor.add(defausseInondations.get( i )) ;
        }
        defausseInondations.clear();
    }
    public static void main(String[] args) {
        PaquetdeCartes paquet = new PaquetdeCartes();
        paquet.melanger_tresor();
        paquet.melanger_inondations();
        for(int i = 0; i < 24; i++){
            System.out.println(paquet.tireerCarte_inondations().getN());
        }
    }
}