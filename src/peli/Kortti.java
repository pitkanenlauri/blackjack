package peli;

public class Kortti {
	
	private Maa maa;
	private Nimi nimi;

	public Kortti(Maa maa, Nimi nimi) {
		this.maa=maa;
		this.nimi=nimi;
	}
	
	public Nimi annaNimi()
    {
        return nimi;
    }

    public Maa annaMaa()
    {
        return maa;
    }
	
	
	public enum Maa{
		PATA, HERTTA, RUUTU, RISTI
	}
	public enum Nimi{
		ÄSSÄ(1),
		KAKKONEN(2),
		KOLMONEN(3),
		NELONEN(4),
		VIITONEN(5),
		KUUTONEN(6),
		SEISKA(7),
		KASI(8),
		YSI(9),
		KYMPPI(10),
		JÄTKÄ(10),
		AKKA(10),
		KURKO(10);
		
		int arvo;
		
		Nimi(int arvo){
			this.arvo=arvo;
		}
		
		public int annaArvo() {
			return arvo;
		}
		
		
	}
	
	
}
