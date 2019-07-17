package de.semesterprojekt.ui.dialogs;

import de.semesterprojekt.concept.Game;
import de.semesterprojekt.ui.Colors;
import de.semesterprojekt.ui.LibraryWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Dialog zum Bearbeiten eines neuen Spiels
 */
public class EditWindow extends JDialog {
    private static EditWindow window;
    private final JTextField name, plattform, studio, publisher, genre;
    private final JSlider ratingBar;
    private final JCheckBox played;
    private Game currentGame;
    private static LibraryWindow libraryWindow;

    private EditWindow() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (closeCheck()) window.dispose();
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
        JButton btnRemove = new JButton("Löschen");
        btnSave.addActionListener(e -> {
            save();
            currentGame = null;
            dispose();
        });
        btnExit.addActionListener(e -> {
            currentGame = null;
            dispose();
        });
        btnRemove.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Willst du das Spiel wirklich löschen?", "Löschen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.OK_OPTION)
                return;
            if (libraryWindow.getLibrary().removeGame(currentGame)) {
                libraryWindow.reloadGames();
            } else {
                JOptionPane.showMessageDialog(this, "Spiel konnte aus unbestimmten Gründen nicht gelöscht werden");
            }
            currentGame = null;
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
        JPanel bottomRight = new JPanel(new FlowLayout());
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
        btnRemove.setBackground(Colors.COLOR_DARK);
        btnExit.setForeground(Colors.COLOR_TEXT);
        btnSave.setForeground(Colors.COLOR_TEXT);
        btnRemove.setForeground(Colors.COLOR_TEXT);
        btnExit.setToolTipText("Verlassen ohne zu Speichern");
        btnSave.setToolTipText("Speichern und Verlassen");
        btnRemove.setToolTipText("Entfernt das Spiel");
        genre.setToolTipText("Genre des Spiels");
        publisher.setToolTipText("Publisher des Spiels");
        studio.setToolTipText("Entwicklerstudio des Spiels");
        plattform.setToolTipText("Plattform, auf der das Spiel läuft");
        name.setToolTipText("Name des Spiels");
        played.setToolTipText("Spiel fertig gespielt");
        ratingBar.setToolTipText("Deine Bewertung des Spiels");

        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnExit);
        buttonPanel.add(btnRemove);
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
     * Zeigt das Fenster zum Bearbeiten eines Spiels
     *
     * @param game          Library zu der hinzugefügt wird
     * @param libraryWindow LibraryWindow welches erneuert wird
     */
    public static void show(Game game, LibraryWindow libraryWindow) {
        EditWindow.libraryWindow = libraryWindow;
        if (window == null) window = new EditWindow();
        if (closeCheck()) {
            window.prepare(game);
            window.pack();
            window.setVisible(true);
        }
    }

    private static boolean closeCheck() {
        if (window.currentGame != null && window.hasChanged()) {
            int selected = JOptionPane.showConfirmDialog(window, "Du hast ungespeicherte Änderungen. Willst du Speichern?", "Warnung", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (selected == JOptionPane.YES_OPTION) {
                window.save();
            } else if (selected == JOptionPane.CANCEL_OPTION) {
                return false;
            }
            window.currentGame = null;

        }
        return true;
    }

    private void save() {
        currentGame.setName(name.getText());
        currentGame.setGenre(genre.getText());
        currentGame.setPlattform(plattform.getText());
        currentGame.setPublisher(publisher.getText());
        currentGame.setStudio(studio.getText());
        if (played.isSelected())
            currentGame.setRating(ratingBar.getValue());
        else
            currentGame.setRating(-1);
        libraryWindow.getLibrary().setUnsaved(true);
        libraryWindow.reloadGames();
    }

    private void prepare(Game game) {
        currentGame = game;
        setTitle(game.getName() + " bearbeiten");
        name.setText(game.getName());
        genre.setText(game.getGenre());
        plattform.setText(game.getPlattform());
        publisher.setText(game.getPublisher());
        studio.setText(game.getStudio());
        ratingBar.setValue(game.getRating());
        played.setSelected(game.isPlayed());
        ratingBar.setEnabled(game.isPlayed());
    }

    private boolean hasChanged() {
        return !name.getText().equals(currentGame.getName()) ||
                !genre.getText().equals(currentGame.getGenre()) ||
                !plattform.getText().equals(currentGame.getPlattform()) ||
                !publisher.getText().equals(currentGame.getPublisher()) ||
                !studio.getText().equals(currentGame.getStudio()) ||
                played.isSelected() != currentGame.isPlayed() ||
                (currentGame.isPlayed() && ratingBar.getValue() != currentGame.getRating());
    }
}
