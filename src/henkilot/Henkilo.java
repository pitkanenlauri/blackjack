package henkilot;

/**
 * Luokka mallintaa peliss� osallisina olevia henkil�it�.
 */

import java.util.ArrayList;

import peli.Kortti;
import peli.Kortti.Nimi;

public abstract class Henkilo {
	protected ArrayList<Kortti> kasi;
	protected String nimi;
	
	public Henkilo() {
		this.kasi= new ArrayList<Kortti>();
	}
	
	public ArrayList<Kortti> annaKasi() {
		return kasi;
	}

	public String annaNimi() {
		return nimi;
	}

	public void asetaNimi(String nimi) {
		this.nimi = nimi;
	}

	public void uusiKortti(Kortti k) {
		kasi.add(k);
	}

	public void nollaaKasi() {
		kasi.clear();
	}

	/**
	 * Metodi laskee henkilon k�den korttien arvon Blackjackin s��nt�jen mukaisesti.
	 * @return K�dess� olevien korttien yhteenlaskettu arvo kokonaislukuna.
	 */
	public int annaSumma() {
		int summa = 0;
		int assat = 0;
		for (Kortti k : this.kasi) {
			summa += k.annaNimi().annaArvo();
			if (k.annaNimi() == Nimi.�SS�)
				assat++;
		}
		while (assat > 0 && summa > 21) {
			summa -= 10;
			assat--;
		}
		return summa;
	}
	/**
	 * Yhdist�� henkil�n k�den kortit allekkain tulostusta varten.
	 * @return Kortit k�dess�.
	 */
	public String kasiToString() {
		String s = "";
		for (Kortti k : kasi) {
			s += k.toString();
			if (kasi.indexOf(k) != kasi.size())
				s += "\n";
		}
		return s;
	}

	@Override
	public abstract String toString();
}