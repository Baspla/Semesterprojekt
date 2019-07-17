/*
 * Erstellt von Tim Morgner
 */

package de.semesterprojekt.ui.dialogs;

import de.semesterprojekt.concept.GameLibrary;
import de.semesterprojekt.db.DataStorage;
import de.semesterprojekt.db.FileStorage;
import de.semesterprojekt.ui.Colors;
import de.semesterprojekt.ui.LibraryWindow;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

/**
 * Fenster mit Einstellungen
 */
public class OptionWindow extends JDialog {
    private static OptionWindow window;
    private final JTextField textSaveLocation;
    private final GameLibrary library;

    private OptionWindow(LibraryWindow libraryWindow) {
        this.library = libraryWindow.getLibrary();
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
        JPanel top = new JPanel(new BorderLayout());
        JPanel bot = new JPanel(new FlowLayout());
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
        JButton btnSave = new JButton("Speichern");
        JButton btnLoad = new JButton("Laden");
        btnSave.addActionListener(e -> {
            library.saveGames();
            libraryWindow.reloadGames();
        });
        btnLoad.addActionListener(e -> {
            library.loadGames();
            libraryWindow.reloadGames();
        });
        bot.add(btnSave);
        bot.add(btnLoad);
        JPanel titledPanel = new JPanel();
        titledPanel.add(textSaveLocation);
        titledPanel.add(btnSaveLocation);
        titledPanel.setBorder(Colors.whiteTextBorder("Speicherort"));
        top.add(titledPanel, BorderLayout.CENTER);
        titledPanel.setBackground(Colors.COLOR_NORMAL);
        textSaveLocation.setForeground(Colors.COLOR_TEXT);
        textSaveLocation.setCaretColor(Colors.COLOR_TEXT);
        textSaveLocation.setBackground(Colors.COLOR_MIDDARK);
        btnSaveLocation.setBackground(Colors.COLOR_DARK);
        btnSaveLocation.setForeground(Colors.COLOR_TEXT_BUTTON);
        btnSave.setBackground(Colors.COLOR_DARK);
        btnSave.setForeground(Colors.COLOR_TEXT_BUTTON);
        btnLoad.setBackground(Colors.COLOR_DARK);
        btnLoad.setForeground(Colors.COLOR_TEXT_BUTTON);
        bot.setBackground(Colors.COLOR_MIDDARK);
        add(top, BorderLayout.CENTER);
        add(bot, BorderLayout.SOUTH);
        pack();
        updateSaveLocation();
        setResizable(false);
        setVisible(true);
    }

    private void updateSaveLocation() {
        DataStorage ds = library.getDataStorage();
        if (ds == null) return;
        if (ds instanceof FileStorage) {
            File location = ((FileStorage) ds).getSaveLocation();
            if (location == null) return;
            textSaveLocation.setText(location.toString());
        } else {
            textSaveLocation.setText("Der DataStorage unterstützt keine Dateispeicherorte.");
        }
    }

    /**
     * Zeigt das Fenster mit den Einstellungen
     */
    public static void showWindow(LibraryWindow library) {
        if (window == null) window = new OptionWindow(library);
    }
}
