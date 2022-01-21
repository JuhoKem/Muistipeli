package application.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBParhaat {

	public static ResultSet haeParhaatTulokset(int vaikeustaso, int pelimuoto, Connection conn, Statement stmt) {
		try {
			stmt = conn.createStatement();
			String sql = String.format("SELECT kayttajanimi, aika, pisteet " + "FROM parhaattulokset "
					+ "WHERE pelimuoto = '%d' AND vaikeustaso = '%d';", pelimuoto, vaikeustaso);
			System.out.println("sql: " + sql); // Troubleshoot
			ResultSet rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException E) {
			E.printStackTrace();
			return null;
		}

	}

	public static boolean paivitaParhaatTulokset(int kulunutaika, int vaikeustaso, String kayttajanimi, int pisteet,
			int pelimuoto, Connection conn, Statement stmt) {
		int parhaatPisteet = 0;
		try {
			Statement statement = conn.createStatement();
			String sql = String.format(
					"SELECT pisteet " + "FROM parhaattulokset " + "WHERE pelimuoto = '%d' AND vaikeustaso = '%d';",
					pelimuoto, vaikeustaso);
			System.out.println("sql: " + sql); // Troubleshoot

			ResultSet rs = statement.executeQuery(sql);

			if (rs.next()) {
				parhaatPisteet = rs.getInt(1);

				// Päivitä paras tulos jos nykyinen on parempi.
				if (parhaatPisteet < pisteet) {

					sql = String.format(
							"UPDATE parhaattulokset " + "SET aika = '%d', pisteet = '%d', kayttajanimi = '%s' "
									+ "WHERE vaikeustaso = '%d' AND " + "pelimuoto = '%d';",
							kulunutaika, pisteet, kayttajanimi, vaikeustaso, pelimuoto);
					System.out.println("sql: " + sql); // Troubleshoot
					statement.execute(sql);
				}	//if
			} else {
				sql = String.format("INSERT INTO parhaattulokset(vaikeustaso, kayttajanimi, pisteet, pelimuoto) " +
						"VALUES(%d, '%s', '%d', '%d');", vaikeustaso, kayttajanimi, pisteet, pelimuoto);
				System.out.println("sql: " + sql); // Troubleshoot
				statement.execute(sql);
			}
			return true;
		} catch (SQLException E) {
			E.printStackTrace();
			return false;
		}

	}
	
	public static ResultSet haeKaikkiParhaatTulokset(Connection conn, Statement stmt) {
		try {
			stmt = conn.createStatement();
			String sql = String.format("SELECT kayttajanimi, pisteet, " + 
			"pelimuoto, vaikeustaso FROM parhaattulokset;");
			System.out.println("sql: " + sql); // Troubleshoot
			ResultSet rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException E) {
			E.printStackTrace();
			return null;
		}

	}
	
	public static ResultSet haeParasPistemaara(ResultSet rs, Statement stmt, Connection conn, int pelimuoto,
			int vaikeustaso) {
		try {
			stmt = conn.createStatement();
			String sql = String.format("SELECT kayttajanimi, pisteet FROM parhaattulokset " +
			"WHERE pelimuoto = '%d' AND vaikeustaso = '%d';", pelimuoto, vaikeustaso);
			System.out.println("HaeParasPistemaara: " + sql);
			rs = stmt.executeQuery(sql);
			if (rs.next()) return rs;
			else return null;
		} catch (SQLException E) {
			E.printStackTrace();
			return null;
		}
	}
}
