/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nezoter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javafx.print.Collation;

/**
 *
 * @author Andor Kovács
 */
public class Nezoter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        // 1. Olvassa be és tárolja el a foglaltsag.txt és a kategoria.txt fájl adatait!
        BufferedReader bufferedReader = new BufferedReader(new FileReader("foglaltsag.txt"));
        ArrayList<String> foglaltsag = new ArrayList<String>();
        String foglaltsagSor; // Miért kell beletenni változóba?

        while ((foglaltsagSor = bufferedReader.readLine()) != null) {
            foglaltsag.add(foglaltsagSor);
        }
        System.out.println("Foglaltaság: ");
        for (int i = 0; i < foglaltsag.size(); i++) {
            System.out.println(foglaltsag.get(i));
        }

        bufferedReader = new BufferedReader(new FileReader("kategoria.txt"));
        ArrayList<String> kategoria = new ArrayList<String>();
        String kategoriaSor; // Miért kell beletenni változóba?

        while ((kategoriaSor = bufferedReader.readLine()) != null) {
            kategoria.add(kategoriaSor);
        }
        System.out.println("Kategória: ");
        for (int i = 0; i < kategoria.size(); i++) {
            System.out.println(kategoria.get(i));
        }

        ArrayList<Szek> szekek = new ArrayList<>();
        int sor;
        int szek;

        for (int i = 0; i < foglaltsag.size(); i++) { // Bemegyünk a sorba
            sor = i + 1;
            for (int j = 0; j < foglaltsag.get(i).length(); j++) { // Bemegyünk a székbe
                szek = j + 1;
                boolean foglalt = foglaltsag.get(i).charAt(j) == 'x' ? true : false;
                szekek.add(new Szek(sor, szek, foglalt, 0, 0));
            }
        }
        sor = 0;
        szek = 0;

        for (int i = 0; i < kategoria.size(); i++) { // Bemegyek a sorba
            sor = i + 1;
            for (int j = 0; j < kategoria.get(i).length(); j++) {
                szek = j + 1;
                for (int k = 0; k < szekek.size(); k++) {
                    if (szekek.get(k).sor == sor && szekek.get(k).szek == szek) {
                        szekek.get(k).kategoria = Integer.parseInt(String.valueOf(kategoria.get(i).charAt(j)));
                        if (szekek.get(k).kategoria == 1) {
                            szekek.get(k).ar = 5000;
                        } else if (szekek.get(k).kategoria == 2) {
                            szekek.get(k).ar = 4000;
                        } else if (szekek.get(k).kategoria == 3) {
                            szekek.get(k).ar = 3000;
                        } else if (szekek.get(k).kategoria == 4) {
                            szekek.get(k).ar = 2000;
                        } else if (szekek.get(k).kategoria == 5) {
                            szekek.get(k).ar = 1500;
                        } else {
                            System.out.print("Hiba: 'A kategória nem esik 1 és 5 közé!'");
                        }
                    }
                }
            }
        }
        System.out.println("Székek: ");
        for (int i = 0; i < szekek.size(); i++) {
            System.out.println(
                    "Sor: " + szekek.get(i).sor
                    + " Szék: " + szekek.get(i).szek
                    + " Foglalt: " + szekek.get(i).foglalt
                    + " Kategória: " + szekek.get(i).kategoria
                    + " Ár: " + szekek.get(i).ar
            );
        }

        // 2. Kérje be a felhasználótól egy sor, és azon belül egy szék számát, majd írassa ki a képernyőre, hogy az adott hely még szabad-e vagy már foglalt! 
        System.out.println("2. feladat");
        int felhasznaloSor = Integer.parseInt(getInput("Kérem adjon meg egy sort: "));
        int felhasznaloSzek = Integer.parseInt(getInput("Kérem adjon meg egy széket: "));

        for (int i = 0; i < szekek.size(); i++) {
            if (szekek.get(i).sor == felhasznaloSor && szekek.get(i).szek == felhasznaloSzek) {
                if (szekek.get(i).foglalt) {
                    System.out.println("Az ön által megadott hely foglalt.");
                } else {
                    System.out.println("Az ön által megadott hely szabad.");
                }
            }
        }

        // 3. Határozza meg, hogy hány jegyet adtak el eddig, és ez a nézőtér befogadóképességének hány százaléka! A százalékértéket kerekítse egészre, és az eredményt a következő formában írassa ki a képernyőre: Például: Az előadásra eddig 156 jegyet adtak el, ez a nézőtér 42%-a. 
        System.out.println("3. feladat");
        int eladottHelyek = 0;
        int szekekSzama = szekek.size();
        for (int i = 0; i < szekek.size(); i++) {
            if (szekek.get(i).foglalt) {
                eladottHelyek++;
            }
        }

        double tmp = (double) eladottHelyek / szekekSzama;
        tmp *= 100;
        int eredmeny = (int) tmp;
        System.out.println("Az előadásra eddig " + eladottHelyek + " jegyet adtak el, ez a nézőtér " + eredmeny + "%-a.");

        // 4. Határozza meg, hogy melyik árkategóriában adták el a legtöbb jegyet! Az eredményt írassa ki a képernyőre az alábbi formában: Például: A legtöbb jegyet a(z) 3. árkategóriában értékesítették. 
        System.out.println("4. feladat");
        int kategoriabanEladottJegyek1 = 0;
        int kategoriabanEladottJegyek2 = 0;
        int kategoriabanEladottJegyek3 = 0;
        int kategoriabanEladottJegyek4 = 0;
        int kategoriabanEladottJegyek5 = 0;

        for (int i = 0; i < szekek.size(); i++) {
            if (szekek.get(i).foglalt) {
                if (szekek.get(i).kategoria == 1) {
                    kategoriabanEladottJegyek1++;
                } else if (szekek.get(i).kategoria == 2) {
                    kategoriabanEladottJegyek2++;
                } else if (szekek.get(i).kategoria == 3) {
                    kategoriabanEladottJegyek3++;
                } else if (szekek.get(i).kategoria == 4) {
                    kategoriabanEladottJegyek4++;
                } else if (szekek.get(i).kategoria == 5) {
                    kategoriabanEladottJegyek5++;
                } else {
                    System.out.println("ERROR");
                }
            }
        }

        int legtobbEladottJegyKategoriankent = Math.max(kategoriabanEladottJegyek1, Math.max(kategoriabanEladottJegyek2, Math.max(kategoriabanEladottJegyek3, Math.max(kategoriabanEladottJegyek4, kategoriabanEladottJegyek5))));
        
        ArrayList<Integer> kategoriak = new ArrayList<>();
        kategoriak.add(kategoriabanEladottJegyek1);
        kategoriak.add(kategoriabanEladottJegyek2);
        kategoriak.add(kategoriabanEladottJegyek3);
        kategoriak.add(kategoriabanEladottJegyek4);
        kategoriak.add(kategoriabanEladottJegyek5);
        int legmenobbKategoria = kategoriak.indexOf(legtobbEladottJegyKategoriankent) + 1;
        
        System.out.println("A legtöbb jegyet a(z) "+ legmenobbKategoria + ". árkategóriában értékesítették.");
        
        // 5. A jegyek árát kategóriánként a következő táblázat tartalmazza: árkategória 1 2 3 4 5 ár (Ft) 5000 4000 3000 2000 1500 Mennyi lenne a színház bevétele a pillanatnyilag eladott jegyek alapján? Írassa ki az eredményt a képernyőre! 
        System.out.println("5. feladat");
        int reszosszeg1 = kategoriabanEladottJegyek1 * 5000;
        int reszosszeg2 = kategoriabanEladottJegyek2 * 4000;
        int reszosszeg3 = kategoriabanEladottJegyek3 * 3000;
        int reszosszeg4 = kategoriabanEladottJegyek4 * 2000;
        int reszosszeg5 = kategoriabanEladottJegyek5 * 1500;
        System.out.println((
                        reszosszeg1 +
                        reszosszeg2 +
                        reszosszeg3 +
                        reszosszeg4 +
                        reszosszeg5 
                        ) + " Ft");
        
        
        // 6. Mivel az emberek általában nem egyedül mennek színházba, ha egy üres hely mellett nincs egy másik üres hely is, akkor azt nehezebben lehet értékesíteni. Határozza meg, és írassa ki a képernyőre, hogy hány ilyen „egyedülálló” üres hely van a nézőtéren!
        System.out.println("6. feladat");
        
        int egyedulalloHelyekSzama = 0;
        
        int sor6 = 0;
        for (int i = 0; i < szekek.size(); i++) {
            if (sor6 != szekek.get(i).sor) {
                sor6++;
            } 
            if (!szekek.get(i).foglalt) {
//                if (szekek.get(i).szek != 1 || szekek.get(i).szek != 20) {
                    if(szekek.get(i-1).foglalt && szekek.get(i+1).foglalt) {
                        egyedulalloHelyekSzama++;
                    }
                } //else if (szekek.get(i).szek == 1) {
//                    if(szekek.get(i+1).foglalt) {
//                        egyedulalloHelyekSzama++;
//                    }
//                } else if (szekek.get(i).szek == 20) {
//                    if(szekek.get(i-1).foglalt) {
//                        egyedulalloHelyekSzama++;
//                    }
//                } else {
//                    System.out.println("ERROR");
//                }
//                System.out.print(sor6);
//            }
            
        }
        
        System.out.println("Egyedülálló helyek száma: " + egyedulalloHelyekSzama);
        // 7. A színház elektronikus eladási rendszere az érdeklődőknek az üres helyek esetén a hely árkategóriáját jeleníti meg, míg a foglalt helyeket csak egy „x” karakterrel jelzi. Készítse el ennek megfelelően a fenti adatokat tartalmazó szabad.txt fájlt! 
        System.out.println("7. feladat");
        PrintWriter output = new PrintWriter(new FileWriter("szabad.txt"));
        int sor7 = 0;
        for (int i = 0; i < szekek.size(); i++) {
            if (szekek.get(i).sor == sor7) {
                output.print(szekek.get(i).foglalt ? "x" : szekek.get(i).kategoria);
            } else {
                sor7++;
                output.println();
                output.print(szekek.get(i).foglalt ? "x" : szekek.get(i).kategoria);
            } 
        }
        output.close();
        System.out.println("A \"szabad.txt\" fákl elkészítve.");
    }

    private static String getInput(String prompt) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.print(prompt);
        System.out.flush();

        try {
            return stdin.readLine();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private static class Szek {

        int sor;
        int szek;
        boolean foglalt = false;
        int kategoria;
        int ar;

        public Szek(int sor, int szek, boolean foglalt, int kategoria, int ar) {
            this.sor = sor;
            this.szek = szek;
            this.foglalt = foglalt;
            this.kategoria = kategoria;
            this.ar = ar;
        }
    }

}
