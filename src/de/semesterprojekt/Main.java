/*
 * Erstellt von Tim Morgner
 */

package de.semesterprojekt;

import de.semesterprojekt.concept.Game;
import de.semesterprojekt.concept.GameLibrary;
import de.semesterprojekt.db.FileStorage;
import de.semesterprojekt.ui.LibraryWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

class Main {

    public static void main(String[] args) {
        GameLibrary library = new GameLibrary();
        FileStorage storage = new FileStorage() {
            private File location;

            @Override
            public Collection<Game> loadGames() {
                return new ArrayList<>();
            }

            @Override
            public void saveGames(Collection<Game> games) {

            }

            @Override
            public void setSaveLocation(File selectedFile) {
                location = selectedFile;
            }

            @Override
            public File getSaveLocation() {
                return location;
            }
        };
        library.setDataStorage(storage);
        library.loadGames();
        new LibraryWindow(library);
    }
}
