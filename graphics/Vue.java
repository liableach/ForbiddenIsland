package graphics;
import javax.swing.* ;

import cartes.PaquetdeCartes;
import ile.Ile;
import ile.Zone;
import ile.Type;
import ile.Etat;
import joueur.Joueur;

import java.awt.*;
import java.util.ArrayList;


public class Vue extends JPanel{
    private Ile ile;
    private PaquetdeCartes paquet;

    public int Width = 1920;
    public int Height = 1080;
    private final Image Calice_de_l_onde = new ImageIcon(getClass().getResource("../data/Calice_de_l_onde.png")).getImage();
    private final Image Cristal_ardent = new ImageIcon(getClass().getResource("../data/Cristal_ardent.png")).getImage();
    private final Image Pierre_sacree = new ImageIcon(getClass().getResource("../data/Pierre_sacree.png")).getImage();
    private final Image Statue_du_Zephir = new ImageIcon(getClass().getResource("../data/Statue_du_Zephir.png")).getImage();
    private final Image fond = new ImageIcon(getClass().getResource("../data/L_ile_interdite_teaseur.jpg")).getImage();
    private final Image heliport_non_inondee = new ImageIcon(getClass().getResource("../data/heliport_non_inondee.png")).getImage();
    private final Image heliport_inondee = new ImageIcon(getClass().getResource("../data/heliport_inondee.png")).getImage();
    private final Image cartes_inondationsImage = new ImageIcon(getClass().getResource("../data/cartes_inondations.png")).getImage();
    private final Image cartes_tresorImage = new ImageIcon(getClass().getResource("../data/cartes_tresors.png")).getImage();

    public Vue(Ile ile, PaquetdeCartes paquet) {
        this.paquet = paquet;
        this.ile = ile;
        setPreferredSize(new Dimension(Width, Height)); // 100px par case
    }
    public void update() {
        revalidate();
        repaint();
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //polices
        Font original = g.getFont();
        Font grand = original.deriveFont(Font.ROMAN_BASELINE, 26f);
        
        //fond
        g.drawImage(fond, 0, 0, Width, Height-50, null);
        
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++){
                Zone z = ile.getZone(x, y);
                if (z == null || z.getType() == Type.vide) continue;
                switch (z.getType()) {
                    case normale -> g.setColor(Color.LIGHT_GRAY);
                    default -> g.setColor(Color.WHITE);
                }
                switch (z.getEtat()){
                    case inondee -> g.setColor(Color.CYAN);
                    case submergee -> g.setColor(Color.WHITE);
                    default -> g.setColor(Color.LIGHT_GRAY);
                }
                g.fillRect(x * 150, y * 170 , 150 , 170);
                g.setColor(Color.BLACK);
                g.drawRect(x * 150 , y * 170 , 150, 170);
                g.drawString(z.getType().toString(), x*150 + 50, y*170 + 85);
            }
        }
        //affichage des infos
        g.setFont(grand);
        g.setColor(Color.green);
        g.drawString("Tour de : " + ile.getJoueurs().get(ile.getCurrentJoueur()).getNom() + ", id : " + ile.getJoueurs().get(ile.getCurrentJoueur()).getId(),Width-380, 40);
        g.setColor(Color.cyan);
        g.drawString("Niveau d'eau : " + ile.getNiveau(),Width-380, 280);
        //affichage de nombre de cartes dans les piles
        int n = paquet.getTailleTresors();
        int nn = paquet.getTailleInondations();
        int nnn = paquet.getTailleDefausseTresors();
        int nnnn = paquet.getTailleDefausseInondations();
        // positions des cartes trésors
        if(n !=0) g.drawImage(cartes_tresorImage, Width/2, 290, 150, 200, null);
        // positions des cartes inondations
        if(nn !=0) g.drawImage(cartes_inondationsImage, Width/2, 50, 150, 200, null);
        // positions de la défausse trésors
        if(nnn!=0) g.drawImage(cartes_tresorImage, Width/2 + 270, 290, 150, 200, null);
        // positions de la défausse inondations
        if(nnnn !=0) g.drawImage(cartes_inondationsImage, Width/2 + 270, 50, 150, 200, null);
        // affichage du nombre de cartes dans les piles
        g.setColor(Color.WHITE);
        g.drawString("Pile trésor : " + n, Width/2, 280);
        g.drawString("Pile inondation : " + nn, Width/2, 40);
        g.drawString("Défausse trésor : " + nnn, Width/2+270, 280);
        g.drawString("Défausse inondation : " + nnnn, Width/2+270, 40);
        // affichage d'heliport
        if(ile.getZoneHeliport().getEtat() == Etat.normale)g.drawImage(heliport_non_inondee,ile.getZoneHeliport().getX150() ,ile.getZoneHeliport().getY170(), 150, 170, null);
        else g.drawImage(heliport_inondee,ile.getZoneHeliport().getX150() ,ile.getZoneHeliport().getY170(), 150, 170, null);
        // affichage des joueurs
        ArrayList<Joueur> joueurs = ile.getJoueurs();
        Joueur j1 = joueurs.get(0);
        Joueur j2 = joueurs.get(1);
        Joueur j3 = joueurs.get(2);
        Joueur j4 = joueurs.get(3);
        g.drawImage(joueurs.get(0).getImage(), j1.getX() + 5 ,  j1.getY() + 5, 40, 40, null);
        g.drawImage(joueurs.get(1).getImage(), j2.getX() + 100 ,j2.getY() + 5 , 40, 40, null);
        g.drawImage(joueurs.get(2).getImage(), j3.getX() + 5 ,  j3.getY() + 110 , 40, 40, null);
        g.drawImage(joueurs.get(3).getImage(), j4.getX()  + 100,j4.getY() + 110, 40, 40, null);
        //affichage des artefacts en sur les 4 coins de l'île 150 x 170
        g.drawImage(Calice_de_l_onde, 0, 0, 150, 170, null);
        g.drawImage(Pierre_sacree, Width/3 + 110 ,  0, 150, 170, null);
        g.drawImage(Cristal_ardent,  0, Height - 230, 150, 170, null);
        g.drawImage(Statue_du_Zephir, Width/3 + 110, Height - 230 , 150, 170, null);
    }
}