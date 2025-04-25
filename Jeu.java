import java.util.List;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class Jeu {
    Ile i;
    private int niveau = 0;
    private int tour = 0;

    public Jeu(){ i = new Ile(6, 6); }
    public Ile getIle(){ return i; }
    public int getNiveau() { return niveau; }
    public int getTour() { return tour; }
    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        SwingUtilities.invokeLater(() -> new FenetreJeu(jeu.i));
    }
}
