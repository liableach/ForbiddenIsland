import java.util.Random;

public class Ile{
    private Zone[][] grille;
    private int largueur, hauteur;


    public Ile(int x, int y){
        largueur = x; hauteur = y;
        grille = new Zone[x][y];
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                grille[i][j] = new Zone(i, j, Type.normale);
            }
        }
        Random r = new Random(); boolean fait = true;
        while(fait){
            int x1 = r.nextInt(x-1), y1 = r.nextInt(y-1);
            if((x1 != 0 && y1 != 0) && (x1 != 0 && y1 != 1) && (x1 != 0 && y1 != 4) && (x1 != 0 && y1 != 5) && (x1 != 1 && y1 != 0)
            && (x1 != 1 && y1 != 5) && (x1 != 4 && y1 != 0) && (x1 != 4 && y1 != 5) && (x1 != 5 && y1 != 0) && (x1 != 5 && y1 != 1)
            && (x1 != 5 && y1 != 4) && (x1 != 5 && y1 != 5)) {
                fait = false;
                grille[x1][y1] = new Zone(x1, y1, Type.heliport);
            }
        }
        grille[0][0] = new Zone(0, 0, Type.element_e);
        grille[0][5] = new Zone(0, 1, Type.element_f);
        grille[5][0] = new Zone(5, 0, Type.element_t);
        grille[5][5] = new Zone(5, 5, Type.element_a);
    }
    public Zone getZone(int x, int y){
        if(x < 0 || x >= largueur || y < 0 || y >= hauteur) return null;
        return grille[x][y];
    }  
    public Zone[][] getGrille(){ return grille; }
    

}