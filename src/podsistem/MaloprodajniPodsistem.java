/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package podsistem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import komponenta.Komponenta;

/**
 * Podsistem namenjen maloprodajnim objektima.
 * 
 * Ključna karakteristika:
 * Ovaj podsistem radi ISKLJUČIVO sa objektima koji implementiraju interfejs Komponenta.
 * Nema nikakve zavisnosti od paketa ili interfejsa servisnog podsistema (Usluga).
 * 
 * Zahvaljujući Adapter uzoru (UslugaAdapter), u ovaj podsistem se potpuno ravnopravno
 * mogu uneti i usluge instalacije bez menjanja ijedne linije ovog postojećeg koda!
 * 
 * @author student
 */
public class MaloprodajniPodsistem {

    private final String nazivObjekta;
    private final List<Komponenta> prodateStavke;

    public MaloprodajniPodsistem() {
        this("Maloprodaja računarske opreme - Objekat 1");
    }

    public MaloprodajniPodsistem(String nazivObjekta) {
        this.nazivObjekta = nazivObjekta;
        this.prodateStavke = new ArrayList<>();
    }

    /**
     * Dodaje prodatu komponentu u evidenciju maloprodaje.
     * 
     * @param komponenta instanca ciljnog interfejsa Komponenta
     */
    public void dodajProdatuKomponentu(Komponenta komponenta) {
        if (komponenta == null) {
            throw new IllegalArgumentException("Komponenta ne može biti null.");
        }
        prodateStavke.add(komponenta);
    }

    /**
     * Uklanja stavku sa date pozicije u evidenciji.
     * 
     * @param index indeks stavke
     * @return uklonjena komponenta
     */
    public Komponenta ukloniStavku(int index) {
        if (index >= 0 && index < prodateStavke.size()) {
            return prodateStavke.remove(index);
        }
        return null;
    }

    /**
     * Čisti celu evidenciju prodatih stavki.
     */
    public void isprazniEvidenciju() {
        prodateStavke.clear();
    }

    /**
     * Vraća nepromenljivu listu svih prodatih stavki.
     * 
     * @return lista prodatih stavki
     */
    public List<Komponenta> getProdateStavke() {
        return Collections.unmodifiableList(prodateStavke);
    }

    /**
     * Vraća ukupan broj prodatih stavki.
     * 
     * @return broj stavki
     */
    public int getBrojStavki() {
        return prodateStavke.size();
    }

    /**
     * Izračunava ukupnu prodajnu vrednost svih prodatih stavki u maloprodaji.
     * 
     * @return ukupna prodajna vrednost
     */
    public double izracunajUkupnuProdajnuCijenu() {
        double ukupno = 0.0;
        for (Komponenta k : prodateStavke) {
            ukupno += k.dajProdajnuCijenu();
        }
        return ukupno;
    }

    /**
     * Kreira zvanični tekstualni izveštaj koji prikazuje sve prodate komponente
     * sa njihovim pojedinačnim specifikacijama, dobavljačima i prodajnim cenama,
     * kao i ukupnu zbirnu sumu maloprodaje.
     * 
     * @return formatiran tekst izveštaja
     */
    public String generisiIzvjestaj() {
        return kreirajIzvjestaj();
    }

    public String kreirajIzvjestaj() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
        String datumVreme = LocalDateTime.now().format(dtf);

        sb.append("========================================================================================\n");
        sb.append(String.format("                       IZVEŠTAJ O PRODATIM STAVKAMA MALOPRODAJE\n"));
        sb.append(String.format("                              %s\n", nazivObjekta));
        sb.append(String.format("                             Vreme kreiranja: %s\n", datumVreme));
        sb.append("========================================================================================\n");
        sb.append(String.format("%-4s | %-45s | %-20s | %12s\n", "R.br", "Specifikacija / Opis", "Dobavljač / Izvršilac", "Cena (RSD)"));
        sb.append("----------------------------------------------------------------------------------------\n");

        if (prodateStavke.isEmpty()) {
            sb.append("                 Nema evidentiranih prodatih stavki u ovom periodu.\n");
        } else {
            for (int i = 0; i < prodateStavke.size(); i++) {
                Komponenta k = prodateStavke.get(i);
                
                String spec = k.dajSpecifikaciju();
                if (spec.length() > 45) {
                    spec = spec.substring(0, 42) + "...";
                }
                
                String dob = k.dajDobavljaca();
                if (dob.length() > 20) {
                    dob = dob.substring(0, 17) + "...";
                }

                sb.append(String.format("%-4d | %-45s | %-20s | %12.2f\n", 
                        (i + 1), spec, dob, k.dajProdajnuCijenu()));
            }
        }

        sb.append("----------------------------------------------------------------------------------------\n");
        sb.append(String.format("UKUPAN BROJ STAVKI: %d\n", prodateStavke.size()));
        sb.append(String.format("UKUPNA MALOPRODAJNA CENA (SA PDV-om): %.2f RSD\n", izracunajUkupnuProdajnuCijenu()));
        sb.append("========================================================================================\n");

        return sb.toString();
    }

    public String getNazivObjekta() {
        return nazivObjekta;
    }
}
