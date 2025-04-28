package joueur;

import cartes.Carte;
import cartes.TypeCarte;
import ile.Ile;

public class TestJoueur{
    public static void main(String[] args) {
    Ile ile = new Ile(6, 6, false);
    Joueur j = ile.getJoueurs().get(0);
    System.out.println("Nom du joueur : " + j.getNom() + ", role : " + j.getRole() + ", position : " + j.getPos().getX() + ", " + j.getPos().getY());
    j.setRole(Role.pilote);
    System.out.println("Nom du joueur : " + j.getNom() + ", role : " + j.getRole() + ", position : " + j.getPos().getX() + ", " + j.getPos().getY());
    System.out.println("Est sur l'heliport au debut : " + (ile.getZoneHeliport() == j.getPos()));
    Carte c = new Carte(TypeCarte.tresor_air);
    j.ajouterCarte(c);
    for(Carte c1 : j.getMain()) System.out.println("Carte : " + c1.getTypeCarte().toString() + " de " + j.getNom()); // tresor_air
    j.retirerCarte(c);
    for(Carte c1 : j.getMain()) System.out.println("Carte : " + c1.getTypeCarte().toString()+ " de" + j.getNom()); // pas d'affichage car vide
    Joueur j2 = ile.getJoueurs().get(1);
    j.ajouterCarte(c);
    for(Carte c1 : j.getMain()) System.out.println("Carte : " + c1.getTypeCarte().toString() + " de " + j.getNom()); // tresor_air
    for(Carte c1 : j2.getMain()) System.out.println("Carte : " + c1.getTypeCarte().toString() + " de " + j2.getNom()); // pas d'affichage car vide
    j.donnerCarte(j2, c);
    for(Carte c1 : j.getMain()) System.out.println("Carte : " + c1.getTypeCarte().toString() + " de " + j.getNom()); // pas d'affichage car vide
    for(Carte c1 : j2.getMain()) System.out.println("Carte : " + c1.getTypeCarte().toString() + " de " + j2.getNom()); // tresor_air
    for(int i : j2.compterCles())  System.out.println("Nombre de cles : " + i); // eau feu terre air
    System.out.println(j2.monteeDesEauxTiree()) ; // false
    j2.ajouterCarte(new Carte(TypeCarte.montee_des_eaux));
    System.out.println(j2.monteeDesEauxTiree()); // true
    }
}
