package model;

import java.net.URI;
import java.net.URL;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;

public class Client {
	
	public static void main(String[] args) {
		
		try {
			URL wsdlURL = URI.create("http://localhost:8084/service?wsdl").toURL();
		
		// Créer QName avec le targetNamespace et le nom du service
		QName qname = new QName("http://model/", "GestionEtudiantsServiceImplService");
		Service service = Service.create(wsdlURL, qname);
		
		// Obtenir le port du service
		GestionEtudiantService etudiantService = service.getPort(GestionEtudiantService.class);
		
		// Créer des étudiants de test
		Etudiant e1 = new Etudiant("Doula", "Chamseddine", "2000-01-15", "4TWIN");
		Etudiant e2 = new Etudiant("Chokri", "Yassine", "2000-05-20", "4TWIN");
		Etudiant e3 = new Etudiant("Yaacoub", "Eya", "2000-08-10", "4TWIN");
		
		// Ajouter des étudiants
		System.out.println("Ajout étudiant 1: " + etudiantService.ajouterEtudiant(e1));
		System.out.println("Ajout étudiant 2: " + etudiantService.ajouterEtudiant(e2));
		System.out.println("Ajout étudiant 3: " + etudiantService.ajouterEtudiant(e3));
		
		// Récupérer tous les étudiants
		System.out.println("\n=== Liste des étudiants ===");
		etudiantService.getAllEtudiants().forEach(e -> 
			System.out.println(e)
		);
		
		// Test de récupération d'un étudiant par ID
		System.out.println("\n=== Récupération de l'étudiant ID=1 ===");
		Etudiant etudiant = etudiantService.getEtudiant(1);
		if (etudiant != null) {
			System.out.println(etudiant);
		}
		
		} catch (Exception e) {
			System.err.println("Erreur: " + e.getMessage());
			e.printStackTrace();
		}
	}
}