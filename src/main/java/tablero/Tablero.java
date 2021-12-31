
package tablero;

public class Tablero {
    
    private final Casilla[][] matrizCasillas;
    
    public Tablero() {
        
        Casilla [][] matriz = { { new Casilla(), new Casilla(), new Casilla() }, 
                                { new Casilla(), new Casilla(), new Casilla() },
                                { new Casilla(), new Casilla(), new Casilla() } };
        
        matrizCasillas = matriz;
    }
   
    public Casilla[][] getMatrizCasillas() {
        return matrizCasillas;
    }

    public Tablero getClone() {
        
        Tablero clone = new Tablero();
        
        for(int fila = 0; fila < 3; fila++){
            
            for(int columna = 0; columna < 3; columna ++){
                
                Posicion posicionActual = new Posicion(fila, columna);
                Relleno relleno = this.getCasilla(posicionActual).getRelleno();
                
                clone.getCasilla(posicionActual).marcar(relleno);
            }
        }
        
        return clone;
    }
    
    public Casilla getCasilla(Posicion posicion){
        
        return matrizCasillas[posicion.getFila()][posicion.getColumna()];
    }
    
    public void imprimir(){
        
        System.out.printf("%s%n", this.toString());
    }
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        for(int fila = 0; fila < 3; fila++){
            
            for(int columna = 0; columna < 3; columna++){
                
                Posicion posicionActual = new Posicion(fila, columna);
                
                Relleno relleno = this.getCasilla(posicionActual).getRelleno();
                String rellenoString = (relleno != Relleno.EMPTY)? relleno.name(): " ";
                
                sb.append(String.format("[%s]",rellenoString));
            }
            
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    public boolean isEqualsTo(Tablero tablero) {
        
        for(int fila = 0; fila < 3; fila++){
            
            for(int columna = 0; columna < 3; columna++){
                
                Posicion posicionActual = new Posicion(fila, columna);
                
                Casilla casilla = this.getCasilla(posicionActual);
                Casilla casillaTablero = tablero.getCasilla(posicionActual);
                
                if(casilla.getRelleno() != casillaTablero.getRelleno()) return false;
            }
        }
        
        return true;
    }
    
    public int calcularUtilidad(Relleno relleno) {
        
        switch(relleno) {
            
            case O: return this.calcularDisponibilidad(relleno) - this.calcularDisponibilidad(Relleno.X);
            
            case X: return this.calcularDisponibilidad(relleno) - this.calcularDisponibilidad(Relleno.O);
            
            default: return 0;
        }
    }
    
    private int calcularDisponibilidad(Relleno relleno) {
        
        int d_H = calcularDisponibilidadHorizontal(relleno);
        int d_V = calcularDisponibilidadVertical(relleno);
        int d_D = calcularDisponibilidadDiagonal(relleno);
        
        return d_H + d_V + d_D;
    }
    
    private int calcularDisponibilidadHorizontal(Relleno relleno) {
        
        int disponibilidad = 0;
        
        Casilla[][] casillas = this.getMatrizCasillas();
        
        for(int fila = 0; fila < 3; fila ++) {
                     
            for(int columna = 0; columna < 3; columna ++) {
                
                Casilla casilla = casillas[fila][columna];
                
                if(casilla.getRelleno() != relleno && casilla.getRelleno() != Relleno.EMPTY){
                    break;
                }
                
                if(columna == 2) disponibilidad ++;
            }
            
        }
        
        return disponibilidad;
    }
    
    private int calcularDisponibilidadVertical(Relleno relleno) {
        
        int disponibilidad = 0;
        
        Casilla[][] casillas = this.getMatrizCasillas();
        
        for(int columna = 0; columna < 3; columna ++) {
                     
            for(int fila=  0; fila < 3; fila ++) {
                
                Casilla casilla = casillas[fila][columna];
                
                if(casilla.getRelleno() != relleno && casilla.getRelleno() != Relleno.EMPTY){
                    break;
                }
                
                if(fila == 2) disponibilidad ++;
            }
            
        }
        
        return disponibilidad;
    }
    
    private int calcularDisponibilidadDiagonal(Relleno relleno) {
        
        int disponibilidad = 0;
        
        Casilla[][] casillas = this.getMatrizCasillas();
        
        for(int fila = 0, columna = 0; fila < 3; fila ++, columna++) {
            
            Casilla casilla = casillas[fila][columna];
                
            if(casilla.getRelleno() != relleno && casilla.getRelleno() != Relleno.EMPTY){
                break;
            }
            
            if(fila == 2) disponibilidad ++;
        }
        
        for(int fila = 2, columna = 0; fila >= 0; fila --, columna++) {
            
            Casilla casilla = casillas[fila][columna];
                
            if(casilla.getRelleno() != relleno && casilla.getRelleno() != Relleno.EMPTY){
                break;
            }
            
            if(fila == 0) disponibilidad ++;
        }
        
        return disponibilidad;
    }
    
}
