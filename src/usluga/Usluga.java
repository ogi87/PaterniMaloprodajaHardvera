/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package usluga;

/**
 * Prilagođavani interfejs (Adaptee) u okviru podsistema servisne službe.
 * 
 * Servisna služba se bavi uslugama instalacije, sklapanja i održavanja računara.
 * Sve usluge predstavljene su ovim interfejsom.
 * 
 * @author student
 */
public interface Usluga {

    /**
     * Vraća osnovnu cenu usluge bez uračunatog poreza na dodatu vrednost (PDV).
     * 
     * @return cena bez PDV-a
     */
    double dajCijenuBezPDVa();

    /**
     * Vraća stopu ili vrednost PDV-a za datu uslugu (npr. 20.0 za 20% ili 0.20).
     * 
     * @return stopa PDV-a
     */
    double dajPDV();

    /**
     * Vraća detaljan opis servisne aktivnosti koja se realizuje.
     * 
     * @return tekstualni opis aktivnosti
     */
    String dajOpisAktivnosti();

    /**
     * Vraća ime i prezime ili oznaku servisera zaduženog za realizaciju usluge.
     * 
     * @return serviser
     */
    String dajServisera();
}
