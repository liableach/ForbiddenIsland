import java.util.Scanner;

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
            incrementerNiveau();
            paquet.melanger_defausse_inondations();
            paquet.replacerAuSommet_inondations();
            Carte c = joueur.getDerniereCarte();
            joueur.retirerCarte(c);
            paquet.poser(c);
        }
    }
    public void tourJoueur() {
        if (partieTerminee) return;

        Joueur joueur = i.getJoueurs().get(i.getCurrentJoueur());

        // Phase d'actions du joueur (gérée ailleurs : FenetreJeu ou Vue)
        // -> ici on suppose que les 3 actions sont faites manuellement
        Scanner sc = new Scanner(System.in);    
        while(joueur.getNbActions() != 0) {
        majAffichage();
        System.out.println("Niveau d'eau : " + niveau);
        System.out.println(paquet.getTailleDefausseTresors() + " cartes tresor dans la défausse.");
        System.out.println(paquet.getTailleTresors() + " cartes tresor");
        System.out.println(paquet.getTailleDefausseInondations() + " cartes inondation dans la défausse.");
        System.out.println(paquet.getTailleInondations() + " cartes inondation");
        System.out.println("Actions disponibles pour " + joueur.getId() + " (" + joueur.getNbActions() + " actions restantes) :");
        System.out.println("1. Se déplacer");
        System.out.println("2. Assécher une zone");
        System.out.println("3. Donner une carte");
        System.out.println("4. Récupérer un artefact");
        System.out.println("5. Déplacer un joueur (navigateur)");
        System.out.println("6. Fin du tour");
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
            joueur.deplacer(z, i); // à coder : méthode seDeplacer
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
            System.out.println("Précisez le joueur à qui donner la carte (id) et la carte (id)");
            int idJoueur = sc.nextInt();
            int idCarte = sc.nextInt();
            Joueur joueurCible = i.getJoueurs().get(idJoueur);
            Carte c = joueur.getCarte(idCarte);
            joueur.donnerCarte(joueurCible, c); // à coder : méthode donnerCarte
            break;
        case 4:
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
        case 5: 
            if(joueur.getRole() != Role.navigateur) {
                System.out.println("Vous n'êtes pas le navigateur !");
                break;
            }
            System.out.println("Précisez le joueur à déplacer (id) et la zone (x, y)");
            idJoueur = sc.nextInt();
            x = sc.nextInt();
            y = sc.nextInt();
            Joueur joueurCible2 = i.getJoueurs().get(idJoueur);
            Zone z2 = i.getZone(x, y);
            if (z2 == null || z2.getType() == Type.vide  || z2.getType() == Type.air || z2.getType() == Type.terre || z2.getType() == Type.eau || z2.getType() == Type.feu) {
                System.out.println("Zone invalide !");
                break;
            }
            else joueur.deplacerAutreJoueur(joueurCible2, z2, i);
            break;
        case 6:
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
            }
            // Pioche de cartes inondation selon le niveau
            for (int j = 0; j < nombreCartesInondation(); j++) {
                Carte c = paquet.tirerCarte_inondations();
                int n = c.getN();
                int nn;
                if(n < 7) nn = n - 2;
                else if(n < 12) nn = n - 5;
                else if(n < 24) nn = n - 6;
                else if(n < 30) nn = n - 7;
                else nn = n + 10;
                Zone z = i.getZone(nn);
                if(z != null) z.inonder();
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
        // Règle classique : plus le niveau d'eau monte, plus on pioche
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
        // 1) create your Swing window on the EDT
        SwingUtilities.invokeLater(() -> {
            FenetreJeu fen = new FenetreJeu(jeu.getIle());
            jeu.setFenetre(fen);
        });
        // 2) run your console/game loop on the main thread
        jeu.jouerPartie();
    }
    
    
}