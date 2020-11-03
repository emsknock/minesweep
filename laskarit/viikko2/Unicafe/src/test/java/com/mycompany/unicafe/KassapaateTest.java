package com.mycompany.unicafe;

public class KassapaateTest {

    Kassapaate k;

    @Before
    public void setUp() {
        k = new Kassapaate(10);
    }

    // luodun kassapäätteen rahamäärä ja myytyjen lounaiden määrä on oikea (rahaa 1000, lounaita myyty 0)
    @Test
    public void () {
        
    }

    // käteisosto toimii sekä edullisten että maukkaiden lounaiden osalta
    @Test
    public void () {
        
    }
    @Test
    public void () {
        
    }

    // jos maksu riittävä: kassassa oleva rahamäärä kasvaa lounaan hinnalla ja vaihtorahan suuruus on oikea
    @Test
    public void () {
        
    }

    // jos maksu on riittävä: myytyjen lounaiden määrä kasvaa
    @Test
    public void () {
        
    }

    // jos maksu ei ole riittävä: kassassa oleva rahamäärä ei muutu, kaikki rahat palautetaan vaihtorahana ja myytyjen lounaiden määrässä ei muutosta
    @Test
    public void () {
        
    }

    // seuraavissa testeissä tarvitaan myös Maksukorttia jonka oletetaan toimivan oikein
    
    // korttiosto toimii sekä edullisten että maukkaiden lounaiden osalta
    @Test
    public void () {
        
    }

    // jos kortilla on tarpeeksi rahaa, veloitetaan summa kortilta ja palautetaan true
    @Test
    public void () {
        
    }

    // jos kortilla on tarpeeksi rahaa, myytyjen lounaiden määrä kasvaa
    @Test
    public void () {
        
    }

    // jos kortilla ei ole tarpeeksi rahaa, kortin rahamäärä ei muutu, myytyjen lounaiden määrä muuttumaton ja palautetaan false
    @Test
    public void () {
        
    }

    // kassassa oleva rahamäärä ei muutu kortilla ostettaessa
    @Test
    public void () {
        
    }

    // kortille rahaa ladattaessa kortin saldo muuttuu ja kassassa oleva rahamäärä kasvaa ladatulla summalla
    @Test
    public void () {
        
    }

}