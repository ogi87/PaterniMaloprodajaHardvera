/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adapter;

import komponenta.Komponenta;
import usluga.Usluga;

/**
 * Adapter klasa koja omogućava prilagođavanje interfejsa Usluga na interfejs Komponenta.
 * 
 * PROJEKTNI UZOR: ADAPTER (Objektni adapter)
 * -------------------------------------------------------------
 * - Uloga: Omogućava da servisne usluge (Usluga) budu ravnopravno obrađene
 *   unutar postojećeg maloprodajnog podsistema koji operiše isključivo
 *   sa objektima tipa Komponenta.
 * - Ciljni interfejs (Target): Komponenta
 * - Prilagođavani interfejs (Adaptee): Usluga
 * - Adapter (Adapter): UslugaAdapter
 * 
 * Preslikavanje metoda:
 * 1. dajProdajnuCijenu()  ->  dajCijenuBezPDVa() uvećana za iznos PDV-a
 *                             (cenaSaPDV = cenaBezPDV * (1 + stopaPDV))
 * 2. dajSpecifikaciju()   ->  dajOpisAktivnosti() servisne usluge
 * 3. dajDobavljaca()      ->  dajServisera() koji realizuje uslugu
 * 
 * @author student
 */
public class UslugaAdapter implements Komponenta {

    /**
     * Referenca na prilagođavani objekat (Adaptee) kroz kompoziciju.
     */
    private final Usluga usluga;

    /**
     * Konstruktor koji prihvata instancu usluge koju adaptiramo.
     * 
     * @param usluga usluga instalacije iz servisnog podsistema
     */
    public UslugaAdapter(Usluga usluga) {
        if (usluga == null) {
            throw new IllegalArgumentException("Usluga ne može biti null prilikom kreiranja adaptera.");
        }
        this.usluga = usluga;
    }

    /**
     * Preslikava cenu bez PDV-a i stopu PDV-a u jedinstvenu prodajnu cenu
     * sa uračunatim porezom, kakvu očekuje maloprodajni podsistem.
     * 
     * Podržava stopu unetu kao procenat (npr. 20.0 za 20%) ili kao decimalni broj (npr. 0.20).
     */
    @Override
    public double dajProdajnuCijenu() {
        double cenaBezPdv = usluga.dajCijenuBezPDVa();
        double pdv = usluga.dajPDV();
        
        // Ako je stopa zadata u procentima (npr. 20), delimo sa 100
        double stopa = (pdv > 1.0) ? (pdv / 100.0) : pdv;
        
        return cenaBezPdv * (1.0 + stopa);
    }

    /**
     * Preslikava opis aktivnosti servisne usluge u specifikaciju stavke.
     */
    @Override
    public String dajSpecifikaciju() {
        return "[USLUGA INSTALACIJE] " + usluga.dajOpisAktivnosti();
    }

    /**
     * Preslikava servisera u dobavljača (izvršioca / pružaoca stavke).
     */
    @Override
    public String dajDobavljaca() {
        return "Serviser: " + usluga.dajServisera();
    }

    /**
     * Pomoćna metoda za dohvatanje originalnog adaptee objekta po potrebi.
     * 
     * @return originalna instanca Usluga
     */
    public Usluga getUsluga() {
        return usluga;
    }

    @Override
    public String toString() {
        return "Adapter -> " + dajSpecifikaciju() + " | Izvršilac: " + dajDobavljaca()
                + " | Konačna prodajna cena: " + String.format("%.2f", dajProdajnuCijenu()) + " RSD";
    }
}
