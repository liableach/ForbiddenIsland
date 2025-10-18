package cartes;

public class TestCartes{
    public static boolean containsTresor(PaquetdeCartes paquet, Carte carte) {
        return paquet.getTresor().contains(carte);
    }
    public static boolean containsInondation(PaquetdeCartes paquet, Carte carte) {
        return paquet.getInondations().contains(carte);
    }
    public static void main(String[] args) {
        PaquetdeCartes paquet = new PaquetdeCartes();
        System.out.println("Taille de la pile d'inondation : " + paquet.getTailleInondations());
        System.out.println("Taille de la pile de tresor : " + paquet.getTailleTresors());
        System.out.println("Taille de la défausse d'inondation : " + paquet.getTailleDefausseInondations());
        System.out.println("Taille de la defausse de tresor: " + paquet.getTailleDefausseTresors());
        
        // Test des méthodes de mélange
        paquet.melanger_tresor();
        paquet.melanger_inondations();
        paquet.melanger_defausse_inondations();
        paquet.melanger_defausse_tresors();

        //d'après le mélange
        System.out.println("Taille de la pile d'inondation : " + paquet.getTailleInondations());
        System.out.println("Taille de la pile de tresor : " + paquet.getTailleTresors());
        System.out.println("Taille de la défausse d'inondation : " + paquet.getTailleDefausseInondations());
        System.out.println("Taille de la defausse de tresor: " + paquet.getTailleDefausseTresors());
        
        // Test de tirage de cartes
        
        Carte carte = paquet.tirerCarte_tresor();
        System.out.println("Carte tirée : " + carte.getTypeCarte());
        // doit renvoyer false car la carte est retirée de la pile de trésor
        System.out.println(containsTresor(paquet, carte));
        paquet.poser(carte);
        System.out.println("Carte posée : " + carte.getTypeCarte());
        // doit renvoyer true car la carte est posée dans la défausse de trésor
        System.out.println(paquet.getDefausseTresors().contains(carte));


        carte = paquet.tirerCarte_inondations();
        System.out.println("Carte tirée : " + carte.getTypeCarte() + ", N : " + carte.getN());
        // doit renvoyer false aussi
        System.out.println(containsInondation(paquet, carte));
        paquet.poser(carte);
        System.out.println("Carte posée : " + carte.getTypeCarte() + ", N : " + carte.getN());
        // doit renvoyer true
        System.out.println(paquet.getDefausseInondations().contains(carte));

        // Test des méthodes de remplacement au sommet
        paquet.replacerAuSommet_tresors();
        System.out.println("Taille de la pile de tresor : " + paquet.getTailleTresors()); //28
        System.out.println("Taille de la défausse de tresor: " + paquet.getTailleDefausseTresors()); //0

        paquet.replacerAuSommet_inondations();
        System.out.println("Taille de la pile d'inondation : " + paquet.getTailleInondations()); //24
        System.out.println("Taille de la défausse d'inondation : " + paquet.getTailleDefausseInondations()); //0
    }
}
