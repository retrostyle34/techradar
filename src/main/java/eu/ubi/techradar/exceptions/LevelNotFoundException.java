package eu.ubi.techradar.exceptions;

public class LevelNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LevelNotFoundException(String level) {
        super("Could not find Level " + level);
    }
}
