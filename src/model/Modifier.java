package model;

public enum Modifier {
    MONTH('m'),
    YEAR('y');

    private final char title;

    Modifier(char title) {
        this.title = title;
    }

    public char getTitle() {
        return title;
    }
}
