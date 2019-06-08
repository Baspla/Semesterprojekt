package de.semesterprojekt.ui.dialogs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Fenster mit Einstellungen
 */
public class OptionWindow extends JDialog {
    private static OptionWindow window;

    private OptionWindow() {
        try {
            setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Einstellungen");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                window=null;
            }
        });
        add(new JLabel("Hier kommt: Import, Darstellung, Speicherort, Export, Datenbank-Import"));
        pack();
        setVisible(true);
    }

    /**
     * Zeigt das Fenster mit den Einstellungen
     */
    public static void showWindow() {
        if (window == null) window = new OptionWindow();
    }
}
