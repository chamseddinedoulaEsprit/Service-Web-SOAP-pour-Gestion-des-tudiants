package model;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jws.WebService;

@WebService(endpointInterface="model.GestionEtudiantsServiceImpl")

public class GestionEtudiantsServiceImpl implements GestionEtudiantService {
    Connection cn = ConnexionDB.getConnexion();
    Statement st = null;

    @Override
    public boolean AjouterEtudiant(Etudiant p) {
        String sql = "INSERT INTO `Etudiant` (`nom`,`prenom`,`age`) VALUES ('" + p.getNom() + "','" + p.getPrenom() +"'," + p.getAge() +")";
        try {
            st = cn.createStatement();
            st.executeUpdate(sql);
            System.out.println("Ajout avec succès");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur add");
            return false;
        }
    }
    
    

    @Override
    public Etudiant RecupererEtudiantParId(int id) {
    	Etudiant person = null;
        String sql = "SELECT `ID`, `Name`, `Age` FROM `Etudiant` WHERE id=" + id;
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                person = new Etudiant();
                person.setId(rs.getInt("id"));
                person.setAge(rs.getInt("age"));
                person.setNom(rs.getString("name"));
                person.setPrenom(rs.getString("prenom"));
            }
            return person;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Etudiant[] RecupererTousLesEtudiants() {
        Etudiant[] persons = new Etudiant[100];
        String sql = "SELECT * FROM `Etudiant`";
        try {
            int index = 0;
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
            	Etudiant person = new Etudiant();
                person.setId(rs.getInt("id"));
                person.setNom(rs.getString("Nom"));
                person.setAge(rs.getInt("age"));
                person.setPrenom(rs.getString("prenom"));
                persons[index] = person;
                index += 1;
            }
            return persons;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }



}
