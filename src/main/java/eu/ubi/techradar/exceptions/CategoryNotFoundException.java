package eu.ubi.techradar.exceptions;

public class CategoryNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CategoryNotFoundException(String category) {
        super("Could not find Category " + category);
    }
}
