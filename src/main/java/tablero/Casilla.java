
package tablero;

public class Casilla {
    
    private Relleno relleno;
    private boolean victoriosa;
    
    public Casilla() {
        relleno = Relleno.EMPTY;
    }

    public Relleno getRelleno() {
        return relleno;
    }
    
    public void marcar(Relleno relleno) {
        this.relleno = relleno;
    }
    
    public boolean isEmpty() {
        return relleno == Relleno.EMPTY;
    }
    
    public boolean isVictoriosa(){
        return victoriosa == true;
    }
    
    public void marcarComoVictoriosa(){
        this.victoriosa = true;
    }
    
}
