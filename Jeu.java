import java.util.List;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class Jeu {
    Ile i;
    List<Joueur> joueurs;
    private int niveau = 0;
    private int tour = 0;

    public Jeu(){ i = new Ile(6, 6); }
    public Ile getIle(){ return i; }
    public List<Joueur> getJoueurs() { return joueurs; }
    public int getNiveau() { return niveau; }
    public int getTour() { return tour; }
    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        SwingUtilities.invokeLater(() -> new FenetreJeu(jeu.i));
        jeu.joueurs = new ArrayList<Joueur>(4);
        jeu.joueurs.add(new Joueur(0, jeu.getIle().getZoneHeliport()));
        jeu.joueurs.add(new Joueur(1, jeu.getIle().getZoneHeliport()));
        jeu.joueurs.add(new Joueur(2, jeu.getIle().getZoneHeliport()));
        jeu.joueurs.add(new Joueur(3, jeu.getIle().getZoneHeliport()));
    }
}
