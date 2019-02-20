package henkilot;

import henkilot.Henkilo;

public class Jakaja extends Henkilo {

	public Jakaja() {
		super.nimi = "Jakajan kortit:";
	}

	public String piilotettuToString() {
		return this.annaNimi() + "\n\n"  + kasi.get(0).toString() + "\n[piilotettu]" + "\nK�den arvo: " + kasi.get(0).annaNimi().annaArvo();
	}

	@Override
	public String toString() {
		return this.annaNimi() + "\n\n" + this.kasiToString() + "K�den arvo: " + super.annaSumma();
	}
}
