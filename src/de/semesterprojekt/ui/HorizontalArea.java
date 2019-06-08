package de.semesterprojekt.ui;

/**
 * Berreich der x-Achse
 */
public class HorizontalArea {
    private final float start;
    private final float end;

    /**
     * @param start x Startpunkt
     * @param end x Endpunkt
     */
    public HorizontalArea(float start, float end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @return Startpunkt
     */
    public float getStart() {
        return start;
    }

    /**
     * @return Endpunkt
     */
    public float getEnd() {
        return end;
    }

    /**
     * @param x zu testender Punkt
     * @return ob der Punkt im Berreich liegt
     */
    public boolean isWithin(int x) {
        return x >= start && x <= end;
    }
}
