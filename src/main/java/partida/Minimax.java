
package partida;

import TDAHeap.Heap;
import tablero.*;
import TDATree.Tree;
import java.util.Comparator;
import java.util.LinkedList;
import jugadores.Jugador;

/**
 *
 * @author Donaé
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

    public final Tree<Tablero> InitMiniMax(Partida partida) {
        return getTreeMiniMax(partida);
    }

    public Tree<Tablero> calculateChildren(Partida partida, Tablero Padre, Jugador jugadorActual, boolean lastLevel) {          
        
        Jugador jugadorSiguiente = partida.getJugadorSiguiente(jugadorActual);
        Relleno rellenoSiguiente = jugadorSiguiente.getRelleno();
        
        Tree<Tablero> treeTablero = new Tree(Padre);

        for (int fila = 0; fila < 3; fila++) {

            for (int columna = 0; columna < 3; columna++) {

                Posicion posicionActual = new Posicion(fila, columna);
                Casilla casilla = Padre.getCasilla(posicionActual);

                if (casilla.isEmpty()) {
                    
                    Tablero clone = Padre.getClone();
                    clone.getCasilla(posicionActual).marcar(rellenoSiguiente);
                    
                    if (!lastLevel){
                        
                        Tree<Tablero> subTablero = calculateChildren(partida, clone, jugadorSiguiente, true );
                        treeTablero.addChild(subTablero);
                        
                    }else {
                       treeTablero.addChild(clone);
                    }
               
                }
            }
        }

        return treeTablero;
    }

    public Tree<Tablero> getTreeMiniMax(Partida partida) {    
        
        Tablero tablero = partida.getTablero();        
        Tree<Tablero> treeMiniMax = calculateChildren(partida, tablero, partida.getJugadorSiguiente(Partida.JUGADOR_ACTUAL), false);
        
        return treeMiniMax;
    }   

    @Override
    public String toString() {
        return ""+minimax;
    }
         
    public Tablero getMejorOpcionTablero(){
        
        Comparator<Tablero> cmpUtilidad = (Tablero t1, Tablero t2) -> t1.calcularUtilidad(Partida.JUGADOR_ACTUAL.getRelleno()) - t2.calcularUtilidad(Partida.JUGADOR_ACTUAL.getRelleno());
        Comparator<Tablero> cmpTableros = (Tablero t1, Tablero t2) -> t1.getValorMinimax() - t2.getValorMinimax();

        Heap<Tablero> heapPadre = new Heap(cmpTableros, true);
               
        LinkedList<Tree<Tablero>> children = getTreeMiniMax(partida).getRoot().getChildren();
        
        for (Tree<Tablero> child: children){
            
            Heap<Tablero> heapHijo = new Heap(cmpUtilidad, false);
            LinkedList<Tree<Tablero>> childrenOfChildren = child.getRoot().getChildren();            
            
            boolean tresEnRaya = false;
            
            for (Tree<Tablero> childOfChild: childrenOfChildren){
                
                if(childOfChild != null){
                Tablero tableroChild = childOfChild.getRoot().getContent();
                
                tresEnRaya = tableroChild.buscarTresEnRaya(Partida.PARTIDA.getJugadorSiguiente(Partida.JUGADOR_ACTUAL)); 
                if(tresEnRaya) break;
                heapHijo.offer(tableroChild); 
                
                int utilidad = tableroChild.calcularUtilidad(Partida.JUGADOR_ACTUAL.getRelleno());
                tableroChild.setValorMinimax(utilidad);     
                }
            }
            
            Tablero tableroMax = heapHijo.poll(); 
            
            Tablero tableroDad = child.getRoot().getContent();
            
            if(!tresEnRaya) {
                tableroDad.setValorMinimax(tableroMax.getValorMinimax());
                heapPadre.offer(tableroDad);
            }    
            
            if(tableroDad.buscarTresEnRaya(Partida.JUGADOR_ACTUAL)) return tableroDad;
                
        }
        
        Tablero chosen = heapPadre.poll();
        chosen.setIsChosen(true);
        return chosen;
                      
    }
    
    public Posicion getMejorPosicion(){
        
        Tablero bestTablero = getMejorOpcionTablero();
        Tablero currentTablero = partida.getTablero();
     
        for (int fila = 0; fila < 3; fila++) {

            for (int columna = 0; columna < 3; columna++) {
                
                Posicion posicion = new Posicion(fila, columna);
                
                Relleno bestRelleno = bestTablero.getCasilla(posicion).getRelleno();
                Relleno currentRelleno = currentTablero.getCasilla(posicion).getRelleno();

                if (bestRelleno != currentRelleno){
                    return posicion;
                }
                
            }
            
        }
        return null;
    }
    

}
