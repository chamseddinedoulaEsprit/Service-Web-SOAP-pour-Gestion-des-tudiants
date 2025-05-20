package model;

public class test {
    public static void main(String[] args) {
        try {
        	GestionEtudiantsServiceImpl ps = new GestionEtudiantsServiceImpl();

        	Etudiant p1 = new Etudiant(4, "chamseddine ","doula", 20);
        	Etudiant p2 = new Etudiant(5, " eya","yaacoub", 20);
        	Etudiant p3 = new Etudiant(6, " yassine","chokri", 20);
            
            System.out.println("Status = " + ps.AjouterEtudiant(p1));
            System.out.println("Status = " + ps.AjouterEtudiant(p2));
            System.out.println("Status = " + ps.AjouterEtudiant(p3));
            
            Etudiant[] persons= ps.RecupererTousLesEtudiants();
            for(Etudiant p :persons) {
            	System.out.println(p.toString());
            }
          
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}