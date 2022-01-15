
// CLASE TEMPORAL PARA HACER PRUEBAS ANTES DE DESARROLLAR LA PARTE GRÁFICA

package partida;

import jugadores.*;
import tablero.*;

public abstract class Testing {
    
    public static void main(String[] args) {
        
        Partida partida = new Partida();
        Partida.VICTORIAS_PARA_GANAR = 1;
        
        Tablero tablero = partida.getTablero();
        
        Jugador jugadorUno = new Humano(Relleno.X);
        Jugador jugadorDos = new Ordenador(Relleno.O);
        
        partida.setJugadorUno(jugadorUno);
        partida.setJugadorDos(jugadorDos);
        
        jugadorUno.marcarCasilla(tablero, new Posicion(0,0));
        jugadorDos.marcarCasilla(tablero, new Posicion(1,0));
        jugadorUno.marcarCasilla(tablero, new Posicion(1,1));
        jugadorDos.marcarCasilla(tablero, new Posicion(2,0));
        jugadorUno.marcarCasilla(tablero, new Posicion(2,2));
        jugadorDos.marcarCasilla(tablero, new Posicion(0,2));
        
        System.out.println(tablero);
        
        System.out.println("Aqui comienza");
        Minimax inteligencia = new Minimax(tablero);
        System.out.println(inteligencia.getTreeMiniMax(partida));
        System.out.println("Aqui termina");
        
        Partida.JUGADOR_ACTUAL = jugadorUno;
        
        tablero.imprimir();
        System.out.println("Utilidad X: "+tablero.calcularUtilidad(Relleno.X));
        System.out.println("Utilidad O: "+tablero.calcularUtilidad(Relleno.O));
        
        
        
        tablero.imprimir();
        partida.buscarTresEnRaya(jugadorUno);
        
        System.out.println(partida.getEstado().name());
        
        
        
        
        
        
    }
    
}
