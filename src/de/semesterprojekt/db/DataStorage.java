package de.semesterprojekt.db;

import de.semesterprojekt.concept.Game;

import java.util.Collection;

/**
 * Persistente speicherung von Spielen und Einstellungen
 */
public interface DataStorage {
    /**
     * @return Sammlung an geladenen Spielen
     */
    Collection<Game> loadGames();

    /**
     * @return Eistellungen des Nutzers
     */
    Options loadOptions() ;
}
