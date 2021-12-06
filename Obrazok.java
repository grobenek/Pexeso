
/**
 * Trieda na vrátenie rôznych tvarov v nulových pozíciách
 * 
 * @author Peter Szathmary
 * @version 27.12.2020
 */
public class Obrazok {
    private final Stvorec stvorec;
    private final Obdlznik obdlznik;
    private final Kruh kruh;
    private final Trojuholnik trojuholnik;
    private final Elipsa elipsa;
    private static final int VELKOST_KARTY = 15;
    /**
     * Vytvorenie každého tvaru
     * vynulovanie každého tvaru
     * Určenie veľkosti každého tvaru
     * Pripadne prispôsobenie x a y na základe pozície štvorca(zadnej časti) karty
     */
    public Obrazok() {
        //vytvaram, nulujem a prisposobujem velkosti a pozicie tvarov na zaklade stvorcov v triede Hra
        this.stvorec = new Stvorec();
        this.stvorec.posunVodorovne(-60);
        this.stvorec.posunZvisle(-50);
        this.stvorec.zmenStranu(VELKOST_KARTY);
        this.obdlznik = new Obdlznik();
        this.obdlznik.zmenStrany(VELKOST_KARTY, VELKOST_KARTY - (VELKOST_KARTY / 2) );
        this.obdlznik.posunVodorovne(-60);
        this.obdlznik.posunZvisle(-45);
        this.kruh = new Kruh();
        this.kruh.posunVodorovne(-20);
        this.kruh.posunZvisle(-60);
        this.kruh.zmenPriemer(VELKOST_KARTY);
        this.trojuholnik = new Trojuholnik();
        this.trojuholnik.zmenRozmery(VELKOST_KARTY, VELKOST_KARTY);
        this.trojuholnik.posunVodorovne(-43);
        this.trojuholnik.posunZvisle(-15);
        this.elipsa = new Elipsa();
        this.elipsa.zmenOsi(VELKOST_KARTY, VELKOST_KARTY / 2);
        this.elipsa.posunVodorovne(-20);
        this.elipsa.posunZvisle(-53);
    }

    /**
     * Vráti nám štvorec
     * @return štvorec vráti štvorec
     */
    public Stvorec getStvorec() {
        return this.stvorec;
    }

    /**
     * Vráti nám trojuholník
     * @return trojuholník vráti nám trojuholník
     */
    public Trojuholnik getTrojuholnik() {
        return this.trojuholnik;
    }
    /**
     * Vráti nám kruh
     * @return kruh vráti nám kruh
     */
    public Kruh getKruh() {
        return this.kruh;
    }
    
    /**
     * Vráti nám obdĺžnik
     * @return vráti nám obdĺžnik
     */
    public Obdlznik getObdlznik() {
        return this.obdlznik;
    }
    
    /**
     * Vráti nám elipsu
     * @return vráti nám elipsu
     */
    public Elipsa getElipsa() {
        return this.elipsa;
    }
    
    /**
     * Vráti nám veľkosť karty
     * @return VELKOST_KARTY vráti nám veľkosť karty
     */
    public int getVelkostKarty() {
        return VELKOST_KARTY;
    }
}
