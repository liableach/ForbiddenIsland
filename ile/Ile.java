package ile;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.ImageIcon;

import joueur.Joueur;
import joueur.Role;

import java.awt.Image;
//classe ile qui gere la grille de jeu, les joueurs et les zones
public class Ile{
    private Zone[][] grille;
    private int largueur, hauteur;
    private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
    private int currentJoueur;
    private int niveau = 0;

    public Ile(int x, int y, boolean b){
        currentJoueur = 0;
        joueurs = new ArrayList<Joueur>(4);
        largueur = x; hauteur = y;
        grille = new Zone[x][y];
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                grille[i][j] = new Zone(i, j, Type.normale, i*largueur + j);
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
                        grille[x1][y1].setType(Type.element_a);
                    }
                }   
        }
        for(int i = 0; i < 2; i++){
            fait = true;
            while(fait){
               int x1 = r.nextInt(x-1), y1 = r.nextInt(y-1);
                if(grille[x1][y1].getType() == Type.normale){
                        fait = false;
                        grille[x1][y1].setType(Type.element_f);
                    }
                }   
        }
        for(int i = 0; i < 2; i++){
            fait = true;
            while(fait){
               int x1 = r.nextInt(x-1), y1 = r.nextInt(y-1);
                if(grille[x1][y1].getType() == Type.normale){
                        fait = false;
                        grille[x1][y1].setType(Type.element_t);
                    }
                }   
        }
        for(int i = 0; i < 2; i++){
                fait = true;
                while(fait){
                   int x1 = r.nextInt(x-1), y1 = r.nextInt(y-1);
                    if(grille[x1][y1].getType() == Type.normale){
                            fait = false;
                            grille[x1][y1].setType(Type.element_e);
                        }
                    }   
        }
        //les noms sont les références de mes bassistes préférés
        joueurs.add(new Joueur("Cliff Burton", getZoneHeliport(), 0));
        joueurs.add(new Joueur("Jaco Pastorious", getZoneHeliport(), 1));
        joueurs.add(new Joueur("Jason Newsted", getZoneHeliport(),2));
        joueurs.add(new Joueur("Krist Novoselic", getZoneHeliport(),3));
        if(b == true) donnerLesRoles();
    }
    // donner une image à un joueur selon son role
    public Image setImageByRole(Role role){
        switch(role){
            case explorateur: 
                return new ImageIcon(getClass().getResource("../data/Joueurs/explorateur.png")).getImage();
            case plongeur: 
                return new ImageIcon(getClass().getResource("../data/Joueurs/plongeur.png")).getImage();
            case messager: 
                return new ImageIcon(getClass().getResource("../data/Joueurs/messager.png")).getImage();
            case pilote: 
                return new ImageIcon(getClass().getResource("../data/Joueurs/pilote.png")).getImage();
            case navigateur: 
                return new ImageIcon(getClass().getResource("../data/Joueurs/navigateur.png")).getImage();
            case ingenieur:
                return new ImageIcon(getClass().getResource("../data/Joueurs/ingenieur.png")).getImage();
        }
        return null;
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
    // méthode très très utile pour soit déplacer les joueurs(carte héliport) soit pour vérifier si tous les joueurs sont sur l'héliport soir pour vérifier si l'héliport est submergé
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
    public int getNiveau() { return niveau; }
    public int getArtefactsRecuperes(){
        int artefacts = 0;
        for(Joueur j : joueurs){
            artefacts += j.nbArtefacts();
        }
        return artefacts;
    }

    // donner un role à chaque joueur au lancement du jeu
    public void donnerLesRoles(){
        int i = 0;
        Set<Integer> roles = new HashSet<>();
        while (i < 4){
            System.out.println("Choisissez le role du joueur : 0 - pilote, 1 - ingenieur, 2 - explorateur, 3 - navigateur, 4 - plongeur, 5 - messager");
            Scanner sc = new Scanner(System.in);
            int role = sc.nextInt();
            if(roles.contains(role)) System.out.println("Role déjà pris! Choisissez un autre role.");
            else{
                roles.add(role);
                getJoueurs().get(i).setRole(Role.values()[role]);
                i++;
            }
        }
        for(i = 0; i < joueurs.size(); i++){ joueurs.get(i).setImage(setImageByRole(joueurs.get(i).getRole()));}
    }
    // 2 méthodes, une pour récupérer une zone par ses coordonnées, l'autre par son numéro
    public void incrementerNiveau() { niveau++; }
    public void setCurrentJoueur(int i){ currentJoueur = i; }
    public boolean heliportSubmerge(){ 
        return getZoneHeliport() == null || getZoneHeliport().getType() == Type.vide || getZoneHeliport().getEtat() == Etat.submergee;
    }
    // pour téster la victoire
    public boolean tousJoueursSurHeliport(){
        for(Joueur j : joueurs)if(j.getPos() != getZoneHeliport()) return false; 
        return true;
    }
    // compter le nombre de tuiles artefactes restantes sur la grille
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
    // tester si un artefact est perdu
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
    // pour renvoyer le niveau d'eau selon les régles du jeu
    public int get_niveau_eau() {
        if (niveau < 2) return 2;
        if (niveau < 5) return 3;
        if (niveau < 7) return 4;
        return 5;
    }
    public boolean checkJoueurMort(){
        for(Joueur j : joueurs) if(j.getPos().getEtat() == Etat.submergee) return true;
        return false;
    }
}