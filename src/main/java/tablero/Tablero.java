
package tablero;

public class Tablero {
    
    private final Casilla[][] matriz_casillas;
    
    public Tablero() {
        
        Casilla [][] matriz = { { new Casilla(), new Casilla(), new Casilla() }, 
                                { new Casilla(), new Casilla(), new Casilla() },
                                { new Casilla(), new Casilla(), new Casilla() } };
        
        matriz_casillas = matriz;
    }
   
    public Casilla[][] getMatrizCasillas() {
        return matriz_casillas;
    }
    
    public Tablero getClone() {
        
        Tablero clone = new Tablero();
        Casilla[][] matriz_clone = clone.getMatrizCasillas();
        
        for(int i = 0; i < 3; i++){
            
            for(int j = 0; i < 3; i++){
                
                matriz_clone[i][j].setRelleno(matriz_casillas[i][j].getRelleno());
            }
        }
        
        return clone;
    }
    
}
