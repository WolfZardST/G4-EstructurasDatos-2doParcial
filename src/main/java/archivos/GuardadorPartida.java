
package archivos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import partida.*;
import jugadores.*;
import tablero.Tablero;

public interface GuardadorPartida {
    
    public static void guardarPartida(File archivo) {
                
        try(BufferedWriter writer = new BufferedWriter( new FileWriter(archivo) )){

            escribirJugador(Partida.PARTIDA.getJugadorUno(), writer);
            escribirJugador(Partida.PARTIDA.getJugadorDos(), writer);
            
            writer.write((Partida.JUGADOR_ACTUAL == Partida.PARTIDA.getJugadorUno()) ? "P1," : "P2,");
            writer.write(Partida.VICTORIAS_PARA_GANAR + ",");
            writer.write(Partida.SEGUNDOS_POR_TURNO + "\n");
            
            for(Tablero tablero: Partida.TABLEROS) {
                writer.write(tablero.toString());
            }
            
        }catch(FileNotFoundException ex){

        }catch(IOException ex){

        }
        
    }
    
    private static void escribirJugador(Jugador jugador, BufferedWriter writer) throws IOException {
        
        writer.write((jugador instanceof Humano) ? "human," : "ia,");
        writer.write(jugador.getRelleno().name() + ",");
        writer.write(jugador.getVictorias() + "\n");
    }
    
}
