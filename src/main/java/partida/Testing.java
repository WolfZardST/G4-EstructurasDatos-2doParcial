
// CLASE TEMPORAL PARA HACER PRUEBAS ANTES DE DESARROLLAR LA PARTE GRÁFICA

package partida;

import jugadores.*;
import tablero.*;

public abstract class Testing {
    
    public static void main(String[] args) {
        
        Partida partida = new Partida();
        Tablero tablero = partida.getTablero();
        
        Jugador jugadorUno = new Humano(Relleno.X);
        Jugador jugadorDos = new Ordenador(Relleno.O);
        
        partida.setJugadorUno(jugadorUno);
        partida.setJugadorDos(jugadorDos);
        
        jugadorUno.marcarCasilla(tablero, new Posicion(0,0));
        jugadorDos.marcarCasilla(tablero, new Posicion(1,0));
        
        tablero.imprimir();
        System.out.println("Utilidad X: "+tablero.calcularUtilidad(Relleno.X));
        System.out.println("Utilidad O: "+tablero.calcularUtilidad(Relleno.O));
        
        jugadorUno.marcarCasilla(tablero, new Posicion(1,1));
        jugadorDos.marcarCasilla(tablero, new Posicion(2,0));
        jugadorUno.marcarCasilla(tablero, new Posicion(2,2));
        
        tablero.imprimir();
        partida.buscarTresEnRaya(Relleno.X);
        
        System.out.println(partida.getEstado().name());
        
        
    }
    
}
