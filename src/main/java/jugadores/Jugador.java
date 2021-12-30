
package jugadores;

import partida.Partida;
import tablero.*;

public abstract class Jugador {
    
    protected Relleno relleno;
    private int victorias = 0;
    
    public Jugador(Relleno relleno){
        this.relleno = relleno;
    }
    
    public void marcarCasilla(Tablero tablero, Posicion posicion){
        tablero.getCasilla(posicion).marcar(relleno);
        Partida.TABLEROS.offer(tablero.getClone());
    }
    
    public void sumarVictoria() {
        victorias ++;
    }

    public int getVictorias() {
        return victorias;
    }
    
    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public Relleno getRelleno() {
        return relleno;
    }
    
}
