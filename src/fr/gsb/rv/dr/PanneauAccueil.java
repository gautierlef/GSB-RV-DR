/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author developpeur
 */
public class PanneauAccueil extends Pane {
    public PanneauAccueil() {
        Label labelAccueil = new Label("Accueil");
        VBox vBox = new VBox();
        vBox.getChildren().add(labelAccueil);
        this.getChildren().add(vBox);
     }
}
