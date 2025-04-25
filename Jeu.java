import javax.swing.SwingUtilities;

public class Jeu {
    Ile i;
    PaquetdeCartes paquet;
    private int niveau = 0;
    private int tour = 0;

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
    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        SwingUtilities.invokeLater(() -> new FenetreJeu(jeu.i));
    }
}
