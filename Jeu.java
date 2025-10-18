import java.util.Scanner;
import javax.swing.SwingUtilities;

import cartes.Carte;
import ile.Element;
import ile.Etat;
import ile.Type;
import ile.Zone;
import ile.Ile;
import cartes.PaquetdeCartes;
import graphics.FenetreJeu;
import joueur.Joueur;
import joueur.Role;

public class Jeu {
    Ile i;
    PaquetdeCartes paquet;
    private int tour = 0;
    private boolean partieTerminee = false;
    private FenetreJeu fenetre;
    
    public Jeu(){ 
        i = new Ile(6, 6, true); // true pour les roles
        paquet = new PaquetdeCartes();
        paquet.melanger_tresor();
        paquet.melanger_inondations();
    }
    public void setFenetre(FenetreJeu f) { this.fenetre = f; }
    public Ile getIle(){ return i; }
    public int getTour() { return tour; }
    // affichage aka update
    private void majAffichage() {
        if (fenetre != null) {
            // ensure we ask Swing to repaint on the EDT
            SwingUtilities.invokeLater(() -> fenetre.getVue().update());
        }
    }
    //que faire si la carte "montee des eaux" est tirée
    public void monteeDesEaux(){
        int n = i.getCurrentJoueur();
        Joueur joueur = i.getJoueurs().get(n);
        if(joueur.monteeDesEauxTiree()){
            System.out.println("Montee des eaux !");
            System.out.println("Niveau d'eau : " + i.getNiveau());
            i.incrementerNiveau();
            paquet.melanger_defausse_inondations();
            paquet.replacerAuSommet_inondations();
            Carte c = joueur.getDerniereCarte();
            joueur.retirerCarte(c);
            paquet.poser(c);
        }
    }
    // AJAHHAHAHAHAHAHHAHAHAHA ça c'est vraiment un truc d'un imbécile (moi), comme il y a que 24 cases parmi 36 qu'on est obligé de initialiser, 
    // il faut rajouter un nombre dependant de sa position sur la grille, 
    // tout ça parce qu'on a décidé de stocker les cartes inondations avec un id de 0 à 23...
    // du coup "n" c'est le numéro de chaque Zone dans la grille
    public int transformerN(int n) {
        if (n < 2) return n+2;
        if (n < 6) return n+5;
        if (n < 18) return n+6;
        if (n < 22) return n+7;
        return n+10;
    }
    // ici commence le truc de fou à 164 lignes de code, petit recap :
    // si la partie n'est pas terminée, on joue
    // on affiche les actions possibles
    // on fait l'action choisie par le joueur
    // on pioche 2 cartes trésor + on vérifir le nombre de cartes + on verifie si la carte "montee des eaux" est tirée
    // on pioche des cartes inondations selon le niveau d'eau
    // on vérifie si la partie est gagnée ou perdue
    // on passe au joueur suivant
    public void tourJoueur() {
        if (partieTerminee) return;

        Joueur joueur = i.getJoueurs().get(i.getCurrentJoueur());

        Scanner sc = new Scanner(System.in);    
        while(joueur.getNbActions() != 0) {
        majAffichage();
        System.out.println("Actions disponibles pour " + joueur.getNom() + ", id : " + joueur.getId() + ", role : " + joueur.getRole() + " (" + joueur.getNbActions() + " actions restantes) :");
        System.out.println("1. Se déplacer");
        System.out.println("2. Assécher une zone");
        System.out.println("3. Voir la main d'un joueur");
        System.out.println("4. Jouer une carte spéciale (sacs de sable ou hélicoptère)");
        System.out.println("5. Donner une carte");
        System.out.println("6. Récupérer un artefact");
        System.out.println("7. Voir artefacts récupérés");
        System.out.println("8. Déplacer un joueur (navigateur)");
        System.out.println("9. Fin du tour");
        int choix = sc.nextInt();
        switch(choix){
        case 1:
            System.out.println("Déplacement vers une autre zone, précisez la zone (x, y)");
            int x = sc.nextInt();
            int y = sc.nextInt();
            Zone z = i.getZone(x, y);
            if (z == null || z.getType() == Type.vide  || z.getType() == Type.air || z.getType() == Type.terre || z.getType() == Type.eau || z.getType() == Type.feu) {
                System.out.println("Zone invalide !");
                break;
            }
            joueur.deplacer(z, i); 
            break;
        case 2:
            System.out.println("Précisez la zone à assécher (x, y)");
            x = sc.nextInt();
            y = sc.nextInt();
            z = i.getZone(x, y);
            if (z == null || z.getType() == Type.vide  || z.getType() == Type.air || z.getType() == Type.terre || z.getType() == Type.eau || z.getType() == Type.feu) {
                System.out.println("Zone invalide !");
                break;
            }
            else if (z.getEtat() == Etat.normale) {
                System.out.println("Zone déjà asséchée !");
                break;
            }
            else joueur.assecher(z,i); // à coder : méthode assecher
            break;
        case 3:
            System.out.println("Précisez le joueur (id)");
            int id = sc.nextInt();
            Joueur joueurCible = i.getJoueurs().get(id);
            System.out.println("Main du joueur " + id + " : ");
            for (Carte c : joueurCible.getMain()) {
                System.out.println(c.getTypeCarte().toString());
            }
            break;
        case 4:
            if(!joueur.contientCarteSpeciale()) {
                System.out.println("Vous ne possédez pas de carte spéciale !");
                break;
            }
            System.out.println("Précisez la carte à jouer (id)");
            int idCarte = sc.nextInt();
            Carte carte = joueur.getCarte(idCarte);
            joueur.jouerCarteSpeciale(carte, i, paquet);
            break;
        case 5:
            System.out.println("Précisez le joueur à qui donner la carte (id) et la carte (id)");
            int idj = sc.nextInt();
            int idc = sc.nextInt();
            joueurCible = i.getJoueurs().get(idj);
            Carte c = joueur.getCarte(idc);
            joueur.donnerCarte(joueurCible, c); // à coder : méthode donnerCarte
            break;
        case 6:
            System.out.println("Précisez l'artefact à récupérer (id) : 0 - eau 1 - feu 2 - terre 3  - air");
            int idArtefact = sc.nextInt();
            switch(idArtefact) {
            case 0:
                joueur.recupererArtefact(Element.eau, i, paquet); // à coder : méthode recupererArtefact
                break;
            case 1:
                joueur.recupererArtefact(Element.feu, i, paquet); // à coder : méthode recupererArtefact
                break;
            case 2:
                joueur.recupererArtefact(Element.terre, i, paquet); // à coder : méthode recupererArtefact
                break;
            case 3:
                joueur.recupererArtefact(Element.air, i, paquet); // à coder : méthode recupererArtefact
                break;
            }
            break;
        case 7:
            if(joueur.nbArtefacts() == 0) {
                System.out.println("Vous n'avez pas récupéré d'artefact !");
                break;
            }
            System.out.println("Artefacts récupérés : ");
            joueur.afficherArtefacts();
            break;
        case 8: 
            if(joueur.getRole() != Role.navigateur) {
                System.out.println("Vous n'êtes pas le navigateur !");
                break;
            }
            System.out.println("Précisez le joueur à déplacer (id) et la zone (x, y)");
            idj = sc.nextInt();
            x = sc.nextInt();
            y = sc.nextInt();
            Joueur joueurCible2 = i.getJoueurs().get(idj);
            Zone z2 = i.getZone(x, y);
            if (z2 == null || z2.getType() == Type.vide  || z2.getType() == Type.air || z2.getType() == Type.terre || z2.getType() == Type.eau || z2.getType() == Type.feu) {
                System.out.println("Zone invalide !");
                break;
            }
            else joueur.deplacerAutreJoueur(joueurCible2, z2, i);
            break;
        case 9:
            System.out.println("Fin du tour.");
            joueur.finTour();
            break;
        
        }

    }
            // Fin du tour : pioche 2 cartes Trésor
            for (int j = 0; j < 2; j++) {
                Carte c = paquet.tirerCarte_tresor();
                joueur.ajouterCarte(c);
                monteeDesEaux();
                if(joueur.nbCartes() > 5){
                    System.out.println("Vous avez plus de 5 cartes Retirez une carte. Précisez la carte à retirer (id)");
                    for(Carte carte : joueur.getMain()) System.out.println(carte.getTypeCarte().toString() + " id : " + joueur.getMain().indexOf(carte));
                    int id = sc.nextInt();
                    Carte carte = joueur.getCarte(id);
                    if(carte.estSpeciale()) joueur.jouerCarteSpeciale(carte, i, paquet);
                    else joueur.retirerCarte(carte);
                    paquet.poser(carte);
                }
            }
            // Pioche de cartes inondation selon le niveau
            for (int j = 0; j < i.get_niveau_eau(); j++){
                if(paquet.getTailleInondations() == 0) break;
                Carte c = paquet.tirerCarte_inondations();
                int n = c.getN();
                int nn = transformerN(n);
                Zone z = i.getZone(nn);
                System.out.println("Zone inondée : " + z.getType().toString());
                if(z != null)z.inonder();
                paquet.poser(c);
            }
            // Vérifications après le tour
            if (checkWin()) {
                finPartie(true);
                return;
            }
            if (checkLoss()) {
                finPartie(false);
                return;
            }
            if (i.getCurrentJoueur() == i.getJoueurs().size()-1) {
                i.setCurrentJoueur(0);
            }
            else i.setCurrentJoueur(i.getCurrentJoueur()+1);
            joueur.actionsReset();
    }
    // dans les régles
    private boolean checkWin() { return i.getArtefactsRecuperes() == 4 && i.tousJoueursSurHeliport(); }

    // oui, le code en 1 ligne
    private boolean checkLoss() { return (i.getNiveau() >= 10) || (i.heliportSubmerge()) || (i.artefactPerdu()) || (i.checkJoueurMort()); }

    private void finPartie(boolean win) {
        partieTerminee = true;
        if (win) System.out.println("Victoire ! Vous vous êtes échappés avec tous les artefacts !"); 
        else System.out.println("Défaite... L'île vous a engloutis.");
    }

    public void jouerPartie() {
        while (!partieTerminee) tourJoueur();
    }

    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        // les nouveaux trucs de java (volés à partir de ocaml)
        SwingUtilities.invokeLater(() -> {
            FenetreJeu fen = new FenetreJeu(jeu.getIle(), jeu.paquet);
            jeu.setFenetre(fen);
        });
        jeu.jouerPartie();
    }
}
