/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usluga;

/**
 * Konkretna implementacija prilagođavanog interfejsa Usluga.
 * Predstavlja uslugu servisne službe (npr. instalaciju operativnog sistema,
 * sklapanje konfiguracije, čišćenje i zamenu termalne paste itd.).
 * 
 * @author student
 */
public class InstalacionaUsluga implements Usluga {

    private String naziv;
    private String opisAktivnosti;
    private String serviser;
    private double cijenaBezPDVa;
    private double stopaPDVa; // npr. 20.0 ili 0.20

    public InstalacionaUsluga() {
        this.stopaPDVa = 20.0; // podrazumevani PDV 20%
    }

    public InstalacionaUsluga(String opisAktivnosti, String serviser, double cijenaBezPDVa, double stopaPDVa) {
        this("", opisAktivnosti, serviser, cijenaBezPDVa, stopaPDVa);
    }

    public InstalacionaUsluga(String naziv, String opisAktivnosti, String serviser, double cijenaBezPDVa, double stopaPDVa) {
        this.naziv = naziv;
        this.opisAktivnosti = opisAktivnosti;
        this.serviser = serviser;
        this.cijenaBezPDVa = cijenaBezPDVa;
        this.stopaPDVa = stopaPDVa;
    }

    @Override
    public double dajCijenuBezPDVa() {
        return cijenaBezPDVa;
    }

    @Override
    public double dajPDV() {
        return stopaPDVa;
    }

    @Override
    public String dajOpisAktivnosti() {
        return (naziv != null && !naziv.trim().isEmpty())
                ? naziv + ": " + opisAktivnosti
                : opisAktivnosti;
    }

    @Override
    public String dajServisera() {
        return serviser;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setOpisAktivnosti(String opisAktivnosti) {
        this.opisAktivnosti = opisAktivnosti;
    }

    public void setServiser(String serviser) {
        this.serviser = serviser;
    }

    public void setCijenaBezPDVa(double cijenaBezPDVa) {
        this.cijenaBezPDVa = cijenaBezPDVa;
    }

    public void setStopaPDVa(double stopaPDVa) {
        this.stopaPDVa = stopaPDVa;
    }

    @Override
    public String toString() {
        return "Usluga: " + dajOpisAktivnosti() + " | Serviser: " + dajServisera()
                + " | Cena bez PDV-a: " + String.format("%.2f", dajCijenuBezPDVa()) 
                + " RSD (PDV: " + stopaPDVa + "%)";
    }
}
