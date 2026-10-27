package adapter;

import komponenta.HardverskaKomponenta;
import podsistem.MaloprodajniPodsistem;
import usluga.InstalacionaUsluga;
import usluga.Usluga;

/**
 * Jednostavan test za proveru ispravnosti Adapter uzora i Maloprodajnog podsistema.
 */
public class AdapterTest {

    public static void main(String[] args) {
        System.out.println("=== TESTIRANJE ADAPTER PROJEKTNOG UZORA ===");

        // 1. Kreiranje hardverske komponente
        HardverskaKomponenta procesor = new HardverskaKomponenta(
                "Intel i9-14900K", 
                "24 jezgra, do 6.0GHz", 
                "Alti d.o.o.", 
                65000.00
        );

        // 2. Kreiranje servisne usluge
        Usluga uslugaInstalacije = new InstalacionaUsluga(
                "Instalacija i testiranje", 
                "Instalacija Windows 11 i Prime95 stress test", 
                "Marko Petrović", 
                4000.00, 
                20.0 // 20% PDV
        );

        // 3. Adaptiranje usluge na interfejs Komponenta
        UslugaAdapter adapter = new UslugaAdapter(uslugaInstalacije);

        // Provera kalkulacije cene sa PDV-om: 4000 * 1.20 = 4800 RSD
        double ocekivanaCena = 4800.00;
        double stvarnaCena = adapter.dajProdajnuCijenu();
        if (Math.abs(ocekivanaCena - stvarnaCena) < 0.001) {
            System.out.println("[PASS] Adapter ispravno računa prodajnu cenu sa PDV-om: " + stvarnaCena + " RSD");
        } else {
            System.err.println("[FAIL] Očekivana cena " + ocekivanaCena + ", ali je dobijeno " + stvarnaCena);
        }

        // Provera specifikacije
        if (adapter.dajSpecifikaciju().contains("Instalacija Windows 11")) {
            System.out.println("[PASS] Adapter ispravno mapira opis aktivnosti u specifikaciju.");
        } else {
            System.err.println("[FAIL] Specifikacija nije ispravno mapirana.");
        }

        // Provera dobavljača / servisera
        if (adapter.dajDobavljaca().contains("Marko Petrović")) {
            System.out.println("[PASS] Adapter ispravno mapira servisera u dobavljača.");
        } else {
            System.err.println("[FAIL] Serviser nije ispravno mapiran.");
        }

        // 4. Testiranje maloprodajnog podsistema
        MaloprodajniPodsistem podsistem = new MaloprodajniPodsistem("Test Shop");
        podsistem.dodajProdatuKomponentu(procesor);
        podsistem.dodajProdatuKomponentu(adapter);

        double ocekivanoUkupno = 65000.00 + 4800.00;
        double stvarnoUkupno = podsistem.izracunajUkupnuProdajnuCijenu();
        if (Math.abs(ocekivanoUkupno - stvarnoUkupno) < 0.001) {
            System.out.println("[PASS] Maloprodajni podsistem ispravno računa ukupnu sumu: " + stvarnoUkupno + " RSD");
        } else {
            System.err.println("[FAIL] Ukupna suma nije tačna.");
        }

        System.out.println("\nIspis generisanog izveštaja:");
        System.out.println(podsistem.generisiIzvjestaj());
        System.out.println("=== TEST USPEŠNO ZAVRŠEN ===");
    }
}
