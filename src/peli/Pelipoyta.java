package peli;

import java.util.Scanner;

import henkilot.*;

public class Pelipoyta {
	private Pakka pakka;
	private Henkilo[] henkilot;
	private int kierros = 0;
	private Scanner skanneri;
	private final int MAX = 1000000;

	public Pelipoyta() {
		this.pakka = new Pakka();
		this.skanneri = new Scanner(System.in);
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

		Integer i = -1;
		do {
			System.out.println("Paljonko sinulla on rahaa?");
			String s = skanneri.nextLine();
			try {
				i = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				System.out.println("Syotä omaisuus kokonaislukuna!");
			}
		} while (i == null);

		((Pelaaja) henkilot[0]).asetaOmaisuus(i);

	}

	public void uusiKierros() {

		for (Henkilo h : henkilot) {
			if (h.annaKasi().size() != 0) {
				h.nollaaKasi();
			}
		}

		pakka.sekoitaPakka();
		kierros++;

		System.out.println("Aseta " + kierros + ". kierroksen panos:");
		((Pelaaja) henkilot[0]).asetaPanos(skanneri.nextInt());
		while (((Pelaaja) henkilot[0]).annaPanos() > ((Pelaaja) henkilot[0]).annaOmaisuus()) {
			System.out.println("Panos liian suuri!");
			((Pelaaja) henkilot[0]).asetaPanos(skanneri.nextInt());
		}

		((Pelaaja) henkilot[0])
				.asetaOmaisuus(((Pelaaja) henkilot[0]).annaOmaisuus() - ((Pelaaja) henkilot[0]).annaPanos());

		for (int j = 0; j < 2; j++) {
			henkilot[j].uusiKortti(pakka.annaKortti());
			henkilot[j].uusiKortti(pakka.annaKortti());
		}

		System.out.println("______________________________________________________________________");

		this.tulostaTilanne();

		this.valintaLoop();

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
			System.out.println("\nHaluatko uuden kortin vai jäädä? (Vastaa k tai j)");
			char x = skanneri.next().charAt(0);
			if (x == 'k') {
				henkilot[0].uusiKortti(pakka.annaKortti());

				this.tulostaTilanne();

				continue;

			} else if (x == 'j') {
				break;
			} else {
				System.out.println("Tarkista syöte!");
				continue;
			}
		}
		System.out.print("--------------------------------------------------------------");

	}

	public void voitto() {
		((Pelaaja) henkilot[0])
				.asetaOmaisuus(((Pelaaja) henkilot[0]).annaOmaisuus() + ((Pelaaja) henkilot[0]).annaPanos() * 2);
		this.tulostaLopputulos();
		System.out.println("VOITIT JAKAJAN!");
	}

	public void havio() {
		this.tulostaLopputulos();
		System.out.println("Jakaja voitti.");
	}

	public void tasapeli() {
		((Pelaaja) henkilot[0])
				.asetaOmaisuus(((Pelaaja) henkilot[0]).annaOmaisuus() + ((Pelaaja) henkilot[0]).annaPanos());
		this.tulostaLopputulos();
		System.out.println("Tasapeli. Saat panoksesi takaisin.");
	}

	public void tulostaTilanne() {
		System.out.println("\n" + ((Jakaja) henkilot[1]).piilotettuToString());
		System.out
				.println("\n" + ((Pelaaja) henkilot[0]).toString() + "\nPanos: " + ((Pelaaja) henkilot[0]).annaPanos());
	}

	public void tulostaLopputulos() {
		System.out.println("\n" + ((Jakaja) henkilot[1]).toString());
		System.out.println("\n" + ((Pelaaja) henkilot[0]).toString());
	}

	public void suljeSkanneri() {
		skanneri.close();
	}

}