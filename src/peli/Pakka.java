package peli;

import java.util.ArrayList;
import java.util.Collections;

import peli.Kortti;

public class Pakka {
	private ArrayList<Kortti> pakka;
	private int i;

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