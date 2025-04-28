package ile;
import java.util.ArrayList;
import java.util.List;
//classe zone
public class Zone{
    private final int x, y; 
    private int n; // en introduisant n, il était plus facile de réaliser l'inondation des zones aléatoire (pile inondation)
    private Etat etat;
    private Type type;
    //2 constructeurs differents 
    public Zone(int x, int y, Type type){
        this.x = x;
        this.y = y;
        this.etat = Etat.normale;
        this.type = type;
    }
    public Zone(int x, int y, Type type, int n){
        this.x = x;
        this.y = y;
        this.n = n;
        this.etat = Etat.normale;
        this.type = type;
    }

    public int getX(){ return x ;}
    public int getY(){ return y ;}
    public int getN(){ return n ;}
    public int getX150(){ return x * 150;}
    public int getY170(){ return y * 170;}
    public Etat getEtat(){ return etat;}
    public Type getType(){ return type;}
    public void setType(Type t){ type = t;}
    public void setN(int n){ this.n = n;}
    public String toString(){ return "Zone : " + "x = " + x + ", y = " + y + ", etat = " + etat + ", type = " + type;} //était utilisée pour les tests

    public boolean traversable(){ return etat != Etat.submergee && type != Type.vide;}
    //verifier si la zone est adjacente avec diagonales ou pas
    public boolean estAdjacente(Zone other, boolean diagonales) {
        int dx = Math.abs(this.x - other.x);
        int dy = Math.abs(this.y - other.y);
        if (diagonales) return (dx <= 1 && dy <= 1 && (dx + dy != 0));
        return (dx + dy == 1);
    }
    // 2e parametre pour savoir si on veut les diagonales ou pas
    public List<Zone> getZonesAdjacentes(Ile ile, boolean diagonales) {
        List<Zone> voisines = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                if (!diagonales && Math.abs(dx) + Math.abs(dy) != 1) continue;
                Zone voisine = ile.getZone(x + dx, y + dy);
                if (voisine != null && voisine.traversable()) voisines.add(voisine);
            }
        }
        return voisines;
    }
    
    public void inonder(){ 
        if(etat == Etat.normale && type != Type.vide) etat = Etat.inondee;
        else if(etat == Etat.inondee) submerger();
    }
    public void submerger(){ 
        etat = Etat.submergee;
        type = Type.vide;
    }
    public void assecher(){ 
        if(etat == Etat.inondee) etat = Etat.normale;
        else return;
    }
}