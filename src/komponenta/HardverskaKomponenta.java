/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komponenta;

/**
 * Konkretna implementacija ciljnog interfejsa Komponenta.
 * Predstavlja fizički hardverski artikal (npr. procesor, grafičku karticu, SSD memoriju itd.).
 * 
 * @author student
 */
public class HardverskaKomponenta implements Komponenta {

    private String naziv;
    private String specifikacija;
    private String dobavljac;
    private double prodajnaCijena;

    public HardverskaKomponenta() {
    }

    public HardverskaKomponenta(String specifikacija, String dobavljac, double prodajnaCijena) {
        this("", specifikacija, dobavljac, prodajnaCijena);
    }

    public HardverskaKomponenta(String naziv, String specifikacija, String dobavljac, double prodajnaCijena) {
        this.naziv = naziv;
        this.specifikacija = specifikacija;
        this.dobavljac = dobavljac;
        this.prodajnaCijena = prodajnaCijena;
    }

    @Override
    public double dajProdajnuCijenu() {
        return prodajnaCijena;
    }

    @Override
    public String dajSpecifikaciju() {
        return (naziv != null && !naziv.trim().isEmpty()) 
                ? naziv + " (" + specifikacija + ")" 
                : specifikacija;
    }

    @Override
    public String dajDobavljaca() {
        return dobavljac;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setSpecifikacija(String specifikacija) {
        this.specifikacija = specifikacija;
    }

    public void setDobavljac(String dobavljac) {
        this.dobavljac = dobavljac;
    }

    public void setProdajnaCijena(double prodajnaCijena) {
        this.prodajnaCijena = prodajnaCijena;
    }

    @Override
    public String toString() {
        return "Komponenta: " + dajSpecifikaciju() + " | Dobavljač: " + dajDobavljaca() 
                + " | Cena: " + String.format("%.2f", dajProdajnuCijenu()) + " RSD";
    }
}
