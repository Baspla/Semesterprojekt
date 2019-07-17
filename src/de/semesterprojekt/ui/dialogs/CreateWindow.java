/*
 * Erstellt von Tim Morgner
 */

package de.semesterprojekt.ui.dialogs;

import de.semesterprojekt.ui.Colors;
import de.semesterprojekt.ui.LibraryWindow;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Dialog zum Hinzufügen eines neuen Spiels
 */
public class CreateWindow extends JDialog {

    private final JTextField name, plattform, studio, publisher, genre;
    private final JSlider ratingBar;
    private final JCheckBox played;
    private static LibraryWindow libraryWindow;

    private static CreateWindow window;

    private CreateWindow() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (closeCheck()) {
                    clear();
                    window.dispose();
                }
            }
        });
        JPanel infoPanel = new JPanel(new GridLayout(3, 2));
        name = new JTextField(15);
        plattform = new JTextField(15);
        studio = new JTextField(15);
        publisher = new JTextField(15);
        genre = new JTextField(15);
        ratingBar = new JSlider(0, 10);
        played = new JCheckBox("gespielt");
        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Speichern");
        JButton btnExit = new JButton("Verlassen");
        JPanel bottomRight = new JPanel(new FlowLayout());
        btnSave.addActionListener(e -> {
            save();
            dispose();
        });
        btnExit.addActionListener(e -> {
            clear();
            dispose();
        });
        played.addActionListener(e -> ratingBar.setEnabled(played.isSelected()));
        name.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), whiteTextBorder("Name")));
        plattform.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), whiteTextBorder("Plattform")));
        studio.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), whiteTextBorder("Studio")));
        publisher.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), whiteTextBorder("Publisher")));
        genre.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), whiteTextBorder("Genre")));
        ratingBar.setMajorTickSpacing(5);
        ratingBar.setMinorTickSpacing(1);
        ratingBar.setPaintTicks(true);
        ratingBar.setPaintLabels(true);
        ratingBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), whiteTextBorder("Bewertung")));
        //Farben
        bottomRight.setBackground(Colors.COLOR_NORMAL);
        played.setBackground(Colors.COLOR_NORMAL);
        played.setForeground(Colors.COLOR_TEXT);
        ratingBar.setBackground(Colors.COLOR_NORMAL);
        ratingBar.setForeground(Colors.COLOR_TEXT);
        name.setBackground(Colors.COLOR_NORMAL);
        name.setForeground(Colors.COLOR_TEXT);
        name.setCaretColor(Colors.COLOR_TEXT);
        plattform.setBackground(Colors.COLOR_NORMAL);
        plattform.setForeground(Colors.COLOR_TEXT);
        plattform.setCaretColor(Colors.COLOR_TEXT);
        studio.setBackground(Colors.COLOR_NORMAL);
        studio.setForeground(Colors.COLOR_TEXT);
        studio.setCaretColor(Colors.COLOR_TEXT);
        publisher.setBackground(Colors.COLOR_NORMAL);
        publisher.setForeground(Colors.COLOR_TEXT);
        publisher.setCaretColor(Colors.COLOR_TEXT);
        genre.setBackground(Colors.COLOR_NORMAL);
        genre.setForeground(Colors.COLOR_TEXT);
        genre.setCaretColor(Colors.COLOR_TEXT);
        buttonPanel.setBackground(Colors.COLOR_NORMAL);
        btnExit.setBackground(Colors.COLOR_DARK);
        btnSave.setBackground(Colors.COLOR_DARK);
        btnExit.setForeground(Colors.COLOR_DARK);
        btnSave.setForeground(Colors.COLOR_DARK);
        //Tooltips
        btnExit.setToolTipText("Verlassen ohne zu Speichern");
        btnSave.setToolTipText("Speichern und Verlassen");
        genre.setToolTipText("Genre des Spiels");
        publisher.setToolTipText("Publisher des Spiels");
        studio.setToolTipText("Entwicklerstudio des Spiels");
        plattform.setToolTipText("Plattform, auf der das Spiel läuft");
        name.setToolTipText("Name des Spiels");
        played.setToolTipText("Spiel fertig gespielt");
        ratingBar.setToolTipText("Deine Bewertung des Spiels");
        ratingBar.setEnabled(false);
        ratingBar.setValue(0);
        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnExit);
        infoPanel.add(name);
        infoPanel.add(plattform);
        infoPanel.add(studio);
        infoPanel.add(publisher);
        infoPanel.add(genre);
        infoPanel.add(ratingBar);
        infoPanel.add(bottomRight);
        bottomRight.add(played);
        bottomRight.add(ratingBar);
        try {
            setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Border whiteTextBorder(String string) {
        TitledBorder border = BorderFactory.createTitledBorder(string);
        border.setTitleColor(Colors.COLOR_TEXT);
        return border;
    }

    /**
     * Zeigt das Fenster zum Hinzufügen eines Spiels
     * @param libraryWindow LibraryWindow welches erneuert wird
     */
    public static void showWindow(LibraryWindow libraryWindow) {
        CreateWindow.libraryWindow = libraryWindow;
        if (window == null) window = new CreateWindow();
        if (closeCheck()) {
            window.pack();
            window.setVisible(true);
        }
    }

    private static boolean closeCheck() {
        if (window.hasChanged()) {
            int selected = JOptionPane.showConfirmDialog(window, "Du hast ungespeicherte Änderungen. Willst du Speichern?", "Warnung", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (selected == JOptionPane.YES_OPTION) {
                window.save();
            } else return selected != JOptionPane.CANCEL_OPTION;
        }
        return true;
    }

    /**
     * Überträgt UI Eingaben in das {@link de.semesterprojekt.concept.Game} Objekt
     */
    private void save() {
        if (name.getText().isBlank()) {
            clear();
            return;
        }
        int rating;
        if (played.isSelected())
            rating = ratingBar.getValue();
        else
            rating = -1;
        libraryWindow.getLibrary().createGame(name.getText(), studio.getText(), publisher.getText(), plattform.getText(), genre.getText(), rating);
        clear();
        libraryWindow.reloadGames();
    }

    private void clear() {
        name.setText("");
        genre.setText("");
        plattform.setText("");
        publisher.setText("");
        studio.setText("");
        played.setSelected(false);
        ratingBar.setValue(0);
    }

    private boolean hasChanged() {
        return !name.getText().isBlank() ||
                !genre.getText().isBlank() ||
                !plattform.getText().isBlank() ||
                !publisher.getText().isBlank() ||
                !studio.getText().isBlank() ||
                played.isSelected();
    }
}
