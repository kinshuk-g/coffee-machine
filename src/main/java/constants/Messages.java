package constants;

import enums.BeverageType;
import enums.Ingredient;

import java.util.List;

/**
 * We can also separate these out in some .json file
 * We can have separate files for different languages and we can take the language as a property in
 * CoffeeMachine as well and pass it while building it
 */
public class Messages {
    public static final String CANNOT_SERVE_FROM_THIS_MACHINE =
            "Sorry! This coffee machine can't serve this drink " +
                    "since the ingredient container required is not available," +
                    " please consider buying our premium model :p";

    public static final String CANNOT_SERVE_MORE_THAN_NUM_OUTLETS_ORDERS =
            "Sorry! This coffee machine can't serve this drink as max number of parallel orders is reached";

    public static final String MACHINE_WITHOUT_ANY_CONTAINERS = "I wonder what will you do with a coffee machine that can't serve anything :thinking:";

    public static final String TRYING_TO_FILL_MORE_INGREDIENT_THAN_CAPACITY = "Oops! Some amount of ingredient spilled over, no problem just be careful next time! :)";


    public static String getBeveragePreparedMessage(BeverageType beverageType) {
        return beverageType.name() + " is prepared!";
    }

    public static String getInsufficientIngredientsMessage(BeverageType beverageType, List<Ingredient> insufficientIngredients) {
        return "Can't make beverage " + beverageType.name() + " bcoz these ingredients are insufficient: " + insufficientIngredients;
    }

    public static String getIngredientsNotAvailableMessage(BeverageType beverageType, List<Ingredient> unavailableIngredients) {
        return "Can't make beverage " + beverageType.name() + " bcoz these ingredients are not available: " + unavailableIngredients;
    }

}
