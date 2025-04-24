import javax.swing.* ;
import java.awt.*;


class Vue extends JPanel {
    private Ile ile;
    public int Width = 1920;
    public int Height = 1080;
    private final Image Calice_de_l_onde = new ImageIcon(getClass().getResource("data/Calice_de_l_onde.png")).getImage();
    private final Image Cristal_ardent = new ImageIcon(getClass().getResource("data/Cristal_ardent.png")).getImage();
    private final Image Pierre_sacree = new ImageIcon(getClass().getResource("data/Pierre_sacree.png")).getImage();
    private final Image Statue_du_Zephir = new ImageIcon(getClass().getResource("data/Statue_du_Zephir.png")).getImage();


    public Vue(Ile ile) {
        this.ile = ile;
        setPreferredSize(new Dimension(Width, Height)); // 100px par case
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
                }
                if(z.getEtat() == Etat.inondee) g.setColor(Color.CYAN);
                g.fillRect(x * 100 + Width/5, y * 100 + Height/5, 100, 100);
                g.setColor(Color.BLACK);
                g.drawRect(x * 100 + Width/5, y * 100 + Width/5, 100, 100);

                g.drawString(z.getType().toString(), x * 100 + 10 + Width/5, y * 100 + 20 + Width/5);
            }
        }
        //affichage des artefacts en sur les 4 coins de l'île 100 x 100
        g.drawImage(Calice_de_l_onde, 0, 0, 100, 100, null);
        g.drawImage(Pierre_sacree, 0, 735, 100, 100, null);
        g.drawImage(Cristal_ardent, 620, 0, 100, 100, null);
        g.drawImage(Statue_du_Zephir, 620, 735, 100, 100, null);
    }
}