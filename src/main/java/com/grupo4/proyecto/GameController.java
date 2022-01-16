
package com.grupo4.proyecto;

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
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import jugadores.*;
import partida.*;
import sonidos.Sonidos;
import tablero.*;

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
    
    private final String fillStyle = "-fx-graphic-text-gap: 0px; -fx-font-weight: bold; -fx-font-size: 118px;";
    private final String squareStyle = "-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;";
    
    @FXML
    private HBox boardPane;
    @FXML
    private Pane treePane;
    @FXML
    private Button muteUnmuteButton;
    @FXML
    private Button btnMenu;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        loadVictories();
        
        if(Partida.SEGUNDOS_POR_TURNO != 0) setTimers();
        
        switch(getNumberOfCurrentPlayer()){
            case 1: VBoxP1.setStyle(BGStyleP1);
            break;
            case 2: VBoxP2.setStyle(BGStyleP2);
        }

        actualizarTablero();
        if(Partida.JUGADOR_ACTUAL instanceof Ordenador) IAMove();
    }
    
    private void IAMove() {
       
        Posicion posicion = Partida.JUGADOR_ACTUAL.requestMove();
        Partida.JUGADOR_ACTUAL.marcarCasilla(posicion);
        
        actualizarTablero();
        checkForVictory();
    }
    
    private void actualizarTablero(){
        
        boardPane.getChildren().clear();
        
        GridPane gridpane=new GridPane();
        Tablero partida=Partida.PARTIDA.getTablero();
        
        for(int x=0;x<3;x++){
            
            for(int y=0;y<3;y++){
                
                Casilla n = partida.getMatrizCasillas()[x][y];
                StackPane pane = crearCasilla(n);
                Posicion pos = new Posicion(x, y);
                
                pane.setOnMouseClicked(e->rellenarCasilla(pane, pos));
                
                pane.setOnMouseEntered(e->oscurecerCasilla(pane));
                pane.setOnMouseExited(e->esclarecerCasilla(pane));
                
                pane.setOnMouseReleased(e->oscurecerCasilla(pane));
                pane.setOnMousePressed(e->esclarecerCasilla(pane));
                
                gridpane.add(pane, y, x);
            }
        }
        
        boardPane.getChildren().add(gridpane);
    }
    
    private StackPane crearCasilla(Casilla n){
        
        StackPane pane=new StackPane();
        pane.setPrefSize(180.0, 180.0);
        
        pane.setStyle(squareStyle);
        pane.setCursor(Cursor.HAND);
        
        Relleno relleno = n.getRelleno();
        
        if(relleno != Relleno.EMPTY){
            
            Label simbolo = new Label(relleno.name());
            
            if(relleno == Partida.PARTIDA.getJugadorUno().getRelleno()) simbolo.setStyle(fillStyle + "-fx-text-fill: #539eee");
            else simbolo.setStyle(fillStyle + "-fx-text-fill: #ff2b18");
            
            pane.getChildren().add(simbolo);
            pane.setMouseTransparent(true);
        }
        
        if(n.isVictoriosa()) pane.setStyle(squareStyle + "-fx-background-color: #FFFEBF");
       
        return pane;
    }
    
    private void oscurecerCasilla(StackPane pane) {
        
        pane.setStyle(squareStyle + "-fx-background-color: lightgray");
    }
    
    private void esclarecerCasilla(StackPane pane) {
        
        pane.setStyle(squareStyle);
    }
    
    private void rellenarCasilla(StackPane pane, Posicion posicion){
        
        //Solo permite clickear si el jugador actual es humano.
        if(Partida.JUGADOR_ACTUAL instanceof Humano){
            //Actualiza el visual
            
            Relleno relleno = Partida.JUGADOR_ACTUAL.getRelleno();
            
            Label simbolo = new Label(relleno.name());
            
            switch(getNumberOfCurrentPlayer()) {
                case 1: simbolo.setStyle(fillStyle + "-fx-text-fill: #539eee");
                break;
                case 2: simbolo.setStyle(fillStyle + "-fx-text-fill: #ff2b18");
            }
            
            pane.getChildren().add(simbolo);
            pane.setMouseTransparent(true);
            
            Sonidos.playSquareSound();
            
            //Actualiza el tablero
            Partida.JUGADOR_ACTUAL.marcarCasilla(posicion);
            
            checkForVictory();
        }

    }
    
    private void setNewBoard() {
        
        Partida.TABLEROS.clear();
        Partida.PARTIDA.setTablero(new Tablero());
        Partida.PARTIDA.setEstado(Estado.EMPATE);

        actualizarTablero();

        if (timer != null) resetTimers();
    }
    
    private void checkForVictory() {
        
        if(Partida.PARTIDA.getTablero().buscarTresEnRaya(Partida.JUGADOR_ACTUAL)) {
            
            addVictoryToCurrentPlayer();
            actualizarTablero();
            
            checkForWin();
            
        } else checkForDraw();
    }
    
    private void checkForWin() {
        
        if(Partida.JUGADOR_ACTUAL.getVictorias() == Partida.VICTORIAS_PARA_GANAR) endgame();
        
        else setNewBoard();
            
    }
    
    private void checkForDraw() {
        
        if(Partida.PARTIDA.getTablero().estaLleno()) setNewBoard();
        else shiftChange();
        
    }
    
    private void endgame() {
        
        Sonidos.playEndgameSound();
        
        Alert alerta = new Alert(AlertType.INFORMATION);
        
        alerta.setTitle("Partida Terminada");
        alerta.setHeaderText(Partida.PARTIDA.getEstado().name());
        
        if(Partida.JUGADOR_ACTUAL instanceof Ordenador) Platform.runLater(() -> alerta.showAndWait());
        
        else alerta.showAndWait();
        
        btnMenu.fire();
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
        
        if(Partida.JUGADOR_ACTUAL instanceof Ordenador) IAMove();
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
        
        Sonidos.playWinSound();
        
        Partida.JUGADOR_ACTUAL.sumarVictoria();
        
        switch(Partida.JUGADOR_ACTUAL.getRelleno()) {
                case O: Partida.PARTIDA.setEstado(Estado.GANA_O);
                break;
                case X: Partida.PARTIDA.setEstado(Estado.GANA_X);
            }
        
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
    
    private void loadTreeView() throws IOException {
        
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
    private void showTree(ActionEvent event) throws IOException {
        TreeController.minimaxTree = false;
        loadTreeView();
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

    @FXML
    private void showMinimaxTree(ActionEvent event) throws IOException {
        TreeController.minimaxTree = true;
        loadTreeView();
    }
    
}
