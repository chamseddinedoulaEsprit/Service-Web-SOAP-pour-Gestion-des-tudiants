package model;

import jakarta.jws.WebService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(endpointInterface = "model.GestionEtudiantService")
public class GestionEtudiantsServiceImpl implements GestionEtudiantService {
    private static final Logger logger = LoggerFactory.getLogger(GestionEtudiantsServiceImpl.class);
    
    @Override
    public boolean ajouterEtudiant(Etudiant e) {
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO etudiant (nom, prenom, dateNaissance, classe) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, e.getNom());
            ps.setString(2, e.getPrenom());
            ps.setString(3, e.getDateNaissance());
            ps.setString(4, e.getClasse());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            logger.error("Erreur lors de l'ajout d'un étudiant: {}", ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean supprimerEtudiant(int id) {
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM etudiant WHERE id = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            logger.error("Erreur lors de la suppression d'un étudiant: {}", ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean modifierEtudiant(Etudiant e) {
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE etudiant SET nom = ?, prenom = ?, dateNaissance = ?, classe = ? WHERE id = ?")) {
            ps.setString(1, e.getNom());
            ps.setString(2, e.getPrenom());
            ps.setString(3, e.getDateNaissance());
            ps.setString(4, e.getClasse());
            ps.setInt(5, e.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            logger.error("Erreur lors de la modification d'un étudiant: {}", ex.getMessage());
            return false;
        }
    }

    @Override
    public Etudiant getEtudiant(int id) {
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM etudiant WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Etudiant e = new Etudiant();
                    e.setId(rs.getInt("id"));
                    e.setNom(rs.getString("nom"));
                    e.setPrenom(rs.getString("prenom"));
                    e.setDateNaissance(rs.getString("dateNaissance"));
                    e.setClasse(rs.getString("classe"));
                    return e;
                }
            }
        } catch (SQLException ex) {
            logger.error("Erreur lors de la récupération d'un étudiant: {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Etudiant> getAllEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        try (Connection conn = ConnexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM etudiant")) {
            while (rs.next()) {
                Etudiant e = new Etudiant();
                e.setId(rs.getInt("id"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setDateNaissance(rs.getString("dateNaissance"));
                e.setClasse(rs.getString("classe"));
                etudiants.add(e);
            }
        } catch (SQLException ex) {
            logger.error("Erreur lors de la récupération des étudiants: {}", ex.getMessage());
        }
        return etudiants;
    }
}




