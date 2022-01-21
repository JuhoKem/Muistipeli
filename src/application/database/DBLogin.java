package application.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class DBLogin {
	public static int tarkastaTunnus(ResultSet rs, Connection conn,
			PreparedStatement prepstmt, String kayt, String sal) {
		
		int hashedPassword = sal.hashCode();

		String sql = String.format(
				"SELECT kayttajanimi, salasana " + "FROM kayttajat " + "WHERE kayttajanimi = '%s';", kayt);
		
		try {
			prepstmt = conn.prepareStatement(sql);
			rs = prepstmt.executeQuery();
			if (rs.next() == false) {
				return 1;	//Käyttäjänimeä ei löytynyt.
			} else {
				if (rs.getInt(2) == hashedPassword) {
					return 2;	//Käyttäjänimi ja salasana täsmää.
				} else {
					return 3;	//Käyttäjänimi ja salasana eivät täsmää.
				}
			}
		} catch (SQLException E) {
			E.printStackTrace();
		}
		return 0;	//Jotain muuta meni pieleen.

	}

}
