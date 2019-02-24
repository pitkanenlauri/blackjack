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

public class Pelipoyta {
	private Pakka pakka;
	private Henkilo[] henkilot;
	private int kierros = 0;
	private Scanner skanneri;
	private ArrayList<Integer> peliHistoria;

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

	public void pelinAlustus() {
		this.henkilot = new Henkilo[] { new Pelaaja(), new Jakaja() };
		System.out.println("Tervetuloa Black Jack pöytään!" + "\n");
		System.out.println("Mikä on nimesi?");
		henkilot[0].asetaNimi(skanneri.nextLine());
		((Pelaaja) henkilot[0]).asetaOmaisuus(this.sisaanOsto());
		peliHistoria.add(((Pelaaja) henkilot[0]).annaOmaisuus());

	}

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
	
	public int sisaanOsto() {
		Integer i = null;
		do {
			System.out.println("Paljonko sinulla on rahaa?");
			try {
				String s = skanneri.nextLine();
				i = Integer.parseInt(s);
				if (i <= 0) {
					i = null;
					System.out.println("Omaisuus pitää olla POSITIIVINEN kokonaisluku!");
					continue;
				}
				if (i>1000000) {
					i=null;
					System.out.println("Maksimi sisäänosto on miljoona.");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Syötä omaisuus kokonaislukuna! Maksimi sisäänosto on miljoona.");
			}
		} while (i == null);
		return i;
	}

	public int panostus() {
		Pelaaja pelaaja = (Pelaaja) henkilot[0];

		Integer i = null;
		do {
			System.out.println("Aseta " + kierros + ". kierroksen panos:");
			try {
				String s = skanneri.nextLine();
				i = Integer.parseInt(s);
				if (i <= 0 || i > pelaaja.annaOmaisuus()) {
					i = null;
					System.out.println("Panos kuuluu olla nollan ja omaisuuden väliltä! Omaisuutesi on: "
							+ pelaaja.annaOmaisuus());
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Syotä panos kokonaislukuna!");
			}
		} while (i == null);

		return i;
	}

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

	public void valintaLoop() {
		while ((henkilot[0].annaSumma() < 21)) {
			Character x = null;
			do {
				System.out.println("Haluatko uuden kortin vai jäädä? (Vastaa k/j)");
				try {
					x = skanneri.nextLine().charAt(0);
					if (x != 'j' && x != 'k') {
						x = null;
						System.out.println("Tarkista syöte!");
						continue;
					}
				} catch (StringIndexOutOfBoundsException e) {
					System.out.println("Tarkista syöte!");
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

	public void havio() {
		this.tulostaLopputulos();
		System.out.println("Jakaja voitti.\n");
	}

	public void tasapeli() {
		Pelaaja pelaaja = (Pelaaja) henkilot[0];
		pelaaja.asetaOmaisuus(pelaaja.annaOmaisuus() + pelaaja.annaPanos());
		this.tulostaLopputulos();
		System.out.println("Tasapeli. Saat panoksesi takaisin.\n");
	}
	
	public boolean tarkistaJatkaminen() {
		Character x = null;
		do {
			System.out.println("Haluatko pelata uuden käden? (Vastaa k/e)");
			try {
				x = skanneri.nextLine().charAt(0);
				if (x != 'e' && x != 'k') {
					x = null;
					System.out.println("Tarkista syöte!");
					continue;
				}
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("Tarkista syöte!");
			}
		} while (x == null);
		if (x == 'k')
			return true;
		else
			return false;
	}

	public void tulostaTilanne() {
		cls();
		System.out.println(((Jakaja) henkilot[1]).piilotettuToString());
		System.out
				.println("\n" + ((Pelaaja) henkilot[0]).toString() + "\nPanos: " + ((Pelaaja) henkilot[0]).annaPanos() + "\n");
	}

	public void tulostaLopputulos() {
		cls();
		System.out.println(((Jakaja) henkilot[1]).toString());
		System.out.println("\n" + ((Pelaaja) henkilot[0]).toString() + "\n");
	}

	public static void cls(){
	    try {
	        if (System.getProperty("os.name").contains("Windows"))
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        else
	            Runtime.getRuntime().exec("clear");
	    } catch (IOException | InterruptedException ex) {}
	}
	
	public void odota(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {}
	}
	
	public void suljeSkanneri() {

		skanneri.close();
	}
	
	public void teeTiedosto(ArrayList<Integer> peliHistoria, String polku) {
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
			w.write("#Kierros #Omaisuus");
			w.newLine();
			for (int k = 0; k < peliHistoria.size(); k++) {
				w.write(k + " " + peliHistoria.get(k));
				w.newLine();
			}
			w.close();

		} catch (IOException e) {
			System.err.println("Tekstin kirjoittaminen tiedostoon epäonnistui.");
		}
	}
	

}