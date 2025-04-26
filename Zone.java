import java.util.ArrayList;
import java.util.List;

public class Zone{
    private final int x, y; 
    private int n;
    private Etat etat;
    private Type type;

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
    public int getX150(){return x * 150;}
    public int getY170(){return y * 170;}
    public Etat getEtat(){ return etat;}
    public Type getType(){ return type;}
    public void setType(Type t){ type = t;}
    public String toString(){ return "Zone : " + "x = " + x + ", y = " + y + ", etat = " + etat + ", type = " + type;}
    public boolean traversable(){ return etat != Etat.submergee && type != Type.vide;}
    public boolean estAdjacente(Zone z){
        if(z == null) return false;
        if(Math.abs(x - z.getX()) <= 1 && Math.abs(y - z.getY()) <= 1) return true;
        return false;
    }
    public boolean estAdjacente(Zone other, boolean diagonales) {
        int dx = Math.abs(this.x - other.x);
        int dy = Math.abs(this.y - other.y);
        if (diagonales) return (dx <= 1 && dy <= 1 && (dx + dy != 0));
        return (dx + dy == 1);
    }
    
    public List<Zone> getZonesAdjacentes(Ile ile, boolean diagonales) {
        List<Zone> voisines = new ArrayList<>();
    
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // on saute le centre (0,0)
                if (dx == 0 && dy == 0) continue;
    
                // si on ne veut pas les diagonales, on saute les déplacements diagonaux
                if (!diagonales && Math.abs(dx) + Math.abs(dy) != 1) continue;
    
                Zone voisine = ile.getZone(x + dx, y + dy);
                if (voisine != null && voisine.traversable()) {
                    voisines.add(voisine);
                }
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

enum Etat{ normale, inondee, submergee }
enum Type{ normale, heliport, element_a, element_t, element_e, element_f, vide, feu, eau, terre, air }
enum Element{ air, terre, eau, feu }