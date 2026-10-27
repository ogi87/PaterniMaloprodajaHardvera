/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package panel;

import java.awt.Component;

/**
 * Zajednički interfejs za polimorfne panele za unos podataka.
 * 
 * Omogućava formi da uniformno komunicira sa različitim UI panelima:
 * - KomponentaPanel vraća objekat HardverskaKomponenta
 * - UslugaPanel vraća objekat UslugaAdapter (koji adaptira Usluga na Komponenta)
 * 
 * @author student
 */
public interface FormInputPanel {

    /**
     * Čita unete podatke, vrši validaciju i vraća instancu odgovarajućeg tipa.
     * U slučaju neispravnih podataka ispisuje poruku o grešci na panelu i baca izuzetak.
     * 
     * @return uneti i validirani objekat (Komponenta)
     * @throws Exception ako je unos neispravan
     */
    Object getValue() throws Exception;

    /**
     * Uklanja tekst greške sa panela.
     */
    void clearError();

    /**
     * Resetuje polja panela na podrazumevane vrednosti i briše greške.
     */
    void reset();

    /**
     * Pomoćna metoda koja vraća AWT/Swing komponentu za ugradnju u kontejnere.
     * 
     * @return Component
     */
    default Component getComponent() {
        if (this instanceof Component) {
            return (Component) this;
        }
        return null;
    }
}
