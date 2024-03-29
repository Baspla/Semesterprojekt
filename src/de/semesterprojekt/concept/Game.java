/*
 * Erstellt von Tim Morgner
 */

package de.semesterprojekt.concept;

import java.io.Serializable;
import java.util.Objects;

/**
 * Ein Game representiert ein Spiel auf einer Plattform
 */
public class Game implements Serializable {
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

    /**
     *
     * @return Bewertung des Spiels
     */
    public int getRating() {
        return rating;
    }

    /**
     *
     * @param rating Bewertung des Spiels
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     *
     * @return Name des Spiels
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name Name des Spiels
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return Plattform des Spiels
     */
    public String getPlattform() {
        return plattform;
    }

    /**
     *
     * @param plattform Plattform des Spiels
     */
    public void setPlattform(String plattform) {
        this.plattform = plattform;
    }

    /**
     *
     * @return Publisher des Spiels
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     *
     * @param publisher Publisher des Spiels
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     *
     * @return Studio des Spiels
     */
    public String getStudio() {
        return studio;
    }

    /**
     *
     * @param studio Studio des Spiels
     */
    public void setStudio(String studio) {
        this.studio = studio;
    }

    /**
     *
     * @return Genre des Spiels
     */
    public String getGenre() {
        return genre;
    }

    /**
     *
     * @param genre Genre des Spiels
     */
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
    boolean isFavorite() {
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
