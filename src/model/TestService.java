package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestService {
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    public static void main(String[] args) {
        GestionEtudiantsServiceImpl service = new GestionEtudiantsServiceImpl();

        try {
            // Création de quelques étudiants de test
            Etudiant e1 = new Etudiant("Doula", "Chamseddine", "1995-01-01", "4TWIN");
            Etudiant e2 = new Etudiant("Yaacoub", "Eya", "1996-02-02", "4TWIN");
            Etudiant e3 = new Etudiant("Chokri", "Yassine", "1997-03-03", "4TWIN");
            
            // Test de l'ajout des étudiants
            logger.info("Ajout des étudiants...");
            service.ajouterEtudiant(e1);
            service.ajouterEtudiant(e2);
            service.ajouterEtudiant(e3);

            // Test de la récupération de tous les étudiants
            logger.info("Récupération de tous les étudiants...");
            service.getAllEtudiants().forEach(e -> 
                logger.info("Étudiant trouvé: {}", e)
            );
            
        } catch (Exception e) {
            logger.error("Erreur lors des tests: {}", e.getMessage());
        }
    }
}