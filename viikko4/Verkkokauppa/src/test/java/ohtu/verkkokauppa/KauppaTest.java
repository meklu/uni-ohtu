package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(),anyInt());
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaArvoilla() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));
    }

    @Test
    public void monituotteinenOstosToimiiJosJokaistaVarastossa() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        // määritellään että tuote numero 2 on juusto jonka hinta on 7 ja saldo 2
        when(varasto.saldo(2)).thenReturn(2);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "juusto", 7));

        // sitten testattava kauppa
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // muistetaan myös juusto leivän päälle
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5+7));
    }

    @Test
    public void monikappaleinenYksituotteinenOstosToimiiJosTarpeeksiVarastossa() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 2 on juusto jonka hinta on 7 ja saldo 2
        when(varasto.saldo(2)).thenReturn(2);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "juusto", 7));

        // sitten testattava kauppa
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(2);     // ostetaan tuotetta numero 2 eli juustoa
        k.lisaaKoriin(2);     // ai niin joo, tarvitaan toinenkin
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(7+7));
    }

    @Test
    public void monituotteinenOstosToimiiVajavaisestiVajavaisillaVarastosaldoilla() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo loppu
        when(varasto.saldo(1)).thenReturn(0);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        // määritellään että tuote numero 2 on juusto jonka hinta on 7 ja saldo 1
        when(varasto.saldo(2)).thenReturn(1);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "juusto", 7));

        // sitten testattava kauppa
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // muistetaan myös juusto leivän päälle
        k.tilimaksu("pekka", "12345");

        // vain juuston pitäisi olla päätynyt ostettavaksi asti
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(7));
    }

    @Test
    public void asioinninAloittaminenNollaaOstokset() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42).thenReturn(84);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        // määritellään että tuote numero 2 on juusto jonka hinta on 7 ja saldo 2
        when(varasto.saldo(2)).thenReturn(2);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "juusto", 7));

        // sitten testattava kauppa
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // muistetaan myös juusto leivän päälle
        // hupsista, hommat peruttu
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        // vain maito ostetaan
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));
    }

    @Test
    public void maksutapahtumilleTuleeOmatViitenumerot() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42).thenReturn(84);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        // määritellään että tuote numero 2 on juusto jonka hinta on 7 ja saldo 2
        when(varasto.saldo(2)).thenReturn(2);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "juusto", 7));

        // sitten testattava kauppa
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // muistetaan myös juusto leivän päälle
        k.tilimaksu("pekka", "12345");

        verify(viite, times(1)).uusi();
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5+7));

        // tehdään toiset ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // muistetaan myös juusto leivän päälle
        k.tilimaksu("tapani", "612345");

        verify(viite, times(2)).uusi();
        verify(pankki).tilisiirto(eq("tapani"), eq(84), eq("612345"), anyString(), eq(5+7));
    }

    @Test
    public void tuotteenPoistoKoristaToimii() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        // määritellään että tuote numero 2 on juusto jonka hinta on 7 ja saldo 2
        when(varasto.saldo(2)).thenReturn(2);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "juusto", 7));

        // sitten testattava kauppa
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // muistetaan myös juusto leivän päälle
        k.poistaKorista(1);   // maito olikin virhe
        k.tilimaksu("pekka", "12345");

        // maitoa ei ostettu
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(7));
    }
}
