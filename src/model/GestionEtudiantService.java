package model;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)

public interface GestionEtudiantService {
	@WebMethod
	public boolean AjouterEtudiant(Etudiant e)  ;
	
	
	
	@WebMethod
	public Etudiant RecupererEtudiantParId(int id);
	
	

	
	@WebMethod
	public Etudiant[] RecupererTousLesEtudiants() ;
}
