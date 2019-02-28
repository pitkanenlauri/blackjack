package henkilot;

import henkilot.Henkilo;

/**
 * Luokka mallintaa pelin jakajaa.
 */
public class Jakaja extends Henkilo {

	public Jakaja() {
		super.nimi = "Jakajan kortit:";
	}

	/**
	 * @return Palauttaa jakajan käden allekkain tulostusta varten toinen kortti
	 *         piilotettuna.
	 */
	public String piilotettuToString() {
		return this.annaNimi() + "\n\n" + kasi.get(0).toString() + "\n[piilotettu]" + "\nKäden arvo: "
				+ kasi.get(0).annaNimi().annaArvo();
	}

	@Override
	public String toString() {
		return this.annaNimi() + "\n\n" + this.kasiToString() + "Käden arvo: " + super.annaSumma();
	}
	
}
