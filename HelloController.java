package com.example.divyatest2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    public Label username;
    public TextField Username;
    public PasswordField password;


    @FXML
    protected void onHelloButtonClick() {
        String uname= Username.getText();
        String upass= password.getText();
        if (uname.equals("")&&password.equals("")){

        }else

        try {
            Parent secondScene = FXMLLoader.load(getClass().getResource("2ndView.fxml"));

            Stage secondStage = new Stage();
            secondStage.setTitle("User Board");
            secondStage.setScene(new Scene(secondScene));
            Stage firstSceneStage = (Stage) username.getScene().getWindow();
            firstSceneStage.close();


            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}