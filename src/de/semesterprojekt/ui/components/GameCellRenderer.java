package de.semesterprojekt.ui.components;

import de.semesterprojekt.concept.Game;
import de.semesterprojekt.ui.Colors;

import javax.swing.*;
import java.awt.*;

/**
 * Stellt das {@link Game} Objekt in der Liste graphisch dar
 */
class GameCellRenderer implements ListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (!(value instanceof Game)) return new JLabel("Unbekanntes Objekt in der Liste! Debug @morgneti"); //Sollte nicht vorkommen
        Game game = (Game) value;
        JPanel panel = new JPanel(new BorderLayout());
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        JPanel topRow = new JPanel(new BorderLayout());
        JPanel botRow = new JPanel(new BorderLayout());
        JPanel midRow = new JPanel(new BorderLayout());
        JLabel name = new JLabel(game.getName());
        JLabel plattformLabel = new JLabel(game.getPlattform());
        JLabel studio = new JLabel(game.getStudio() + ", " + game.getPublisher());
        JLabel genre = new JLabel(game.getGenre());
        RatingBar ratingBar = new RatingBar((Game) value);

        name.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
        studio.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
        genre.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
        plattformLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));

        name.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        name.setForeground(Colors.COLOR_TEXT);
        plattformLabel.setForeground(Colors.COLOR_TEXT);
        studio.setForeground(Colors.COLOR_TEXT);
        genre.setForeground(Colors.COLOR_TEXT);
        if (isSelected) {
            infoPanel.setBackground(list.getSelectionBackground());
            infoPanel.setForeground(list.getSelectionForeground());
            topRow.setBackground(Colors.COLOR_MIDLIGHT);
            botRow.setBackground(list.getSelectionBackground());
            midRow.setBackground(list.getSelectionBackground());
            ratingBar.setBackground(list.getSelectionBackground());
        } else {
            topRow.setBackground(Colors.COLOR_MIDDARK);
            botRow.setBackground(list.getBackground());
            midRow.setBackground(list.getBackground());
            infoPanel.setBackground(list.getBackground());
            infoPanel.setForeground(list.getForeground());
            ratingBar.setBackground(list.getBackground());
        }

        panel.add(infoPanel, BorderLayout.CENTER);
        topRow.add(name, BorderLayout.WEST);
        topRow.add(plattformLabel, BorderLayout.EAST);
        infoPanel.add(topRow);
        midRow.add(studio, BorderLayout.WEST);
        midRow.add(new JLabel(new ImageIcon(getClass().getResource(game.isPlayed()?"/check.png":"/placeholder.png"))), BorderLayout.EAST);
        infoPanel.add(midRow);
        botRow.add(genre, BorderLayout.WEST);
        botRow.add(ratingBar, BorderLayout.EAST);
        infoPanel.add(botRow);
        return panel;
    }
}
