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
                    case vide -> g.setColor(Color.WHITE);
                    case normale -> g.setColor(Color.LIGHT_GRAY);
                    case heliport -> g.setColor(Color.YELLOW);
                    case element_a -> g.setColor(Color.CYAN);
                    case element_t -> g.setColor(Color.GRAY);
                    case element_e -> g.setColor(Color.BLUE);
                    case element_f -> g.setColor(Color.RED);
                }
                switch (z.getEtat()){
                    case inondee -> g.setColor(Color.CYAN);
                    case submergee -> g.setColor(Color.WHITE);
                }
                g.fillRect(x * 150, y * 170 , 150 , 170);
                g.setColor(Color.BLACK);
                g.drawRect(x * 150 , y * 170 , 150, 170);

                g.drawString(z.getType().toString(), x * 150 + 10 , y * 170 + 20 );
            }
        }

        //affichage des artefacts en sur les 4 coins de l'île 100 x 100
        g.drawImage(Calice_de_l_onde, 0, 0, 150, 170, null);
        g.drawImage(Pierre_sacree, Width/3 + 110 ,  0, 150, 170, null);
        g.drawImage(Cristal_ardent,  0, Height - 230, 150, 170, null);
        g.drawImage(Statue_du_Zephir, Width/3 + 110, Height - 230 , 150, 170, null);
    }
}