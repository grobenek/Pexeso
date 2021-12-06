import java.util.Scanner;
/**
 * Menu, ktoré nás sprevádza hrou
 * vieme si vytvoriť novú hru
 * vieme si vytvoriť novú hru počas rozohratej hry
 * vieme ukončiť hru
 * 
 * @author Peter Szathmary 
 * @version 2.1.2021
 */
public class Menu {
    private static Menu instancia;
    private int pocetHracov;
    private int pocetKariet;
    private int volba;
    private int spinac;
    private final Scanner vstup;
    private Hra hra;
    /**
     * Vytvoríme si scanner, ktorý bude čítať vstup hráča
     * spínač si dáme na 1, čiže menu bude bežať
     * spustíme menu
     */
    private Menu() {
        this.vstup = new Scanner(System.in);
        this.spinac = 1;
        this.startMenu();
    }
    
    /**
     * Vytvorí nám jedináčika Menu, čiže nemôžeme mať naraz 2 menu
     */
    public static void noveMenu() {
        if (Menu.instancia == null) {
            Menu.instancia = new Menu();
        }

    }
    
    /**
     * Vytvorí sa menu ešte pred vytvorením hry
     * privíta nás a dá nám na výber:
     * vytvorenie hry, pozretie si pravidiel, ukončenie hry
     * kontroluje, či sme zadali číslo, ak nie, tak nás upozorní
     * a vstup zadávame znovu
     * kontroluje, či zadávame vstup len v rozmedzí možností
     * ak vytvárame hru, tak zadávame počet hráčov, ktorý je kontrolovaný
     * zadávame počet kariet, ktoré v hre chceme mať, vstup je tiež kontrolovaný
     */
    private void startMenu() {
        while (this.spinac == 1) {
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Vitaj v hre Pexeso od Petra Szathmáryho! Práve sa nachádzaš v menu.");
            System.out.println("Nová hra (1)\nPravidlá (2)\nUkončiť hru (3)");
            System.out.println("--------------------------------------------------------------------");
            if (this.vstup.hasNextInt()) {
                this.volba = this.vstup.nextInt();
            } else {
                System.out.println("--------------------------------");
                System.out.println("Musíš zadať číslo\nSkús to znova");
                System.out.println("--------------------------------");
                this.vstup.next();
                this.startMenu();
            }
            if (this.volba < 1 || this.volba > 3) {
                System.out.println("---------------------------------------");
                System.out.println("Nesprávne zadaná hodnota\nSkús to znova");
                System.out.println("---------------------------------------");
                this.startMenu();
                this.vstup.next();
            } else {
                switch (this.volba) {
                    case 1 -> {
                        System.out.println("-----------------------");
                        System.out.println("Koľko hráčov bude hrať?");
                        System.out.println("-----------------------");
                        try {
                            this.pocetHracov = this.vstup.nextInt();
                        } catch (Exception e) {
                            System.out.println("--------------------------------");
                            System.out.println("Musíš zadať číslo\nSkús to znova");
                            System.out.println("--------------------------------");
                            this.volba = 1;
                            this.startMenu();
                        }
                        if (this.pocetHracov <= 0) {
                            System.out.println("-----------------------");
                            System.out.println("Nesprávny počet hráčov\nSkús to znova");
                            System.out.println("-----------------------");
                            this.volba = 1;
                            this.startMenu();
                        } else {
                            System.out.println("---------------------------------");
                            System.out.println("Koľko kariet má pexeso obsahovať?");
                            System.out.println("---------------------------------");
                            try {
                                this.pocetKariet = this.vstup.nextInt();
                            } catch (Exception e) {
                                System.out.println("--------------------------------");
                                System.out.println("Musíš zadať číslo\nSkús to znova");
                                System.out.println("--------------------------------");
                                this.volba = 1;
                                this.startMenu();
                            }
                            if (this.pocetKariet <= 0) {
                                System.out.println("-------------------------------------");
                                System.out.println("Počet kariet musí byť väčší ako 0\nSkús to znova");
                                System.out.println("-------------------------------------");
                                this.volba = 1;
                                this.startMenu();
                            } else if (this.pocetKariet % 2 != 0) {
                                System.out.println("------------------------------------------");
                                System.out.println("Počet kariet musí byť párny\nSkús to znova");
                                System.out.println("------------------------------------------");
                                this.volba = 1;
                                this.startMenu();
                            } else if (this.pocetKariet > 196) {
                                System.out.println("-----------------------------------");
                                System.out.println("Počet kariet musí byť menší ako 196\nSkús to znova");
                                System.out.println("-----------------------------------");
                                this.volba = 1;
                                this.startMenu();
                            } else {
                                this.spinac *= -1;
                                this.novaHra(this.pocetHracov, this.pocetKariet);
                            }
                        }
                    }
                    case 2 -> {
                        System.out.println("Balíček obsahuje dvojice kariet, ktoré rozložíte zadnou stranou nahor.");
                        System.out.println("Prvý hráč obráti ľubovoľné dve karty.");
                        System.out.println("Ak je to rovnaká dvojica, odloží si ich nabok a môže obrátiť ďalšie dve kartičky.");
                        System.out.println("Ak nie sú rovnaké, obrátia sa naspäť a pokračuje ďalší hráč.");
                        this.startMenu();
                    }
                    case 3 -> {
                        System.out.println("-------------");
                        System.out.println("Hra ukončená!");
                        System.out.println("-------------");
                        this.zrus();
                    }
                    default -> {
                        System.out.println("---------------------------------");
                        System.out.println("Zle zadaná možnosť\nSkús to znova");
                        System.out.println("---------------------------------");
                        this.vstup.next();
                        this.startMenu();
                    }
                }
            }
        }
    }
    
    /**
     * Vytvorí novuú hru a vytvorí menu v hre
     * @param pocetHracov udáva počet hráčov v hre
     * @param pocetKariet udáva počet kariet v hre
     */
    private void novaHra(int pocetHracov, int pocetKariet) {
        this.hra = new Hra(pocetHracov, pocetKariet);
        this.spinac *= -1;
        this.menuVHre();
    }
    
    /**
     * Vytvorí menu, ktoré beží počas hry
     * všetky vstupy sú kontrolované
     * umožňuje nám vytvoriť novú hru, vytvorenie musíme potvrdiť
     * doprevádza nás počas hry, vysvetľuje ovládanie
     * vieme hru ukončiť
     */
    private void menuVHre() {
        while (this.spinac == 1) {
            System.out.println("----------------------------");
            System.out.println("Kliknutím na kartu ju otočíš");
            System.out.println("Nová hra (1)");
            System.out.println("Ukončiť hru (2)");
            System.out.println("----------------------------");
            if (this.vstup.hasNextInt()) {
                this.volba = this.vstup.nextInt();
            } else {
                System.out.println("--------------------------------");
                System.out.println("Musíš zadať číslo\nSkús to znova");
                System.out.println("--------------------------------");
                this.vstup.next();
                this.menuVHre();
            }
            switch (this.volba) {
                case 1 -> {
                    System.out.println("-------------------------------------------------------------------");
                    System.out.println("Ozaj chceš vytvoriť novú hru? Práve prebiehajúca hra bude stratená!\nÁno (1)\nNie (2)");
                    System.out.println("-------------------------------------------------------------------");
                    if (this.vstup.hasNextInt()) {
                        this.volba = this.vstup.nextInt();
                    } else {
                        System.out.println("--------------------------------");
                        System.out.println("Musíš zadať číslo\nSkús to znova");
                        System.out.println("--------------------------------");
                        this.vstup.next();
                        this.volba = 2;
                        this.menuVHre();
                    }
                    switch (this.volba) {
                        case 1 -> {
                            this.hra.vymazVsetkyKarty();
                            this.hra = null;
                            this.startMenu();
                        }
                        case 2 -> this.menuVHre();
                    }
                }
                case 2 -> {
                    System.out.println("----------------------------------------------------------------------");
                    System.out.println("Ozaj chceš vytvoriť novú hru? Práve prebiehajúca hra bude stratená!\nÁno (1)\nNie (2)");
                    System.out.println("----------------------------------------------------------------------");
                    if (this.vstup.hasNextInt()) {
                        this.volba = this.vstup.nextInt();
                    } else {
                        System.out.println("--------------------------------");
                        System.out.println("Musíš zadať číslo\nSkús to znova");
                        System.out.println("--------------------------------");
                        this.vstup.next();
                        this.volba = 2;
                        this.menuVHre();
                    }
                    switch (this.volba) {
                        case 1 -> {
                            System.out.println("-------------");
                            System.out.println("Hra ukončená!");
                            System.out.println("-------------");
                            this.zrus();
                        }
                        case 2 -> this.menuVHre();
                    }
                }
            }
        }
    }
    
    /**
     * Ukončí program
     */
    private void zrus() {
        System.exit(0);
    }
}