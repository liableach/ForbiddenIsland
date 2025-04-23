import javax.swing.*;
import java.awt.*;

public class FenetreJeu extends JFrame {
    public FenetreJeu(Ile ile) {
        setTitle("L'Île Interdite - Vue");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Vue vue = new Vue(ile);
        add(vue, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

