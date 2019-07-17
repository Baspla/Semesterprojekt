package de.semesterprojekt.db;

import de.semesterprojekt.concept.Game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.prefs.Preferences;

public class ObjectFileStorage implements FileStorage {
    private File saveLocation;

    public ObjectFileStorage(){
        Preferences prefs = Preferences.userNodeForPackage(ObjectFileStorage.class);
        String location = prefs.get("fileSaveLocation", "C:\\Program Files\\Spielebibliothek\\save.data");
        saveLocation = new File(location);
    }

    @Override
    public void setSaveLocation(File selectedFile) {
        saveLocation = selectedFile;
        Preferences prefs = Preferences.userNodeForPackage(ObjectFileStorage.class);
        prefs.put("fileSaveLocation",selectedFile.toString());
    }

    @Override
    public File getSaveLocation() {
        return saveLocation;
    }

    @Override
    public Collection<Game> loadGames() {
        if (saveLocation == null) {
            System.err.println("[LOAD] Keine Save Location");
            return null;
        }
        if (!saveLocation.isFile()) {
            System.err.println("[LOAD] Save Location ist keine Datei");
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(saveLocation);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            if (!(obj instanceof ArrayList)) {
                fis.close();
                ois.close();
                return null;
            }
            ArrayList<Game> data = (ArrayList<Game>) obj;
            fis.close();
            ois.close();
            return data;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveGames(Collection<Game> games) {
        if (saveLocation == null) {
            System.err.println("[SAVE] Keine Save Location");
            return false;
        }
        saveLocation.mkdirs();
        try (
                FileOutputStream fos = new FileOutputStream(saveLocation);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(games);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
