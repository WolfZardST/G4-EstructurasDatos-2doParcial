package com.grupo4.proyecto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import partida.Partida;
import tablero.Relleno;
import jugadores.*;
import archivos.*;
import java.io.File;
import sonidos.Sonidos;

public class MenuController implements Initializable { 

    @FXML
    private Pane winsPane;
    @FXML
    private Label winsLabel;
    @FXML
    private Pane firstTurnPaneP1;
    @FXML
    private Pane firstTurnPaneP2;
    @FXML
    private Pane IAPaneP1;
    @FXML
    private Pane humanPaneP1;
    @FXML
    private Pane humanPaneP2;
    @FXML
    private Pane IAPaneP2;
    @FXML
    private Pane fillPaneP1;
    @FXML
    private Pane fillPaneP2;
    @FXML
    private Pane newGamePane;
    @FXML
    private Pane loadGamePane;
    @FXML
    private Pane exitPane;
    @FXML
    private Label timerLabel;
    @FXML
    private Pane timerPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Pane[] panes = {winsPane, firstTurnPaneP1, firstTurnPaneP2, IAPaneP1, 
                        IAPaneP2, humanPaneP1, humanPaneP2, fillPaneP1, fillPaneP2,
                        newGamePane, loadGamePane, exitPane, timerPane};
        
        for(Pane pane: panes) {
            pane.setOnMouseEntered(e -> mouseEnteredButton(pane));
            pane.setOnMouseExited(e -> mouseExitedButton(pane));
            pane.setOnMouseReleased(e -> mouseEnteredButton(pane));
            pane.setOnMousePressed(e -> mouseExitedButton(pane));
        }
        
        fillPaneP1.setStyle(String.format("-fx-background-image:url('%s');", App.class.getResource("images/menu/O.png")));
        fillPaneP2.setStyle(String.format("-fx-background-image:url('%s');", App.class.getResource("images/menu/X.png")));
        
    }

    @FXML
    private void winsButton(MouseEvent event) {
        
        Sonidos.playTrophySound();
        
        int wins = Integer.valueOf(winsLabel.getText());
        wins = (wins < 3) ? wins + 1 : 1;
        
        winsLabel.setText(Integer.toString(wins));
        
        Partida.VICTORIAS_PARA_GANAR = wins;
    }
    
    private void mouseEnteredButton( Pane pane ) {
        pane.setOpacity(pane.getOpacity() - 0.15);
    }
    
    private void mouseExitedButton( Pane pane ) {
        pane.setOpacity(pane.getOpacity() + 0.15);
    }

    @FXML
    private void firstTurnP1(MouseEvent event) {
        Sonidos.playLightSound();
        
        firstTurnPaneP1.setOpacity(0.0);
        firstTurnPaneP2.setOpacity(0.85);
    }

    @FXML
    private void firstTurnP2(MouseEvent event) {
        Sonidos.playLightSound();
        
        firstTurnPaneP2.setOpacity(0.0);
        firstTurnPaneP1.setOpacity(0.85);
    }

    @FXML
    private void IAP1(MouseEvent event) {
        Sonidos.playIASound();
        
        IAPaneP1.setOpacity(0.0);
        humanPaneP1.setOpacity(0.85);
    }

    @FXML
    private void humanP1(MouseEvent event) {
        Sonidos.playHumanSound();
        
        IAPaneP1.setOpacity(0.85);
        humanPaneP1.setOpacity(0.0);
    }

    @FXML
    private void humanP2(MouseEvent event) {
        Sonidos.playHumanSound();
        
        IAPaneP2.setOpacity(0.85);
        humanPaneP2.setOpacity(0.0);
    }

    @FXML
    private void IAP2(MouseEvent event) {
        Sonidos.playIASound();
        
        IAPaneP2.setOpacity(0.0);
        humanPaneP2.setOpacity(0.85);
    }

    @FXML
    private void changeFill(MouseEvent event) {
        Sonidos.playSlideSound();
        
        if(fillPaneP1.getStyle().contains("O.png")) {
            
            fillPaneP1.setStyle(String.format("-fx-background-image:url('%s');", App.class.getResource("images/menu/X.png")));
            fillPaneP2.setStyle(String.format("-fx-background-image:url('%s');", App.class.getResource("images/menu/O.png")));
        } else {
            fillPaneP1.setStyle(String.format("-fx-background-image:url('%s');", App.class.getResource("images/menu/O.png")));
            fillPaneP2.setStyle(String.format("-fx-background-image:url('%s');", App.class.getResource("images/menu/X.png")));
        }
    }

    @FXML
    private void newGame(MouseEvent event) throws IOException {
        
                
        Relleno rellenoJugadorUno = (fillPaneP1.getStyle().contains("O.png")) ? Relleno.O : Relleno.X;
        Relleno rellenoJugadorDos = (fillPaneP2.getStyle().contains("O.png")) ? Relleno.O : Relleno.X;
        
        Jugador jugadorUno = (humanPaneP1.getOpacity() == 0.15) ? new Humano(rellenoJugadorUno) : new Ordenador(rellenoJugadorUno);
        Jugador jugadorDos = (humanPaneP2.getOpacity() == 0.15) ? new Humano(rellenoJugadorDos) : new Ordenador(rellenoJugadorDos);
        
        Partida partida = new Partida(jugadorUno,jugadorDos);
        
        Partida.JUGADOR_ACTUAL = (firstTurnPaneP1.getOpacity() == 0.15) ? jugadorUno : jugadorDos;
        
        App.setRoot("game");
        
    }

    @FXML
    private void loadGame(MouseEvent event) throws IOException {
        
        File archivoPartida = SelectorArchivos.cargarArchivoPartida();
        if(archivoPartida != null) {
            
            CargadorPartida.cargarPartida(archivoPartida);
            App.setRoot("game");
        }
    }

    @FXML
    private void exit(MouseEvent event) {
        System.exit(0);
        Platform.exit();
    }

    @FXML
    private void changeTimer(MouseEvent event) {
        Sonidos.playClockSound();
        
        switch(timerLabel.getText()) {
            
            case "- - -" : timerLabel.setText("3 sec"); Partida.SEGUNDOS_POR_TURNO = 3;
            break;
            case "3 sec" : timerLabel.setText("5 sec"); Partida.SEGUNDOS_POR_TURNO = 5;
            break;
            case "5 sec" : timerLabel.setText("- - -"); Partida.SEGUNDOS_POR_TURNO = 0;
        }
    }

}
