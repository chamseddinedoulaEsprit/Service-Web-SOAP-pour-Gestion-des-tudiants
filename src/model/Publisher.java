package model;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;


@WebService
public class Publisher {
public static void main(String[] args) {
		
		Endpoint.publish("http://localhost:8888/ws/Etudiant",new GestionEtudiantsServiceImpl());
		System.out.println("Server Running");
	}
}
