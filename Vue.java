import javax.swing.* ;
import java.awt.*;

class Vue extends JPanel {
    private Ile ile;
    public int Width = 900;
    public int Height = 900;
    private final Image Calice_de_l_onde = new ImageIcon(getClass().getResource("Calice_de_l_onde.png")).getImage();
    private final Image Cristal_ardent = new ImageIcon(getClass().getResource("Cristal_ardent.png")).getImage();
    private final Image Pierre_sacree = new ImageIcon(getClass().getResource("Pierre_sacree.png")).getImage();
    private final Image Statue_du_Zephir = new ImageIcon(getClass().getResource("Statue_du_Zephir.png")).getImage();


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
                if (z == null) continue;
                switch (z.getEtat()){
                    case normale -> g.setColor(Color.LIGHT_GRAY);
                    case inondee -> g.setColor(Color.CYAN);
                    case submergee -> g.setColor(Color.BLUE);
                }
                switch (z.getType()) {
                    case normale -> g.setColor(Color.LIGHT_GRAY);
                    case heliport -> g.setColor(Color.YELLOW);
                    case element_a -> g.setColor(Color.CYAN);
                    case element_t -> g.setColor(Color.GRAY);
                    case element_e -> g.setColor(Color.BLUE);
                    case element_f -> g.setColor(Color.RED);
                }
                g.fillRect(x * 100 + Width/5, y * 100 + Height/5, 100, 100);
                g.setColor(Color.BLACK);
                g.drawRect(x * 100 + Width/5, y * 100 + Width/5, 100, 100);

                //affichage des artefacts en sur les 4 coins de l'île
                g.drawImage(Calice_de_l_onde, Width/5, Height/5, 100, 100, null);
                g.drawImage(Pierre_sacree, Width/5 , Height/5 + 100*5, 100, 100, null);
                g.drawImage(Cristal_ardent, Width/5 + 100 * 5, Height/5, 100, 100, null);
                g.drawImage(Statue_du_Zephir, Width/5 + 100 * 5, Height/5 + 100*5, 100, 100, null);

                g.drawString(z.getType().toString(), x * 100 + 10 + Width/5, y * 100 + 20 + Width/5);
            }
        }
    }
}