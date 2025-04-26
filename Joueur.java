import java.util.List;
import java.util.Queue;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedList;

public class Joueur {
    private int nom;
    private int id;
    private Zone position;
    private Role role;
    private boolean actionSpeciale = false;
    private int nbActions = 3;
    private boolean secondeAssechementIngenieur = false; // À réinitialiser à chaque tour
    private ArrayList<Element> artefacts;
    // j'ai ajouté ça pour les cartes du joueur
    private ArrayList<Carte> cartes_joueur;
    private List<Carte>cles; // 0 - eau, 1 - feu, 2 - terre, 3 - air
    private Image image;

    public Joueur(int nom, Zone position, Image image, int i, Role role) {
        this.role = role;
        this.nom = nom;
        this.id = i;
        this.position = position;
        this.artefacts = new ArrayList<>();
        this.cartes_joueur = new ArrayList<Carte>(5);
        this.cles = new ArrayList<Carte>(4); 
        this.image = image;
    }
    public int getX(){return this.position.getX150(); }
    public int getY(){return this.position.getY170(); }
    public Role getRole(){ return role; }
    public int getNbActions(){ return nbActions; }
    public Zone getPos(){ return position; }
    //public String getNom(){ return nom; }
    public int getId(){ return id; }
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
    
    public void deplacerAutreJoueur(Joueur cible, Zone destination, Ile ile) {
        if (this.role != Role.navigateur || cible == this || !actionSpeciale) return;

        List<Zone> adjacentes1 = cible.position.getZonesAdjacentes(ile, false);
        List<Zone> adjacentes2 = new ArrayList<>();
        for (Zone z1 : adjacentes1) adjacentes2.addAll(z1.getZonesAdjacentes(ile, false));
    
        Set<Zone> deplacementsPossibles = new HashSet<>(adjacentes1);
        deplacementsPossibles.addAll(adjacentes2);
    
        if (deplacementsPossibles.contains(destination)){
            cible.position = destination;
            actionFaite();
            actionSpeciale = true;
        }
        return;
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
        return true;
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
        if (!s.contains("tresor")){
            System.out.println("Carte non valide.");
            return;
        };
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
        Zone z = position;
        if(z.getType() != Type.element_a && e == Element.air){
            System.out.println("Zone non valide.");
            return;
        }
        else if(z.getType() != Type.element_f && e == Element.feu){
            System.out.println("Zone non valide.");
            return;
        }
        else if(z.getType() != Type.element_t && e == Element.terre){
            System.out.println("Zone non valide.");
            return;
        }
        else if(z.getType() != Type.element_e && e == Element.eau){
            System.out.println("Zone non valide.");
            return;
        }
        for(Joueur p : i.getJoueurs()) if(p != this && p.contientArtefact(e)){
            System.out.println("Artefact déjà pris par un autre joueur.");
            return;
        }
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
        else System.out.println("Pas assez de cartes.");
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
        return getDerniereCarte().getTypeCarte() == TypeCarte.montee_des_eaux;
    }
    public Image getImage(){
        return this.image;
    }
    public void jouerCarteSpeciale(Carte c, Ile i, PaquetdeCartes paquet){
        if(c.getTypeCarte() == TypeCarte.helicoptere){
            System.out.println("Action 1 ou 2?");
            Scanner sc = new Scanner(System.in);
            int action = sc.nextInt();
            if(action == 1){
                System.out.println("Déplacement vers une autre zone, preciser la zone : (x, y) et aprés le id de joueur à déplacer");
                sc = new Scanner(System.in);
                int x = sc.nextInt();
                int y = sc.nextInt();
                Zone z = i.getZone(x, y);
                int j = sc.nextInt();
                Joueur cible = i.getJoueurs().get(j);
                if(cible == this) deplacer(z, i);
                else deplacerAutreJoueur(cible, z, i);
                paquet.poser(c);
                retirerCarte(c);
            }
            else if(action == 2){
                System.out.println("Déplacement vers l'heliport");
                Zone h = i.getZoneHeliport();
                for(Joueur j : i.getJoueurs()){
                    if(j == this) continue;
                    deplacerAutreJoueur(j, h, i);
                }
                paquet.poser(c);
                retirerCarte(c);
            }
        }
        else if(c.getTypeCarte() == TypeCarte.sacs_de_sable){
            System.out.println("Préciser la zone à assécher : (x, y)");
            Scanner sc = new Scanner(System.in);
            int x = sc.nextInt();
            int y = sc.nextInt();
            Zone z = i.getZone(x, y);
            z.assecher();
            paquet.poser(c);
            retirerCarte(c);
        }
        else System.out.println("Carte non jouable.");
    }
    public Carte getCarte(int i){
        if(i < 0 || i >= cartes_joueur.size()){
            System.out.println("Index invalide.");
            return null;
        }
        return cartes_joueur.get(i);
    }
    public void finTour(){ nbActions = 0;}
}

enum Role{ pilote, ingenieur, explorateur, navigateur, plongeur, messager }