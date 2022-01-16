
package tablero;

import java.util.LinkedList;
import java.util.List;
import jugadores.Jugador;

public class Tablero {
    
    private final Casilla[][] matrizCasillas;
    private int valorMinimax;
    private boolean isChosen;
    
    public Tablero() {
        
        Casilla [][] matriz = { { new Casilla(), new Casilla(), new Casilla() }, 
                                { new Casilla(), new Casilla(), new Casilla() },
                                { new Casilla(), new Casilla(), new Casilla() } };
        
        matrizCasillas = matriz;
    }
   
    public Casilla[][] getMatrizCasillas() {
        return matrizCasillas;
    }

    public void setValorMinimax(int valorMinimax) {
        this.valorMinimax = valorMinimax;
    }

    public void setIsChosen(boolean isChosen) {
        this.isChosen = isChosen;
    }

    public boolean isIsChosen() {
        return isChosen;
    }
    

    public int getValorMinimax() {
        return valorMinimax;
    }
        
    public Tablero getClone() {
        
        Tablero clone = new Tablero();
        
        for(int fila = 0; fila < 3; fila++){
            
            for(int columna = 0; columna < 3; columna ++){
                
                Posicion posicionActual = new Posicion(fila, columna);
                Relleno relleno = this.getCasilla(posicionActual).getRelleno();
                
                clone.getCasilla(posicionActual).marcar(relleno);
                
            }
        }
        
        return clone;
    }
    
    public Casilla getCasilla(Posicion posicion){
        
        return matrizCasillas[posicion.getFila()][posicion.getColumna()];
    }
       
    public void imprimir(){
        
        System.out.printf("%s%n", this.toString());
    }
    
    public boolean estaLleno() {
        
        for(int fila = 0; fila < 3; fila++){
            
            for(int columna = 0; columna < 3; columna ++){
                
                Posicion posicionActual = new Posicion(fila, columna);
                Relleno relleno = this.getCasilla(posicionActual).getRelleno();
                
                if(relleno == Relleno.EMPTY) return false;
            }
        }
        
        return true;
    }
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        for(int fila = 0; fila < 3; fila++){
            
            for(int columna = 0; columna < 3; columna++){
                
                Posicion posicionActual = new Posicion(fila, columna);
                
                Relleno relleno = this.getCasilla(posicionActual).getRelleno();
                String rellenoString = (relleno != Relleno.EMPTY)? relleno.name(): " ";
                
                sb.append(String.format("[%s]",rellenoString));
            }
            
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    public boolean isEqualsTo(Tablero tablero) {
        
        for(int fila = 0; fila < 3; fila++){
            
            for(int columna = 0; columna < 3; columna++){
                
                Posicion posicionActual = new Posicion(fila, columna);
                
                Casilla casilla = this.getCasilla(posicionActual);
                Casilla casillaTablero = tablero.getCasilla(posicionActual);
                
                if(casilla.getRelleno() != casillaTablero.getRelleno()) return false;
            }
        }
        
        return true;
    }
    
    public int calcularUtilidad(Relleno relleno) {
        
        switch(relleno) {
            
            case O: return this.calcularDisponibilidad(relleno) - this.calcularDisponibilidad(Relleno.X);
            
            case X: return this.calcularDisponibilidad(relleno) - this.calcularDisponibilidad(Relleno.O);
            
            default: return 0;
        }
    }
    
    private int calcularDisponibilidad(Relleno relleno) {
        
        int d_H = calcularDisponibilidadHorizontal(relleno);
        int d_V = calcularDisponibilidadVertical(relleno);
        int d_D = calcularDisponibilidadDiagonal(relleno);
        
        return d_H + d_V + d_D;
    }
    
    private int calcularDisponibilidadHorizontal(Relleno relleno) {
        
        int disponibilidad = 0;
        
        Casilla[][] casillas = this.getMatrizCasillas();
        
        for(int fila = 0; fila < 3; fila ++) {
                     
            for(int columna = 0; columna < 3; columna ++) {
                
                Casilla casilla = casillas[fila][columna];
                
                if(casilla.getRelleno() != relleno && casilla.getRelleno() != Relleno.EMPTY){
                    break;
                }
                
                if(columna == 2) disponibilidad ++;
            }
            
        }
        
        return disponibilidad;
    }
    
    private int calcularDisponibilidadVertical(Relleno relleno) {
        
        int disponibilidad = 0;
        
        Casilla[][] casillas = this.getMatrizCasillas();
        
        for(int columna = 0; columna < 3; columna ++) {
                     
            for(int fila=  0; fila < 3; fila ++) {
                
                Casilla casilla = casillas[fila][columna];
                
                if(casilla.getRelleno() != relleno && casilla.getRelleno() != Relleno.EMPTY){
                    break;
                }
                
                if(fila == 2) disponibilidad ++;
            }
            
        }
        
        return disponibilidad;
    }
    
    private int calcularDisponibilidadDiagonal(Relleno relleno) {
        
        int disponibilidad = 0;
        
        Casilla[][] casillas = this.getMatrizCasillas();
        
        for(int fila = 0, columna = 0; fila < 3; fila ++, columna++) {
            
            Casilla casilla = casillas[fila][columna];
                
            if(casilla.getRelleno() != relleno && casilla.getRelleno() != Relleno.EMPTY){
                break;
            }
            
            if(fila == 2) disponibilidad ++;
        }
        
        for(int fila = 2, columna = 0; fila >= 0; fila --, columna++) {
            
            Casilla casilla = casillas[fila][columna];
                
            if(casilla.getRelleno() != relleno && casilla.getRelleno() != Relleno.EMPTY){
                break;
            }
            
            if(fila == 0) disponibilidad ++;
        }
        
        return disponibilidad;
    }
    
    public boolean buscarTresEnRaya(Jugador jugador) {
        
        Relleno relleno = jugador.getRelleno();
        
        List<Casilla> victoriosas = new LinkedList<>();
        
        this.buscarTresEnRayaHorizontal(victoriosas, relleno);
        
        this.buscarTresEnRayaVertical(victoriosas, relleno);
        
        this.buscarTresEnRayaDiagonal(victoriosas, relleno);
        
        // Registrando Victoria
        
        if(!victoriosas.isEmpty()){
            
            victoriosas.forEach(casilla -> casilla.marcarComoVictoriosa());
            return true;
        }
        return false;
    }
    
    private void buscarTresEnRayaVertical(List<Casilla> victoriosas, Relleno relleno) {
        
        for(int columna = 0; columna < 3; columna ++) {
            if(!victoriosas.isEmpty()) break;
                     
            for(int fila = 0; fila < 3; fila ++) {
                
                Casilla casilla = matrizCasillas[fila][columna];
                if(casilla.getRelleno() == relleno) victoriosas.add(casilla);
            }
            
            if(victoriosas.size() < 3) victoriosas.clear();
        }
        
    }
    
    private void buscarTresEnRayaHorizontal(List<Casilla> victoriosas, Relleno relleno) {
        
        for(int fila = 0; fila < 3; fila ++) {
            if(!victoriosas.isEmpty()) break;
                     
            for(int columna = 0; columna < 3; columna ++) {
                
                Casilla casilla = matrizCasillas[fila][columna];
                if(casilla.getRelleno() == relleno) victoriosas.add(casilla);
            }
            
            if(victoriosas.size() < 3) victoriosas.clear();
        }
        
    }
   
    private void buscarTresEnRayaDiagonal(List<Casilla> victoriosas, Relleno relleno) {
        
        if(!victoriosas.isEmpty()) return;
        
        for(int fila = 0, columna = 0; fila < 3; fila ++, columna++) {
            
            Casilla casilla = matrizCasillas[fila][columna];
            if(casilla.getRelleno() == relleno) victoriosas.add(casilla);
        }
        
        if(victoriosas.size() < 3) victoriosas.clear();
        
        for(int fila = 2, columna = 0; fila >= 0; fila --, columna++) {
            
            Casilla casilla = matrizCasillas[fila][columna];
            if(casilla.getRelleno() == relleno) victoriosas.add(casilla);
        }
        
        if(victoriosas.size() < 3) victoriosas.clear();
    }
   
}
