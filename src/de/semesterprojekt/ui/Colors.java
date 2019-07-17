/*
 * Erstellt von Tim Morgner
 */

package de.semesterprojekt.ui;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.Color;

/**
 * Farbschema der Oberfläche
 */
public abstract class Colors {
    public static final Color COLOR_NORMAL = new Color(84, 110, 122);
    public static final Color COLOR_MIDDARK = new Color(69, 90, 100);
    public static final Color COLOR_DARK = new Color(41, 67, 78);
    public static final Color COLOR_HALO = new Color(120, 144, 156);
    public static final Color COLOR_MIDLIGHT = new Color(120, 144, 156);
    public static final Color COLOR_LIGHT = new Color(129, 156, 169);
    public static final Color COLOR_HOVER = new Color(176, 190, 197);
    public static final Color COLOR_TEXT = Color.WHITE;
    public static Color COLOR_TEXT_BUTTON = Color.WHITE;

    public static Border whiteTextBorder(String string) {
        TitledBorder border = BorderFactory.createTitledBorder(string);
        border.setTitleColor(Colors.COLOR_TEXT);
        return border;
    }
}
