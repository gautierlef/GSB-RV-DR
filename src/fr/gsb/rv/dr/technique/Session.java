/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.technique;
import fr.gsb.rv.dr.entites.Visiteur;

/**
 *
 * @author developpeur
 */
public class Session {
    private static Session session = null;
    private static Visiteur leVisiteur = null;

    private Session(Visiteur leVisiteur) {
        Session.leVisiteur = leVisiteur;
    }
    
    public static void ouvrir(Visiteur visiteur) {
        Session.session = new Session(visiteur);
    }
    
    public static void fermer() {
        Session.session = null;
        Session.leVisiteur = null;
    }
    
    public static Session getSession() {
        return Session.session;
    }
    
    public static Visiteur getVisiteur() {
        return Session.leVisiteur;
    }
    
    public static boolean estOuverte() {
        return Session.session != null;
    }
}
