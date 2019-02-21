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
			while (true) {
				System.out.println("\nHaluatko pelata uuden käden? (Vastaa k/e)");
				char x = poyta.annaSkanneri().next().charAt(0);
				if (x == 'k') {
					break;
				} else if (x == 'e') {
					haluaaJatkaa = false;
					break;
				} else {
					System.out.println("Tarkista syöte!");
					continue;
				}
			}
		} while (haluaaJatkaa);

		do {
			System.out.println("\nHaluatko tekstitiedoston omaisuus per kierros pelistäsi? (Vastaa k/e)");
			char x = poyta.annaSkanneri().next().charAt(0);
			if (x == 'k') {
				System.out.println(
						"Kirjoita polku jonne haluat tekstitiedoston tallennettavan. \nEsim: C:\\Users\\Kayttaja\\Desktop\\peliHistoria.txt");
				String polku = poyta.annaSkanneri().next();
				poyta.teeTiedosto(poyta.annaPeliHistoria(), polku);
				break;
			} else if (x == 'e') {
				break;
			} else {
				System.out.println("Tarkista syöte!");
				continue;
			}
		} while (true);

		poyta.suljeSkanneri();

		System.out.println("\nTervetuloa uudelleen " + poyta.annaHenkilot()[0].annaNimi() + "!");

	}

}
