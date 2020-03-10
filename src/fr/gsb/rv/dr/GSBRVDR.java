/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
//    Visiteur visiteur = new Visiteur("OB0041", "Oumayma", "BELLILI"); //TEST 2-4
    Visiteur visiteur = null; //TEST 2-8 et TEST 3-4
    boolean session = Session.estOuverte();

    @Override
    public void start(Stage primaryStage) throws ConnexionException, SQLException {
/*        Connection connexion = ConnexionBD.getConnexion(); //TEST 2-8
        ResultSet res;
        String req = "SELECT vis_nom, vis_prenom, vis_matricule, vis_mot_de_passe FROM Visiteur where vis_matricule = ?";
        PreparedStatement pstmt = (PreparedStatement) connexion.prepareStatement(req);
        pstmt.setString(1, "a131");
        res = pstmt.executeQuery();
        while (res.next()) {
            visiteur = new Visiteur(res.getString(3), res.getString(1), res.getString(2));
        }*/
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
            try {   //TEST 3.4
                visiteur = ModeleGsbRv.seConnecter("a131", "azerty");
            } catch (ConnexionException ex) {
                Logger.getLogger(GSBRVDR.class.getName()).log(Level.SEVERE, null, ex);
            }
            Session.ouvrir(visiteur);
            session = Session.estOuverte();
            primaryStage.setTitle("GSB-RV-DR " + Session.getVisiteur().getNom() + " " + Session.getVisiteur().getPrenom());
            itemSeConnecter.setDisable(session);
            itemSeDeconnecter.setDisable(!session);
            menuRapports.setDisable(!session);
            menuPraticiens.setDisable(!session);
        });
        itemSeDeconnecter.setOnAction(actionEvent -> {
            root.setCenter(new Label("Se déconnecter"));
            Session.fermer();
            session = Session.estOuverte();
            primaryStage.setTitle("GSB-RV-DR");
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
            }
        });
        itemConsulter.setOnAction(actionEvent -> {
            root.setCenter(new Label("[Rapports] " + Session.getVisiteur().getNom() + " " + Session.getVisiteur().getPrenom())); //TEST 2-4, 2-8 et 3-4
        });
        itemHesitants.setOnAction(actionEvent -> {
            root.setCenter(new Label("[Praticiens] " + Session.getVisiteur().getNom() + " " + Session.getVisiteur().getPrenom())); //TEST 2-4, 2-8 et 3-4
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
