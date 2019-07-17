/*
 * Erstellt von Stefan Schmid
 */

package de.semesterprojekt.db;

import java.io.File;

/**
 * DataStorage, welcher die Daten in csv Dateien speichert.
 */
public interface FileStorage extends DataStorage {
/**
 * 
 * @param selectedFile Speicherort der Datei
 */
    void setSaveLocation(File selectedFile);
/**
 * 
 * 
 * @return Speicherort der Spiele-Datei
 */
    File getSaveLocation();
}
