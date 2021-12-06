/**
 * Táto trieda nám vytvára/skrýva/zobrazuje/vymazuje karty pexesa
 * 
 * @author Peter Szathmary 
 * @version 2.1.2021
 */
public class Karta {
    private final Obrazok obrazok;
    //typ karty z enumu
    private final TypKarty typ;
    private int pocitadlo;
    private int y;
    private int x;
    private final Stvorec stvorec;
    private final String farba;
    /**
     * Vytvoríme si nový obrázok, určíme si typ karty z enumu, určíme si farbu daného tvaru
     * zadáme si kontrolne x a y daneého tvaru
     * vytvoríme si nový štvorec, ktorý sa bude tvárit ako zadná časť karty a vynulujeme jeho pozíciu
     */
    public Karta(TypKarty typ, String farba) {
        this.obrazok = new Obrazok();
        this.typ = typ;
        this.pocitadlo = 1;
        this.x = 0;
        this.y = 0;
        this.stvorec = new Stvorec();
        this.stvorec.posunVodorovne(-60);
        this.stvorec.posunZvisle(-50);
        this.farba = farba;
    }
    
    /**
     * Vráti nám typ karty
     * @return typ vráti nám typ karty
     */
    public TypKarty getTyp() {
        return this.typ;
    }
    
    /**
     * Vráti nám farbu tvaru karty
     * @return farba vráti nám farbu tvaru karty
     */
    public String getFarba() {
        return this.farba;
    }
    
    /**
     * "Vymaže" nám tvar tak, že sa štvorec, ktorý
     * tvorí zadnú stranu karty premaľuje na bielo(farba pozadia)
     * a zobrazí sa, tým pádom nám zakryje tvar
     * @param farba určuje farbu štvorca
     */
    public void vymazTvar(String farba) {
        //"zadnu" stranu karty zmeni na bielu a zobrazi ju, tym dosiahnem efekt ze karta zmizla
        this.stvorec.zmenFarbu(farba);
        this.stvorec.zobraz();
    }
    
    /**
     * Vykresľuje kartu na základe jej typu
     * zmení jej tvar na zadanú farbu
     * zobrazí štvorec, ktorý tvorí zadnú časť karty
     * zmení počítadlo, ćize vieme, že tvar je skrytý
     */
    public void vykresliSa() {
        //podla typu z enumu vykreslim dany tvar a stvorec za nim
        switch (this.typ) {
            case STVOREC -> {
                this.obrazok.getStvorec().skry();
                this.obrazok.getStvorec().zmenFarbu(this.farba);
                this.stvorec.zobraz();
                this.pocitadlo *= -1;
            }
            case TROJUHOLNIK -> {
                this.obrazok.getTrojuholnik().skry();
                this.obrazok.getTrojuholnik().zmenFarbu(this.farba);
                this.stvorec.zobraz();
                this.pocitadlo *= -1;
            }
            case KRUH -> {
                this.obrazok.getKruh().skry();
                this.obrazok.getKruh().zmenFarbu(this.farba);
                this.stvorec.zobraz();
                this.pocitadlo *= -1;
            }
            case OBDLZNIK -> {
                this.obrazok.getObdlznik().skry();
                this.obrazok.getObdlznik().zmenFarbu(this.farba);
                this.stvorec.zobraz();
                this.pocitadlo *= -1;
            }
            case ELIPSA -> {
                this.obrazok.getElipsa().skry();
                this.obrazok.getElipsa().zmenFarbu(this.farba);
                this.stvorec.zobraz();
                this.pocitadlo *= -1;
            }
        }
    }
    
    /**
     * Otočí kartu na základe jej typu
     * ciže, skryje/odkryje štvorec a tvar na základe počítadla
     * takže, ak je skrytá, odkryje ju a naopak
     */
    public void otocSa() {
        //vykreslujem alebo skryvam tvar na zaklade typu karty z enumu
        // ak je pocitadlo -1, tvar je skryty, ak je 1, tak je zobrazeny
        switch (this.typ) {
            case STVOREC -> {
                if (this.pocitadlo == 1) {
                    this.obrazok.getStvorec().skry();
                    this.stvorec.zobraz();
                } else {
                    this.obrazok.getStvorec().zobraz();
                    this.stvorec.skry();
                }
                this.pocitadlo *= -1;
            }
            case TROJUHOLNIK -> {
                if (this.pocitadlo == 1) {
                    this.obrazok.getTrojuholnik().skry();
                    this.stvorec.zobraz();
                } else {
                    this.obrazok.getTrojuholnik().zobraz();
                    this.stvorec.skry();
                }
                this.pocitadlo *= -1;
            }
            case KRUH -> {
                if (this.pocitadlo == 1) {
                    this.obrazok.getKruh().skry();
                    this.stvorec.zobraz();
                } else {
                    this.obrazok.getKruh().zobraz();
                    this.stvorec.skry();
                }
                this.pocitadlo *= -1;
            }
            case OBDLZNIK -> {
                if (this.pocitadlo == 1) {
                    this.obrazok.getObdlznik().skry();
                    this.stvorec.zobraz();
                } else {
                    this.obrazok.getObdlznik().zobraz();
                    this.stvorec.skry();
                }
                this.pocitadlo *= -1;
            }
            case ELIPSA -> {
                if (this.pocitadlo == 1) {
                    this.obrazok.getElipsa().skry();
                    this.stvorec.zobraz();
                } else {
                    this.obrazok.getElipsa().zobraz();
                    this.stvorec.skry();
                }
                this.pocitadlo *= -1;
            }
        }
    }
    
    /**
     * vráti true alebo false na základe toho, či sa typy a farba karty rovnajú
     * @param prvaKarta je prvá otočená karta
     * @param druhaKarta je druhá otočená karta
     */
    public boolean jeRovnaka(Karta prvaKarta, Karta druhaKarta) {
        //kontrolujem ci zadana prva karta ma rovnake hodnoty, teda rovnaky tvar ako druha, ak by som zadal ==, kontroloval by som referenciu medzi nimi
        return prvaKarta.getTyp().equals(druhaKarta.getTyp()) && prvaKarta.getFarba().equals(druhaKarta.getFarba());
    }
    
    /**
     * Posunie daný tvar a štvorec vodorovne na základe typu karty
     * nastaví veľkosť štvorca na základe veľkosti karty
     * zmeny farbu štvorca na čiernu
     * @param kolko o koľko sa ma daná karta posunúť
     */
    public void posunVodorovne(int kolko) {
        //posuvam dany tvar aj so stvorcom za nim na zaklade typu z enumu vodorovne
        this.x += kolko;
        switch (this.typ) {
            case STVOREC -> {
                this.obrazok.getStvorec().posunVodorovne(kolko);
                this.stvorec.posunVodorovne(kolko);
                this.stvorec.zmenStranu(this.obrazok.getVelkostKarty());
                this.stvorec.zmenFarbu("black");
            }
            case TROJUHOLNIK -> {
                this.obrazok.getTrojuholnik().posunVodorovne(kolko);
                this.stvorec.posunVodorovne(kolko);
                this.stvorec.zmenStranu(this.obrazok.getVelkostKarty());
                this.stvorec.zmenFarbu("black");
            }
            case KRUH -> {
                this.obrazok.getKruh().posunVodorovne(kolko);
                this.stvorec.posunVodorovne(kolko);
                this.stvorec.zmenStranu(this.obrazok.getVelkostKarty());
                this.stvorec.zmenFarbu("black");
            }
            case OBDLZNIK -> {
                this.obrazok.getObdlznik().posunVodorovne(kolko);
                this.stvorec.posunVodorovne(kolko);
                this.stvorec.zmenStranu(this.obrazok.getVelkostKarty());
                this.stvorec.zmenFarbu("black");
            }
            case ELIPSA -> {
                this.obrazok.getElipsa().posunVodorovne(kolko);
                this.stvorec.posunVodorovne(kolko);
                this.stvorec.zmenStranu(this.obrazok.getVelkostKarty());
                this.stvorec.zmenFarbu("black");
            }
        }
    }
    
    /**
     * Posunie daný tvar a štvorec zvisle na základe typu karty
     * nastaví veľkosť štvorca na základe veľkosti karty
     * zmeny farbu štvorca na čiernu
     * @param kolko o koľko sa ma daná karta posunúť
     */
    public void posunZvisle(int kolko) {
        this.y += kolko;
        //posuvam dany tvar aj so stvorcom za nim na zaklade typu z enumu zvisle
        switch (this.typ) {
            case STVOREC -> {
                this.obrazok.getStvorec().posunZvisle(kolko);
                this.stvorec.posunZvisle(kolko);
                this.stvorec.zmenStranu(this.obrazok.getVelkostKarty());
                this.stvorec.zmenFarbu("black");
            }
            case TROJUHOLNIK -> {
                this.obrazok.getTrojuholnik().posunZvisle(kolko);
                this.stvorec.posunZvisle(kolko);
                this.stvorec.zmenStranu(this.obrazok.getVelkostKarty());
                this.stvorec.zmenFarbu("black");
            }
            case KRUH -> {
                this.obrazok.getKruh().posunZvisle(kolko);
                this.stvorec.posunZvisle(kolko);
                this.stvorec.zmenStranu(this.obrazok.getVelkostKarty());
                this.stvorec.zmenFarbu("black");
            }
            case OBDLZNIK -> {
                this.obrazok.getObdlznik().posunZvisle(kolko);
                this.stvorec.posunZvisle(kolko);
                this.stvorec.zmenStranu(this.obrazok.getVelkostKarty());
                this.stvorec.zmenFarbu("black");
            }
            case ELIPSA -> {
                this.obrazok.getElipsa().posunZvisle(kolko);
                this.stvorec.posunZvisle(kolko);
                this.stvorec.zmenStranu(this.obrazok.getVelkostKarty());
                this.stvorec.zmenFarbu("black");
            }
        }
    }
    
    /**
     * Vráti nám x-ovú pozíciu tvaru
     * @return x vráti x-ovú pozíciu tvaru
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Vráti nám y-ovú pozíciu tvaru
     * @return y vráti y-ovú pozíciu tvaru
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Vráti nám veľkosť karty
     * @return obrazok.getVelkostKarty() vráti nám veľkosť karty
     */
    public int getVelkostKarty() {
        return this.obrazok.getVelkostKarty();
    }
}
