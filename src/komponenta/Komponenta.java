/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package komponenta;

/**
 * Ciljni interfejs (Target) u okviru podsistema za maloprodajne objekte.
 * 
 * Sve hardverske komponente koje se prodaju u maloprodajnim objektima
 * predstavljene su ovim zajedničkim interfejsom.
 * 
 * @author student
 */
public interface Komponenta {

    /**
     * Vraća prodajnu cenu komponente (sa uračunatim porezom, tj. konačnu maloprodajnu cenu).
     * 
     * @return prodajna cena
     */
    double dajProdajnuCijenu();

    /**
     * Vraća tehničku specifikaciju i osnovne karakteristike komponente.
     * 
     * @return tehnička specifikacija
     */
    String dajSpecifikaciju();

    /**
     * Vraća naziv dobavljača komponente.
     * 
     * @return naziv dobavljača
     */
    String dajDobavljaca();
}
