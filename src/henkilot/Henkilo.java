package henkilot;

import java.util.ArrayList;

import peli.Kortti;
import peli.Kortti.Nimi;

/**
 * Luokka mallintaa peliss� osallisena olevaa henkil��.
 */
public abstract class Henkilo {

	protected ArrayList<Kortti> kasi;
	protected String nimi;

	public Henkilo() {
		this.kasi = new ArrayList<Kortti>();
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
	 * Laskee henkilon k�den korttien arvon Blackjackin s��nt�jen mukaisesti.
	 * 
	 * @return Palauttaa k�dess� olevien korttien yhteenlasketun arvon
	 *         kokonaislukuna.
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
	 * 
	 * @return Palauttaa henkil�n k�dess� olevat kortit.
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