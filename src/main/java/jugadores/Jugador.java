
package jugadores;

import tablero.*;

public abstract class Jugador {
    
    protected Relleno relleno;
    
    public Jugador(Relleno relleno){
        this.relleno = relleno;
    }
    
    public void marcarCasilla(Tablero tablero, Posicion posicion){
        
        tablero.getCasilla(posicion).marcar(relleno);
    }
}
