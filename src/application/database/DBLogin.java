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
				return 1;	//K�ytt�j�nime� ei l�ytynyt.
			} else {
				if (rs.getInt(2) == hashedPassword) {
					return 2;	//K�ytt�j�nimi ja salasana t�sm��.
				} else {
					return 3;	//K�ytt�j�nimi ja salasana eiv�t t�sm��.
				}
			}
		} catch (SQLException E) {
			E.printStackTrace();
		}
		return 0;	//Jotain muuta meni pieleen.

	}

}
