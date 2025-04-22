public class Zone{
    private int /*final*/ x, y;
    private Etat etat;
    private Type type;

    public Zone(int x, int y, Type type){
        this.x = x;
        this.y = y;
        this.etat = Etat.normale;
        this.type = type;
    }

    public int getX(){ return x;}
    public int getY(){ return y;}
    public Etat getEtat(){ return etat;}
    public Type getType(){ return type;}
    public String toString(){ return "Zone : " + "x = " + x + ", y = " + y + ", etat = " + etat + ", type = " + type;}
    public boolean traversable(){ return etat != Etat.submergee;}
    public void inonder(){ 
        if(etat == Etat.normale) etat = Etat.inondee;
        else etat = Etat.submergee;
    }
    public void submerger(){ etat = Etat.submergee;}
    public void assecher(){ 
        if(etat == Etat.inondee) etat = Etat.normale;
        else return;
    }
}

enum Etat{ normale, inondee, submergee}
enum Type{ normale, heliport, element_a, element_t, element_e, element_f}
enum Element{ air, terre, eau, feu}