package model;

import jakarta.xml.ws.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Publisher {
    private static final Logger logger = LoggerFactory.getLogger(Publisher.class);
    private static final String URL = "http://localhost:8084/service";

    public static void main(String[] args) {
        try {
            GestionEtudiantsServiceImpl implementor = new GestionEtudiantsServiceImpl();
            Endpoint.publish(URL, implementor);
            logger.info("Service web publié avec succès à l'adresse: {}", URL);
        } catch (Exception e) {
            logger.error("Erreur lors de la publication du service: {}", e.getMessage());
        }
    }
}
