/*
 * Erstellt von Tim Morgner
 */

package de.semesterprojekt.ui.dialogs;

import de.semesterprojekt.concept.GameLibrary;
import de.semesterprojekt.db.DataStorage;
import de.semesterprojekt.db.FileStorage;
import de.semesterprojekt.ui.Colors;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Fenster mit Einstellungen
 */
public class OptionWindow extends JDialog {
    private static OptionWindow window;
    private final JTextField textSaveLocation;
    private final GameLibrary library;

    private OptionWindow(GameLibrary library) {
        this.library = library;
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
                window = null;
            }
        });
        setLayout(new BorderLayout());
        textSaveLocation = new JTextField(20);
        textSaveLocation.setEditable(false);
        JButton btnSaveLocation = new JButton("Auswählen");
        btnSaveLocation.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(false);
            if (chooser.showDialog(OptionWindow.this, "Auswählen") == JFileChooser.APPROVE_OPTION) {
                DataStorage ds = library.getDataStorage();
                if (ds instanceof FileStorage) {
                    ((FileStorage) ds).setSaveLocation(chooser.getSelectedFile());
                    updateSaveLocation();
                }
            }
        });
        JPanel titledPanel = new JPanel();
        titledPanel.add(textSaveLocation);
        titledPanel.add(btnSaveLocation);
        titledPanel.setBorder(Colors.whiteTextBorder("Speicherort"));
        add(titledPanel, BorderLayout.CENTER);
        titledPanel.setBackground(Colors.COLOR_NORMAL);
        textSaveLocation.setForeground(Colors.COLOR_TEXT);
        textSaveLocation.setCaretColor(Colors.COLOR_TEXT);
        textSaveLocation.setBackground(Colors.COLOR_MIDDARK);
        btnSaveLocation.setBackground(Colors.COLOR_DARK);
        btnSaveLocation.setForeground(Colors.COLOR_TEXT_BUTTON);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void updateSaveLocation() {
        DataStorage ds = library.getDataStorage();
        if (ds instanceof FileStorage) {
            textSaveLocation.setText(((FileStorage) ds).getSaveLocation().toString());
        }else{
            textSaveLocation.setText("Der DataStorage unterstützt keine Dateispeicherorte.");
        }
    }

    /**
     * Zeigt das Fenster mit den Einstellungen
     */
    public static void showWindow(GameLibrary library) {
        if (window == null) window = new OptionWindow(library);
    }
}
