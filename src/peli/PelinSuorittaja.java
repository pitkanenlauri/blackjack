package peli;

import peli.Pelipoyta;
import henkilot.Pelaaja;

public class PelinSuorittaja {

	public static void main(String[] args) {

		boolean haluaaJatkaa = true;
		Pelipoyta poyta = new Pelipoyta();
		poyta.pelinAlustus();

		do {
			poyta.uusiKierros();
			if (((Pelaaja) poyta.annaHenkilot()[0]).annaOmaisuus() == 0) {
				System.out.println("\nAika lähteä kotiin. Rahat on loppu.");
				break;
			}
			System.out.println("Haluatko pelata uuden käden? (Vastaa k/e)");
			char x = poyta.annaSkanneri().next().charAt(0);
			if (x == 'k') {
				poyta.annaHenkilot()[0].uusiKortti(poyta.annaPakka().annaKortti());
				continue;
			} else if (x == 'e') {
				haluaaJatkaa = false;
				break;
			} else {
				System.out.println("Tarkista syöte!");
				continue;
			}
		} while (((Pelaaja) poyta.annaHenkilot()[0]).annaOmaisuus() > 0 && haluaaJatkaa);

		poyta.suljeSkanneri();

		System.out.println("\nTervetuloa uudelleen " + poyta.annaHenkilot()[0].annaNimi() + "!");

	}

}
