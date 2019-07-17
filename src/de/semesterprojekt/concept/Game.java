/*
 * Erstellt von Tim Morgner
 */

package de.semesterprojekt.concept;

import java.util.Objects;

/**
 * Ein Game representiert ein Spiel auf einer Plattform
 */
public class Game {
    private int rating;
    private String name, plattform, publisher, studio, genre;

    /**
     * Erstellt ein Game Objekt. Sollte nur von der GameLibrary aufgrufen werden. Oeffentlicher aufruf mit {@link GameLibrary#createGame(String, String, String, String, String, int)}.
     *
     * @param name      Name des Spiels
     * @param studio    Entwicklerstudio
     * @param publisher Publisher
     * @param plattform Plattform
     * @param genre     Genre
     * @param rating    Bewertung zwischen 1 und 10. 0 ist unbewertet gespielt und -1 nicht gespielt
     */
    Game(String name, String studio, String publisher, String plattform, String genre, int rating) {
        this.name = name;
        this.plattform = plattform;
        this.publisher = publisher;
        this.studio = studio;
        this.genre = genre;
        this.rating = Math.max(-1, Math.min(10, rating));
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlattform() {
        return plattform;
    }

    public void setPlattform(String plattform) {
        this.plattform = plattform;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * @return wurde das Spiel gespielt
     */
    public boolean isPlayed() {
        return rating != -1;
    }

    /**
     * @return wurde das Spiel gespielt und mit besser als 5 Sterne bewertet
     */
    public boolean isFavorite() {
        return rating > 5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return name.equals(game.name) &&
                Objects.equals(plattform, game.plattform);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, plattform);
    }
}
