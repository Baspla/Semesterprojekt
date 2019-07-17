/*
 * Erstellt von Tim Morgner
 */

package de.semesterprojekt.ui;

import de.semesterprojekt.concept.Game;
import de.semesterprojekt.concept.GameLibrary;
import de.semesterprojekt.ui.components.CustomButton;
import de.semesterprojekt.ui.components.CustomTabSelector;
import de.semesterprojekt.ui.components.GameList;
import de.semesterprojekt.ui.dialogs.CreateWindow;
import de.semesterprojekt.ui.dialogs.OptionWindow;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

/**
 * Tiefstes UI-Fenster zur Darstellung einer {@link GameLibrary}
 */
public class LibraryWindow extends JFrame {
    private static final String WINDOW_TITLE = "Spielebibliothek";
    private static final int MIN_WIDTH = 500;
    private static final int MIN_HEIGHT = 500;
    private static final int MAX_WIDTH = 3000;
    private static final int MAX_HEIGHT = 3000;
    public static final String PANEL_FAVORITE = "FAVORITE";
    public static final String PANEL_GAME = "GAME";
    public static final String PANEL_RECCOMENDATION = "RECCOMENDATION";
    // --Commented out by Inspection (17.07.2019 08:25):private static final int BAR_WIDTH = 0;
    private static final int BAR_HEIGHT = 70;
    public static final boolean USE_SELECTION_HALO = false;
    private static final int PREF_WIDTH = 1000;
    private static final int PREF_HEIGHT = 700;
    private final GameList favoriteList, gameList, reccomendationList;

    private final GameLibrary library;

    /**
     * Erstellt ein Fenster zu einer gegebenen {@link GameLibrary}
     *
     * @param library Library die angezeigt werden soll
     */
    @SuppressWarnings("unchecked")
    public LibraryWindow(GameLibrary library) {
        super(WINDOW_TITLE);
        this.library = library;
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //TODO LookAndFeel
        if(System.getProperty("os.name").toLowerCase().contains("mac")){
            Colors.COLOR_TEXT_BUTTON=Colors.COLOR_DARK;
        }
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }*/
        setBackground(Colors.COLOR_NORMAL);
        setForeground(Colors.COLOR_TEXT);

        JPanel listPanel = new JPanel(new CardLayout());
        JPanel favoritePanel = new JPanel(new BorderLayout());
        JPanel gamePanel = new JPanel(new BorderLayout());
        JPanel reccomendationPanel = new JPanel(new BorderLayout());

        favoritePanel.setBackground(Colors.COLOR_NORMAL);
        favoriteList = new GameList(this);
        JScrollPane favoriteScroll = new JScrollPane(favoriteList);
        favoriteScroll.setBorder(BorderFactory.createEmptyBorder());
        favoritePanel.add(favoriteScroll, BorderLayout.CENTER);

        gamePanel.setBackground(Colors.COLOR_NORMAL);
        gameList = new GameList(this);
        JScrollPane gameScroll = new JScrollPane(gameList);
        gameScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        gameScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        gameScroll.setBorder(BorderFactory.createEmptyBorder());
        gamePanel.add(gameScroll, BorderLayout.CENTER);

        reccomendationPanel.setBackground(Colors.COLOR_NORMAL);
        reccomendationList = new GameList(this);
        JScrollPane reccomendationScroll = new JScrollPane(reccomendationList);
        reccomendationScroll.setBorder(BorderFactory.createEmptyBorder());
        reccomendationPanel.add(reccomendationScroll, BorderLayout.CENTER);

        listPanel.add(favoritePanel, PANEL_FAVORITE);
        listPanel.add(gamePanel, PANEL_GAME);
        listPanel.add(reccomendationPanel, PANEL_RECCOMENDATION);
        add(listPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        CustomButton addButton = new CustomButton(false);
        addButton.setToolTipText("Spiel hinzufügen");
        addButton.addActionListener(e -> CreateWindow.showWindow(this));
        CustomButton optionsButton = new CustomButton(true);
        optionsButton.setToolTipText("Einstellungen");
        optionsButton.addActionListener(e -> OptionWindow.showWindow(library));
        CustomTabSelector tabSelector = new CustomTabSelector(listPanel);
        //noinspection SuspiciousNameCombination
        addButton.setPreferredSize(new Dimension(BAR_HEIGHT, BAR_HEIGHT));
        //noinspection SuspiciousNameCombination
        optionsButton.setPreferredSize(new Dimension(BAR_HEIGHT, BAR_HEIGHT));
        tabSelector.setPreferredSize(new Dimension(0, BAR_HEIGHT));
        bottomPanel.setPreferredSize(new Dimension(0, BAR_HEIGHT));
        bottomPanel.setBackground(Colors.COLOR_DARK);
        bottomPanel.add(addButton, BorderLayout.WEST);
        bottomPanel.add(optionsButton, BorderLayout.EAST);
        bottomPanel.add(tabSelector, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        //Disabled features
        boolean USE_CUSTOM_TITLE_BAR = false;
        //noinspection ConstantConditions
        if (USE_CUSTOM_TITLE_BAR) {
            setUndecorated(true);
            JPanel titleBar = new JPanel(new BorderLayout());
            titleBar.setPreferredSize(new Dimension(0, 30));
            JPanel titleButtons = new JPanel(new GridLayout(1, 3));
            JButton minimize = new JButton("_");
            JButton maximize = new JButton("[#]");
            JButton close = new JButton("X");
            minimize.addActionListener(e -> setExtendedState(Frame.ICONIFIED));
            maximize.addActionListener(e -> setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH));
            close.addActionListener(e -> dispose());
            titleButtons.add(minimize);
            titleButtons.add(maximize);
            titleButtons.add(close);
            MouseAdapter titleBarAdapter = new MouseAdapter() {
                private Point dragLocation;

                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    dragLocation = e.getPoint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    dragLocation = null;
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    super.mouseDragged(e);
                    if (dragLocation != null) {
                        setLocation((int) (e.getLocationOnScreen().getX() - dragLocation.getX()), (int) (e.getLocationOnScreen().getY() - dragLocation.getY()));
                    }
                }
            };
            titleBar.addMouseListener(titleBarAdapter);
            titleBar.addMouseMotionListener(titleBarAdapter);
            titleBar.add(new JLabel("  Spielebibliothek"), BorderLayout.WEST);
            titleBar.add(titleButtons, BorderLayout.EAST);
            add(titleBar, BorderLayout.NORTH);
        }
        if (gameList.getModel() instanceof DefaultListModel) {
            ((DefaultListModel<Game>) gameList.getModel()).addAll(library.getGames());
        }
        if (favoriteList.getModel() instanceof DefaultListModel) {
            ((DefaultListModel<Game>) favoriteList.getModel()).addAll(library.getFavoriteGames());
        }
        if (reccomendationList.getModel() instanceof DefaultListModel) {
            ((DefaultListModel<Game>) reccomendationList.getModel()).addAll(library.getReccomendedGames());
        }
        try {
            URL bild = getClass().getResource("/icon.png");
            if (bild != null)
                setIconImage(ImageIO.read(bild));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setMaximumSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        setPreferredSize(new Dimension(PREF_WIDTH, PREF_HEIGHT));
        setSize(new Dimension(PREF_WIDTH, PREF_HEIGHT));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width / 2 - this.getSize().width / 2, screenSize.height / 2 - this.getSize().height / 2);
        setVisible(true);
    }

    /**
     * Läd die Spiel-Listen neu
     */
    @SuppressWarnings("unchecked")
    public void reloadGames() {
        if (gameList.getModel() instanceof DefaultListModel) {
            ((DefaultListModel<Game>) gameList.getModel()).removeAllElements();
            ((DefaultListModel<Game>) gameList.getModel()).addAll(library.getGames());
        }
        if (favoriteList.getModel() instanceof DefaultListModel) {
            ((DefaultListModel<Game>) favoriteList.getModel()).removeAllElements();
            ((DefaultListModel<Game>) favoriteList.getModel()).addAll(library.getFavoriteGames());
        }
        library.generateReccomendedGames();
        if (reccomendationList.getModel() instanceof DefaultListModel) {
            ((DefaultListModel<Game>) reccomendationList.getModel()).removeAllElements();
            ((DefaultListModel<Game>) reccomendationList.getModel()).addAll(library.getReccomendedGames());
        }
        if (library.isUnsaved())
            setTitle(WINDOW_TITLE + " [ungespeichert]");
        else {
            setTitle(WINDOW_TITLE);
        }
    }

    public GameLibrary getLibrary() {
        return library;
    }
}
