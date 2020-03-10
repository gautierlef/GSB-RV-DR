package fr.gsb.rv.dr.modeles;

import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModeleGsbRv {
    public static Visiteur seConnecter( String matricule , String mdp ) throws ConnexionException {
        Connection connexion = ConnexionBD.getConnexion();
        String requete = "select vis_nom, vis_prenom"
                + "from Visiteur "
                + "where vis_matricule = ?";

        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            requetePreparee.setString(1 , matricule);
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
}