package ohtu.verkkokauppa;

import java.util.ArrayList;

public interface KirjanpitoIFace {

    ArrayList<String> getTapahtumat();

    void lisaaTapahtuma(String tapahtuma);
    
}
