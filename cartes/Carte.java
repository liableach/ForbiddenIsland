package cartes;
// classe principale pour les cartes
public class Carte {
    private TypeCarte type;
    private int n; // pour les cartes inondations, car plus facile de gérer les cartes inondations avec un nombre donné

    public Carte(TypeCarte t){ type = t; }
    public Carte(TypeCarte t, int n){ type = t; this.n = n; }
    public TypeCarte getTypeCarte(){ return this.type; }
    public int getN(){ return n; }
    public boolean estSpeciale(){ return type == TypeCarte.helicoptere || type == TypeCarte.sacs_de_sable; }
}