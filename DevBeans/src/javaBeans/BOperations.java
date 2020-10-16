package javaBeans;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class BOperations {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://sqletud.u-pem.fr/npernet_db?useTimezone=true&serverTimezone=UTC";
	static final String USER = "npernet";
	static final String PASS = "k2oiwyo4gF";
	private Connection connection;
	
	private String noDeCompte;
	private String nom;
	private String prenom;
	private String op;
	private String dateInf;
	private String dateSup;
	private ArrayList<String[]> operationsParDates;
	private BigDecimal solde;
	private BigDecimal ancienSolde;
	private BigDecimal nouveauSolde;
	private BigDecimal valeur;
	
	public void ouvrirConnexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (Exception e) {
			System.out.println("TO DO");
		}
	}
	
	public void fermerConnexion() {
		try {
			connection.close();
		} catch (Exception e) {
			System.out.println("TO DO");
		}
	}
	
	public void consulter() {
		String sql = "select nom, prenom, solde from COMPTE where nocompte = ?;";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, noDeCompte);
			ResultSet rows = statement.executeQuery();
			while (rows.next()) {
				nom = rows.getString(1);
				prenom = rows.getString(2);
				solde = rows.getBigDecimal(3);
			}			
		} catch (Exception e) {
			System.out.println("TO DO");
		}
	}
	
	public void traiter() {		
		try {
			connection.setAutoCommit(false);
			// 1
			String sql_solde = "select solde from COMPTE where nocompte=?;";
			PreparedStatement statement = connection.prepareStatement(sql_solde);
			statement.setString(1, noDeCompte);
			ResultSet rows = statement.executeQuery();
			while (rows.next()) {
				ancienSolde = rows.getBigDecimal(1);
			}
			// 2
			String sql_ops = "select op, valeur from operations where nocompte=?;";
			statement = connection.prepareStatement(sql_ops);
			statement.setString(1, noDeCompte);
			nouveauSolde = ancienSolde;
			switch (op) {
			case "+":
				nouveauSolde = nouveauSolde.add(valeur);
				break;
			case "-":
				nouveauSolde.subtract(valeur);
				break;
			}
			if (nouveauSolde.signum() == -1) {
				System.out.println("LOL");
			} else {
				statement = connection.prepareStatement("update COMPTE set solde=? where nocompte=?;");
				statement.setBigDecimal(1, nouveauSolde);
				statement.setString(2, noDeCompte);
				statement.executeUpdate();
				statement = connection.prepareStatement("insert into OPERATIONS (nocompte, date, heure, op, valeur) values (?,DATE(CURRENT_TIMESTAMP),TIME(CURRENT_TIMESTAMP),?,?);");
				statement.setString(1, noDeCompte);
				statement.setString(2, op);
				statement.setBigDecimal(3, valeur);
				statement.executeUpdate();
				connection.commit();
			}
		} catch (Exception e) {
			try { connection.rollback(); } catch (Exception exception) { }
		}
	}
	
	public void listerparDates() {
		operationsParDates = new ArrayList<>();
		try {			
			String sql_ops = "select nocompte, date, heure, op, valeur from OPERATIONS where nocompte=? and date>? and date<?;";
			PreparedStatement statement = connection.prepareStatement(sql_ops);
			statement.setString(1, noDeCompte);
			statement.setString(2, dateInf);
			statement.setString(3, dateSup);
			ResultSet rows = statement.executeQuery();
			while (rows.next()) {
				operationsParDates.add(new String[] {
					rows.getString(1),
					rows.getString(2),
					rows.getString(3),
					rows.getString(4),
					String.valueOf(rows.getBigDecimal(5)),
				});
			}
		} catch (Exception e) {
			System.out.println("TO DO");
		}
	}
	
	public String getNoDeCompte() {
		return noDeCompte;
	}
	
	public void setNoDeCompte(String noDeCompte) {
		this.noDeCompte = noDeCompte;
	}
	
	public String getNom() {
		return nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public BigDecimal getSolde() {
		return solde;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public void setValeur(String valeur) {
		this.valeur = BigDecimal.valueOf(Long.valueOf(valeur));
	}

	public String getValeur(BigDecimal valeur) {
		return String.valueOf(valeur);
	}

	public BigDecimal getAncienSolde() {
		return ancienSolde;
	}

	public BigDecimal getNouveauSolde() {
		return nouveauSolde;
	}

	public String getDateInf() {
		return dateInf;
	}

	public void setDateInf(String dateInf) {
		this.dateInf = dateInf;
	}

	public String getDateSup() {
		return dateSup;
	}

	public void setDateSup(String dateSup) {
		this.dateSup = dateSup;
	}
	
	public String listToString() {
		StringBuilder builder = new StringBuilder();
		for (String[] array : operationsParDates) {
			builder.append(Arrays.deepToString(array));
		}
		return builder.toString();			
	}

	@Override
	public String toString() {
		return "BOperations [connection=" + connection + ", noDeCompte=" + noDeCompte + ", nom=" + nom + ", prenom="
				+ prenom + ", solde=" + solde + "]\n" + listToString();
				
	}
	
}
