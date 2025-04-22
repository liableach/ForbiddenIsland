import java.util.List;

public class Jeu {
    Ile i;
    List<Joueur> joueurs;

    public Jeu(){
        i = new Ile(6, 6);
    }

    public static void main(String[] args) {
        Jeu jeu = new Jeu();
    }
}
