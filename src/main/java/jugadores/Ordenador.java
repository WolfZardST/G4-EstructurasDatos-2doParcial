
package jugadores;

import partida.Partida;
import tablero.*;

public class Ordenador extends Jugador {
    
    public Ordenador(Relleno relleno){
        super(relleno);
    }
    
    @Override
    public Posicion requestMove() {
        
        return Partida.minimax.getMejorPosicion();
    }
    
}
