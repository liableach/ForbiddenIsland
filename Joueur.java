import java.util.ArrayList;
import java.util.Random;

public class Joueur {
    private int nom;
    private Zone position;
    private boolean alive;
    private Classe classe;
    private int nbActions = 3;
    private ArrayList<Element> elements;
    private ArrayList<Element> cles;
    
    public Joueur(int nom, Zone position) {
        this.nom = nom;
        this.position = position;
        this.elements = new ArrayList<>();
        this.cles = new ArrayList<>();
    }
    public int getNbActions() { return nbActions; }
    public boolean estVivant() { return alive; }
    
    public void action(){ nbActions--; }
    public void actionsReset() { nbActions = 3; }
    public void deplacer(Zone z) { 
        if (z.traversable()) position = z;
        action();
    }
    public void inonder() { 
        Random r = new Random();
        int count = 0;
        while(count < 3){
            int x = r.nextInt();
            int y = r.nextInt();
        }
        action();     
    }
}

enum Classe{ pilote, ingenieur, explorateur, navigateur, plongeur, messager}