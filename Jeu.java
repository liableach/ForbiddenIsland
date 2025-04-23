import java.util.List;

public class Jeu {
    Ile i;
    List<Joueur> joueurs;
    private int niveau = 0;

    public Jeu(){
        i = new Ile(6, 6);
    }

    public static void main(String[] args) {
        Jeu jeu = new Jeu();
    }
}
