package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	static boolean connsuccess = false; // Määrittelee, onko yhteys luotu vai ei.
	static Connection conn; // Tietokantayhteys
	static PreparedStatement prepstmt;
	static Statement stmt;
	static ResultSet rs;

	public static void main() {

		// Luodaan tietokantayhteys
		// Luetaan tietokannan yhdistämiseen tiedot properties-tiedostosta.
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
			System.out.println("Tietokantayhteys epäonnistui.");
		}
		// Tietokantayhteys luotu.
	}

	/**
	 * Tarkastaa käyttäjätunnuksen ja salasanan olevan oikein tietokannasta.
	 *
	 * @param kayt Syötetty käyttäjätunnus
	 * @param sal  Syötetty salasana
	 *
	 * @return kokonaislukuarvo, joka määrää käyttäjän kirjautumisen onnistumisen. 1
	 *         = Käyttäjänimeä ei löytynyt 2 = Käyttäjänimi ja salasana täsmäävät 3
	 *         = Käyttäjänimi ja salasana eivät täsmää 0 = Muu virhe.
	 */
	public static int tarkastaTunnus(String kayt, String sal) {
		int i = DBLogin.tarkastaTunnus(rs, conn, prepstmt, kayt, sal);
		return i;

	}

	/**
	 * Lisää käyttäjän tietokantaan.
	 * 
	 * @param username Käyttäjän käyttäjänimi
	 * @param password Käyttäjän salasana
	 * @param question Turvakysymys salasanan unohtumiselle.
	 * @param answer   Käyttäjän vastaus turvakysymykseen.
	 * @return Kokonaislukuarvo siitä, lisättiinkö käyttäjä tauluun. Arvot:
	 * -1 = virhe
	 * 0 = käyttäjänimi löytyi jo taulusta
	 * 1 = lisäys onnistui
	 */
	public static int lisaaTunnus(String username, String password, String question, String answer) {
		int b = DBSignin.lisaaTunnus(prepstmt, conn, username, password, question, answer);
		return b;
	}

	/**
	 * Hakee parhaan tuloksen parhaattulokset - taulusta.
	 * 
	 * @param vaikeustaso vaikeustaso, jolla käyttäjä pelaa.
	 * @param pelimuoto   pelimuoto joka on kyseessä. 1 = muistipeli, 2 = väripeli.
	 * @return rs ResultSet parhaista tuloksista.
	 */
	public static ResultSet haeParhaatTulokset(int vaikeustaso, int pelimuoto) {
		ResultSet rs = DBParhaat.haeParhaatTulokset(vaikeustaso, pelimuoto, conn, stmt);
		return rs;
	}
	
	/**
	 * Hakee kaikki parhaat tulokset parhaattulokset-taulusta.
	 * 
	 * @return rs ResultSet sisältää kaikki parhaat tulokset.
	 */
	public static ResultSet haeKaikkiParhaatTulokset() {
		ResultSet rs = DBParhaat.haeKaikkiParhaatTulokset(conn, stmt);
		return rs;
	}
	
	//TODO: hae paras tulos.

	/**
	 * Lisää muistipelien tulokset tietokantaan muistipelien omaan tauluun.
	 * 
	 * @param kulunutaika         peliin kulunut aika.
	 * @param vaikeustaso  pelin vaikeustaso.
	 * @param kayttajanimi pelaajan käyttäjänimi.
	 * @param pisteet      käyttäjän saamat pisteet.
	 */
	public static boolean lisaaMuistipelitulos(int kulunutaika, int vaikeustaso, String kayttajanimi, int pisteet) {
		final int pelimuoto = 1;
		boolean added = DBMuistipeli.lisaaMuistipelitulos(kulunutaika, vaikeustaso, kayttajanimi, pisteet, pelimuoto, conn,
				stmt);
		return added;
	}

	/**
	 * Päivittää käyttäjän parhaan tuloksen pelityypille ja vaikeusasteelle
	 * parhaattulokset - tauluun.
	 * 
	 * @param kulunutaika         peliin kulunut aika.
	 * @param vaikeustaso  pelin vaikeustaso.
	 * @param kayttajanimi pelaajan käyttäjänimi.
	 * @param pisteet      käyttäjän saamat pisteet.
	 * @param pelimuoto    pelimuoto jota pelataan. 1 = muistipeli, 2 = väripeli.
	 */
	public static boolean paivitaParhaatTulokset(int kulunutaika, int vaikeustaso, String kayttajanimi, int pisteet,
			int pelimuoto) {
		boolean updated = DBParhaat.paivitaParhaatTulokset(kulunutaika, vaikeustaso, kayttajanimi, pisteet, pelimuoto, conn,
				stmt);
		return updated;
	}

	/**
	 * Lisää väripelin tulokset tietokantaan väripelin omaan tauluun.
	 * 
	 * @param vaikeustaso  pelin vaikeustaso.
	 * @param kayttajanimi pelaajan käyttäjänimi.
	 * @param pisteet      käyttäjän saamat pisteet.
	 */
	public static boolean lisaaVaripelitulos(int vaikeustaso, String kayttajanimi, int pisteet) {
		final int pelimuoto = 2;
		boolean added = DBVaripelit.lisaaVaripelitulos(vaikeustaso, kayttajanimi, pisteet, pelimuoto, conn, stmt);
		return added;
	}
	
	
	/**
	 * Tarkistaa, onko käyttäjän turvakysymyksen vastaus oikein.
	 * 
	 * @param user Käyttäjänimi
	 * @param answer Vastaus lisäkysymykseen.
	 * @return Totuusarvo, oliko vastaus oikein. Arvot:
	 * -1: Virhe
	 * 0: Käyttäjänimeä ei löytynyt
	 * 1: Annettu vastaus oli väärin
	 * 2: Annettu vastaus oli oikein
	 */
	public static int tarkistaLisakysymys(String user, String answer) {
		int correct = DBSignin.tarkistaLisakysymys(rs, stmt, conn, user, answer);
		return correct;
	}
	
	/**
	 * Päivittää käyttäjän salasanan.
	 * 
	 * @param user	Käyttäjänimi
	 * @param pass	Salasana
	 * @return totuusarvo siitä, onnistuiko päivitys
	 */
	public static boolean updatePassword(String user, String pass) {
		boolean updated = false;
		updated = DBSignin.updatePassword(stmt, conn, user, pass);
		return updated;
	}
	
	/**
	 * Hakee parhaan tuloksen pistemäärän tietokannasta.
	 * 
	 * @param pelimuoto 1 = Muistipeli, 2 = väripeli.
	 * @param vaikeustaso Pelattu vaikeustaso.
	 * @return Paras pistemäärä tai -1 virhetilanteessa.
	 */
	public static ResultSet haeParasPistemaara(int pelimuoto, int vaikeustaso) {
		return DBParhaat.haeParasPistemaara(rs, stmt, conn, pelimuoto, vaikeustaso);
	}

}
