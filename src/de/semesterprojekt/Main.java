package de.semesterprojekt;

import de.semesterprojekt.concept.GameLibrary;
import de.semesterprojekt.db.FileStorage;
import de.semesterprojekt.ui.LibraryWindow;

class Main {

    public static void main(String[] args) {
        GameLibrary library = new GameLibrary();
        FileStorage storage = new FileStorage();
        library.loadGames(storage);
        new LibraryWindow(library);
    }
}
