package pelitestit;
import test.Testi;

import peli.Kortti;
import peli.Kortti.Maa;
import peli.Kortti.Nimi;

public class KorttiTestit extends Testi{
	
	public void Mit‰KortissaTesti() {
		Kortti k = new Kortti(Kortti.Maa.HERTTA, Kortti.Nimi.KURKO);
		System.out.println(k.annaNimi().annaArvo());
	}

	@Override
	public void suoritaTestit() {
		Mit‰KortissaTesti();
	}
}
