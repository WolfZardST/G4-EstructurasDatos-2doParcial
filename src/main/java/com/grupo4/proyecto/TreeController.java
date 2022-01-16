
package com.grupo4.proyecto;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.LinkedList;
import java.util.Stack;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import partida.Partida;
import TDATree.*;
import tablero.*;

public class TreeController implements Initializable {

    @FXML
    private Pane backgroundPane;
    
    private final String borderStyle = "-fx-border-color: black;-fx-border-style: solid;-fx-border-width: 0.5px;";
    private final String labelStyle = "-fx-graphic-text-gap: 0px; -fx-font-weight: bold; -fx-font-size: 10px;";
    
    private final double ANCHO_TOTAL = 500.0;
    private final double ANCHO_TABLERO = 50.0;
    private final double ESPACIADO_VERTICAL = 50.0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Tree tree = buildTreeFromBoards( Partida.TABLEROS );     
        showGraphicalTree(Partida.minimax.getTreeMiniMax(Partida.PARTIDA));
        
    }
    
    private Relleno getRellenoP1() {
        return Partida.PARTIDA.getJugadorUno().getRelleno();
    }
    
    private Relleno getRellenoP2() {
        return Partida.PARTIDA.getJugadorDos().getRelleno();
    }
    
    private GridPane buildGridPaneFromBoard(Tablero tablero) {
        
        GridPane gridPane = new GridPane();
        
        gridPane.setStyle(borderStyle);
        gridPane.setPrefSize(ANCHO_TABLERO, ANCHO_TABLERO);
        
        for(int fila = 0; fila < 3; fila ++) {
            
            for(int columna = 0; columna < 3; columna ++) {
                
                StackPane casilla = new StackPane();
                Posicion posicionActual = new Posicion(fila, columna);
                
                Relleno relleno = tablero.getCasilla(posicionActual).getRelleno();
                
                Label rellenoLabel = new Label( (relleno.name().equals("EMPTY")) ? " " : relleno.name() );
                
                rellenoLabel.setStyle(labelStyle);
                
                if(relleno == getRellenoP1()) rellenoLabel.setStyle(labelStyle + "-fx-text-fill: #539eee");
                if(relleno == getRellenoP2()) rellenoLabel.setStyle(labelStyle + "-fx-text-fill: #ff2b18");
                
                StackPane.setAlignment(rellenoLabel, Pos.CENTER);
                
                casilla.setPrefSize(ANCHO_TABLERO/3, ANCHO_TABLERO/3);
                casilla.setStyle(borderStyle);
                
                casilla.getChildren().add(rellenoLabel);
                gridPane.add(casilla, columna, fila);
            }
        }
        
        setUtilitiesToolTip(gridPane, tablero);
        
        return gridPane;
    }
    
    private void setUtilitiesToolTip(GridPane gridPane, Tablero tablero) {
        
        int utilidadX = tablero.calcularUtilidad(Relleno.X);
        int utilidadO = tablero.calcularUtilidad(Relleno.O);
        
        Tooltip utilidades = new Tooltip(String.format("U(X) = %d%nU(O) = %d", utilidadX, utilidadO));
        
        utilidades.setStyle("-fx-font-size: 12px; -fx-font-weight: bold");
        utilidades.setShowDelay(Duration.millis(250));
        
        Tooltip.install(gridPane, utilidades);
        
    }
    
    private Relleno getNextFill(Tablero tableroActual, Tablero tableroSiguiente) {
        
        Casilla[][] casillasActuales = tableroActual.getMatrizCasillas();
        Casilla[][] casillasSiguientes = tableroSiguiente.getMatrizCasillas();
        
        for(int fila = 0; fila < 3; fila ++) {
            
            for(int columna = 0; columna < 3; columna ++) {
                
                Relleno rellenoActual = casillasActuales[fila][columna].getRelleno();
                Relleno rellenoSiguiente = casillasSiguientes[fila][columna].getRelleno();
                
                if(rellenoActual != rellenoSiguiente) return rellenoSiguiente;
            }
        }
        
        return null;
    }
    
    private void fillTreeWithPossibilites(Tree<Tablero> arbol, Tree<Tablero> arbolTableroSiguiente) {
        
        Tablero tablero = arbol.getRoot().getContent();
        Tablero tableroSiguiente = arbolTableroSiguiente.getRoot().getContent();
        
        Relleno relleno = getNextFill(tablero, tableroSiguiente);
        
        Casilla[][] casillas = tablero.getMatrizCasillas();
        
        for(int fila = 0; fila < 3; fila ++) {
            
            for(int columna = 0; columna < 3; columna ++) {
                
                Casilla casilla = casillas[fila][columna];
                
                if(casilla.getRelleno() == Relleno.EMPTY) {
                    
                    Tablero clonTablero = tablero.getClone();
                    Posicion posicionActual = new Posicion(fila, columna);
                    
                    clonTablero.getCasilla(posicionActual).marcar(relleno);
                    
                    if(clonTablero.isEqualsTo(tableroSiguiente))
                        
                        arbol.addChild(arbolTableroSiguiente);
                    
                    else 
                        
                        arbol.addChild(new Tree(clonTablero));
                }
            }
        }
    }
    
    private List<Tree<Tablero>> wrapBoardsInTrees(List<Tablero> tableros) {
        
        List<Tree<Tablero>> arbolesTableros = new LinkedList();
        tableros.forEach(tablero -> arbolesTableros.add(new Tree(tablero)));
        
        return arbolesTableros;
    }
    
    private Tree<Tablero> buildTreeFromBoards(List<Tablero> tableros) {
        
        Tree<Tablero> arbol = new Tree(new Tablero());
        
        List<Tree<Tablero>> arbolesTableros = wrapBoardsInTrees(tableros);
        
        Tree<Tablero> punteroArbol = arbol;
        
        for(int i = 0; i < arbolesTableros.size(); i ++) {
            
            fillTreeWithPossibilites(punteroArbol, arbolesTableros.get(i));
            punteroArbol = arbolesTableros.get(i);
        }
        
        return arbol;
    }
    
    private void showGraphicalTree( Tree<Tablero> tree ) {
        
        showParentTree( tree );
        
        Stack<Tree<Tablero>> pila = new Stack();
        pila.push(tree);
        
        int nivel = 0;
        
        double padreX = ANCHO_TOTAL / 2;
        double padreY = ANCHO_TABLERO;
        
        while(!pila.isEmpty()) {
            
            nivel ++;
            
            double inicioX = padreX;
            double inicioY = padreY;
            
            List<Tree<Tablero>> arbolesTableros = pila.pop().getRoot().getChildren();
            double espacioTableros = (ANCHO_TOTAL - arbolesTableros.size() * ANCHO_TABLERO) / (arbolesTableros.size() - 1);
            
            HBox hbox = newBoardHBox( nivel, espacioTableros );
            
            int contador = 0;
            
            for(Tree<Tablero> arbolTablero : arbolesTableros) {
                
                Tablero tablero = arbolTablero.getRoot().getContent();
                
                GridPane tableroGridPane = buildGridPaneFromBoard(tablero);
                
                hbox.getChildren().add( tableroGridPane );
                
                double finalX = contador * (ANCHO_TABLERO + espacioTableros) + ANCHO_TABLERO/2;
                double finalY = (ANCHO_TABLERO + ESPACIADO_VERTICAL) * nivel;
                
                Line linea = new Line(inicioX, inicioY, finalX, finalY);
                backgroundPane.getChildren().add( linea );
                
                if(!arbolTablero.isLeaf()) { 
                    
                    pila.push(arbolTablero);
                    
                    padreX = finalX;
                    padreY = finalY + ANCHO_TABLERO;
                }
                
                contador ++;
            }
        }
    }
    
    private void showParentTree( Tree<Tablero> tree ) {
        
        Tablero tableroPadre = tree.getRoot().getContent();
        
        HBox hbox = new HBox();
        hbox.setPrefWidth(ANCHO_TOTAL);
        hbox.setAlignment(Pos.CENTER);
        
        hbox.getChildren().add( buildGridPaneFromBoard(tableroPadre) );
        
        backgroundPane.getChildren().add( hbox );
        
    }
    
    private HBox newBoardHBox ( int nivel, double espacioTableros ) {
        
        HBox hbox = new HBox();
            
        hbox.setPrefWidth(ANCHO_TOTAL);
        hbox.setLayoutY((ANCHO_TABLERO + ESPACIADO_VERTICAL) * nivel);
           
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing( espacioTableros );
            
        backgroundPane.getChildren().add( hbox );
        
        return hbox;
    }
}
