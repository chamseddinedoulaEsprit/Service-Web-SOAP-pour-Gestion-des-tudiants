package model;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;


public class Client {
	
	public static void main(String[] args) throws MalformedURLException {
		
		URL wsdlURL= new URL("http://localhost:8888/ws/Etudiant?wsdl");
		//check above URL in browser, you should see wsdlfile
		
		//create QName using targetNamespace and name
		QName qname = new QName("http://service.info.com/", "GestionEtudiantsServiceImpl");
		Service service = Service.create(wsdlURL, qname);
		
		//we need to pass interface and model beans to client
		GestionEtudiantService ps = service.getPort(GestionEtudiantService.class);
		
		Etudiant p1 = new Etudiant(); p1.setNom("chamseddine doula"); p1.setId(1); p1.setAge(20);
		Etudiant p2 = new Etudiant(); p2.setNom("chokri yassine"); p2.setId(2); p2.setAge(20);
		Etudiant p3 = new Etudiant(); p3.setNom("yaacoub eya"); p3.setId(3); p3.setAge(41);
		
		//add Person
		System.out.println("Add Person Status="+ps.AjouterEtudiant(p1));
		System.out.println("Add Person Status="+ps.AjouterEtudiant(p2));
		System.out.println("Add Person Status="+ps.AjouterEtudiant(p3));
	}

}