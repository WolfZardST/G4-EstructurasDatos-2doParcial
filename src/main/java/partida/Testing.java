
// CLASE TEMPORAL PARA HACER PRUEBAS ANTES DE DESARROLLAR LA PARTE GRÁFICA

package partida;

import jugadores.*;
import tablero.*;

public abstract class Testing {
    
    public static void main(String[] args) {
        
        Jugador jugadorUno = new Humano(Relleno.X);
        Jugador jugadorDos = new Ordenador(Relleno.O);
        
        Partida partida = new Partida(jugadorUno,jugadorDos);
        Partida.VICTORIAS_PARA_GANAR = 1;
        
        Tablero tablero = partida.getTablero();
  
        partida.setJugadorUno(jugadorUno);
        partida.setJugadorDos(jugadorDos);

        jugadorUno.marcarCasilla(new Posicion(1,1));
        jugadorDos.marcarCasilla(new Posicion(0,0));
        jugadorUno.marcarCasilla(new Posicion(0,2));
        jugadorDos.marcarCasilla(new Posicion(2,0));
        jugadorUno.marcarCasilla(new Posicion(1,0));
        jugadorDos.marcarCasilla(new Posicion(1,2));
        jugadorUno.marcarCasilla(new Posicion(0,1));
        jugadorDos.marcarCasilla(new Posicion(2,1));
        
        System.out.println(tablero);
        
        System.out.println("Aqui comienza");
        //Minimax inteligencia = new Minimax(partida);
        //partida.setMinimax(inteligencia);
        System.out.println(partida.getMinimax().getTreeMiniMax(partida));
        System.out.println("Aqui termina");
        
        
        Partida.JUGADOR_ACTUAL = jugadorUno;
        
        
        //tablero.imprimir();
        System.out.println("Utilidad X: "+tablero.calcularUtilidad(Relleno.X));
        System.out.println("Utilidad O: "+tablero.calcularUtilidad(Relleno.O));
        
        System.out.println("F "+ partida.getMinimax().getMejorPosicion());       

        //tablero.imprimir();
        //partida.buscarTresEnRaya(jugadorUno);
        
        System.out.println(partida.getEstado().name());
        
        
        
        
        
        
    }
    
}
