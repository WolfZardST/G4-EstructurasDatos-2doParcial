
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
    
    public Partida() {
        this.tablero = new Tablero();
        this.estado = Estado.EMPATE;
        PARTIDA = this;
        TABLEROS = new LinkedList();
    }

    public Tablero getTablero() {
        return tablero;
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
    
    public void buscarTresEnRaya(Jugador jugador) {
        
        Relleno relleno = jugador.getRelleno();
        
        List<Casilla> victoriosas = new LinkedList<>();
        
        this.buscarTresEnRayaHorizontal(victoriosas, relleno);
        
        this.buscarTresEnRayaVertical(victoriosas, relleno);
        
        this.buscarTresEnRayaDiagonal(victoriosas, relleno);
        
        // Registrando Victoria
        
        if(!victoriosas.isEmpty()){
            victoriosas.forEach(casilla -> casilla.marcarComoVictoriosa());
            
            jugador.sumarVictoria();
            
            switch(relleno) {
                case O: setEstado(Estado.GANA_O);
                break;
                case X: setEstado(Estado.GANA_X);
            }
            
        }
    }
    
    private void buscarTresEnRayaVertical(List<Casilla> victoriosas, Relleno relleno) {
        
        Casilla[][] casillas = tablero.getMatrizCasillas();
        
        for(int columna = 0; columna < 3; columna ++) {
            if(!victoriosas.isEmpty()) break;
                     
            for(int fila = 0; fila < 3; fila ++) {
                
                Casilla casilla = casillas[fila][columna];
                
                if(casilla.getRelleno() != relleno){
                    victoriosas.clear();
                    break;
                }
                victoriosas.add(casilla);
            }
        }
        
    }
    
    private void buscarTresEnRayaHorizontal(List<Casilla> victoriosas, Relleno relleno) {
        
        Casilla[][] casillas = tablero.getMatrizCasillas();
        
        for(int fila = 0; fila < 3; fila ++) {
            if(!victoriosas.isEmpty()) break;
                     
            for(int columna = 0; columna < 3; columna ++) {
                
                Casilla casilla = casillas[fila][columna];
                
                if(casilla.getRelleno() != relleno){
                    victoriosas.clear();
                    break;
                }
                victoriosas.add(casilla);
            }
        }
        
    }
   
    private void buscarTresEnRayaDiagonal(List<Casilla> victoriosas, Relleno relleno) {
        
        Casilla[][] casillas = tablero.getMatrizCasillas();
        
        for(int fila = 0, columna = 0; fila < 3; fila ++, columna++) {
            if(!victoriosas.isEmpty()) break;
            
            Casilla casilla = casillas[fila][columna];
                
            if(casilla.getRelleno() != relleno){
                victoriosas.clear();
                break;
            }
            victoriosas.add(casilla);
        }
        
        for(int fila = 2, columna = 0; fila >= 0; fila --, columna++) {
            if(!victoriosas.isEmpty()) break;
            
            Casilla casilla = casillas[fila][columna];
                
            if(casilla.getRelleno() != relleno){
                victoriosas.clear();
                break;
            }
            victoriosas.add(casilla);
        }
        
    }
    
}
