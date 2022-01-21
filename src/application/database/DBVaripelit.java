package application.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBVaripelit {

	public static boolean lisaaVaripelitulos(int vaikeustaso, String kayttajanimi, int pisteet, int pelimuoto,
			Connection conn, Statement stmt) {
		try {
			stmt = conn.createStatement();
			String sql = String.format("INSERT INTO varipelitulokset("
					+ "vaikeustaso, kayttajanimi, pisteet, pelimuoto) " + "VALUES('%d', '%s', '%d', '%d');",
					vaikeustaso, kayttajanimi, pisteet, pelimuoto);
			stmt.execute(sql);
			return true;
		} catch (SQLException E) {
			E.printStackTrace();
			return false;
		}

	}

}
