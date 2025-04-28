package cartes;
import ile.Element;
public enum TypeCarte {
    // types de cartes, il y a des réferences au type Element pour simplifier la vie
    inondation, tresor_feu(Element.feu), tresor_eau(Element.eau), tresor_terre(Element.terre), tresor_air(Element.air), montee_des_eaux, helicoptere, sacs_de_sable;
    private final Element element;
    // besoin des méthodes pour utiliser les réferences 
    TypeCarte(Element element){ this.element = element; }
    TypeCarte(){ this.element = null; }
    public Element getElement(){ return element; }
}