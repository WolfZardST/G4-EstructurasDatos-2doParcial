
package tablero;

public class Casilla {
    
    private Relleno relleno;
    
    public Casilla() {
        relleno = Relleno.EMPTY;
    }
    
    public void setO() {
        relleno = Relleno.O;
    }
    
    public void setX() {
        relleno = Relleno.O;
    }

    public Relleno getRelleno() {
        return relleno;
    }
    
    public void setRelleno(Relleno relleno) {
        this.relleno = relleno;
    }
    
    public boolean isEmpty() {
        return relleno == Relleno.EMPTY;
    }
    
}
