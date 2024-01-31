package ca.ulaval.glo4003.lib.catalog.recipe;

import java.util.List;

public final class StandardMealKitRecipes {

    public static final List<Recipe> RECIPES = List.of(
        new Recipe(
            "Spaghetti Carbonara",
            650,
            List.of(
                new Ingredient("spaghetti", 200, Unit.GRAM),
                new Ingredient("egg", 2, Unit.UNIT),
                new Ingredient("pecorino cheese", 50, Unit.GRAM),
                new Ingredient("guanciale", 100, Unit.GRAM)
            )
        ),
        new Recipe(
            "Chicken Salad",
            350,
            List.of(
                new Ingredient("chicken breast", 200, Unit.GRAM),
                new Ingredient("lettuce", 1, Unit.HEAD),
                new Ingredient("tomato", 2, Unit.UNIT),
                new Ingredient("cucumber", 1, Unit.UNIT)
            )
        ),
        new Recipe(
            "Pancakes",
            520,
            List.of(
                new Ingredient("flour", 200, Unit.GRAM),
                new Ingredient("milk", 300, Unit.MILLILITER),
                new Ingredient("egg", 1, Unit.UNIT),
                new Ingredient("baking powder", 1, Unit.TEASPOON)
            )
        ),
        new Recipe(
            "Guacamole",
            400,
            List.of(
                new Ingredient("avocado", 2, Unit.UNIT),
                new Ingredient("lime", 1, Unit.UNIT),
                new Ingredient("tomato", 1, Unit.UNIT),
                new Ingredient("onion", 0.5, Unit.UNIT)
            )
        ),
        new Recipe(
            "Chocolate Cake",
            700,
            List.of(
                new Ingredient("flour", 200, Unit.GRAM),
                new Ingredient("cocoa powder", 50, Unit.GRAM),
                new Ingredient("sugar", 150, Unit.GRAM),
                new Ingredient("egg", 2, Unit.UNIT)
            )
        )
    );

    private StandardMealKitRecipes() {}
}
