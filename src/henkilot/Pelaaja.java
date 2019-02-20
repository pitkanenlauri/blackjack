package henkilot;

import henkilot.Henkilo;

public class Pelaaja extends Henkilo {
	private int omaisuus;
	private int panos;

	public int annaOmaisuus() {
		return omaisuus;
	}

	public void asetaOmaisuus(int omaisuus) {
		this.omaisuus = omaisuus;
	}

	public int annaPanos() {
		return panos;
	}

	public void asetaPanos(int panos) {
		this.panos = panos;
	}

	@Override
	public String toString() {
		return this.annaNimi() + ":" + "\n\n" + this.kasiToString() + "Käden arvo: " + super.annaSumma() + "\n"
				+ "\n" + "Omaisuus: " + omaisuus;
	}

}