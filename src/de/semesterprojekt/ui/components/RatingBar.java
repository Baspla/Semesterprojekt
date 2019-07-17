/*
 * Erstellt von Tim Morgner
 */

package de.semesterprojekt.ui.components;

import de.semesterprojekt.concept.Game;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;

/**
 * Leiste mit bis zu 10 Sternen, je nach Bewertung. Bei 0 ist die Leiste unsichtbar.
 */
class RatingBar extends JPanel {
    public RatingBar(Game game) {
        super(new FlowLayout());
        if (game.getRating() <= 0) {
            add(new JLabel(new ImageIcon(getClass().getResource("/placeholder.png"))));
            return;
        }
        for (int i = 1; i <= 10; i++) {
            add(new JLabel(new ImageIcon(getClass().getResource((game.getRating() >= i) ? "/star.png" : "/staroff.png"))));
        }
    }
}
