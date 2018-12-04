package itsepalvelupos.ui;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import itsepalvelupos.database.AccountDao;
import itsepalvelupos.database.Database;
import itsepalvelupos.database.ProductDao;
import itsepalvelupos.domain.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;


public class PosUI extends Application {

    private StoreService storeService;

    @Override
    public void init() throws Exception {
        storeService = new StoreService("pos.db", 1);
    }

    @Override
    public void stop() throws SQLException {

    }

    @Override
    public void start(Stage primaryStage)  {

        primaryStage.setTitle("itsepalveluPOS");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text title = new Text("Tervetuloa!");
        title.setFont(Font.font("Verdana", FontWeight.NORMAL, 30));
        grid.add(title, 0, 0, 2, 1);

        Label userName = new Label("Käyttäjätunnus:");
        grid.add(userName, 0, 1);

        JFXTextField userTextField = new JFXTextField();
        userTextField.setLabelFloat(true);
        grid.add(userTextField, 1, 1);

        Label password = new Label("Salasana:");
        grid.add(password, 0, 2);

        JFXPasswordField passwordBox = new JFXPasswordField();
        grid.add(passwordBox, 1, 2);

        JFXButton button = new JFXButton("Lisää käyttäjä");
        button.getStyleClass().add("button-raised");

        HBox hotBoxButton = new HBox(10);
        hotBoxButton.setAlignment(Pos.BOTTOM_RIGHT);
        hotBoxButton.getChildren().add(button);
        grid.add(hotBoxButton, 1, 4);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);

        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                actionTarget.setFill(Color.FIREBRICK);
                if ((!userTextField.getText().isEmpty() && !passwordBox.getText().isEmpty())) {
                    actionTarget.setText("Käyttäjä tunnus: " + userTextField.getText() + " Salasana:" + passwordBox.getText());
                } else {
                    actionTarget.setText("Käyttäjätunnus tai salasana puuttuu");
                }
            }
        });

        Scene scene = new Scene(grid, 600, 350);
        scene.getStylesheets().add("itsepalvelupos/ui/stylesheet.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        launch(PosUI.class);
    }
}