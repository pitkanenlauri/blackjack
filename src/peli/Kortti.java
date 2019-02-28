package peli;
/**
 * Luokka mallintaa Blackjackissa k�ytett�v�n korttipakan sis�lt�v�� pelikorttia.
 */
public class Kortti {

	private Maa maa;
	private Nimi nimi;

	public Kortti(Maa maa, Nimi nimi) {
		this.maa = maa;
		this.nimi = nimi;
	}

	public Nimi annaNimi() {
		return nimi;
	}

	public Maa annaMaa() {
		return maa;
	}

	public enum Maa {
		PATA, HERTTA, RUUTU, RISTI
	}
	/**
	 * Kortit nimetty tulostusta varten ja niill� on arvo Blackjackin s��nt�jen mukaisesti.
	 */
	public enum Nimi {
		�SS�(11), KAKKONEN(2), KOLMONEN(3), NELONEN(4), VIITONEN(5), KUUTONEN(6), SEISKA(7), KASI(8), YSI(9), KYMPPI(10),
		J�TK�(10), AKKA(10), KURKO(10);

		int arvo;

		private Nimi(int arvo) {
			this.arvo = arvo;
		}

		public int annaArvo() {
			return arvo;
		}

	}

	@Override
	public String toString() {
		return maa + " " + nimi;
	}

}