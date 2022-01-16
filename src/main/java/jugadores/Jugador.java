
package jugadores;

import partida.Partida;
import tablero.*;

public abstract class Jugador {
    
    protected Relleno relleno;
    private int victorias = 0;
    
    public Jugador(Relleno relleno){
        this.relleno = relleno;
    }
    
    public void marcarCasilla(Posicion posicion){
        Partida.PARTIDA.getTablero().getCasilla(posicion).marcar(relleno);
        Partida.TABLEROS.add(Partida.PARTIDA.getTablero().getClone());
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
