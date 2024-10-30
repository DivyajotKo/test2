package com.example.divyatest2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class SecondController {

    public Label back;

    public void onLoginPageClick(ActionEvent actionEvent) {
        try {
            Parent secondScene = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

            Stage secondStage = new Stage();
            secondStage.setTitle("Login");
            secondStage.setScene(new Scene(secondScene));
            Stage firstSceneStage = (Stage) back.getScene().getWindow();
            firstSceneStage.close();


            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Button input;
    public void database(ActionEvent actionEvent) {
        try {
            Parent secondScene = FXMLLoader.load(getClass().getResource("crud.fxml"));

            Stage secondStage = new Stage();
            secondStage.setTitle("DataBase");
            secondStage.setScene(new Scene(secondScene));
            Stage firstSceneStage = (Stage) input.getScene().getWindow();
            firstSceneStage.close();


            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
