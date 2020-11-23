
package ohtu.intjoukkosovellus;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä.
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla.

    public IntJoukko() {
        this(KAPASITEETTI);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasitteetti väärin");//heitin vaan jotain :D
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("kapasiteetti2");//heitin vaan jotain :D
        }
        ljono = new int[kapasiteetti];
        for (int i = 0; i < ljono.length; i++) {
            ljono[i] = 0;
        }
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    private void kasvataTarvittaessa() {
        if (alkioidenLkm % ljono.length == 0) {
            int[] taulukkoOld = ljono;
            ljono = new int[alkioidenLkm + kasvatuskoko];
            kopioiTaulukko(taulukkoOld, ljono);
        }
    }

    public boolean lisaa(int luku) {
        if (kuuluu(luku)) {
            return false;
        }
        ljono[alkioidenLkm] = luku;
        alkioidenLkm++;
        kasvataTarvittaessa();
        return true;
    }

    private int etsi(int luku) {
        int ekaLoytynytIndeksi = -1;
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                ekaLoytynytIndeksi = i;
            }
        }
        return ekaLoytynytIndeksi;
    }

    public boolean kuuluu(int luku) {
        return etsi(luku) >= 0;
    }

    public boolean poista(int luku) {
        int kohta = etsi(luku);
        int apu;
        if (kohta == -1) {
            return false;
        }
        ljono[kohta] = 0;
        for (int j = kohta; j < alkioidenLkm - 1; j++) {
            apu = ljono[j];
            ljono[j] = ljono[j + 1];
            ljono[j + 1] = apu;
        }
        alkioidenLkm--;
        return true;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi, int pituusraja) {
        for (int i = 0; i < pituusraja; i++) {
            uusi[i] = vanha[i];
        }
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        kopioiTaulukko(vanha, uusi, vanha.length);
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else {
            String tuotos = "{";
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                tuotos += ljono[i];
                tuotos += ", ";
            }
            tuotos += ljono[alkioidenLkm - 1];
            tuotos += "}";
            return tuotos;
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        kopioiTaulukko(ljono, taulu, taulu.length);
        return taulu;
    }

    public IntStream toIntStream() {
        return Arrays.stream(ljono, 0, alkioidenLkm);
    }

    private void operoiJoukkoa(IntConsumer ic) {
        this.toIntStream().forEach(ic);
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko yhdistetty = new IntJoukko();
        a.operoiJoukkoa(luku -> yhdistetty.lisaa(luku));
        b.operoiJoukkoa(luku -> yhdistetty.lisaa(luku));
        return yhdistetty;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko leikattu = new IntJoukko();
        a.operoiJoukkoa(luku -> {
            if (b.kuuluu(luku)) {
                leikattu.lisaa(luku);
            }
        });
        return leikattu;
    }

    public static IntJoukko erotus (IntJoukko a, IntJoukko b) {
        IntJoukko erotettu = new IntJoukko();
        a.operoiJoukkoa(luku -> erotettu.lisaa(luku));
        b.operoiJoukkoa(luku -> erotettu.poista(luku));
        return erotettu;
    }

}
