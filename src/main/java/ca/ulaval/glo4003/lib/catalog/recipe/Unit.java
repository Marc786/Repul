package ca.ulaval.glo4003.lib.catalog.recipe;

public enum Unit {
    GRAM("g"),
    MILLILITER("ml"),
    UNIT(""),
    TEASPOON(" tsp"),
    HEAD(" head");

    private final String value;

    Unit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
