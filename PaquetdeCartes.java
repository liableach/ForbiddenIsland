import java.util.ArrayList;

public class PaquetdeCartes{
    private ArrayList<Carte> tresor; //28
    private ArrayList<Carte> inondations; //24
    private ArrayList<Carte> defausse;
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

        defausse = new ArrayList<Carte>();
    }

    public void melanger(){
        
    }
    public Carte tirer(){
        return null;
    }
    public void poser(){

    }
    public void melangerDefausse(){

    }
    public void replacerAuSommet(){}
}