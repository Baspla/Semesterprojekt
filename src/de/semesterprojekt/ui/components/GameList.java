/*
 * Erstellt von Tim Morgner
 */

package de.semesterprojekt.ui.components;

import de.semesterprojekt.concept.Game;
import de.semesterprojekt.ui.Colors;
import de.semesterprojekt.ui.LibraryWindow;
import de.semesterprojekt.ui.dialogs.EditWindow;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("unchecked")
/* Liste an Spielen für alle Tabs
 */
public class GameList extends JList {
    public GameList(LibraryWindow libraryWindow) {
        super(new DefaultListModel());
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        setBackground(Colors.COLOR_NORMAL);
        setSelectionBackground(Colors.COLOR_LIGHT);
        setForeground(Colors.COLOR_TEXT);
        setSelectionForeground(Colors.COLOR_HOVER);
        setCellRenderer(new GameCellRenderer());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    Rectangle r = getCellBounds(0, getLastVisibleIndex());
                    if (r != null && r.contains(e.getPoint())) {
                        int index = locationToIndex(e.getPoint());
                        EditWindow.show((Game) getModel().getElementAt(index), libraryWindow);
                    }
                }
            }
        });


    }
}
