/*
 * Erstellt von Stefan Schmid
 */

package de.semesterprojekt.db;

import java.io.File;

/**
 * DataStorage, welcher die Daten in csv Dateien speichert.
 */
public interface FileStorage extends DataStorage {

    void setSaveLocation(File selectedFile);

    File getSaveLocation();

    /*

        Zum Speichern/Laden des Speicherorts

        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String location = prefs.get("fileSaveLocation", "C:\\Program Files\\Spielebibliothek\\save.data");
        prefs.put("fileSaveLocation","\"C:\\\\Program Files\\\\Spielebibliothek\\\\save.data\"");

     */
}
