package pelitestit;

import henkilot.Henkilo;
import peli.Kortti;
import peli.Pelipoyta;
import peli.Kortti.Maa;
import peli.Kortti.Nimi;
import test.Testi;

public class PelipoytaTestit extends Testi {
	public void pelinAlustusTesti() {
		Pelipoyta p = new Pelipoyta();
		p.pelinAlustus();
		p.annaHenkilot()[0].uusiKortti(new Kortti(Maa.PATA, Nimi.ÄSSÄ));
		System.out.println(p.annaHenkilot()[0].toString());
		for (Henkilo h : p.annaHenkilot()) {
			if (h.annaKasi().size() != 0) {
				h.nollaaKasi();
			}
		}
		System.out.println(p.annaHenkilot()[0].toString());
	}

	public void kierrosTesti() {
		Pelipoyta p = new Pelipoyta();
		p.pelinAlustus();
		p.uusiKierros();
		p.suljeSkanneri();
	}

	@Override
	public void suoritaTestit() {
		pelinAlustusTesti();
		// kierrosTesti();
	}
}