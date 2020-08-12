package exceptions;

import enums.Ingredient;

public class IngredientContainerDoesNotExistException extends IllegalArgumentException {

    public IngredientContainerDoesNotExistException(Ingredient ingredient) {
        super("Container for ingredient " + ingredient.name() + " not present in coffee machine");
    }
}
