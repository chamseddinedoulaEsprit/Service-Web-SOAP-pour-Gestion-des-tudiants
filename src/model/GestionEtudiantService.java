package model;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import java.util.List;

@WebService
public interface GestionEtudiantService {
    @WebMethod
    boolean ajouterEtudiant(Etudiant e);
    
    @WebMethod
    boolean supprimerEtudiant(int id);
    
    @WebMethod
    boolean modifierEtudiant(Etudiant e);
    
    @WebMethod
    Etudiant getEtudiant(int id);
    
    @WebMethod
    List<Etudiant> getAllEtudiants();
}
