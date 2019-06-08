package de.semesterprojekt.concept;

import de.semesterprojekt.db.DataStorage;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Hauptklasse des Konzeptlayers
 */
public class GameLibrary {
    //TODO Als Einstellung?
    private static int MIN_MATCHES = 2;
    private static int MAX_MATCH_PER_FEATURE = 4;
    private final ArrayList<Game> games;
    private ArrayList<Game> reccomendedGames;

    /**
     * Erstellt eine neue leere GameLibrary
     */
    public GameLibrary() {
        games = new ArrayList<>();
        generateReccomendedGames();
    }

    /**
     * Erstellt ein neues Game Objekt und fuegt es zur Library hinzu
     *
     * @param name      Name des Spiels
     * @param studio    Entwicklerstudio
     * @param publisher Publisher
     * @param plattform Plattform
     * @param genre     Genre
     * @param rating    Bewertung zwischen 1 und 10. 0 ist unbewertet gespielt und -1 nicht gespielt
     */
    public void createGame(String name, String studio, String publisher, String plattform, String genre, int rating) {
        Game game = new Game(name, studio, publisher, plattform, genre, rating);
        games.add(game);
    }

    /**
     * Entfernt ein Spiel aus der Library
     *
     * @param game Spiel das entfernt werden soll
     * @return ob das Spiel entfernt wurde
     */
    public boolean removeGame(Game game) {
        if (game == null) return false;
        return games.remove(game);
    }

    /**
     * Gibt die Liste der Spiele zurueck.
     * Dies sind keine kopien.
     *
     * @return Liste der Spiele
     */
    public ArrayList<Game> getGames() {
        return games;
    }

    /**
     * Erstellt eine Liste an empfohlenen Spielen nach Haeufigkeit bestimmter features in den Favoriten
     */
    public void generateReccomendedGames() {
        @SuppressWarnings("unchecked") ArrayList<String>[] preferences = new ArrayList[4];
        preferences[0] = new ArrayList<>();
        preferences[1] = new ArrayList<>();
        preferences[2] = new ArrayList<>();
        preferences[3] = new ArrayList<>();
        getFavoriteGames().forEach(game -> {
            preferences[0].add(game.getStudio());
            preferences[1].add(game.getPublisher());
            preferences[2].add(game.getPlattform());
            preferences[3].add(game.getGenre());
        });
        HashMap<String, Long> studioCounts = count(preferences[0]);
        HashMap<String, Long> publisherCounts = count(preferences[1]);
        HashMap<String, Long> plattformCounts = count(preferences[2]);
        HashMap<String, Long> genreCounts = count(preferences[3]);
        //noinspection unchecked
        reccomendedGames = (ArrayList<Game>) new ArrayList(getGames().stream()
                .filter(game -> (studioCounts.containsKey(game.getStudio()) ||
                        publisherCounts.containsKey(game.getPublisher()) ||
                        plattformCounts.containsKey(game.getPlattform()) ||
                        genreCounts.containsKey(game.getGenre())) && !game.isPlayed()
                ).collect(Collectors.toList()));
    }

    private HashMap<String, Long> count(ArrayList<String> list) {
        HashMap<String, Long> map = new HashMap<>();
        list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))    //Zählt die Vorkommen der Features
                .entrySet().stream().filter(stringLongEntry -> stringLongEntry.getValue() >= MIN_MATCHES) //Filtert Ergebnisse, die zu selten vorkommen
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder())) //Sortiert die Ergebnisse
                .limit(MAX_MATCH_PER_FEATURE) //Limitiert es auf die Top n Ergebnisse
                .forEach(o -> map.put(o.getKey(), o.getValue())); //Fuegt die Ergebnisse zu einer neuen Map hinzu
        return map;
    }

    /**
     * Gibt die Liste der Spiele zurueck, welche als Favoriten gelten.
     * Dies sind keine kopien.
     *
     * @return Liste der Favoriten
     */
    public ArrayList<Game> getFavoriteGames() {
        // noinspection FuseStreamOperations
        return new ArrayList<>(games.stream().filter(Game::isFavorite).collect(Collectors.toList()));
    }

    /**
     * Gibt die Liste der Spiele zurueck, welche empfohlen werden.
     * Dies sind keine kopien.
     * Die Liste muss vorher mit {@link GameLibrary#generateReccomendedGames()} erstellt werden.
     *
     * @return Liste der Favoriten
     */
    public ArrayList<Game> getReccomendedGames() {
        return reccomendedGames;
    }

    /**
     * Laedt Spiele aus einem {@link DataStorage} und fuegt es zur vorhandenen Liste hinzu.
     *
     * @param storage DataStorage aus der geladen wird.
     */
    public void loadGames(DataStorage storage) {
        Collection<Game> loaded = storage.loadGames();
        if (loaded != null) {
            loaded.forEach(game -> {
                if (!games.contains(game)) games.add(game);
            });
        }
        //TODO Spiele laden
    }
}
