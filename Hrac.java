
/**
 * Trieda hráča
 * vieme si pozrieť jeho body a meno
 * vieme hráčovi body pridať
 * 
 * @author Peter Szathmáry
 * @version 30.12.20
 */
public class Hrac {
    private int body;
    private final String menoHraca;
    
    /**
     * Vynulujem si počet bodov a zadám meno hráča
     * @param menoHraca určuje meno hráča
     */
    public Hrac(String menoHraca) {
        this.body = 0;
        this.menoHraca = menoHraca;
    }
    
    /**
     * Vráti nám pocet bodov hráča
     * @return body vráti nám počet bodov hráča
     */
    public int getBody() {
        return this.body;
    }
    
    /**
     * Vráti nám meno hráča
     * @return menoHraca vráti nám meno hráča
     */
    public String getMenoHraca() {
        return this.menoHraca;
    }
    
    /**
     * Pridá hráčovi jeden bod
     */
    public void pridajBod() {
        this.body += 1;
    }
}
