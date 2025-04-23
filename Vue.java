import javax.swing.* ;
import java.awt.*;

class Vue extends JPanel {
    private Ile ile;

    public Vue(Ile ile) {
        this.ile = ile;
        setPreferredSize(new Dimension(900, 900)); // 100px par case
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                Zone z = ile.getZone(x, y);
                if (z == null) continue;
                switch (z.getEtat()) {
                    case normale -> g.setColor(Color.LIGHT_GRAY);
                    case inondee -> g.setColor(Color.CYAN);
                    case submergee -> g.setColor(Color.BLUE);
                }
                g.fillRect(x * 100, y * 100, 100, 100);
                g.setColor(Color.BLACK);
                g.drawRect(x * 100, y * 100, 100, 100);

                g.drawString(z.getType().toString(), x * 100 + 10, y * 100 + 20);
            }
        }
    }
}