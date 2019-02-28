package peli;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import henkilot.*;

/**
 * T‰ss‰ luokassa kootaan koko peli muiden luokkien avulla, eli sis‰lt‰‰ esim.
 * kaikki pelin kulkuun liittyv‰t metodit.
 *
 */
public class Pelipoyta {

	private Pakka pakka;
	private Henkilo[] henkilot;
	private int kierros = 0;
	private Scanner skanneri;
	// peliHistoria attribuutti on tekstitiedostoa varten.
	private ArrayList<Integer> peliHistoria;

	/**
	 * Konstruktori, jossa luodaan mm. sekoitettu pakka.
	 */
	public Pelipoyta() {
		this.pakka = new Pakka();
		this.skanneri = new Scanner(System.in);
		this.peliHistoria = new ArrayList<Integer>();
	}

	public ArrayList<Integer> annaPeliHistoria() {
		return peliHistoria;
	}

	public int annaKierros() {
		return kierros;
	}

	public Pakka annaPakka() {
		return pakka;
	}

	public Scanner annaSkanneri() {
		return skanneri;
	}

	public Henkilo[] annaHenkilot() {
		return this.henkilot;
	}

	/**
	 * Ennen pelin alkua suoritettava metodi.
	 */
	public void pelinAlustus() {
		this.henkilot = new Henkilo[] { new Pelaaja(), new Jakaja() };
		System.out.println("Tervetuloa Black Jack pˆyt‰‰n!" + "\n");
		System.out.println("Mik‰ on nimesi?");
		henkilot[0].asetaNimi(skanneri.nextLine());
		((Pelaaja) henkilot[0]).asetaOmaisuus(this.sisaanOsto());
		peliHistoria.add(((Pelaaja) henkilot[0]).annaOmaisuus());
	}

	/**
	 * Yhteen pelikierrokseen kootut metodit. Panostus, korttien jako ja pelaajan
	 * valinnat, joiden j‰lkeen tarkistetaan kierroksen voittaja.
	 */
	public void uusiKierros() {
		Pelaaja pelaaja = (Pelaaja) henkilot[0];

		for (Henkilo h : henkilot) {
			if (h.annaKasi().size() != 0) {
				h.nollaaKasi();
			}
		}

		pakka.sekoitaPakka();
		kierros++;

		pelaaja.asetaPanos(this.panostus());
		pelaaja.asetaOmaisuus(pelaaja.annaOmaisuus() - pelaaja.annaPanos());

		for (int j = 0; j < 2; j++) {
			henkilot[j].uusiKortti(pakka.annaKortti());
			henkilot[j].uusiKortti(pakka.annaKortti());
		}

		this.tulostaTilanne();
		this.valintaLoop();
		this.tarkistaVoittaja();
		peliHistoria.add(pelaaja.annaOmaisuus());

	}

	/**
	 * K‰ytt‰j‰lt‰ kysyt‰‰n pelin sis‰‰nosto, 0 < sis‰‰nosto <= 1 000 000.
	 * 
	 * @return K‰ytt‰j‰n syˆtt‰m‰ omaisuus kokonaislukuna
	 */
	public int sisaanOsto() {
		Integer i = null;
		do {
			System.out.println("Paljonko sinulla on rahaa?");
			try {
				String s = skanneri.nextLine();
				i = Integer.parseInt(s);
				if (i <= 0) {
					i = null;
					System.out.println("Omaisuus pit‰‰ olla POSITIIVINEN kokonaisluku!");
					continue;
				}
				if (i > 1000000) {
					i = null;
					System.out.println("Maksimi sis‰‰nosto on miljoona.");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Syˆt‰ omaisuus kokonaislukuna! Maksimi sis‰‰nosto on miljoona.");
			}
		} while (i == null);
		return i;
	}

	/**
	 * K‰ytt‰j‰lt‰ kysyt‰‰n pelikierroksen panos, 0 < panos <= omaisuus
	 * 
	 * @return K‰ytt‰j‰n syˆtt‰m‰ kierroksen panos
	 */
	public int panostus() {
		Pelaaja pelaaja = (Pelaaja) henkilot[0];

		Pelipoyta.cls();
		System.out.println("Omaisuus: " + pelaaja.annaOmaisuus());
		Integer i = null;
		do {
			System.out.println("Aseta " + kierros + ". kierroksen panos:");
			try {
				String s = skanneri.nextLine();
				i = Integer.parseInt(s);
				if (i <= 0 || i > pelaaja.annaOmaisuus()) {
					i = null;
					System.out.println("Panos kuuluu olla nollan ja omaisuuden v‰lilt‰!");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Syot‰ panos kokonaislukuna!");
			}
		} while (i == null);
		return i;
	}

	/**
	 * T‰m‰ metodi suoritetaan kaikkien korttien jakamisen j‰lkeen, ja ohjaa joko
	 * voitto(), tasapeli() tai havio() metodiin.
	 */
	public void tarkistaVoittaja() {
		if (henkilot[0].annaSumma() < 22) {
			while (henkilot[1].annaSumma() < 17) {
				henkilot[1].uusiKortti(pakka.annaKortti());
			}
			if (henkilot[1].annaSumma() > 21) {
				this.voitto();
			} else {
				if (henkilot[0].annaSumma() > henkilot[1].annaSumma()) {
					this.voitto();
				} else if (henkilot[0].annaSumma() == henkilot[1].annaSumma()) {
					this.tasapeli();
				} else {
					this.havio();
				}
			}
		} else {
			this.havio();
		}
	}

	/**
	 * Kysyy k‰ytt‰j‰lt‰ lis‰‰ kortteja, kunnes pelaajan k‰den arvo > 21 tai
	 * k‰ytt‰j‰ ei halua enemp‰‰ kortteja.
	 */
	public void valintaLoop() {
		while ((henkilot[0].annaSumma() < 21)) {
			Character x = null;
			do {
				System.out.println("Haluatko uuden kortin vai j‰‰d‰? (Vastaa k/j)");
				try {
					x = skanneri.nextLine().charAt(0);
					if (x != 'j' && x != 'k') {
						x = null;
						System.out.println("Tarkista syˆte!");
						continue;
					}
				} catch (StringIndexOutOfBoundsException e) {
					System.out.println("Tarkista syˆte!");
				}
			} while (x == null);

			if (x == 'k') {
				henkilot[0].uusiKortti(pakka.annaKortti());
				if (henkilot[0].annaSumma() < 21)
					this.tulostaTilanne();
				continue;
			} else
				break;
		}
	}

	/**
	 * T‰m‰ metodi suoritetaan, jos pelaajan k‰si voittaa jakajan k‰den. Omaisuus
	 * kasvaa 2*panoksen verran, koska pelaaja saa takasin panoksensa ja lis‰ksi
	 * voiton.
	 */
	public void voitto() {
		Pelaaja pelaaja = (Pelaaja) henkilot[0];
		if (pelaaja.annaOmaisuus() + pelaaja.annaPanos() * 2 > 0) {
			pelaaja.asetaOmaisuus(pelaaja.annaOmaisuus() + pelaaja.annaPanos() * 2);
		} else {
			pelaaja.asetaOmaisuus(pelaaja.annaOmaisuus() + pelaaja.annaPanos());
		}
		this.tulostaLopputulos();
		System.out.println("VOITIT JAKAJAN!\n");
	}

	/**
	 * T‰m‰ metodi suoritetaan, jos pelaajan k‰si h‰vi‰‰ jakajan k‰den. Omaisuus ei
	 * muutu, koska pelaaja h‰visi panoksen.
	 */
	public void havio() {
		this.tulostaLopputulos();
		System.out.println("Jakaja voitti.\n");
	}

	/**
	 * T‰m‰ metodi suoritetaan, jos pelaajan ja jakajan k‰den arvo on sama
	 * kierroksen lopuksi. Omaisuuteen lis‰t‰‰n kierroksen alussa asetettu panos.
	 */
	public void tasapeli() {
		Pelaaja pelaaja = (Pelaaja) henkilot[0];
		pelaaja.asetaOmaisuus(pelaaja.annaOmaisuus() + pelaaja.annaPanos());
		this.tulostaLopputulos();
		System.out.println("Tasapeli. Saat panoksesi takaisin.\n");
	}

	/**
	 * Kysyt‰‰n k‰ytt‰j‰lt‰ haluaako jatkaa seuraavalle kierrokselle.
	 * 
	 * @return true jos k‰ytt‰j‰ haluaa jatkaa. Muuten false.
	 */
	public boolean tarkistaJatkaminen() {
		Character x = null;
		do {
			System.out.println("Haluatko pelata uuden k‰den? (Vastaa k/e)");
			try {
				x = skanneri.nextLine().charAt(0);
				if (x != 'e' && x != 'k') {
					x = null;
					System.out.println("Tarkista syˆte!");
					continue;
				}
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("Tarkista syˆte!");
			}
		} while (x == null);
		if (x == 'k')
			return true;
		else
			return false;
	}

	/**
	 * Tulostaa k‰ytt‰j‰lle tilanteen, jossa pelin kierros on kesken.
	 */
	public void tulostaTilanne() {
		cls();
		System.out.println(((Jakaja) henkilot[1]).piilotettuToString());
		System.out.println("\n" + ((Pelaaja) henkilot[0]).toString() + "\nPanos: " + ((Pelaaja) henkilot[0]).annaPanos()
				+ "\nOmaisuus: " + ((Pelaaja) henkilot[0]).annaOmaisuus() + "\n");
	}

	/**
	 * Tulostaa k‰ytt‰j‰lle tilanteen, jossa pelin kierros on loppunut.
	 */
	public void tulostaLopputulos() {
		cls();
		System.out.println(((Jakaja) henkilot[1]).toString());
		System.out.println("\n" + ((Pelaaja) henkilot[0]).toString() + "\nOmaisuus: "
				+ ((Pelaaja) henkilot[0]).annaOmaisuus() + "\n");
	}

	/**
	 * Tyhjent‰‰ k‰ytt‰j‰n ruudun.
	 */
	public static void cls() {
		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				Runtime.getRuntime().exec("clear");
		} catch (IOException | InterruptedException ex) {
		}
	}

	/**
	 * Pys‰ytt‰‰ ohjelman annetulla parametrilla, jossa aika syˆtet‰‰n
	 * millisekunteina.
	 * 
	 * @param ms
	 */
	public void odota(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}

	public void suljeSkanneri() {
		skanneri.close();
	}

	/**
	 * Metodi luo tekstitiedoston ja tallentaa sen parametrin polkuun. Palauttaa
	 * polun, koska se saattaa muuttua metodissa.
	 * 
	 * @param peliHistoria
	 * @param polku
	 * @return metodissa saattaa muuttua
	 */
	public String teeTiedosto(ArrayList<Integer> peliHistoria, String polku) {
		try {
			if (((Pelaaja) henkilot[0]).annaNimi().equals("Lauri")) {
				String paiva = new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
				polku = "C:\\Users\\Lauri\\Desktop\\pelit\\peliHistoria" + paiva + ".txt";
			}
			if (((Pelaaja) henkilot[0]).annaNimi().equals("Santeri")) {
				String paiva = new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
				polku = "C:\\Users\\Omistaja\\Desktop\\BlackJackPelit\\peliHistoria" + paiva + ".txt";
			}
			File tiedosto = new File(polku);
			FileOutputStream striimi = new FileOutputStream(tiedosto);
			OutputStreamWriter osw = new OutputStreamWriter(striimi);
			BufferedWriter w = new BufferedWriter(osw);
			w.write("# kierros-omaisuus");
			w.newLine();
			for (int k = 0; k < peliHistoria.size(); k++) {
				w.write(k + " " + peliHistoria.get(k));
				w.newLine();
			}
			w.close();
		} catch (IOException e) {
			System.err.println("Tekstin kirjoittaminen tiedostoon ep‰onnistui.");
		}
		return polku;
	}

	/**
	 * Tulostaa k‰ytt‰j‰lle komennon kuvaajan luomista varten GnuPlot ohjelmalla.
	 * 
	 * @param polku
	 */
	public void tulostaGnuPlotKomento(String polku) {
		StringBuilder sb = new StringBuilder();
		int i = polku.length() - 1;
		while (i > 0) {
			if (polku.charAt(i) == '\\')
				break;
			sb.insert(0, polku.charAt(i));
			i--;
		}
		System.out.println("\nT‰ss‰ GnuPlot ohjelman komento kuvaajaa varten: \n" + "plot [] [0:] '" + sb.toString()
				+ "' with linespoints ls 6");
	}
}