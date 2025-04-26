import javax.swing.* ;
import java.awt.*;
import java.util.ArrayList;


class Vue extends JPanel {
    private Ile ile;
    public int Width = 1920;
    public int Height = 1080;
    private final Image Calice_de_l_onde = new ImageIcon(getClass().getResource("data/Calice_de_l_onde.png")).getImage();
    private final Image Cristal_ardent = new ImageIcon(getClass().getResource("data/Cristal_ardent.png")).getImage();
    private final Image Pierre_sacree = new ImageIcon(getClass().getResource("data/Pierre_sacree.png")).getImage();
    private final Image Statue_du_Zephir = new ImageIcon(getClass().getResource("data/Statue_du_Zephir.png")).getImage();
    private final Image fond = new ImageIcon(getClass().getResource("data/L_ile_interdite_teaseur.jpg")).getImage();
    private final Image heliport_non_inondee = new ImageIcon(getClass().getResource("data/heliport_non_inondee.png")).getImage();
    private final Image heliport_inondee = new ImageIcon(getClass().getResource("data/heliport_inondee.png")).getImage();


    public Vue(Ile ile) {
        this.ile = ile;
        setPreferredSize(new Dimension(Width, Height)); // 100px par case
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(fond, 0, 0, Width, Height-50, null); // Affichage de l'image de fond
        //Positions des cartes inondations
        g.drawRect( Width/2, 50, 150, 200);
        g.fillRect( Width/2, 50, 150, 200);

        //Positions des cartes trésors
        g.drawRect( Width/2, 150 + 100 + 40, 150, 200);
        g.fillRect( Width/2, 150 + 100 + 40, 150 , 200);

        //Positions de la défausse inondations
        g.drawRect( Width/2 + 200, 50, 150, 200);
        g.fillRect( Width/2 + 200, 50, 150, 200);

        //Positions de la défausse trésors
        g.drawRect( Width/2 + 200, 150 + 100 + 40, 150, 200);
        g.fillRect( Width/2 + 200, 150 + 100 + 40, 150, 200);

        //Position de la vue "Tour de "
        g.drawRect(Width-300, 10, 300, 70);

        //Position de l'affichage du niveau
        g.drawRect(Width-600, 10, 250, 70);
        //Affichage des cartes des joueurs et leurs récompense
        g.drawRect(Width/2 - 50, Height/2 - 50, Width/2 + 30, 500);

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++){
                Zone z = ile.getZone(x, y);
                if (z == null || z.getType() == Type.vide) continue;
                switch (z.getType()) {
                    case normale -> g.setColor(Color.LIGHT_GRAY);
                    case heliport -> g.setColor(Color.YELLOW);
                    case element_a -> g.setColor(Color.CYAN);
                    case element_t -> g.setColor(Color.GRAY);
                    case element_e -> g.setColor(Color.BLUE);
                    case element_f -> g.setColor(Color.RED);
                    default -> g.setColor(Color.WHITE);
                }
                switch (z.getEtat()){
                    case inondee -> g.setColor(Color.CYAN);
                    case submergee -> g.setColor(Color.WHITE);
                }
                g.fillRect(x * 150, y * 170 , 150 , 170);
                g.setColor(Color.BLACK);
                g.drawRect(x * 150 , y * 170 , 150, 170);
                g.drawString(z.getType().toString(), x*150 + 50, y*170 + 85);
            }
        }
        

        //affichage des artefacts en sur les 4 coins de l'île 100 x 100
        g.drawImage(Calice_de_l_onde, 0, 0, 150, 170, null);
        g.drawImage(Pierre_sacree, Width/3 + 110 ,  0, 150, 170, null);
        g.drawImage(Cristal_ardent,  0, Height - 230, 150, 170, null);
        g.drawImage(Statue_du_Zephir, Width/3 + 110, Height - 230 , 150, 170, null);

        //affichage de l'héliport
        g.drawImage(heliport_non_inondee,ile.getZoneHeliport().getX150() ,ile.getZoneHeliport().getY170(), 150, 170, null);

        ArrayList<Joueur> joueurs = ile.getJoueurs();
        //affichage des pions sur l'île(joueurs) en début de partie.
        g.drawImage(joueurs.get(0).getImage(), ile.getZoneHeliport().getX150() + 5 ,ile.getZoneHeliport().getY170() + 5, 40, 40, null);
        g.drawImage(joueurs.get(1).getImage(), ile.getZoneHeliport().getX150() + 100 ,ile.getZoneHeliport().getY170() + 5 , 40, 40, null);
        g.drawImage(joueurs.get(2).getImage(), ile.getZoneHeliport().getX150() + 5 ,ile.getZoneHeliport().getY170() + 110 , 40, 40, null);
        g.drawImage(joueurs.get(3).getImage(), ile.getZoneHeliport().getX150()  + 100,ile.getZoneHeliport().getY170() + 110, 40, 40, null);
    }
}