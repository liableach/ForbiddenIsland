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
        
        
        grille[3][3] = new Zone(x/2, y/2, Type.heliport);
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