package peli;

import peli.Pelipoyta;
import java.io.IOException;
import henkilot.Pelaaja;

public class PelinSuorittaja {

	public static void main(String[] args) {

		boolean haluaaJatkaa = true;
		Pelipoyta poyta = new Pelipoyta();
		poyta.pelinAlustus();

		do {
			poyta.uusiKierros();
			if (((Pelaaja) poyta.annaHenkilot()[0]).annaOmaisuus() == 0) {
				System.out.println("Aika lähteä kotiin. Rahat on loppu.");
				break;
			}
			haluaaJatkaa = poyta.tarkistaJatkaminen();

		} while (haluaaJatkaa);

		do {
			System.out.println("\nHaluatko tekstitiedoston omaisuus per kierros pelistäsi? (Vastaa k/e)");
			char x = poyta.annaSkanneri().next().charAt(0);
			if (x == 'k') {
				Pelipoyta.cls();
				System.out.println("Kirjoita polku jonne haluat tekstitiedoston tallennettavan. "
						+ "\nEsim: C:\\Users\\Kayttaja\\Desktop\\peliHistoria.txt");
				String polku = poyta.annaSkanneri().next();
				poyta.tulostaGnuPlotKomento(poyta.teeTiedosto(poyta.annaPeliHistoria(), polku));
				System.out.println("\nPaina enter kun olet kopioinut polun.");
				try {
					System.in.read();
				} catch (IOException e) {
				}
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

		poyta.odota(2000);
		
	}

}
