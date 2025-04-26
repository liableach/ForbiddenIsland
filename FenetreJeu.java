import javax.swing.*;
import java.awt.BorderLayout;

public class FenetreJeu extends JFrame {
    private final Vue vue;
    public FenetreJeu(Ile ile) {
        setTitle("L'Île Interdite – Vue");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.vue = new Vue(ile);
        add(vue, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public Vue getVue() {
        return vue;
    }
}

