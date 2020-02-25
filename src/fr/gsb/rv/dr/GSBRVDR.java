/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCode;

/**
 *
 * @author developpeur
 */
public class GSBRVDR extends Application {

    public boolean session = false;

    @Override
    public void start(Stage primaryStage) {
        MenuBar barreMenus = new MenuBar();
        Menu menuFichier = new Menu("Fichier");
        MenuItem itemSeConnecter = new MenuItem("Se connecter");
        MenuItem itemSeDeconnecter = new MenuItem("Se déconnecter");
        itemSeDeconnecter.setDisable(!session);
        MenuItem itemQuitter = new MenuItem("Quitter");
        itemQuitter.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        menuFichier.getItems().add(itemSeConnecter);
        menuFichier.getItems().add(itemSeDeconnecter);
        menuFichier.getItems().add(itemQuitter);
        barreMenus.getMenus().add(menuFichier);
        Menu menuRapports = new Menu("Rapports");
        MenuItem itemConsulter = new MenuItem("Consulter");
        menuRapports.getItems().add(itemConsulter);
        barreMenus.getMenus().add(menuRapports);
        menuRapports.setDisable(!session);
        Menu menuPraticiens = new Menu("Praticiens");
        MenuItem itemHesitants = new MenuItem("Hésitants");
        menuPraticiens.getItems().add(itemHesitants);
        barreMenus.getMenus().add(menuPraticiens);
        menuPraticiens.setDisable(!session);
        BorderPane root = new BorderPane();
        root.setTop(barreMenus);

        itemSeConnecter.setOnAction(actionEvent -> {
            root.setCenter(new Label("Se connecter"));
            session = true;
            itemSeConnecter.setDisable(session);
            itemSeDeconnecter.setDisable(!session);
            menuRapports.setDisable(!session);
            menuPraticiens.setDisable(!session);
        });
        itemSeDeconnecter.setOnAction(actionEvent -> {
            root.setCenter(new Label("Se déconnecter"));
            session = false;
            itemSeConnecter.setDisable(session);
            itemSeDeconnecter.setDisable(!session);
            menuRapports.setDisable(!session);
            menuPraticiens.setDisable(!session);
        });
        itemQuitter.setOnAction(actionEvent -> {
            Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
            ButtonType btnOui = new ButtonType ("Oui");
            ButtonType btnNon = new ButtonType ("Non");
            alert.setTitle ("Quitter");
            alert.setHeaderText("Demande de confirmation");
            alert.setContentText("Êtes vous sûr de vouloir quitter l'application?");
            alert.getButtonTypes().setAll(btnNon, btnOui);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == btnOui) {
                Platform.exit();
                System.out.println(session);
            }
        });
        itemConsulter.setOnAction(actionEvent -> {
            root.setCenter(new Label("Consulter"));
        });
        itemHesitants.setOnAction(actionEvent -> {
            root.setCenter(new Label("Hésitants"));
        });

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("GSB-RV-DR");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
