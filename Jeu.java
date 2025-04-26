import javax.swing.SwingUtilities;

public class Jeu {
    Ile i;
    PaquetdeCartes paquet;
    private int niveau = 0;
    private int tour = 0;
    private boolean partieTerminee = false;

    public Jeu(){ 
        i = new Ile(6, 6); 
        paquet = new PaquetdeCartes();
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
        joueur.actionsReset();

        // Phase d'actions du joueur (gérée ailleurs : FenetreJeu ou Vue)
        // -> ici on suppose que les 3 actions sont faites manuellement

        if (joueur.getNbActions() <= 0) {
            // Fin du tour : pioche 2 cartes Trésor
            for (int j = 0; j < 2; j++) {
                Carte c = paquet.tirerCarte_tresor();
                monteeDesEaux();
                joueur.ajouterCarte(c);
            }
            // Pioche de cartes inondation selon le niveau
            for (int j = 0; j < nombreCartesInondation(); j++) {
                Carte c = paquet.tirerCarte_inondations();
                int n = c.getN();
                int nn;
                if(n < 7) nn = n + 2;
                else if(n < 12) nn = n + 5;
                else if(n < 24) nn = n + 6;
                else if(n < 30) nn = n + 7;
                else nn = n + 10;
                Zone z = i.getZone(nn);
                z.inonder();
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
            if (i.getCurrentJoueur() >= i.getJoueurs().size()) {
                i.setCurrentJoueur(0);
            }
            i.setCurrentJoueur(i.getCurrentJoueur() + 1);
            incrementerTour();
        }
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
        // Défaite si :
        // - le niveau d'eau est au max
        if (niveau >= 10) return true;
        // - héliport submergé
        if (i.heliportSubmerge()) return true;
        // - artefact irrécupérable
        if (i.artefactPerdu()) return true;
        // - un joueur meurt
        for (Joueur joueur : i.getJoueurs()) {
            if (!joueur.estVivant()) return true;
        }
        return false;
    }

    private void finPartie(boolean victoire) {
        partieTerminee = true;
        if (victoire) {
            System.out.println("Victoire ! Vous vous êtes échappés avec tous les artefacts !");
        } else {
            System.out.println("Défaite... L'île vous a engloutis.");
        }
    }

    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        SwingUtilities.invokeLater(() -> new FenetreJeu(jeu.i));
    }
}