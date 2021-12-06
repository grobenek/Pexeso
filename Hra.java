import java.util.*;

/**
 * Stará sa o chod hry
 * spravuje počet hráčov, počet kariet
 * 
 * @author Peter Szathmary
 * @version 27.12.2020
 */
public class Hra {
    private static final int MEDZERA_MEDZI_KARTAMI = 6;
    private final int pocetHracov;
    private int hracNaTahuIndex;
    private final Hrac[] hraci;
    //pocet dvojic pre kazdy tvar
    private final int pocetKariet;
    //index, podla ktoreho viem, ci hrac otocil jednu, alebo 2 karty
    //ArrayList kariet, ktory pomiesam a nasledne dam do Arrayu rozlozenie
    private final ArrayList<Karta> karty = new ArrayList<>();
    private final String[] farby = {"red", "yellow", "blue", "green", "magenta", "black"};
    //dvojite pole podla ktoreho viem presne urcit kartu na presnej pozicii
    private final Karta[][] rozlozenie;
    //prva otocena karta hraca
    private Karta prvaKarta = null;
    //druha otocena karta hraca
    private Karta druhaKarta = null;
    private StavHry stavHry;
    /**
     * Vytvorí manažéra a dá mu spravovať hru
     * zapíše počet hráčov do poľa
     * kontroluje, či počet kariet nie je nepárny, alebo väčší ako 196(max počet kariet, ktoré sa zmestia na plochu)
     * pýtame sa na mená hráčov
     * Vytvorí dvojité pole 14x14 což je maximálny počet kariet, ktoré sa zmestia na plochu
     * prechádzam for-om toľkokrát, koľko je polovica zadanych kariet(lebo chcem páry) a pre každé otočenie vytvorím 2 rovnaké karty
     * s rovnakou náhodnou farbou a rovnakým náhodným typom z enumu.
     * Následne karty zamiešam a vykreslím
     * @param pocetHracov určuje počet hráčov v hre
     * @param pocetKariet určuje počet kariet v hre
     */
    public Hra(int pocetHracov, int pocetKariet) {
        this.stavHry = StavHry.CAKANIE_NA_PRVU_KARTU;
        Manazer manazer = new Manazer();
        manazer.spravujObjekt(this);
        this.pocetKariet = pocetKariet;
        this.pocetHracov = pocetHracov;
        //vytvori array s poctom zadanych hracov
        this.hraci = new Hrac[this.pocetHracov];
        this.menaHracov();
        //zacina 0. cize 1. hrac
        this.hracNaTahuIndex = 0;
        //vytvori array s presnym poctom riadkom a stlpcami
        this.rozlozenie = new Karta[14][14];
        // prechadzam enumom a pre kazdy typ vytvorim n rovnakych kariet s rovnakou nahodnou farbou
        for (int i = 0; i < this.pocetKariet / 2; i++) {
            String farba = this.getRandomFarbu();
            int nahoda = new Random().nextInt(TypKarty.values().length);
            for (int j = 0; j < 2; j++) {
                this.karty.add(new Karta(TypKarty.values()[nahoda], farba));
            }
        }
        this.zamiesaj();
        this.vykresli();
    }

    /**
     * Otáča nám karty na základe kliknutia myšou
     * na základe x a y si vypočítam, na ktorý riadok a stĺpec klikám
     * podľa enumu StavHry zisťujeme, ktorá karta je otočená
     * ak sa čaká na prvú, tak ju otočím, vypočítam hráča na ťahu a stav sa prepína na čakanie na druhú
     * ak sa čaká na druhu, tak kontrolujem či to nie je tá istá ako prvá, otáčam druhú a prepínam stav na ukazovanie dvojice
     * ak je stav ukazovanie dvojice, tak zisťujem, či sú karty rovnaké, ak áno, vymažem ich, pridám hráčovi bod a ide znova
     * ak nie sú, tak ich otočím a ide ďalší
     * ak bol na rade posledný hráč, vynulujem index hráčov a ide prvý hráč
     * @param x určuje x-ovú pozíciu kliknutia myši
     * @param y určuje y-ovú pozíciu kliknutia myši
     *
     */
    public void vyberSuradnice(int x, int y)  {
        int stlpec = x / (this.karty.get(0).getVelkostKarty() + MEDZERA_MEDZI_KARTAMI) + 1;
        int riadok = 1 + y / (this.karty.get(0).getVelkostKarty() + MEDZERA_MEDZI_KARTAMI);
        try {
            switch (this.stavHry) {
                case CAKANIE_NA_PRVU_KARTU -> {
                    this.prvaKarta = this.rozlozenie[riadok - 1][stlpec - 1];
                    this.prvaKarta.otocSa();
                    this.hracNaTahu();
                    this.stavHry = StavHry.CAKANIE_NA_DRUHU_KARTU;
                }
                case CAKANIE_NA_DRUHU_KARTU -> {
                    this.druhaKarta = this.rozlozenie[riadok - 1][stlpec - 1];
                    if (this.prvaKarta == this.druhaKarta) {
                        System.out.println("Nemozes si vybrat 2x tu istu kartu. Vyberaj znova.");
                        this.rozlozenie[riadok - 1][stlpec - 1].otocSa();
                        this.hracNaTahu();
                        return;
                    }
                    //otocim kartu so zadanymi suradnicami
                    this.druhaKarta.otocSa();
                    this.stavHry = StavHry.UKAZOVANIE_DVOJICE;
                }
                case UKAZOVANIE_DVOJICE -> {
                    if (this.jeKartaRovnaka()) {
                        this.hraci[this.hracNaTahuIndex].pridajBod();
                        this.vymazTvar();
                        this.hracNaTahu();
                        //ak uz nie su ziadne karty na platne, hra skoncila a vypise sa vitaz
                        if (this.kontrolaHry()) {
                            System.out.println("------------------------------------------------");
                            System.out.printf("Hra skoncila! \nVyhrava hrac %s", this.najdiNajviacBodovMeno());
                            this.statistikaVyhercu();
                            System.out.println("------------------------------------------------");
                            this.zrus();
                        }
                    } else {
                        this.prvaKarta.otocSa();
                        this.druhaKarta.otocSa();
                        //ak indexHracaNaTahu sa rovna poctu hracov, tak ho vynulujem a tym padom idu hraci odznova
                        if (this.hracNaTahuIndex != this.pocetHracov) {
                            this.hracNaTahuIndex += 1;
                            this.hracNaTahu();
                        } else {
                            this.hracNaTahuIndex = 0;
                            this.hracNaTahu();
                        }
                    }
                    this.stavHry = StavHry.CAKANIE_NA_PRVU_KARTU;
                }
            }
        }   catch (Exception e) {
            System.out.println("Nespravne zadana hodnota!\nSkus znova.");
        }
    }
    /**
     * Vráti true ak sa prvá karta rovnej druhej
     * return this.prvaKarta.jeRovnaka(this.prvaKarta, this.druhaKarta) vráti true ak sa prvá karta rovnej druhej
     */
    private boolean jeKartaRovnaka() {
        return this.prvaKarta.jeRovnaka(this.prvaKarta, this.druhaKarta);
    }

    /**
     * Pomocou Collections.Shuffle zamiešam poradie kariet v Arrayliste
     * následne prechádzam kartami v arrayliste
     * pripočítavam si počítadlo stĺpec
     * keď sa bude rovnať 13, tak pridám 1 pocitadloRiadok
     * tieto premenné slúžia ako súradnice, pomocou ktorých vkladám karty na dané pozície v dvojitom poli
     * ak sa počítadlo rovná počtu kariet, tak sa for stopne a tým pádom mám všetky karty uložené
     */
    private void zamiesaj() {
        //zamiesam karty v arrayliste
        Collections.shuffle(this.karty);
        int pocitadlo = 0;
        int pocitadloRiadok = 0;
        int pocitadloStlpec = 0;
        // prechadzam kazdu kartu v arrayliste karty a aktualnu kartu vkladam do dvojrozmerneho podla so suradnicami pocitadloRiadok
        // a pocitadloStlpec. Ak sa pocitadloStlpec rovna poctu stlpcov 
        //tak ho musim vynulovat a pridat jeden riadok, aby sme docielili maticu this.riadky, this.stlpce
        for (Karta aktualna : this.karty) {
            this.rozlozenie[pocitadloRiadok][pocitadloStlpec] = aktualna;
            if (pocitadlo == this.pocetKariet) {
                break;
            }
            if (pocitadloStlpec == 13) {
                pocitadloStlpec = 0;
                pocitadloRiadok++;
            } else {
                pocitadloStlpec++;
            }
            pocitadlo++;
        }
    }
    
    /**
     * Vykresli nám karty na plátno
     * prechádzam 2x for-om a tým pádom aj dvojitým poľom a vykreslím všetky karty v ňom
     * karty rozložím tak, že kartu posuniem vodorovne na základe veľkosti a medzery kartami riadkami a daným stĺpcom
     * následne ich posúvam zvisle na základe veľkosti karty a medzery medzi kartami a daným riadkom
     * ak sa pocitadlo rovná počtu kariet, for prestane a tým pádom mám všetky karty vykreslené
     */
    private void vykresli() {
        int pocitadlo = 0;
        for (int riadok = 0; riadok < 14; riadok++ ) {
            for (int stlpec = 0; stlpec < 14; stlpec++) {
                // nasobim to riadkom alebo stlpcom a to znamena ze presny riadok a presny stlpec do ktoreho to vykreslujem
                if (pocitadlo == this.pocetKariet) {
                    break;
                }
                this.rozlozenie[riadok][stlpec].posunVodorovne(( this.rozlozenie[riadok][stlpec].getVelkostKarty() + MEDZERA_MEDZI_KARTAMI) * stlpec + MEDZERA_MEDZI_KARTAMI);
                this.rozlozenie[riadok][stlpec].posunZvisle(((this.rozlozenie[riadok][stlpec].getVelkostKarty() + MEDZERA_MEDZI_KARTAMI)) * riadok + MEDZERA_MEDZI_KARTAMI);
                this.rozlozenie[riadok][stlpec].vykresliSa();
                pocitadlo++;
            }
        }
    }
    
    /**
     * Vráti mi náhodnú farbu na základe náhodného čísla, pomocou ktorého si z arrayu vytiahnem farbu
     * @return farby[nahoda] vráti mi farbu na pozícii náhodného čísla v poli
     */
    private String getRandomFarbu() {
        //vyberam nahodne cislo a podla cisla vyberiem nahodnu farbu z arrayu
        int nahoda = new Random().nextInt(this.farby.length);
        return this.farby[nahoda];
    }
    
    /**
     * Zadávame meno pre každého hráča
     * kontrolujeme či dané meno už nebolo raz zadané
     */
    private void menaHracov() {
        //scanner je input pre uzivatela, pocitadlo jednoducho pocita kolkokrat cyklus while prebehol
        Scanner vstup = new Scanner(System.in);
        int pocitadlo = 0;
        //while cyklus prebieha dotvtedy, dokedy je pocitadlo mensie ako pocet hracov a stale si vypita meno hraca a priradi ho do array hraci
        while (pocitadlo < this.pocetHracov) {
            System.out.println("---------------------");
            System.out.printf("Zadaj meno %d. hraca. ", pocitadlo + 1);
            System.out.println("\n---------------------");
            String meno = vstup.nextLine();
            if (pocitadlo >= 1) {
                for (Hrac aktualny : this.hraci) {
                    if (aktualny != null) {
                        if (aktualny.getMenoHraca().equals(meno)) {
                            System.out.println("---------------------------------");
                            System.out.println("Hrac s takymto menom uz existuje.");
                            System.out.println("---------------------------------");
                            break;
                        }
                    } else {
                        this.hraci[pocitadlo] = new Hrac(meno);
                        pocitadlo++;
                        break;
                    }
                }
            } else {
                this.hraci[pocitadlo] = new Hrac(meno);
                pocitadlo++;
            }   
        }
        //vypise po vypisani vsetkych mien meno prveho hraca na tahu
        this.hracNaTahu();
    }
    
    /**
     * Určuje, ktorý hráč je na ťahu na základe indexu
     */
    private void hracNaTahu() {
        //ak sa index nerovna poctu hracov, vypise ktory hrac je na rade a vrati meno daneho hraca s danym indexom
        if (this.hracNaTahuIndex != this.pocetHracov) {
            System.out.printf("Na tahu je hrac %s\n", this.hraci[this.hracNaTahuIndex].getMenoHraca());
        } else {
            //ak nie, vynuluje index a tym padom idu hraci odznova
            this.hracNaTahuIndex = 0;
            System.out.printf("Na tahu je hrac %s\n", this.hraci[this.hracNaTahuIndex].getMenoHraca());
        }
    }
    
    /**
     * Prechádzame dvojitým poľom a voláme metódu vymazTvar a zadávame mu farbu bielu
     * vymazávame referenciu na kartu v pamäti tak, že ju dáme, ako nula a následne ju garbage collector vymaže
     */
    private void vymazTvar() {
        //metoda na vymazanie tvaru, prechadza dvojrozmernym polom a hlada ci sa na danej pozicii nachadza prva alebo druha karta
        //ak ano, tak sa spusti metoda prvej alebo druhej karty vymazTvar(farba) a danu kartu v poli nahradim nullom, tym padom
        //prerusim referenciu na objekt v pamati a garbage collector ho vymaze, tym padom kartu nevieme znovu prevratit
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                if (this.rozlozenie[i][j] == this.prvaKarta) {
                    this.rozlozenie[i][j].vymazTvar("white");
                    this.rozlozenie[i][j] = null;
                }
                if (this.rozlozenie[i][j] == this.druhaKarta) {
                    this.rozlozenie[i][j].vymazTvar("white");
                    this.rozlozenie[i][j] = null;
                }
            }
        }
    }
    
    /**
     * Vymaže všetky karty
     */
    public void vymazVsetkyKarty() {
        int pocitadlo = 0;
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                if (pocitadlo == this.pocetKariet) {
                    break;
                }
                this.rozlozenie[i][j].vymazTvar("white");
                this.rozlozenie[i][j] = null;
                pocitadlo++;
            }
        }
    }
    
    /**
     * Kontroluje, či sa na plátne ešte nachádzajú nejaké karty
     * ak nie, vráti true ak sa počítadlo, ktoré počíta koľko kariet má referenciu null, rovná 196
     * @return pocitadlo vráti true ak je počítadlo rovné 196, false ak nie
     */
    private boolean kontrolaHry() {
        //kontroluje ci sa v poli nachadzaju karty, vrati true alebo false
        int pocitadlo = 0;
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                if (this.rozlozenie[i][j] == null) {
                    pocitadlo += 1;
                }
            }
        }
        return pocitadlo == 196;
    }
    
    /**
     * Prechádza vsekými hráčmi a porovnáva ich body a mená, na konci vráti meno hráča, ktorý ma najviac bodov
     * @return menoNajviacBodov vráti meno hráča, ktorý ma najviac bodov
     */
    private String najdiNajviacBodovMeno() {
        //najde hraca s najviac bodmi
        int najviacBodov = 0;
        int body;
        String meno;
        String menoNajviacBodov = "";
        for (Hrac aktualny : this.hraci) {
            body = aktualny.getBody();
            meno = aktualny.getMenoHraca();
            if (body > najviacBodov) {
                najviacBodov = body;
                menoNajviacBodov = meno;
            }
        }
        return menoNajviacBodov;
    }
    
    /**
     * Nájde body najúspešnejšieho hráča na základe jeho mena
     * vypočíta koľko % kariet hráč uhádol
     */
    private void statistikaVyhercu() {
        String vyherca = this.najdiNajviacBodovMeno();
        int pocitadlo = 0;
        for (Hrac aktualny : this.hraci) {
            pocitadlo += 1;
            if (Objects.equals(aktualny.getMenoHraca(), this.najdiNajviacBodovMeno())) {
                break;
            }
        }
        double body = this.hraci[pocitadlo - 1].getBody();
        double percentaZoVsetkychDvojic = (body / (this.pocetKariet / 2)) * 100;
        System.out.printf("\nVyherca %s uhadol %f%% vsetkych parov\n", vyherca, percentaZoVsetkychDvojic);
    }
    
    /**
     * Zruší program
     */
    private void zrus() {
        System.exit(0);
    }
}