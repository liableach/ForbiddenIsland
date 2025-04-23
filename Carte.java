public class Carte {
    private TypeCarte type;
    private int n;

    public Carte(TypeCarte t){
        type = t;
    }
    public Carte(TypeCarte t, int n){
        type = t; this.n = n;
    }
}
enum TypeCarte {
    inondation,
    tresor_feu(Element.feu),
    tresor_eau(Element.eau),
    tresor_terre(Element.terre),
    tresor_air(Element.air),
    montee_des_eaux,
    helicoptere,
    sacs_de_sable;

    private final Element element;

    TypeCarte(Element element){
        this.element = element;
    }
    TypeCarte(){
        this.element = null;
    }

    public Element getElement(){
        return element;
    }
    TypeCarte getTypeCarte(){
        return this.type;
    }
}