package fr.gsb.rv.dr.modeles;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ModeleGsbRv {
    public static Visiteur seConnecter( String matricule , String mdp ) throws ConnexionException {
        Connection connexion = ConnexionBD.getConnexion();
        String requete = "select vis_nom, vis_prenom from Visiteur where vis_matricule = ? and vis_mot_de_passe = ?";
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            requetePreparee.setString(1 , matricule);
            requetePreparee.setString(2 , mdp);
            ResultSet resultat = requetePreparee.executeQuery();
            if (resultat.next()) {
                Visiteur visiteur = new Visiteur(matricule, resultat.getString("vis_nom"), resultat.getString("vis_prenom"));
                requetePreparee.close();
                return visiteur;
            }
            else {
                return null;
            }
        }
        catch(SQLException e) {
            return null;
        }
    }
    
    public static List<Praticien> getPraticiensHesitants() throws ConnexionException {
        Connection connexion = ConnexionBD.getConnexion();
        String requete = "SELECT p.pra_num, p.pra_nom, p.pra_ville, p.pra_coefnotoriete, MAX(r.rap_date_visite) AS date, r.rap_coef_confiance"
                + " FROM Praticien p INNER JOIN RapportVisite r ON p.pra_num = r.pra_num"
                + " GROUP BY p.pra_num";
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            ResultSet resultat = requetePreparee.executeQuery();
            List<Praticien> praticiensHesitants = new ArrayList<Praticien>();
            while (resultat.next()) {
                Praticien praticien = new Praticien(resultat.getString("pra_num"), resultat.getString("pra_nom"), resultat.getString("pra_ville"), Float.parseFloat(resultat.getString("pra_coefnotoriete")), Date.valueOf(resultat.getString("date")).toLocalDate(), Integer.parseInt(resultat.getString("rap_coef_confiance")));
//                System.out.println(praticien);
                praticiensHesitants.add(praticien);
            }
            requetePreparee.close();
            return praticiensHesitants;
        }
        catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
