
package com.grupo4.proyecto;

import TDATree.Tree;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;

import archivos.*;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import jugadores.Humano;
import jugadores.Jugador;
import partida.Partida;
import sonidos.Sonidos;
import tablero.Casilla;
import tablero.Posicion;
import tablero.Relleno;
import tablero.Tablero;

public class GameController implements Initializable { 

    @FXML
    private VBox winsVBoxP1;
    @FXML
    private VBox winsVBoxP2;
    @FXML
    private Label timerLabelP1;
    @FXML
    private Label timerLabelP2;
    @FXML
    private VBox VBoxP1;
    @FXML
    private VBox VBoxP2;
    
    private Timeline timer;
    
    private final String BGStyleP1 = "-fx-background-color: linear-gradient(from 100% 50% to 0% 50%, #86BCF5, white);";
    private final String BGStyleP2 = "-fx-background-color: linear-gradient(from 0% 50% to 100% 50%, #FF4F40, white);";
    @FXML
    private Pane boardPane;
    @FXML
    private Pane treePane;
    @FXML
    private Button muteUnmuteButton;
    @FXML
    private HBox boxT;
    
    //PABLOSKI

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        loadVictories();
        
        if(Partida.SEGUNDOS_POR_TURNO != 0) setTimers();
        
        switch(getNumberOfCurrentPlayer()){
            case 1: VBoxP1.setStyle(BGStyleP1);
            break;
            case 2: VBoxP2.setStyle(BGStyleP2);
        }
        /*
        Tablero tablero = Partida.PARTIDA.getTablero();
        Jugador jugadorUno = Partida.PARTIDA.getJugadorUno();
        Jugador jugadorDos = Partida.PARTIDA.getJugadorDos();
        jugadorUno.marcarCasilla(tablero, new Posicion(0,0));
        jugadorDos.marcarCasilla(tablero, new Posicion(1,0));
        jugadorUno.marcarCasilla(tablero, new Posicion(1,1));
        jugadorDos.marcarCasilla(tablero, new Posicion(2,0));
        jugadorUno.marcarCasilla(tablero, new Posicion(2,2));
        jugadorDos.marcarCasilla(tablero, new Posicion(0,2));
        */
        generarTablero();
    }
    
    private void generarTablero(){
        GridPane gridpane=new GridPane();
        
        Tablero partida=Partida.PARTIDA.getTablero();
        
        for(int x=0;x<3;x++){
            for(int y=0;y<3;y++){
                int posx=x;
                int posy=y;
                Casilla n=partida.getMatrizCasillas()[x][y];
                StackPane pane=crearCasilla(n);
                
                pane.setOnMouseClicked(e->rellenar(pane, n));
                
                gridpane.add(pane,x,y);
            }
        }
        
        boxT.getChildren().add(gridpane);
        boxT.setAlignment(Pos.CENTER);
    
    }
    
    private StackPane crearCasilla(Casilla n){
        StackPane pane=new StackPane();
        int width=180;
        int height=180;
        
        pane.setPrefSize(width, height);
        pane.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
        pane.setCursor(Cursor.HAND);
        
        if(n.getRelleno()!=Relleno.EMPTY){
            Label simbolo=new Label(n.getRelleno().name());
            simbolo.setStyle("-fx-font-size: 50");
            pane.getChildren().add(simbolo);
        }else{
            Label simbolo=new Label();
            simbolo.setStyle("-fx-font-size: 50");
            pane.getChildren().add(simbolo);
        }
       
        return pane;
    }
    
    private void rellenar(StackPane pane, Casilla n){
        //Solo permite clickear si el jugador actual es humano.
        if(Partida.PARTIDA.getJUGADOR_ACTUAL() instanceof Humano){
            //Actualiza el visual
            Label simbolo=new Label(Partida.PARTIDA.getJUGADOR_ACTUAL().getRelleno().name());
            simbolo.setStyle("-fx-font-size: 50");
            pane.getChildren().add(simbolo);
            pane.setMouseTransparent(true);
            //Actualiza el tablero
            n.marcar(Partida.PARTIDA.getJUGADOR_ACTUAL().getRelleno());
            
            shiftChange();
        }
    }
    
    private int getNumberOfCurrentPlayer() {
        
        if (Partida.JUGADOR_ACTUAL == Partida.PARTIDA.getJugadorDos()) return 2;
        return 1;
    }
    
    private void shiftChange() {
        
        switch(getNumberOfCurrentPlayer()){
            case 1: {
                Partida.JUGADOR_ACTUAL = Partida.PARTIDA.getJugadorDos();
                VBoxP2.setStyle(BGStyleP2);
                VBoxP1.setStyle("");
            } 
            break;
            case 2: {
                Partida.JUGADOR_ACTUAL = Partida.PARTIDA.getJugadorUno();
                VBoxP1.setStyle(BGStyleP1);
                VBoxP2.setStyle("");
            } 
        }
        
        if(timer != null){
            updateTimersVisibility();
            resetTimers();
        }
    }

    private void setTimers() {
        
        updateTimersVisibility();
        
        timerLabelP1.setText(Integer.toString(Partida.SEGUNDOS_POR_TURNO));
        timerLabelP2.setText(Integer.toString(Partida.SEGUNDOS_POR_TURNO));
        
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimerLabels()));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        
    }
    
    private void updateTimerLabels() {
        
        int segundosRestantes = Integer.parseInt(timerLabelP1.getText()) - 1;
        
        if(segundosRestantes < 1) shiftChange();
        else {
            
            timerLabelP1.setText(Integer.toString(segundosRestantes));
            timerLabelP2.setText(Integer.toString(segundosRestantes));
        }
        
    }
    
    private void updateTimersVisibility() {
        
        switch(getNumberOfCurrentPlayer()){
            case 1: timerLabelP1.setVisible(true); timerLabelP2.setVisible(false);
            break;
            case 2: timerLabelP2.setVisible(true); timerLabelP1.setVisible(false);
        }
    }
    
    private void resetTimers() {
        
        timerLabelP1.setText(Integer.toString(Partida.SEGUNDOS_POR_TURNO));
        timerLabelP2.setText(Integer.toString(Partida.SEGUNDOS_POR_TURNO));
        
        timer.playFromStart();
    }
    
    private void loadVictories() {
        
        for(int i = 0; i < Partida.PARTIDA.getJugadorUno().getVictorias(); i++) {
            winsVBoxP1.getChildren().add(newTrophyPane());
        }
        
        for(int i = 0; i < Partida.PARTIDA.getJugadorDos().getVictorias(); i++) {
            winsVBoxP2.getChildren().add(newTrophyPane());
        }
        
    }
    
    private void addVictoryToCurrentPlayer() {
        
        switch(getNumberOfCurrentPlayer()){
            case 1: winsVBoxP1.getChildren().add(newTrophyPane());
            break;
            case 2: winsVBoxP2.getChildren().add(newTrophyPane());
        }
    }
    
    private Pane newTrophyPane() {
        Pane pane = new Pane();
        
        pane.setPrefHeight(72.0);
        pane.setMaxWidth(72.0);
        
        pane.setStyle(String.format("-fx-background-image:url('%s');", App.class.getResource("images/game/trophy.png")));
        
        return pane;
    }

    @FXML
    private void menu(ActionEvent event) throws IOException {
        
        Partida.SEGUNDOS_POR_TURNO = 0;
        if(timer != null) timer.stop();
        
        App.setRoot("menu");
        
    }

    @FXML
    private void saveGame(ActionEvent event) {
        
        if(timer != null) timer.pause();
        
        File archivoPartida = SelectorArchivos.guardarArchivoPartida();
        if(archivoPartida != null) GuardadorPartida.guardarPartida(archivoPartida);
        
        if(timer != null) timer.play();
    }

    @FXML
    private void showTree(ActionEvent event) throws IOException {
        
        if(treePane.isVisible()) {
            
            treePane.setVisible(false);
            if(timer != null) timer.play();
            
        } else {
            
            if(timer != null) timer.pause();
            treePane.setVisible(true);
            
            Parent tree = App.loadFXML("tree");
            treePane.getChildren().add(tree);
        }
    }

    @FXML
    private void muteUnmuteMusic(ActionEvent event) {
        
        if(!muteUnmuteButton.getStyle().contains("mute.png")) {
            
            muteUnmuteButton.setStyle(String.format("-fx-graphic: url('%s');", App.class.getResource("images/game/mute.png")));
            Sonidos.pauseBackgroundMusic();
            
        } else {
            
            muteUnmuteButton.setStyle(String.format("-fx-graphic: url('%s');", App.class.getResource("images/game/sound.png")));
            Sonidos.resumeBackgroundMusic();
        }
    }
    
}
