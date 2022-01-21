package application.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class DBSignin {
	public static int lisaaTunnus(Statement stmt, Connection conn, 
			String username, String password, String question, String answer) {
		
		int hashedPassword = password.hashCode();
		
		//Tarkistetaan, ettei käyttäjänimeä ole jo taulussa
		String sql = String.format("SELECT kayttajanimi "
				+ "FROM kayttajat "
				+ "WHERE kayttajanimi = '%s';", username);
		try {
			stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return 0;	//Käyttäjänimi on jo taulussa, joten palataan.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sql = String.format("INSERT INTO kayttajat(kayttajanimi,"
				+ "salasana, lisakysymys, lisakysymysVastaus) "
				+ "VALUES('%s', '%d', '%s', '%s');", username, hashedPassword,
				question, answer);
		System.out.println(sql);
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
			return 1;
		} catch (SQLException E) {
			E.printStackTrace();
			return -1;
		}
	}
	
	public static int tarkistaLisakysymys(ResultSet rs, Statement stmt, Connection conn, String user, String answer) {
		int correct = -1;
		String sql = String.format("SELECT kayttajanimi, lisakysymysvastaus "
				+ "FROM kayttajat "
				+ "WHERE kayttajanimi = '%s';", user);
		System.out.println(sql);
		try {
			rs = null;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				rs.first();
				String dbUser = rs.getString(1);
				String dbAnswer = rs.getString(2);
				System.out.println(user + ", " + dbUser + ", " + answer + ", " + dbAnswer);
				if (dbUser.equals(user) && dbAnswer.equals(answer)) {
					return 2;	//Vastaus oikein
				} else if (dbUser.equals(user) && !dbAnswer.equals(answer)) {
					return 1;	//Vastaus väärin
				}
			}
			return 0;	//Käyttäjänimeä ei löytynyt.
		} catch (SQLException e) {
			e.printStackTrace();
			return correct;
		}
	}
	
	public static boolean updatePassword(Statement stmt, Connection conn, String user, String pass) {
		boolean updated = false;
		int hashedPassword = pass.hashCode();
		String sql = String.format("UPDATE kayttajat "
				+ "SET salasana = '%d' "
				+ "WHERE kayttajanimi = '%s';", hashedPassword, user);
		System.out.println(sql);
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return updated;
		}
	}

}
