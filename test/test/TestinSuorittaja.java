package test;

import test.Testi;
import pelitestit.KorttiTestit;;

public class TestinSuorittaja {
	static Testi[] testit = { new KorttiTestit() };

	public static void main(String[] args) {
		suoritaKaikkiTestit();
	}

	public static void suoritaKaikkiTestit() {

		for (Testi t : testit) {
			String testinNimi = t.getClass().getSimpleName();
			System.out.println("Suoritetaan: " + testinNimi);

			t.suoritaTestit();

		}
		System.out.println("Kaikki testit suoritettu onnistuneesti!");
	}

	public static void suoritaKaikkiTestitVaikkaTestiEpäonnistuu() {
		int virheita = 0;
		for (Testi t : testit) {
			String testinNimi = t.getClass().getSimpleName();
			System.out.println("Suoritetaan: " + testinNimi);

			try {
				t.suoritaTestit();
			} catch (Throwable e) {
				System.out.println("Testi \"" + testinNimi + "\" epäonnistui:");
				e.printStackTrace();
				virheita++;
			}
		}
		System.out.println("Testit suoritettu: " + (testit.length - virheita) + "/" + testit.length + " onnistui");

	}
}
