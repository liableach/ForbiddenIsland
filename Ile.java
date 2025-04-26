import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

public class Ile{
    private Zone[][] grille;
    private int largueur, hauteur;
    private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
    private int currentJoueur;

    public Ile(int x, int y){
        currentJoueur = 0;
        joueurs = new ArrayList<Joueur>(4);
        largueur = x; hauteur = y;
        grille = new Zone[x][y];
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                grille[i][j] = new Zone(i, j, Type.normale, i + j*6);
            }
        }
        grille[0][0].setType(Type.eau);
        grille[0][1].setType(Type.vide);
        grille[0][4].setType(Type.vide);
        grille[0][5].setType(Type.terre);
        grille[1][0].setType(Type.vide);
        grille[1][5].setType(Type.vide);
        grille[4][0].setType(Type.vide);
        grille[4][5].setType(Type.vide);
        grille[5][0].setType(Type.feu);
        grille[5][1].setType(Type.vide);
        grille[5][4].setType(Type.vide);
        grille[5][5].setType(Type.air);
        Random r = new Random(); boolean fait = true;
        while(fait){
            int x1 = r.nextInt(x-1), y1 = r.nextInt(y-1);
            if(grille[x1][y1].getType() == Type.normale){
                fait = false;
                grille[x1][y1].setType(Type.heliport);
            }
        }
        for(int i = 0; i < 2; i++){
            fait = true;
            while(fait){
               int x1 = r.nextInt(x-1), y1 = r.nextInt(y-1);
                if(grille[x1][y1].getType() == Type.normale){
                        fait = false;
                        grille[x1][y1] = new Zone(x1, y1, Type.element_a);
                    }
                }   
        }
        for(int i = 0; i < 2; i++){
            fait = true;
            while(fait){
               int x1 = r.nextInt(x-1), y1 = r.nextInt(y-1);
                if(grille[x1][y1].getType() == Type.normale){
                        fait = false;
                        grille[x1][y1] = new Zone(x1, y1, Type.element_f);
                    }
                }   
        }
        for(int i = 0; i < 2; i++){
            fait = true;
            while(fait){
               int x1 = r.nextInt(x-1), y1 = r.nextInt(y-1);
                if(grille[x1][y1].getType() == Type.normale){
                        fait = false;
                        grille[x1][y1] = new Zone(x1, y1, Type.element_t);
                    }
                }   
        }
        for(int i = 0; i < 2; i++){
                fait = true;
                while(fait){
                   int x1 = r.nextInt(x-1), y1 = r.nextInt(y-1);
                    if(grille[x1][y1].getType() == Type.normale){
                            fait = false;
                            grille[x1][y1] = new Zone(x1, y1, Type.element_e);
                        }
                    }   
        }
        joueurs.add(new Joueur(0, getZoneHeliport(), new ImageIcon(getClass().getResource("data/Joueurs/explorateur.png")).getImage(), 0, Role.pilote));
        joueurs.add(new Joueur(1, getZoneHeliport(), new ImageIcon(getClass().getResource("data/Joueurs/navigateur.png")).getImage(), 1, Role.navigateur));
        joueurs.add(new Joueur(2, getZoneHeliport(), new ImageIcon(getClass().getResource("data/Joueurs/pilote.png")).getImage(),2, Role.messager));
        joueurs.add(new Joueur(3, getZoneHeliport(), new ImageIcon(getClass().getResource("data/Joueurs/plongeur.png")).getImage(),3, Role.plongeur));
    }
    public Zone getZone(int x, int y){
        if(x < 0 || x >= largueur || y < 0 || y >= hauteur) return null;
        return grille[x][y];
    }
    public Zone getZone(int n){
        for(Zone[] row : grille){
            for(Zone z : row){
                if(z.getN() == n) return z;
            }
        }
        return null;
    }  
    public Zone getZoneHeliport(){
        for(Zone[] row : grille){
            for(Zone z : row){
                if(z.getType() == Type.heliport) return z;
            }
        }
        return null;
    }
    public Zone[][] getGrille(){ return grille; }
    public ArrayList<Joueur> getJoueurs(){ return joueurs; }
    public int getCurrentJoueur(){ return currentJoueur; }
    public void setCurrentJoueur(int i){ currentJoueur = i; }
    public boolean heliportSubmerge(){ 
        if(getZoneHeliport() == null || getZoneHeliport().getType() == Type.vide || getZoneHeliport().getEtat() == Etat.submergee) return true;
        return false;
    }
    public int getArtefactsRecuperes(){
        int artefacts = 0;
        for(Joueur j : joueurs){
            artefacts += j.nbArtefacts();
        }
        return artefacts;
    }
    public boolean tousJoueursSurHeliport(){
        for(Joueur j : joueurs)if(j.getPos() != getZoneHeliport()) return false; 
        return true;
    }
    public int tuilesArtefact(Element e){
        int tuiles = 0;
        for(Zone[] row : grille){
            for(Zone z : row){
                if(z.getType() == Type.element_a && e == Element.air) tuiles++;
                else if(z.getType() == Type.element_f && e == Element.feu) tuiles++;
                else if(z.getType() == Type.element_t && e == Element.terre) tuiles++;
                else if(z.getType() == Type.element_e && e == Element.eau) tuiles++;
            }
        }
        return tuiles;
    }
    public boolean artefactPerdu(){
        for(Element e : Element.values()){
            if(tuilesArtefact(e) == 0){
                boolean found = false;
                for(Joueur j : joueurs){
                    if(j.contientArtefact(e)){
                        found = true;
                        break;
                    }
                }
                if(!found) return true;
            }
        }
        return false;
    }
    public boolean checkJoueurMort(){
        for(Joueur j : joueurs){
            if(j.getPos().getEtat() == Etat.submergee) return true;
        }
        return false;
    }
}