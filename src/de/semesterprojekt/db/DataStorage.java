/*
 * Erstellt von Stefan Schmid
 */

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

    boolean saveGames(Collection<Game> games);

}
