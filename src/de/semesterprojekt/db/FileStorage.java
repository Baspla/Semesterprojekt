package de.semesterprojekt.db;

import de.semesterprojekt.concept.Game;

import java.io.File;
import java.util.Collection;

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
