/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package partida;

import tablero.*;
import TDATree.Node;
import TDATree.Tree;
import jugadores.Jugador;

/**
 *
 * @author Danaé
 */
public class Minimax {

    Tree<Tablero> minimax;
    Partida partida;

    public Minimax(Partida partida) {
        this.partida = partida;
        this.minimax = InitMiniMax(partida);
        
    }

    public Tree<Tablero> getMinimax() {
        return minimax;
    }
    

    public Tree<Tablero> InitMiniMax(Partida partida) {
        return getTreeMiniMax(partida);
    }

    public Tree<Tablero> calculateChildren(Partida partida, Tablero Padre, Jugador jugadorActual, boolean lastLevel) {          
        
        Jugador jugadorSiguiente = partida.getJugador_Siguiente(jugadorActual);
        Tree<Tablero> treeTablero = new Tree(Padre);

        for (int fila = 0; fila < 3; fila++) {

            for (int columna = 0; columna < 3; columna++) {

                Posicion posicionActual = new Posicion(fila, columna);
                Casilla casilla = Padre.getCasilla(posicionActual);

                if (casilla.isEmpty()) {
                    Tablero clone = Padre.getClone();
                    jugadorSiguiente.marcarCasilla(clone, posicionActual);
                    // Obtener los children de este
                    if (!lastLevel){
                        Tree<Tablero> subTablero = calculateChildren(partida, clone, jugadorSiguiente, true );
                        treeTablero.addChild(subTablero);
                    }else{
                       
                       treeTablero.addChild(clone);
                    }
               
                }
            }
        }

        return treeTablero;
    }

    public Tree<Tablero> getTreeMiniMax(Partida partida) {      
        Tablero tablero = partida.getTablero();        
        Tree<Tablero> treeMiniMax = calculateChildren(partida, tablero, partida.getJUGADOR_ACTUAL(), false);
        return treeMiniMax;
        
    }   

    @Override
    public String toString() {
        return ""+minimax;
    }
    

}
