package gestionDB;

import java.sql.*;

public class CreationTable {
	
	

	static String sql_compte = "CREATE TABLE `COMPTE` ( NOCOMPTE CHAR(4) NOT NULL , NOM VARCHAR(20) , PRENOM VARCHAR(20) , SOLDE DECIMAL(10,2) NOT NULL ) ENGINE = InnoDB;";
	static String sql_operations = "CREATE TABLE `OPERATIONS` ( NOCOMPTE CHAR NOT NULL , DATE DATE NOT NULL , HEURE TIME NOT NULL , OP CHAR NOT NULL , VALEUR DECIMAL(10,2) NOT NULL ) ENGINE = InnoDB;";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			
			stmt = conn.createStatement();
			stmt.executeUpdate(sql_compte);
			stmt.executeUpdate(sql_operations);
			/*
			while (rs.next()) {
				int id = rs.getInt("id");
				int age = rs.getInt("age");
				String first = rs.getString("first");
				String last = rs.getString("last");
				System.out.print("ID: " + id);
				System.out.print(", Age: " + age);
				System.out.print(", First: " + first);
				System.out.println(", Last: " + last);
			}
			*/
			// STEP 6: Clean-up environment
			stmt.close();
			conn.close();
		} catch (Exception se) {
			
		}
	}
}
