package eu.ubi.techradar.exceptions;

public class ItemNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ItemNotFoundException(String item) {
        super("Could not find Item " + item);
    }
}
