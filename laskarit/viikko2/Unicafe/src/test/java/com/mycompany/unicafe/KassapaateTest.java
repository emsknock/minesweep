package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate k;
    Maksukortti m;

    @Before
    public void setUp() {
        k = new Kassapaate();
        m = new Maksukortti(1000);
    }

    // luodun kassapäätteen rahamäärä ja myytyjen lounaiden määrä on oikea (rahaa 1000, lounaita myyty 0)
    @Test
    public void rahatJaMäärätOikein () {
        assertTrue(k.maukkaitaLounaitaMyyty() == 0);
        assertTrue(k.edullisiaLounaitaMyyty() == 0);
        assertTrue(k.kassassaRahaa() == 100000);
    }

    // jos maksu riittävä: kassassa oleva rahamäärä kasvaa lounaan hinnalla ja vaihtorahan suuruus on oikea
    @Test
    public void siirtoToimiiKunRahaa () {
        assertTrue(k.syoMaukkaasti(500) == 100);
        assertTrue(k.syoEdullisesti(500) == 260);
        assertTrue(k.kassassaRahaa() == 100000 + 240 + 400);
    }

    // jos maksu on riittävä: myytyjen lounaiden määrä kasvaa
    @Test
    public void myytyjenLounaidenMääräKasvaaJosMaksuOikein () {
        k.syoMaukkaasti(400);
        k.syoEdullisesti(240);
        assertTrue(k.maukkaitaLounaitaMyyty() == 1);
        assertTrue(k.edullisiaLounaitaMyyty() == 1);
    }

    // jos maksu ei ole riittävä: kassassa oleva rahamäärä ei muutu, kaikki rahat palautetaan vaihtorahana ja myytyjen lounaiden määrässä ei muutosta
    @Test
    public void riittämätönMaksuEiMuutaMitään () {
        assertTrue(k.syoMaukkaasti(1) == 1);
        assertTrue(k.syoEdullisesti(1) == 1);
        assertTrue(k.kassassaRahaa() == 100000);
        assertTrue(k.edullisiaLounaitaMyyty() == 0);
        assertTrue(k.maukkaitaLounaitaMyyty() == 0);
    }

    // jos kortilla on tarpeeksi rahaa, veloitetaan summa kortilta ja palautetaan true
    @Test
    public void korttiostoToimiiEdullisesti () {
        assertTrue(k.syoEdullisesti(m));
        assertTrue(m.saldo() == 1000 - 240);
    }
    @Test
    public void korttiostoToimiiMaukkaasti () {
        assertTrue(k.syoMaukkaasti(m));
        assertTrue(m.saldo() == 1000 - 400);
    }

    // jos kortilla on tarpeeksi rahaa, myytyjen lounaiden määrä kasvaa
    @Test
    public void myydytMäärätKasvavatKorttiostossa () {
        k.syoMaukkaasti(m);
        k.syoEdullisesti(m);
        assertTrue(k.edullisiaLounaitaMyyty() == 1);
        assertTrue(k.maukkaitaLounaitaMyyty() == 1);
    }

    // jos kortilla ei ole tarpeeksi rahaa, kortin rahamäärä ei muutu, myytyjen lounaiden määrä muuttumaton ja palautetaan false
    @Test
    public void saldotonKorttiostoEiMuutaMitään () {
        m.otaRahaa(900);
        assertFalse(k.syoEdullisesti(m));
        assertFalse(k.syoMaukkaasti(m));
        assertTrue(m.saldo() == 100);
        assertTrue(k.edullisiaLounaitaMyyty() == 0);
        assertTrue(k.maukkaitaLounaitaMyyty() == 0);
    }

    // kassassa oleva rahamäärä ei muutu kortilla ostettaessa
    @Test
    public void kassanRahatEivätMuutuKorttiostossa() {
        k.syoEdullisesti(m);
        k.syoMaukkaasti(m);
        assertTrue(k.kassassaRahaa() == 100000);
    }

    // kortille rahaa ladattaessa kortin saldo muuttuu ja kassassa oleva rahamäärä kasvaa ladatulla summalla
    @Test
    public void kortinLatausMuuttaaArvojaOikein() {
        k.lataaRahaaKortille(m, 1);
        k.lataaRahaaKortille(m, -1);
        assertTrue(k.kassassaRahaa() == 100001);
        assertTrue(m.saldo() == 1001);
    }

}