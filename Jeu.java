import java.util.List;
import javax.swing.SwingUtilities;

public class Jeu {
    Ile i;
    List<Joueur> joueurs;
    private int niveau = 0;

    public Jeu(){
        i = new Ile(6, 6);
    }
    public Ile getIle() {
        return i;
    }
    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        SwingUtilities.invokeLater(() -> new FenetreJeu(jeu.i));
        Ile i = jeu.getIle();   
        i.getZone(3, 3).inonder();
        i.getZone(3, 3).assecher();
        
    }
}
