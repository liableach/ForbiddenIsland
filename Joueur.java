import java.util.List;
import java.util.Queue;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedList;

public class Joueur {
    private int nom;
    private Zone position;
    private boolean alive;
    private Role role;
    private boolean actionSpeciale = false;
    private int nbActions = 3;
    private boolean secondeAssechementIngenieur = false; // À réinitialiser à chaque tour
    private ArrayList<Element> artefacts;
    // j'ai ajouté ça pour les cartes du joueur
    private ArrayList<Carte> cartes_joueur;
    private List<Carte>cles; // 0 - eau, 1 - feu, 2 - terre, 3 - air
    private Image image;

    public Joueur(int nom, Zone position, Image image){
        this.nom = nom;
        this.position = position;
        this.artefacts = new ArrayList<>();
        this.cartes_joueur = new ArrayList<Carte>(5);
        this.cles = new ArrayList<Carte>(4); 
        this.image = image;
    }
    public int getNbActions(){ return nbActions; }
    public boolean estVivant(){ return alive; }
    
    public void actionFaite(){ nbActions--; }
    public void actionsReset(){ nbActions = 3; }
    public void resetActionSpeciale(){ actionSpeciale = false; }
    public boolean deplacementPossible(Zone z, Ile ile){
        return switch (role){
            case explorateur -> position.estAdjacente(z, true);
            case pilote -> !actionSpeciale || position.estAdjacente(z, false);
            case plongeur -> cheminPlongeurPossible(z, ile);
            case navigateur -> position.estAdjacente(z, false); // déplacement normal pour lui-même
            default -> position.estAdjacente(z, false);
        };
    }

    public void deplacer(Zone z, Ile i){ 
        if (deplacementPossible(z, i)){
            if (role == Role.pilote && !actionSpeciale && !position.estAdjacente(z, false)) actionSpeciale = true;
            position = z;
            actionFaite();
        }
    }
    
    public boolean deplacerAutreJoueur(Joueur cible, Zone destination, Ile ile) {
        if (this.role != Role.navigateur || cible == this || !actionSpeciale) return false;

        List<Zone> adjacentes1 = cible.position.getZonesAdjacentes(ile, false);
        List<Zone> adjacentes2 = new ArrayList<>();
        for (Zone z1 : adjacentes1) adjacentes2.addAll(z1.getZonesAdjacentes(ile, false));
    
        Set<Zone> deplacementsPossibles = new HashSet<>(adjacentes1);
        deplacementsPossibles.addAll(adjacentes2);
    
        if (deplacementsPossibles.contains(destination)){
            cible.position = destination;
            actionFaite();
            actionSpeciale = true;
            return true;
        }
        return false;
    }
    
    private boolean cheminPlongeurPossible(Zone destination, Ile ile){
        if (destination.getType() == Type.vide && destination.getEtat() != Etat.submergee) return false;
        Set<Zone> visitees = new HashSet<>();
        Queue<Zone> aExplorer = new LinkedList<>();
        aExplorer.add(position);
        visitees.add(position);
        while (!aExplorer.isEmpty()) {
            Zone actuelle = aExplorer.poll();
            if (actuelle.equals(destination)) return false;
            for (Zone voisine : actuelle.getZonesAdjacentes(ile, false)) {
                if (!visitees.contains(voisine) /*&& voisine.getEtat() != Etat.submergee*/) {
                    visitees.add(voisine);
                    aExplorer.add(voisine);
                }
            }
        }
        return false;
    }    
    public void assecher(Zone z, Ile ile){
        boolean adjacente;
        if (role == Role.explorateur) adjacente = position.estAdjacente(z, true); // Diagonales incluses
        else adjacente = position.estAdjacente(z, false); // Seulement orthogonales
        boolean estZoneValide = z != null && (z == position || adjacente);
        if (estZoneValide && z.getEtat() == Etat.inondee) {
            z.assecher();
            if (role == Role.ingenieur) {
                if (secondeAssechementIngenieur) {
                    actionFaite();
                    secondeAssechementIngenieur = false;
                } else secondeAssechementIngenieur = true;
            } else actionFaite();
        }
    }
    public int nbCartes(){ return cartes_joueur.size(); }
    public void donnerCarte(Joueur j, Carte c){
        if(this.role != Role.messager || j.position != this.position) return;
        String s = c.getTypeCarte().toString();
        if (!s.contains("tresor")) throw new IllegalStateException("Carte non échangeable.");
        if(cartes_joueur.contains(c) && j.nbCartes() < 5){
            retirerCarte(c);
            j.ajouterCarte(c);
            actionFaite();
        }
    }
    public boolean contientArtefact(Element e){
        for(Element a : artefacts){
            if(a == e) return true;
        }
        return false;
    }
    public List<Integer> compterCles(){
        List<Integer> res = new ArrayList<>(List.of(0, 0, 0, 0));
        for (Carte c : cartes_joueur) {
            TypeCarte type = c.getTypeCarte();
            switch (type) {
                case tresor_eau -> res.set(0, res.get(0) + 1);
                case tresor_feu -> res.set(1, res.get(1) + 1);
                case tresor_terre -> res.set(2, res.get(2) + 1);
                case tresor_air -> res.set(3, res.get(3) + 1);
                default -> {}
            }
        }
        return res;
    }
    private boolean correspond(TypeCarte t, Element e){ return (t.getElement() == e); }
    public void recupererArtefact(Element e, Ile i, PaquetdeCartes cartes){
        for(Joueur p : i.getJoueurs()) if(p != this && p.contientArtefact(e)) throw new IllegalStateException("Artefact déjà pris par un autre joueur.");
        int ind = switch(e){
            case eau -> 0;
            case feu -> 1;
            case terre -> 2;
            case air -> 3;
        };
        if(compterCles().get(ind) >= 4){
            ajouterArtefact(e);
            actionFaite();
            int toRemove = 4;
            Iterator<Carte> it = cartes_joueur.iterator();
            while (it.hasNext() && toRemove > 0){
                Carte c = it.next();
                if (correspond(c.getTypeCarte(), e)){
                    cartes.poser(c);
                    it.remove();
                    toRemove--;
                }
            }
            i.getZone(position.getX(), position.getY()).setType(Type.normale);
        }
        else throw new IllegalStateException("Pas assez de cartes.");
    }
    public void ajouterCarte(Carte c){ cartes_joueur.add(c); }
    public void retirerCarte(Carte c){ cartes_joueur.remove(c); }
    public void ajouterArtefact(Element e){ artefacts.add(e); }
    public int nbArtefacts(){ return artefacts.size(); }
    public Carte getDerniereCarte(){
        if(cartes_joueur.size() == 0) return null;
        return cartes_joueur.get(cartes_joueur.size() - 1);
    }
    public boolean monteeDesEauxTiree(){
        for(Carte c : cartes_joueur){
            if(c.getTypeCarte() == TypeCarte.montee_des_eaux) return true;
        }
        return false;
    }

    public Image getImage(){
        return this.image;
    } 
}

enum Role{ pilote, ingenieur, explorateur, navigateur, plongeur, messager }