package peli;

import java.util.ArrayList;
import java.util.Collections;

import peli.Kortti;

/**
 * Luokka mallintaa peliss‰ k‰ytett‰v‰‰ korttipakkaa. Pakka t‰ytet‰‰n luotaessa
 * Kortti luokan olioilla.
 */
public class Pakka {

	private ArrayList<Kortti> pakka;
	private int i;

	/**
	 * Konstruktori luo uuden pakan, johon tulee 52 Kortti olioita 13 jokaista
	 * nelj‰‰ maata kohden, ja sekoittaa pakan satunnaiseen j‰rjestykseen k‰yttˆ‰
	 * varten.
	 */
	public Pakka() {
		pakka = new ArrayList<Kortti>();
		for (Kortti.Maa m : Kortti.Maa.values()) {
			for (Kortti.Nimi n : Kortti.Nimi.values()) {
				pakka.add(new Kortti(m, n));
			}
		}
		this.sekoitaPakka();
	}

	public ArrayList<Kortti> annaPakka() {
		return pakka;
	}

	public Kortti annaKortti() {
		i++;
		return pakka.get(i);
	}

	public void sekoitaPakka() {
		Collections.shuffle(pakka);
		i = -1;
	}

}