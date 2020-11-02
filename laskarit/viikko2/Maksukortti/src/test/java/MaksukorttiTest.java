
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void konstruktoriAsettaaSaldonOikein() {
        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
    }

    @Test
    public void syoEdullisestiVahentaaSaldoaOikein() {
        kortti.syoEdullisesti();
        assertEquals("Kortilla on rahaa 7.5 euroa", kortti.toString());
    }

    @Test
    public void syoMaukkaastiVahentaaSaldoaOikein() {
        kortti.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 6.0 euroa", kortti.toString());
    }

    @Test
    public void syoEdullisestiEiVieSaldoaNegatiiviseksi() {
        kortti.syoMaukkaasti();
        kortti.syoMaukkaasti();
        kortti.syoEdullisesti();
        assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
    }

    @Test
    public void kortilleVoiLadataRahaa() {
        kortti.lataaRahaa(25);
        assertEquals("Kortilla on rahaa 35.0 euroa", kortti.toString());
    }

    @Test
    public void kortinSaldoEiYlitaMaksimiarvoa() {
        kortti.lataaRahaa(200);
        assertEquals("Kortilla on rahaa 150.0 euroa", kortti.toString());
    }

    // maukkaan lounaan syöminen ei vie saldoa negatiiviseksi, ota tähän mallia testistä syoEdullisestiEiVieSaldoaNegatiiviseksi
    @Test
    public void syoMaukkaastiEiVieSaldoaNegatiiviseksi() {
        kortti.syoEdullisesti();
        kortti.syoEdullisesti();
        kortti.syoEdullisesti();
        kortti.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 2.5 euroa", kortti.toString());
    }
    
    //negatiivisen summan lataaminen ei muuta kortin saldoa
    @Test
    public void negatiivisenSummanLataaminenEiMuutaSaldoa() {
        kortti.lataaRahaa(-5);
        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
    }
    
    //kortilla pystyy ostamaan edullisen lounaan, kun kortilla rahaa vain edullisen lounaan verran (eli 2.5e)
    @Test
    public void voiSyodaEdullisestiKunTasaraha() {
        Maksukortti k = new Maksukortti(2.5);
        k.syoEdullisesti();
        assertEquals("Kortilla on rahaa 0.0 euroa", k.toString());
    }
    
    //kortilla pystyy ostamaan maukkaan lounaan, kun kortilla rahaa vain maukkaan lounaan verran (eli 4e)
    @Test
    public void voiSyodaMaukkaastiKunTasaraha() {
        Maksukortti k = new Maksukortti(4.0);
        k.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 0.0 euroa", k.toString());
    }
    
    
}
