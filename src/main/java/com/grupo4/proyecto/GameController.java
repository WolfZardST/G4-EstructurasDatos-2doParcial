
package com.grupo4.proyecto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class GameController implements Initializable { //PABLOSKI

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void menu(ActionEvent event) throws IOException {
        App.setRoot("menu");
        
    }
    
}
