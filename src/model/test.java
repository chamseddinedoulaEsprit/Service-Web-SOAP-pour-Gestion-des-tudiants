package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class test {
    private static final Logger logger = LoggerFactory.getLogger(test.class);

    public static void main(String[] args) {
        try {
            GestionEtudiantsServiceImpl service = new GestionEtudiantsServiceImpl();

            // Création des étudiants de test avec les bons champs
            Etudiant e1 = new Etudiant("Doula", "Chamseddine", "2000-01-15", "4TWIN");
            Etudiant e2 = new Etudiant("Yaacoub", "Eya", "2000-05-20", "4TWIN");
            Etudiant e3 = new Etudiant("Chokri", "Yassine", "2000-08-10", "4TWIN");
            
            // Ajout des étudiants
            System.out.println("Status ajout 1: " + service.ajouterEtudiant(e1));
            System.out.println("Status ajout 2: " + service.ajouterEtudiant(e2));
            System.out.println("Status ajout 3: " + service.ajouterEtudiant(e3));
            
            // Récupération de tous les étudiants
            System.out.println("\n=== Liste de tous les étudiants ===");
            service.getAllEtudiants().forEach(e -> {
                System.out.println(e.toString());
            });
            
            // Test de récupération par ID
            System.out.println("\n=== Récupération de l'étudiant ID=1 ===");
            Etudiant etudiant = service.getEtudiant(1);
            if (etudiant != null) {
                System.out.println(etudiant);
            }
            
            // Test de modification
            if (etudiant != null) {
                System.out.println("\n=== Modification de l'étudiant ID=1 ===");
                etudiant.setClasse("5TWIN");
                boolean modified = service.modifierEtudiant(etudiant);
                System.out.println("Status modification: " + modified);
            }
            
        } catch (Exception e) {
            logger.error("Erreur lors des tests: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}