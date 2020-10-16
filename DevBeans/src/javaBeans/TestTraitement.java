package javaBeans;

import java.math.BigDecimal;

public class TestTraitement {
	public static void main(String[] args) {
		BOperations boperations = new BOperations();
		boperations.ouvrirConnexion();
		boperations.setNoDeCompte("bite");
		boperations.setOp("+");
		boperations.setValeur("200");
		boperations.traiter();
		boperations.consulter();
		System.out.println(boperations);
		boperations.fermerConnexion();
	}
}
