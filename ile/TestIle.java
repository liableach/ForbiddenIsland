package ile;

import joueur.Joueur;

public class TestIle {
    public static void main(String[] args) {
        Zone z = new Zone(0, 0, Type.normale);
        System.out.println(z.getX() + " " + z.getY() + " " + z.getType() + " " + z.getEtat());
        z.inonder();
        System.out.println("Est traversable? " + z.traversable());
        System.out.println(z.getX() + " " + z.getY() + " " + z.getType() + " " + z.getEtat());
        z.inonder();    
        System.out.println(z.getX() + " " + z.getY() + " " + z.getType() + " " + z.getEtat());
        System.out.println("Est traversable? " + z.traversable());
        z.assecher();

        Ile i = new Ile(6, 6, false); // pour ne donnder pas des roles 
        // les zones vides pour les tuiles qu'on utilise pas
        for(Zone row[] : i.getGrille()) for(Zone z1 : row) System.out.println(z1.getX() + " " + z1.getY() + " " + z1.getType() + " " + z1.getEtat());
        // les zones adjacentes
        for(Zone row[] : i.getGrille()) for(Zone z3 : row) for(Zone z4 : z3.getZonesAdjacentes(i, false)) System.out.println("Zones adjacates pour : " + z3.getX() + " " + z3.getY() + " : " + z4.getX() + " " + z4.getY()) ;
        for(Joueur j : i.getJoueurs()) System.out.println("Joueur : " + j.getNom() + " " + j.getRole());
        for(Joueur joueur : i.getJoueurs()) System.out.println(joueur.getNom() + " " + joueur.getRole() + " sur l'heliport? " + (i.getZoneHeliport() == joueur.getPos())); // les roles vaut nulles parce que pas encore choisis
        System.out.println("Niveau : " + i.get_niveau_eau()); // doit valoir 2 au debut
    }
}
