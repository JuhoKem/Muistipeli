package application.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBMuistipeli {

	public static boolean lisaaMuistipelitulos(int kulunutaika, int vaikeustaso, String kayttajanimi, int pisteet,
			int pelimuoto, Connection conn, Statement stmt) {
		try {
			Statement statement = conn.createStatement();
			String sql = String.format(
					"INSERT INTO muistipelitulokset(" + "aika, vaikeustaso, kayttajanimi, pisteet, pelimuoto) "
							+ "VALUES('%d', '%d', '%s', '%d', '%d');",
					kulunutaika, vaikeustaso, kayttajanimi, pisteet, pelimuoto);
			System.out.println(sql);
			statement.execute(sql);
			return true;
		} catch (SQLException E) {
			E.printStackTrace();
			return false;
		}

	}

}
