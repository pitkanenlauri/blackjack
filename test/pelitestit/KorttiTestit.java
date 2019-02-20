package pelitestit;

import test.Testi;
import peli.*;

public class KorttiTestit extends Testi {

	public void mitaKortissaTesti() {
		Kortti k = new Kortti(Kortti.Maa.HERTTA, Kortti.Nimi.KURKO);
		System.out.println(k.toString() + ", arvona: " + k.annaNimi().annaArvo());
	}

	public void tulostaPakkaTesti() {
		Pakka p = new Pakka();
		for (Kortti k : p.annaPakka()) {
			System.out.println(k.toString());
		}
		System.out.println(p.annaPakka().size());
	}

	@Override
	public void suoritaTestit() {
		mitaKortissaTesti();
		tulostaPakkaTesti();
	}
}