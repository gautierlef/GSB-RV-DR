/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import javafx.geometry.HPos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 *
 * @author developpeur
 */
public class VueConnexion extends Dialog<Pair<String, String>> {
    public VueConnexion() {
        ButtonType btnConfirmer = new ButtonType ("Confirmer");
        ButtonType btnAnnuler = new ButtonType ("Annuler");
        TextField textFieldMatricule = new TextField("");
        PasswordField passwordFieldMdp = new PasswordField();
        Label labelId = new Label("Identifiant : ");
        Label labelMdp = new Label("Mot de passe : ");
        GridPane root = new GridPane();
        root.add(labelId, 0, 0);
        root.add(textFieldMatricule, 1, 0);
        root.add(labelMdp, 0, 1);
        root.add(passwordFieldMdp, 1, 1);
        root.setHgap(25);
        root.setVgap(15);
        root.setHalignment(labelId, HPos.LEFT);
        root.setHalignment(textFieldMatricule, HPos.RIGHT);
        root.setHalignment(labelMdp, HPos.LEFT);
        root.setHalignment(passwordFieldMdp, HPos.RIGHT);
        this.setTitle("Connexion");
        this.setHeaderText("Se connecter :");
        this.getDialogPane().setContent(root);
        this.getDialogPane().getButtonTypes().setAll(btnAnnuler, btnConfirmer);
        setResultConverter((ButtonType typeBouton) -> {
            if (typeBouton == btnConfirmer) {
                String matricule = textFieldMatricule.getText();
                String mdp = passwordFieldMdp.getText();
                if ("".equals(matricule) || "".equals(mdp)) {
                    matricule = "erreur";
                    mdp = "erreur";
                }
                return new Pair<String, String>(matricule, mdp);
            }
            return null;
        });
    }
}
