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
public class PanneauRapports extends Pane {
    private Pane pane = new Pane();
    
    public PanneauRapports() {
        VBox grid = new VBox();
        Label label = new Label("Rapports");
        grid.getChildren().add(label);
        grid.setStyle("-fx-background-color : white"); 
        pane.getChildren().add(grid);
    }
    
    public Pane getPane() {
        return pane;
    }
}
