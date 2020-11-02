package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }

    // kortin saldo alussa oikein
    @Test
    public void kortinSaldoAlussaOikein() {
        assertTrue(kortti.saldo() == 10);      
    }

    // rahan lataaminen kasvattaa saldoa oikein
    @Test
    public void lataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(10);
        assertTrue(kortti.saldo() == 20);      
    }

    // rahan ottaminen toimii
    @Test
    public void rahanOttaminenToimii() {
        kortti.otaRahaa(10);
        assertTrue(kortti.saldo() == 0);      
    }

    // saldo vähenee oikein, jos rahaa on tarpeeksi
    @Test
    public void saldoVäheneeJosOnRahaa() {
        kortti.otaRahaa(5);
        assertTrue(kortti.saldo() == 5);      
    }

    // saldo ei muutu, jos rahaa ei ole tarpeeksi
    @Test
    public void saldoEiMuutuJosEiRahaa() {
        kortti.otaRahaa(20);
        assertTrue(kortti.saldo() == 10);      
    }

    // otaRahaa palauttaa true, jos rahat riittivät ja muuten false
    @Test
    public void otaRahaaPalauttaaTrueJosRahaRiittää() {
        assertTrue(kortti.otaRahaa(10));      
    }
    @Test
    public void otaRahaaPalauttaaFalseJosRahaEiRiitä() {
        assertFalse(kortti.otaRahaa(20));      
    }

}
