package javaBeans;

public class TestListeParDates {
	public static void main(String[] args) {
		BOperations boperations = new BOperations();
		boperations.ouvrirConnexion();
		boperations.setNoDeCompte("bite");
		boperations.setDateInf("2020-10-01");
		boperations.setDateSup("2020-10-03");
		boperations.listerparDates();
		boperations.consulter();
		System.out.println(boperations);
		boperations.fermerConnexion();
	}
}
