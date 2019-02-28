package peli;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import henkilot.Henkilo;
import henkilot.Jakaja;
import henkilot.Pelaaja;

/**
 * Luokka joka mallintaa pelin kulkuun liittyv�t tapahtumat.
 */
public class Pelipoyta {

	private Pakka pakka;
	private Henkilo[] henkilot;
	private int kierros = 0;
	private Scanner skanneri;
	/** Lista johon pelihistoria tallennetaan. */
	private ArrayList<Integer> peliHistoria;

	/**
	 * Konstruktori alustaa pelip�yd�n sis�lt�en sekoitetun korttipakan, skannerin
	 * pelaajan sy�tteiden lukua varten ja listan pelihistorian tallentamiseen.
	 */
	public Pelipoyta() {
		this.pakka = new Pakka();
		this.skanneri = new Scanner(System.in);
		this.peliHistoria = new ArrayList<Integer>();
	}

	public Pakka annaPakka() {
		return pakka;
	}

	public Henkilo[] annaHenkilot() {
		return this.henkilot;
	}

	public int annaKierros() {
		return kierros;
	}

	public Scanner annaSkanneri() {
		return skanneri;
	}

	public ArrayList<Integer> annaPeliHistoria() {
		return peliHistoria;
	}

	/**
	 * Ennen pelin alkua suoritettava metodi. Pelaaja ja jakaja saapuvat p�yt��n ja
	 * pelaajalta kysyt��n nimi ja peliss� k�ytett�v� omaisuus eli sis��nosto.
	 */
	public void pelinAlustus() {
		this.henkilot = new Henkilo[] { new Pelaaja(), new Jakaja() };
		System.out.println("Tervetuloa Black Jack p�yt��n!" + "\n");
		System.out.println("Mik� on nimesi?");
		henkilot[0].asetaNimi(skanneri.nextLine());
		((Pelaaja) henkilot[0]).asetaOmaisuus(this.sisaanOsto());
		peliHistoria.add(((Pelaaja) henkilot[0]).annaOmaisuus());
	}

	/**
	 * Yhden kierroksen kulkua mallintava metodi. Pelaajan ja jakajan k�det
	 * nollataan ja pakka sekoitetaan. Pelaaja panostaa ja kortit jaetaan jota
	 * seuraa pelaajan valinnat ja lopuksi tarkistetaan voittaja.
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

		/** Pelaaja ja jakaja saavat kaksi korttia. */
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
	 * K�ytt�j�lt� kysyt��n pelin sis��nosto, 0 < sis��nosto <= 1 000 000.
	 * 
	 * @return K�ytt�j�n sy�tt�m� omaisuus kokonaislukuna.
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
					System.out.println("Omaisuus pit�� olla POSITIIVINEN kokonaisluku!");
					continue;
				}
				if (i > 1000000) {
					i = null;
					System.out.println("Maksimi sis��nosto on miljoona.");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Sy�t� omaisuus kokonaislukuna! Maksimi sis��nosto on miljoona.");
			}
		} while (i == null);
		return i;
	}

	/**
	 * K�ytt�j�lt� kysyt��n pelikierroksen panos, 0 < panos <= omaisuus.
	 * 
	 * @return K�ytt�j�n sy�tt�m� kierroksen panos.
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
					System.out.println("Panos kuuluu olla nollan ja omaisuuden v�lilt�!");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Syot� panos kokonaislukuna!");
			}
		} while (i == null);
		return i;
	}

	/**
	 * Kysyy k�ytt�j�lt� lis�� kortteja, kunnes pelaajan k�den arvo > 21 tai
	 * k�ytt�j� ei halua enemp�� kortteja.
	 */
	public void valintaLoop() {
		while ((henkilot[0].annaSumma() < 21)) {
			Character x = null;
			do {
				System.out.println("Haluatko uuden kortin vai j��d�? (Vastaa k/j)");
				try {
					x = skanneri.nextLine().charAt(0);
					if (x != 'j' && x != 'k') {
						x = null;
						System.out.println("Tarkista sy�te!");
						continue;
					}
				} catch (StringIndexOutOfBoundsException e) {
					System.out.println("Tarkista sy�te!");
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
	 * Tarkistetaan pelin voittaja, metodi ohjaa joko voitto(), havio() tai
	 * tasapeli() metodiin.
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
	 * T�m� metodi suoritetaan, jos pelaajan k�si voittaa jakajan k�den. Omaisuus
	 * kasvaa 2*panoksen verran, koska pelaaja saa takasin panoksensa ja lis�ksi
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
	 * T�m� metodi suoritetaan, jos pelaajan k�si h�vi�� jakajan k�den. Omaisuus ei
	 * muutu, koska pelaaja h�visi panoksen.
	 */
	public void havio() {
		this.tulostaLopputulos();
		System.out.println("Jakaja voitti.\n");
	}

	/**
	 * T�m� metodi suoritetaan, jos pelaajan ja jakajan k�den arvo on sama
	 * kierroksen lopuksi. Omaisuuteen lis�t��n kierroksen alussa asetettu panos.
	 */
	public void tasapeli() {
		Pelaaja pelaaja = (Pelaaja) henkilot[0];
		pelaaja.asetaOmaisuus(pelaaja.annaOmaisuus() + pelaaja.annaPanos());
		this.tulostaLopputulos();
		System.out.println("Tasapeli. Saat panoksesi takaisin.\n");
	}

	/**
	 * Kysyt��n k�ytt�j�lt� haluaako h�n pelata uuden kierroksen.
	 * 
	 * @return true jos k�ytt�j� haluaa jatkaa, muuten false.
	 */
	public boolean tarkistaJatkaminen() {
		Character x = null;
		do {
			System.out.println("Haluatko pelata uuden k�den? (Vastaa k/e)");
			try {
				x = skanneri.nextLine().charAt(0);
				if (x != 'e' && x != 'k') {
					x = null;
					System.out.println("Tarkista sy�te!");
					continue;
				}
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("Tarkista sy�te!");
			}
		} while (x == null);
		if (x == 'k')
			return true;
		else
			return false;
	}

	/**
	 * Tulostaa k�ytt�j�lle tilanteen, jossa pelin kierros on kesken.
	 */
	public void tulostaTilanne() {
		cls();
		System.out.println(((Jakaja) henkilot[1]).piilotettuToString());
		System.out.println("\n" + ((Pelaaja) henkilot[0]).toString() + "\nPanos: " + ((Pelaaja) henkilot[0]).annaPanos()
				+ "\nOmaisuus: " + ((Pelaaja) henkilot[0]).annaOmaisuus() + "\n");
	}

	/**
	 * Tulostaa k�ytt�j�lle tilanteen, jossa pelin kierros on loppunut.
	 */
	public void tulostaLopputulos() {
		cls();
		System.out.println(((Jakaja) henkilot[1]).toString());
		System.out.println("\n" + ((Pelaaja) henkilot[0]).toString() + "\nOmaisuus: "
				+ ((Pelaaja) henkilot[0]).annaOmaisuus() + "\n");
	}

	/**
	 * Tyhjent�� k�ytt�j�n ruudun.
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
	 * Pys�ytt�� ohjelman annetulla parametrina millisekunneissa annetuksi ajaksi.
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
	 * Metodi luo tekstitiedoston ja tallentaa sen parametrina annettuun polkuun.
	 * Palauttaa polun, koska sit� k�ytet��n gnuplot komentoa varten ja se voi
	 * muuttua metodin suorituksen aikana.
	 * 
	 * @param peliHistoria
	 * @param polku
	 * @return Palauttaa polun johon pelihistoria on tallennettu.
	 */
	public String teeTiedosto(ArrayList<Integer> peliHistoria, String polku) {
		try {
			
			// Valmiit polut pelin tekij�iden iloksi. :)
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
			System.err.println("Tekstin kirjoittaminen tiedostoon ep�onnistui.");
		}
		return polku;
	}

	/**
	 * Tulostaa ruudulle komennon kuvaajan piirt�mist� varten gnuplot ohjelmalla.
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
		System.out.println("\nT�ss� GnuPlot ohjelman komento kuvaajaa varten: \n" + "plot [] [0:] '" + sb.toString()
				+ "' with linespoints ls 6");
	}

}