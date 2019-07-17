package de.semesterprojekt.ui.dialogs;

import de.semesterprojekt.concept.GameLibrary;
import de.semesterprojekt.db.DataStorage;
import de.semesterprojekt.db.FileStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
        titledPanel.setBorder(BorderFactory.createTitledBorder("Speicherort"));
        add(titledPanel, BorderLayout.CENTER);
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
