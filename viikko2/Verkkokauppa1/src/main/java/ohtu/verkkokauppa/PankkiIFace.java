package ohtu.verkkokauppa;

public interface PankkiIFace {

    boolean tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa);
    
}
