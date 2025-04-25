import java.util.Random;
import javax.swing.ImageIcon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Ile{
    private Zone[][] grille;
    private int largueur, hauteur;
    private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();

    public Ile(int x, int y){
        joueurs = new ArrayList<Joueur>(4);
        largueur = x; hauteur = y;
        grille = new Zone[x][y];
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                grille[i][j] = new Zone(i, j, Type.normale);
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
                grille[x1][y1] = new Zone(x1, y1, Type.heliport);
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
        joueurs.add(new Joueur(0, getZoneHeliport(), new ImageIcon(getClass().getResource("data/Joueurs/explorateur.png")).getImage()));
        joueurs.add(new Joueur(1, getZoneHeliport(), new ImageIcon(getClass().getResource("data/Joueurs/navigateur.png")).getImage()));
        joueurs.add(new Joueur(2, getZoneHeliport(), new ImageIcon(getClass().getResource("data/Joueurs/pilote.png")).getImage()));
        joueurs.add(new Joueur(3, getZoneHeliport(), new ImageIcon(getClass().getResource("data/Joueurs/plongeur.png")).getImage()));
    }
    public Zone getZone(int x, int y){
        if(x < 0 || x >= largueur || y < 0 || y >= hauteur) return null;
        return grille[x][y];
    }  
    public Zone getZoneHeliport(){
        for(int i = 0; i < largueur; i++){
            for(int j = 0; j < hauteur; j++){
                if(grille[i][j].getType() == Type.heliport) return grille[i][j];
            }
        }
        return null;
    }
    public Zone[][] getGrille(){ return grille; }
    public ArrayList<Joueur> getJoueurs(){ return joueurs; }
}