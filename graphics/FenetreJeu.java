package graphics;
import javax.swing.*;

import cartes.PaquetdeCartes;
import ile.Ile;

import java.awt.BorderLayout;
//fenetre de jeu
public class FenetreJeu extends JFrame {
    private final Vue vue;
    public FenetreJeu(Ile ile, PaquetdeCartes paquet){
        setTitle("L'Île Interdite – Vue");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.vue = new Vue(ile, paquet);
        add(vue, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public Vue getVue() { return vue; }
}

