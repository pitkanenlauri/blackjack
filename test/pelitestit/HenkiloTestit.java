package pelitestit;

import test.Testi;

import henkilot.*;
import peli.Kortti.Nimi;
import peli.Kortti;
import peli.Kortti.Maa;
import peli.Pakka;

public class HenkiloTestit extends Testi {
	public void PelaajanKasiTesti() {
		Pelaaja pelaaja = new Pelaaja();
		Pakka pakka = new Pakka();
		pelaaja.asetaNimi("Keijo");

		for (int i = 0; i < 2; i++) {
			pelaaja.uusiKortti(pakka.annaKortti());
		}
		System.out.println(pelaaja.toString());
	}

	public void JakajanKasiTesti() {
		Jakaja jakaja = new Jakaja();
		Pakka pakka = new Pakka();

		for (int i = 0; i < 2; i++) {
			jakaja.uusiKortti(pakka.annaKortti());
		}
		System.out.println("Piilotettu:\n");
		System.out.println(jakaja.piilotettuToString());
		System.out.println("\nTodellinen:\n");
		System.out.println(jakaja.toString());
		System.out.println("Jakaja kusettaa! :O");
		
	}

	@Override
	public void suoritaTestit() {
		// PelaajanKasiTesti();
		// JakajanKasiTesti();
	}

}