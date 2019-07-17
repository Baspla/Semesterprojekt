package de.semesterprojekt.ui.components;

import de.semesterprojekt.ui.Colors;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Benutzerdefinierter {@link JButton} für nicht-Text UI (+ und ...)
 */
public class CustomButton extends JButton {
    private final boolean dots;
    private boolean hover;

    public CustomButton(boolean dots) {
        super();
        this.dots = dots;
        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Colors.COLOR_DARK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(hover ? Colors.COLOR_HOVER : Colors.COLOR_TEXT);
        if (dots) {
            //Zeichnet "..." und dreht es um 90°
            g2d.rotate(Math.toRadians(90), g2d.getClipBounds().getWidth() / 2, g2d.getClipBounds().getHeight() / 2);
            g2d.drawString("...", (float) (g2d.getClipBounds().getWidth() - g2d.getFontMetrics().stringWidth("...")) / 2, (float) (g2d.getClipBounds().getHeight() + g2d.getFontMetrics().getDescent()) / 2);
        } else {
            //Zeichnet ein zentriertes "+"
            g2d.drawString("+", (float) (g2d.getClipBounds().getWidth() - g2d.getFontMetrics().stringWidth("+")) / 2, (float) (g2d.getClipBounds().getHeight() + g2d.getFontMetrics().stringWidth("+")) / 2);
        }
    }
}
