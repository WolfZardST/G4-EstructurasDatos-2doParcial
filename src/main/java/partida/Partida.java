
package partida;

import java.util.LinkedList;
import java.util.List;

import jugadores.*;
import tablero.*;

public class Partida {
    
    private Tablero tablero;
    private Jugador jugadorUno;
    private Jugador jugadorDos;
    private Estado estado;
    
    public static Partida PARTIDA;
    public static int VICTORIAS_PARA_GANAR = 1;
    public static int SEGUNDOS_POR_TURNO = 0;
    public static Jugador JUGADOR_ACTUAL;
    public static List<Tablero> TABLEROS;
    public static  Minimax minimax;
       
    public Partida(Jugador jugadorUno, Jugador jugadorDos) {
        this.tablero = new Tablero();
        this.estado = Estado.EMPATE;
        PARTIDA = this;
        TABLEROS = new LinkedList();
        this.jugadorUno = jugadorUno;
        this.jugadorDos = jugadorDos;
        minimax = new Minimax(this);
    }

    public Tablero getTablero() {
        return tablero;
    }
    
    public Jugador getJugadorSiguiente(Jugador jugadorActual){
        if(jugadorActual == jugadorUno) return jugadorDos;
        else return jugadorUno;
    }
    
    public Jugador getJugadorUno() {
        return jugadorUno;
    }

    public Jugador getJugadorDos() {
        return jugadorDos;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setJugadorUno(Jugador jugadorUno) {
        this.jugadorUno = jugadorUno;
    }

    public void setJugadorDos(Jugador jugadorDos) {
        this.jugadorDos = jugadorDos;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Minimax getMinimax() {
        return minimax;
    }

    public void setMinimax(Minimax minimax) {
        Partida.minimax = minimax;
    }
    
}
