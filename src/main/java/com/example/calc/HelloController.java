package com.example.calc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    public Button rbotonResolver;
    public TextField op;
    @FXML
    private Label welcomeText;
    private Calculadora calculadora = new Calculadora();
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    public void resolver(ActionEvent event){
        String operacion = op.getText();
        calculadora.operar(operacion);
    }
}