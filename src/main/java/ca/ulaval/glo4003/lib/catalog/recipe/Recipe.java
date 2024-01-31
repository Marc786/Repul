package ca.ulaval.glo4003.lib.catalog.recipe;

import java.util.List;

public record Recipe(String name, int calories, List<Ingredient> ingredients) {}
