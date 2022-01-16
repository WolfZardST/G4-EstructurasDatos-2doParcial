
package archivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import jugadores.*;
import partida.*;
import tablero.*;

public interface CargadorPartida {
    
    public static void cargarPartida(File archivo) {
        
        try ( BufferedReader reader = new BufferedReader( new FileReader(archivo) ) ) {
                       
            String[] tokens;
            
            Jugador jugadorUno = obtenerJugador(reader.readLine());
            Jugador jugadorDos = obtenerJugador(reader.readLine());
            
            Partida partida = new Partida(jugadorUno,jugadorDos);
                    
            partida.setJugadorUno(jugadorUno);
            partida.setJugadorDos(jugadorDos);
            
            String turnoVictoriasTiempo = reader.readLine();
            tokens = turnoVictoriasTiempo.split(",");
            
            Partida.JUGADOR_ACTUAL = (tokens[0].equals("P1")) ? jugadorUno : jugadorDos;
            Partida.VICTORIAS_PARA_GANAR = Integer.parseInt(tokens[1]);
            Partida.SEGUNDOS_POR_TURNO = Integer.parseInt(tokens[2]);
            
            Tablero tablero;
            
            while((tablero = obtenerTablero(reader)) != null) {
                
                Partida.PARTIDA.setTablero(tablero);
                Partida.TABLEROS.add(tablero.getClone());
            }
            
            Partida.PARTIDA = partida;
            
        } catch (FileNotFoundException ex) {
            
            
        } catch (IOException ex) {
            
        }
        
    }
    
    private static Jugador obtenerJugador(String datosJugador) {
        
        String[] tokens = datosJugador.split(",");
        
        Relleno rellenoJugador = (tokens[1].equals("O")) ? Relleno.O : Relleno.X;
        
        Jugador jugador = (tokens[0].equals("human")) ? new Humano(rellenoJugador) : new Ordenador(rellenoJugador);
        
        jugador.setVictorias(Integer.parseInt(tokens[2]));
        
        return jugador;
    }
    
    private static Tablero obtenerTablero(BufferedReader reader) throws IOException {
        
        Tablero tablero = new Tablero();
        Casilla[][] matrizCasillas = tablero.getMatrizCasillas();
        
        
        for(int fila = 0; fila < 3; fila ++) {
            
            String casillas = reader.readLine();
            if(casillas == null) return null;
            
            casillas = casillas.replace("[", "");
            casillas = casillas.replace("]", "");
            
            for(int columna = 0; columna < 3; columna ++) {
                
                Casilla casilla = new Casilla();
                matrizCasillas[fila][columna] = casilla;
                
                switch(Character.toString(casillas.charAt(columna))) {
                    case "X": casilla.marcar(Relleno.X);
                    break;
                    case "O": casilla.marcar(Relleno.O);
                }
            }
        }
        
        return tablero;
    }
    
}
