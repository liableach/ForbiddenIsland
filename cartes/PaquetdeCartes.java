package cartes;
import java.util.ArrayList;
import java.util.Collections;
// creer/gerer le paquet de cartes
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

    public ArrayList<Carte> getTresor(){ return tresor; }
    public ArrayList<Carte> getInondations(){ return inondations; }
    public ArrayList<Carte> getDefausseTresors(){ return defausseTresors; }
    public ArrayList<Carte> getDefausseInondations(){ return defausseInondations; }
    public void melanger_tresor(){
        if(tresor.isEmpty()) return;
        Collections.shuffle(tresor);
    }

    public void melanger_inondations(){
        if(inondations.isEmpty()) return;
        Collections.shuffle(inondations);
    }
    public void melanger_defausse_inondations(){
        if(defausseInondations.isEmpty()) return;
        Collections.shuffle(defausseInondations);
    }
    public void melanger_defausse_tresors(){
        if(defausseTresors.isEmpty()) return;
        Collections.shuffle(defausseTresors);
    }
    public int getTailleInondations(){ return inondations.size(); }
    public int getTailleDefausseInondations(){ return defausseInondations.size(); }
    public int getTailleTresors(){ return tresor.size(); }
    public int getTailleDefausseTresors(){ return defausseTresors.size(); }
    public Carte tirerCarte_tresor(){
        if (tresor.isEmpty()) {
            melanger_defausse_tresors();
            tresor.addAll(defausseTresors);
            defausseTresors.clear();
        }
        Carte carte = tresor.remove(tresor.size() - 1);
        return carte;
    }
    
    public Carte tirerCarte_inondations(){
            // il y avait des soucis avec cette méthode car dans les régles il n'est pas dit quoi faire si la pile de cartes inondations est vide
            /*if (inondations.isEmpty()) {
                melanger_defausse_inondations();
                inondations.addAll(defausseInondations);
                defausseInondations.clear();
            }*/
            return inondations.remove(inondations.size() - 1);
        }

    // Cette méthode dépose la carte dans la défausse dependant de son type
    public void poser(Carte carte){
        if (carte.getTypeCarte() == TypeCarte.inondation) defausseInondations.add(carte);
        else defausseTresors.add(carte);
    }

    public void replacerAuSommet_tresors(){    
        for(int i = 0; i<defausseTresors.size(); i++) tresor.add(defausseTresors.get(i)) ;
        defausseTresors.clear();
    }

    public void replacerAuSommet_inondations(){    
        for(int i = 0; i<defausseInondations.size(); i++) inondations.add(defausseInondations.get(i));
        defausseInondations.clear();
    }
}