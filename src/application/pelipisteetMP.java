package application;

public class pelipisteetMP {
	
	int vaikeustaso;	
	int loppupisteet = 0;
	
	public pelipisteetMP() {
		//this.vaikeustaso = vaikeustaso;
	}
	
	public int pistekerroin(int vaikeustaso) {
		
		int pisteet = 0;
		
		if(vaikeustaso==1)
				pisteet = 1000;
		else if(vaikeustaso==2)
				pisteet = 2000;
		else if(vaikeustaso==3)
				pisteet = 3000;
		
		
		return pisteet;
	}
	
	// Jokaisen löydetyn parin yhteydessä kutsutaan tätä: MuistipeliApp: handleMouseClick -metodin 'else' -kohdassa
	public void lisaaPisteet(int vaikeustaso) {
		this.vaikeustaso = vaikeustaso;
		loppupisteet = loppupisteet + pistekerroin(vaikeustaso);				
	}
	
	// Jokaisella kellonlyömällä sekuntilla kutsutaan tätä (tarkastetaan jatkuvasti: jos kellon aika on muuttunut -> kutsutaan tätä)
	public void vahennaPisteet() {
		if(loppupisteet>0) {
		loppupisteet = loppupisteet - 50;
		}
	}
	
	// TulosikkunaMP: Label tulosL = new Label(getLoppupisteet());
	public int getLoppupisteet() {		
		int palautuspisteet = loppupisteet;
		loppupisteet = 0;
		return palautuspisteet;
	}
	public void nollaapisteet() {
		loppupisteet = 0;
	}
	public int getvalipisteet() {
		return loppupisteet;
	}
}
