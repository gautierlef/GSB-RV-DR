/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import fr.gsb.rv.dr.utilitaires.ComparateurDateVisite;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author developpeur
 */
public class PanneauPraticiens extends Pane {
    public static int CRITERE_COEF_CONFIANCE = 1;
    public static int CRITERE_COEF_NOTORIETE = 2;
    public static int CRITERE_DATE_VISITE = 3;
    private int critereTri = CRITERE_COEF_CONFIANCE;
    private RadioButton rbCoefConfiance;
    private RadioButton rbCoefNotoriete;
    private RadioButton rbDateVisite;
    private TableView<Praticien> tvPraticiens;
    private Pane pane = new Pane();
    
    public PanneauPraticiens() {
        VBox root = new VBox();
        ToggleGroup groupe = new ToggleGroup();
        rbCoefConfiance = new RadioButton("Confiance");
        rbCoefNotoriete = new RadioButton("Notoriété");
        rbDateVisite = new RadioButton("Date Visite");
        groupe.getToggles().addAll(rbCoefConfiance, rbCoefNotoriete, rbDateVisite);
        switch(critereTri) {
            case 1:
                rbCoefConfiance.setSelected(true);
                break;
            case 2:
                rbCoefNotoriete.setSelected(true);
                break;
            case 3:
                rbDateVisite.setSelected(true);
                break;
        }
        rbCoefConfiance.setOnAction( (ActionEvent event) -> {
            critereTri = CRITERE_COEF_CONFIANCE;
            try {
                rafraichir();
            } catch (ConnexionException | SQLException ex) {
                Logger.getLogger(PanneauPraticiens.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        rbCoefNotoriete.setOnAction( (ActionEvent event) -> {
            critereTri = CRITERE_COEF_NOTORIETE;
            try {
                rafraichir();
            } catch (ConnexionException | SQLException ex) {
                Logger.getLogger(PanneauPraticiens.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        rbDateVisite.setOnAction( (ActionEvent event) -> {
            critereTri = CRITERE_DATE_VISITE;
            try {
                rafraichir();
            } catch (ConnexionException | SQLException ex) {
                Logger.getLogger(PanneauPraticiens.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        tvPraticiens = new TableView<Praticien>();
        TableColumn<Praticien,Integer> colNumero = new TableColumn<Praticien,Integer>("Numéro");
        TableColumn<Praticien,String> colNom = new TableColumn<Praticien,String>("Nom");
        TableColumn<Praticien,String> colVille = new TableColumn<Praticien,String>("Ville");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colVille.setCellValueFactory(new PropertyValueFactory<>("ville"));
        tvPraticiens.getColumns().addAll(colNumero, colNom, colVille);
        HBox boutons = new HBox();
        boutons.getChildren().add(rbCoefConfiance);
        boutons.getChildren().add(rbCoefNotoriete);
        boutons.getChildren().add(rbDateVisite);
        root.getChildren().add(boutons);
        root.getChildren().add(tvPraticiens);
        root.setStyle("-fx-background-color : white");
        pane.getChildren().add(root);
    }
    
    public Pane getPane() {
        return pane;
    }
    
    public void rafraichir() throws ConnexionException, SQLException{
        List<Praticien> liste = ModeleGsbRv.getPraticiensHesitants();
        ObservableList<Praticien> listeObs = FXCollections.observableArrayList(liste);
        switch(critereTri) {
            case 1:
                Collections.sort(listeObs, new ComparateurCoefConfiance());
                break;
            case 2:
                Collections.sort(listeObs, new ComparateurCoefNotoriete());
                Collections.reverse(listeObs);
                break;
            case 3:
                Collections.sort(listeObs, new ComparateurDateVisite());
                Collections.reverse(listeObs);
                break;
        }
        tvPraticiens.setItems(listeObs);
    }
    
    public int getCritereTri(){
        return critereTri;
    }
    
    public void setCritereTri(int critere) {
        critereTri = critere;
    }
}
