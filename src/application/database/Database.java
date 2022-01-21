package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	static boolean connsuccess = false; // M‰‰rittelee, onko yhteys luotu vai ei.
	static Connection conn; // Tietokantayhteys
	static PreparedStatement prepstmt;
	static Statement stmt;
	static ResultSet rs;

	public static void main() {

		// Luodaan tietokantayhteys
		// Luetaan tietokannan yhdist‰miseen tiedot properties-tiedostosta.
		String dbuser = "admin";
		String dbpass = "admin";
		String dburl = "jdbc:mysql://localhost:3306/ot2?useLegacyDatetimeCode=false&serverTimezone=UTC";
		/*try {
			// Luodaan lukija properties-tiedostolle
			FileReader reader = new FileReader("src/application/var.properties");
			Properties prop = new Properties();
			prop.load(reader);
			dbuser = prop.getProperty("dbuser");
			dbpass = prop.getProperty("dbpass");
			dburl = prop.getProperty("db");
		} catch (IOException E) {
			E.printStackTrace();
		}*/
		if (dbuser != "" && dbpass != "" && dburl != "") {
			try {
				conn = DriverManager.getConnection(dburl, dbuser, dbpass);
				connsuccess = true;
				System.out.println("Tietokantayhteys luotu.");
			} catch (SQLException E) {
				connsuccess = false;
				E.printStackTrace();
			}
		} else {
			connsuccess = false;
			System.out.println("Tietokantayhteys ep‰onnistui.");
		}
		// Tietokantayhteys luotu.
	}

	/**
	 * Tarkastaa k‰ytt‰j‰tunnuksen ja salasanan olevan oikein tietokannasta.
	 *
	 * @param kayt Syˆtetty k‰ytt‰j‰tunnus
	 * @param sal  Syˆtetty salasana
	 *
	 * @return kokonaislukuarvo, joka m‰‰r‰‰ k‰ytt‰j‰n kirjautumisen onnistumisen. 1
	 *         = K‰ytt‰j‰nime‰ ei lˆytynyt 2 = K‰ytt‰j‰nimi ja salasana t‰sm‰‰v‰t 3
	 *         = K‰ytt‰j‰nimi ja salasana eiv‰t t‰sm‰‰ 0 = Muu virhe.
	 */
	public static int tarkastaTunnus(String kayt, String sal) {
		int i = DBLogin.tarkastaTunnus(rs, conn, prepstmt, kayt, sal);
		return i;

	}

	/**
	 * Lis‰‰ k‰ytt‰j‰n tietokantaan.
	 * 
	 * @param username K‰ytt‰j‰n k‰ytt‰j‰nimi
	 * @param password K‰ytt‰j‰n salasana
	 * @param question Turvakysymys salasanan unohtumiselle.
	 * @param answer   K‰ytt‰j‰n vastaus turvakysymykseen.
	 * @return Kokonaislukuarvo siit‰, lis‰ttiinkˆ k‰ytt‰j‰ tauluun. Arvot:
	 * -1 = virhe
	 * 0 = k‰ytt‰j‰nimi lˆytyi jo taulusta
	 * 1 = lis‰ys onnistui
	 */
	public static int lisaaTunnus(String username, String password, String question, String answer) {
		int b = DBSignin.lisaaTunnus(prepstmt, conn, username, password, question, answer);
		return b;
	}

	/**
	 * Hakee parhaan tuloksen parhaattulokset - taulusta.
	 * 
	 * @param vaikeustaso vaikeustaso, jolla k‰ytt‰j‰ pelaa.
	 * @param pelimuoto   pelimuoto joka on kyseess‰. 1 = muistipeli, 2 = v‰ripeli.
	 * @return rs ResultSet parhaista tuloksista.
	 */
	public static ResultSet haeParhaatTulokset(int vaikeustaso, int pelimuoto) {
		ResultSet rs = DBParhaat.haeParhaatTulokset(vaikeustaso, pelimuoto, conn, stmt);
		return rs;
	}
	
	/**
	 * Hakee kaikki parhaat tulokset parhaattulokset-taulusta.
	 * 
	 * @return rs ResultSet sis‰lt‰‰ kaikki parhaat tulokset.
	 */
	public static ResultSet haeKaikkiParhaatTulokset() {
		ResultSet rs = DBParhaat.haeKaikkiParhaatTulokset(conn, stmt);
		return rs;
	}
	
	//TODO: hae paras tulos.

	/**
	 * Lis‰‰ muistipelien tulokset tietokantaan muistipelien omaan tauluun.
	 * 
	 * @param kulunutaika         peliin kulunut aika.
	 * @param vaikeustaso  pelin vaikeustaso.
	 * @param kayttajanimi pelaajan k‰ytt‰j‰nimi.
	 * @param pisteet      k‰ytt‰j‰n saamat pisteet.
	 */
	public static boolean lisaaMuistipelitulos(int kulunutaika, int vaikeustaso, String kayttajanimi, int pisteet) {
		final int pelimuoto = 1;
		boolean added = DBMuistipeli.lisaaMuistipelitulos(kulunutaika, vaikeustaso, kayttajanimi, pisteet, pelimuoto, conn,
				stmt);
		return added;
	}

	/**
	 * P‰ivitt‰‰ k‰ytt‰j‰n parhaan tuloksen pelityypille ja vaikeusasteelle
	 * parhaattulokset - tauluun.
	 * 
	 * @param kulunutaika         peliin kulunut aika.
	 * @param vaikeustaso  pelin vaikeustaso.
	 * @param kayttajanimi pelaajan k‰ytt‰j‰nimi.
	 * @param pisteet      k‰ytt‰j‰n saamat pisteet.
	 * @param pelimuoto    pelimuoto jota pelataan. 1 = muistipeli, 2 = v‰ripeli.
	 */
	public static boolean paivitaParhaatTulokset(int kulunutaika, int vaikeustaso, String kayttajanimi, int pisteet,
			int pelimuoto) {
		boolean updated = DBParhaat.paivitaParhaatTulokset(kulunutaika, vaikeustaso, kayttajanimi, pisteet, pelimuoto, conn,
				stmt);
		return updated;
	}

	/**
	 * Lis‰‰ v‰ripelin tulokset tietokantaan v‰ripelin omaan tauluun.
	 * 
	 * @param vaikeustaso  pelin vaikeustaso.
	 * @param kayttajanimi pelaajan k‰ytt‰j‰nimi.
	 * @param pisteet      k‰ytt‰j‰n saamat pisteet.
	 */
	public static boolean lisaaVaripelitulos(int vaikeustaso, String kayttajanimi, int pisteet) {
		final int pelimuoto = 2;
		boolean added = DBVaripelit.lisaaVaripelitulos(vaikeustaso, kayttajanimi, pisteet, pelimuoto, conn, stmt);
		return added;
	}
	
	
	/**
	 * Tarkistaa, onko k‰ytt‰j‰n turvakysymyksen vastaus oikein.
	 * 
	 * @param user K‰ytt‰j‰nimi
	 * @param answer Vastaus lis‰kysymykseen.
	 * @return Totuusarvo, oliko vastaus oikein. Arvot:
	 * -1: Virhe
	 * 0: K‰ytt‰j‰nime‰ ei lˆytynyt
	 * 1: Annettu vastaus oli v‰‰rin
	 * 2: Annettu vastaus oli oikein
	 */
	public static int tarkistaLisakysymys(String user, String answer) {
		int correct = DBSignin.tarkistaLisakysymys(rs, stmt, conn, user, answer);
		return correct;
	}
	
	/**
	 * P‰ivitt‰‰ k‰ytt‰j‰n salasanan.
	 * 
	 * @param user	K‰ytt‰j‰nimi
	 * @param pass	Salasana
	 * @return totuusarvo siit‰, onnistuiko p‰ivitys
	 */
	public static boolean updatePassword(String user, String pass) {
		boolean updated = false;
		updated = DBSignin.updatePassword(stmt, conn, user, pass);
		return updated;
	}
	
	/**
	 * Hakee parhaan tuloksen pistem‰‰r‰n tietokannasta.
	 * 
	 * @param pelimuoto 1 = Muistipeli, 2 = v‰ripeli.
	 * @param vaikeustaso Pelattu vaikeustaso.
	 * @return Paras pistem‰‰r‰ tai -1 virhetilanteessa.
	 */
	public static ResultSet haeParasPistemaara(int pelimuoto, int vaikeustaso) {
		return DBParhaat.haeParasPistemaara(rs, stmt, conn, pelimuoto, vaikeustaso);
	}

}
