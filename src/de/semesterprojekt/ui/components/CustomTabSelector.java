/*
 * Erstellt von Tim Morgner
 */

package de.semesterprojekt.ui.components;

import de.semesterprojekt.ui.Colors;
import de.semesterprojekt.ui.HorizontalArea;
import de.semesterprojekt.ui.LibraryWindow;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Benutzerdefiniertes Kontrollelement um zwischen Panels des CardLayouts zu wechseln
 */
public class CustomTabSelector extends JComponent {
    private final JPanel listPanel;
    private final Font bigFont;
    private final Font normalFont;
    private Tab selected;
    private HorizontalArea triggerLeft;
    private HorizontalArea triggerCenter;
    private HorizontalArea triggerRight;
    private Tab hovered;

    public CustomTabSelector(JPanel listPanel) {
        super();
        this.listPanel = listPanel;
        normalFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 25);
        MouseAdapter adapter = new MouseAdapter() {

            private Tab triggered;

            @Override
            public void mouseMoved(MouseEvent e) {
                if (triggerLeft != null && triggerCenter != null && triggerRight != null) {
                    if (triggerLeft.isWithin(e.getX())) hover(Tab.LEFT);
                    else if (triggerCenter.isWithin(e.getX())) hover(Tab.CENTER);
                    else if (triggerRight.isWithin(e.getX())) hover(Tab.RIGHT);
                    else hover(null);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (triggerLeft != null && triggerCenter != null && triggerRight != null) {
                    if (triggerLeft.isWithin(e.getX()) && triggered == Tab.LEFT) select(Tab.LEFT);
                    else if (triggerCenter.isWithin(e.getX()) && triggered == Tab.CENTER) select(Tab.CENTER);
                    else if (triggerRight.isWithin(e.getX()) && triggered == Tab.RIGHT) select(Tab.RIGHT);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (triggerLeft != null && triggerCenter != null && triggerRight != null) {
                    if (triggerLeft.isWithin(e.getX())) triggered = Tab.LEFT;
                    else if (triggerCenter.isWithin(e.getX())) triggered = Tab.CENTER;
                    else if (triggerRight.isWithin(e.getX())) triggered = Tab.RIGHT;
                }
            }
        };
        select(Tab.CENTER);
        addMouseMotionListener(adapter);
        addMouseListener(adapter);
    }

    private void hover(Tab tab) {
        if (hovered != tab) {
            hovered = tab;
            repaint();
        }
    }

    private void select(Tab tab) {
        selected = tab;
        if (selected == Tab.LEFT)
            ((CardLayout) listPanel.getLayout()).show(listPanel, LibraryWindow.PANEL_FAVORITE);
        else if (selected == Tab.CENTER)
            ((CardLayout) listPanel.getLayout()).show(listPanel, LibraryWindow.PANEL_GAME);
        else if (selected == Tab.RIGHT)
            ((CardLayout) listPanel.getLayout()).show(listPanel, LibraryWindow.PANEL_RECCOMENDATION);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        float center = (float) (g2d.getClipBounds().getWidth() / 2);
        float textBaseline = (float) ((g2d.getClipBounds().getHeight() / 3) * 2);
        float distance = 20;
        g2d.setFont((selected == Tab.LEFT) ? bigFont : normalFont);
        float leftWidth = g2d.getFontMetrics().stringWidth("Favoriten");
        g2d.setFont((selected == Tab.CENTER) ? bigFont : normalFont);
        float centerWidth = g2d.getFontMetrics().stringWidth("Spiele");
        g2d.setFont((selected == Tab.RIGHT) ? bigFont : normalFont);
        float rightWidth = g2d.getFontMetrics().stringWidth("Empfehlungen");
        float blockWidth = leftWidth + distance + centerWidth + distance + rightWidth;
        float start = center - (blockWidth / 2);

        //alle Texte zu block Zusammengefasst und Zentriert und dann von Links gelayoutet
        triggerLeft = new HorizontalArea(start - (distance / 2), start + leftWidth + (distance / 2));
        triggerCenter = new HorizontalArea(start + leftWidth + (distance / 2), start + leftWidth + distance + centerWidth + (distance / 2));
        triggerRight = new HorizontalArea(start + leftWidth + distance + centerWidth + (distance / 2), start + leftWidth + distance + centerWidth + distance + rightWidth + (distance / 2));

        Paint paint = g2d.getPaint();
        BufferedImage halo = null;
        try {
            halo = ImageIO.read(getClass().getResource("/halo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (LibraryWindow.USE_SELECTION_HALO)
            switch (selected) {
                case LEFT:
                    g2d.drawImage(halo, (int)start,0,(int)leftWidth,getHeight(),null);
                    //halo(triggerLeft, g2d, distance / 2);
                    break;
                case CENTER:
                    g2d.drawImage(halo, (int)(start+leftWidth+distance),0,(int)centerWidth,getHeight(),null);
                    //halo(triggerCenter, g2d, distance / 2);
                    break;
                case RIGHT:
                    g2d.drawImage(halo, (int)(start+leftWidth+distance+centerWidth+distance),0,(int)rightWidth,getHeight(),null);
                    //halo(triggerRight, g2d, distance / 2);
                    break;
            }
        g2d.setPaint(paint);
        //Zeichnet das ausgewählte Tab größer und das gehoverte in anderer Farbe
        g2d.setFont((selected == Tab.LEFT) ? bigFont : normalFont);
        g2d.setColor((hovered == Tab.LEFT) ? Colors.COLOR_HOVER : Colors.COLOR_TEXT);
        g2d.drawString("Favoriten", start, textBaseline);
        g2d.setFont((selected == Tab.CENTER) ? bigFont : normalFont);
        g2d.setColor((hovered == Tab.CENTER) ? Colors.COLOR_HOVER : Colors.COLOR_TEXT);
        g2d.drawString("Spiele", start + leftWidth + distance, textBaseline);
        g2d.setFont((selected == Tab.RIGHT) ? bigFont : normalFont);
        g2d.setColor((hovered == Tab.RIGHT) ? Colors.COLOR_HOVER : Colors.COLOR_TEXT);
        g2d.drawString("Empfehlungen", start + leftWidth + distance + centerWidth + distance, textBaseline);
        //debugDrawTrigger(g);
    }

    private void halo(HorizontalArea trigger, Graphics2D g2d, float r) {
        float[] fractions = {0, 1f};
        Color[] colors = {Colors.COLOR_HALO, Colors.COLOR_DARK};
        //LEFT
        g2d.setPaint(new RadialGradientPaint(trigger.getStart() + r, 0, 10, fractions, colors));
        g2d.fillRect((int) (trigger.getStart()), 0, (int) (trigger.getStart() + r), 1000);
        //RIGHT
        g2d.setPaint(new RadialGradientPaint(trigger.getEnd() - r, 0, 10, fractions, colors));
        g2d.fillRect((int) (trigger.getEnd() - r), 0, (int) (trigger.getEnd() - trigger.getStart()), 1000);
        //MIDDLE
        g2d.setPaint(new GradientPaint(0, 0, Colors.COLOR_HALO, 0, 10, Colors.COLOR_DARK));
        g2d.fillRect((int) (trigger.getStart() + r), 0, (int) (trigger.getEnd() - trigger.getStart() - r - r), 1000);
    }

// --Commented out by Inspection START (17.07.2019 08:24):
//    /**
//     * Debugfunktion um anzuzeigen wo clicks registriert werden
//     */
//    private void debugDrawTrigger(Graphics g) {
//        line(g, triggerLeft.getStart());
//        line(g, triggerLeft.getEnd());
//        line(g, triggerCenter.getStart());
//        line(g, triggerCenter.getEnd());
//        line(g, triggerRight.getStart());
//        line(g, triggerRight.getEnd());
//    }
// --Commented out by Inspection STOP (17.07.2019 08:24)

    private void line(Graphics g, float x) {
        g.drawLine((int) x, 0, (int) x, 1000);
    }

    private enum Tab {
        LEFT, CENTER, RIGHT
    }
}