import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
import javax.swing.SwingUtilities;

public class Jeu {
    Ile i;
    PaquetdeCartes paquet;
    private int niveau = 0;
    private int tour = 0;
    private boolean partieTerminee = false;
    private FenetreJeu fenetre;      // new
    public void setFenetre(FenetreJeu f) { this.fenetre = f; }


    public Jeu(){ 
        i = new Ile(6, 6); 
        paquet = new PaquetdeCartes();
        paquet.melanger_tresor();
        paquet.melanger_inondations();
    }

    private void majAffichage() {
        if (fenetre != null) {
            // ensure we ask Swing to repaint on the EDT
            SwingUtilities.invokeLater(() -> fenetre.getVue().update());
        }
    }
    public Ile getIle(){ return i; }
    public int getNiveau() { return niveau; }
    public int getTour() { return tour; }
    public void incrementerNiveau() { niveau++; }
    public void incrementerTour() { tour++; }
    public void monteeDesEaux(){
        int n = i.getCurrentJoueur();
        Joueur joueur = i.getJoueurs().get(n);
        if(joueur.monteeDesEauxTiree()){
            System.out.println("Montee des eaux !");
            System.out.println("Niveau d'eau : " + niveau);
            incrementerNiveau();
            paquet.melanger_defausse_inondations();
            paquet.replacerAuSommet_inondations();
            Carte c = joueur.getDerniereCarte();
            joueur.retirerCarte(c);
            paquet.poser(c);
        }
    }
    public int transformerN(int n) {
        if (n < 2) return n+2;
        if (n < 6) return n+5;
        if (n < 18) return n+6;
        if (n < 22) return n+7;
        return n+10;
    }

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
            for (int j = 0; j < nombreCartesInondation(); j++) {
                Carte c = paquet.tirerCarte_inondations();
                int n = c.getN();
                System.out.println("Carte inondation : " + n);
                int nn = transformerN(n);
                System.out.println("Zone inondée : " + nn);
                Zone z = i.getZone(nn);
                if(z != null)z.inonder();
                paquet.poser(c);

            }
            // Vérifications après le tour
            if (checkVictoire()) {
                finPartie(true);
                return;
            }
            if (checkDefaite()) {
                finPartie(false);
                return;
            }
            if (i.getCurrentJoueur() == i.getJoueurs().size()-1) {
                i.setCurrentJoueur(0);
            }
            else i.setCurrentJoueur(i.getCurrentJoueur()+1);
            incrementerTour();
            joueur.actionsReset();
    }

    private int nombreCartesInondation() {
        if (niveau < 2) return 2;
        if (niveau < 5) return 3;
        if (niveau < 7) return 4;
        return 5;
    }

    private boolean checkVictoire() { return i.getArtefactsRecuperes() == 4 && i.tousJoueursSurHeliport(); }

    private boolean checkDefaite() {
        return (niveau >= 10) || (i.heliportSubmerge()) || (i.artefactPerdu()) || (i.checkJoueurMort());
    }

    private void finPartie(boolean victoire) {
        partieTerminee = true;
        if (victoire) {
            System.out.println("Victoire ! Vous vous êtes échappés avec tous les artefacts !");
        } else {
            System.out.println("Défaite... L'île vous a engloutis.");
        }
    }

    public void jouerPartie() {
        while (!partieTerminee) {
            tourJoueur();
        }
    }

    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        SwingUtilities.invokeLater(() -> {
            FenetreJeu fen = new FenetreJeu(jeu.getIle());
            jeu.setFenetre(fen);
        });
        jeu.jouerPartie();
    }
}