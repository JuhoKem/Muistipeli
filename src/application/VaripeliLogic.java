package application;

import javafx.scene.paint.Color;
import java.util.*;

class VaripeliLogic {
	public String teksti;
	public Color tekstinVari;
	public Random random;
	public String[] napit;
	public String oikeaNappi;
	public Color[] varit;
	public String[] sanat;
	public int pisteet;
	public ArrayList<String> taustat;
	public int tekstinVariEiTaustaksi;
	public int kierros;
	public boolean oikein;

	public VaripeliLogic() {
		this.random = new Random();
		this.napit = new String[5];
		this.varit = new Color[8];
		this.sanat = new String[8];
		this.taustat = new ArrayList<>();
		this.kierros = 0;

		alustaPeli();
	}
	
	public void alustaPeli() {
		this.varit[0] = Color.RED;
		this.varit[1] = Color.LIMEGREEN;
		this.varit[2] = Color.MEDIUMBLUE;
		this.varit[3] = Color.YELLOW;
		this.varit[4] = Color.DARKORANGE;
		this.varit[5] = Color.DARKVIOLET;
		this.varit[6] = Color.DIMGRAY;
		this.varit[7] = Color.BLACK;
		
		this.sanat[0] = "PUNAINEN";
		this.sanat[1] = "VIHREÄ";
		this.sanat[2] = "SININEN";
		this.sanat[3] = "KELTAINEN";
		this.sanat[4] = "ORANSSI";
		this.sanat[5] = "VIOLETTI";
		this.sanat[6] = "HARMAA";
		this.sanat[7] = "MUSTA";
		
		this.taustat.add("application/resources/taustapunainen.png");
		this.taustat.add("application/resources/taustavihrea.png");
		this.taustat.add("application/resources/taustasininen.png");
		this.taustat.add("application/resources/taustakeltainen.png");
		this.taustat.add("application/resources/taustaoranssi.png");
		this.taustat.add("application/resources/taustavioletti.png");
		this.taustat.add("application/resources/taustaharmaa.png");
		this.taustat.add("application/resources/taustavastaus.png");
		
	}
	
	public void luoVarit() {
		int sana = arvoNumero();
		int tekstinVari = arvoNumero();
		while(true) {
			if(sana != tekstinVari) {
				break;
			}
			tekstinVari = arvoNumero();
		}
		setSana(sana);
		setTekstinVari(tekstinVari);
		arvoNapit();
		oikein = false;
	}
	
	public void arvoNapit() {
		Set<String> napit = new HashSet<>();
		napit.add(oikeaNappi);
		napit.add(getSana());
		
		while(napit.size() < 4) {
			napit.add(getNapinTeksti(random.nextInt(8)));
		}
		Iterator<String> i = napit.iterator();
		while(i.hasNext()) {
			this.napit[1] = i.next();
			this.napit[2] = i.next();
			this.napit[3] = i.next();
			this.napit[4] = i.next();
		}
	}

	public int arvoNumero() {
		int arvottuNumero = random.nextInt(8);
		return arvottuNumero;
	}
	
	public void setSana(int arvottuNumero) {
		this.teksti = sanat[arvottuNumero];
	}
	
	public void setTekstinVari(int arvottuNumero) {
		this.tekstinVari = varit[arvottuNumero];
		this.tekstinVariEiTaustaksi = arvottuNumero;
		oikeaNappi = sanat[arvottuNumero];
	}
	
	public String getNapinTeksti(int arvottuNumero) {
		return sanat[arvottuNumero];
	}
	
	public String getSana() {
		return this.teksti;
	}
	
	public Color getTekstinVari() {
		return this.tekstinVari;
	}
	
	public String getTausta() {
		int arvottavienMaara = 7;
		int lisattava = 0;
		// punavihersokeita varten muutos taustan arpomiseen
		if(this.tekstinVari == Color.RED || this.tekstinVari == Color.LIMEGREEN) {
			arvottavienMaara = 5;
			lisattava = 2;
		}
		int tausta = random.nextInt(arvottavienMaara) + lisattava;
		while(true) {
			if (this.tekstinVariEiTaustaksi != tausta) {
				break;
			}
			tausta = random.nextInt(arvottavienMaara) + lisattava;
		}
		return this.taustat.get(tausta);
	}
	
	public String getNappi(int nappi) {
		return this.napit[nappi];
	}
	
	public void tarkistaVastaus(int nappi) {
		if(this.napit[nappi] == oikeaNappi) {
			setPisteet();
			oikein = true;
		}
	}
	
	public void setPisteet() {
		this.pisteet += 100;
	}
	
	public int getPisteet() {
		return this.pisteet;
	}
	
	public void setKierros() {
		this.kierros++;
	}
	
	public int getKierros() {
		return this.kierros;
	}
	
	public boolean getOikein() {
		return this.oikein;
	}
	
	public String getVastauksenTausta() {
		return this.taustat.get(7);
	}
}
