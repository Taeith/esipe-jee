package javaBeans;

public class TestConsultation {
	public static void main(String[] args) {
		BOperations boperations = new BOperations();
		boperations.ouvrirConnexion();
		boperations.setNoDeCompte("bite");
		boperations.consulter();
		System.out.println(boperations);
		boperations.fermerConnexion();
	}
}
