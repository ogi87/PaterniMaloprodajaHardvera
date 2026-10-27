/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import form.MainForm;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Glavna ulazna tačka aplikacije (Main entry point).
 * 
 * @author student
 */
public class Main {

    public static void main(String[] args) {
        // Postavljanje modernog Flat / System ili Nimbus Look & Feel-a
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                // Ukoliko sistem ne podržava, ostaje podrazumevani Swing izgled
            }
        }

        // Pokretanje Swing grafičke forme na Event Dispatching niti (EDT)
        SwingUtilities.invokeLater(() -> {
            MainForm forma = new MainForm();
            forma.setVisible(true);
        });
    }
}
