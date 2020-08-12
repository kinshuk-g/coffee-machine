import constants.BeverageCompositions;
import enums.BeverageType;
import enums.Ingredient;
import exceptions.IngredientContainerDoesNotExistException;
import exceptions.MachineWithoutIngredientContainerException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static constants.Messages.CANNOT_SERVE_MORE_THAN_NUM_OUTLETS_ORDERS;
import static constants.Messages.getBeveragePreparedMessage;
import static constants.Messages.getIngredientsNotAvailableMessage;
import static constants.Messages.getInsufficientIngredientsMessage;

/**
 * A coffee machine can only be built using the nested Builder class
 * <p>
 * Used the builder pattern here since:
 * 1. We are required to pass a lot of arguments to build a coffee machine
 * 2. A requirement can come to make different kinds of coffee machines,
 * for instance one with just water, milk and coffee, and one which has ginger syrup and lemon syrup also, etc.
 */
public class CoffeeMachine {
    // Used a map for ingredientsContainers to keep the machine flexible,
    // since requirements can come to make different kinds of coffee machines,
    // for instance one with just water, milk and coffee, and one which has ginger syrup and lemon syrup also
    private final Map<Ingredient, Container> ingredientsContainers;
    private final int numOutlets;
    // because we can only serve numOutlets beverages at once
    private int numOutletsCurrentlyFree;

    private CoffeeMachine(int numOutlets) {
        this.ingredientsContainers = new HashMap<>();
        this.numOutlets = numOutlets;
    }

    public void fillIngredient(Ingredient ingredient, int quantity) {
        System.out.println("Filling " + quantity + " amount of " + ingredient.name() + " in container");
        if (!this.ingredientsContainers.containsKey(ingredient)) {
            throw new IngredientContainerDoesNotExistException(ingredient);
        }
        Container container = this.ingredientsContainers.get(ingredient);
        container.fill(quantity);
    }

    /**
     * @return a map of: ingredients that are low in quantity vs the container(ie its current quantity and max capacity)
     */
    public Map<Ingredient, Container> getIngredientsIndicator() {
        Map<Ingredient, Container> indicator = new HashMap<>();
        for (Map.Entry<Ingredient, Container> entry : this.ingredientsContainers.entrySet()) {
            Container container = entry.getValue();
            if (container.isCurrentQuantityLow()) {
                indicator.put(entry.getKey(), container);
            }
        }
        return indicator;
    }


    public Map<BeverageType, String> serveBeverages(List<BeverageType> requiredBeverages) {
        numOutletsCurrentlyFree = numOutlets;
        Map<BeverageType, String> result = new HashMap<>();
        for (BeverageType beverageType : requiredBeverages) {
            String message = serveBeverage(beverageType);
            result.put(beverageType, message);
        }
        return result;
    }

    private String serveBeverage(BeverageType beverageType) {
        // ingredients validation
        List<Ingredient> unavailableIngredients = filterIngredients(beverageType, this::isIngredientUnavailable);
        if (!unavailableIngredients.isEmpty())
            return getIngredientsNotAvailableMessage(beverageType, unavailableIngredients);
        List<Ingredient> insufficientIngredients = filterIngredients(beverageType, this::isIngredientInsufficient);
        if (!insufficientIngredients.isEmpty())
            return getInsufficientIngredientsMessage(beverageType, insufficientIngredients);

        // beverage creation
        if (numOutletsCurrentlyFree > 0) {
            numOutletsCurrentlyFree--;
            Map<Ingredient, Integer> ingredientsAmounts = BeverageCompositions.beverageCompositions.get(beverageType);
            for (Ingredient ingredient : ingredientsAmounts.keySet()) {
                Container container = this.ingredientsContainers.get(ingredient);
                container.use(ingredientsAmounts.get(ingredient));
            }
        } else {
            // return sorry message for remaining orders as only numOutlets can be served in parallel
            // we can also choose to raise an exception instead as soon as the serve beverages method is called
            // with more than numOutlets orders, but I've decided to first check the actual number of orders we are
            // serving (ie after ingredients check) and then if we are serving more than numOutlets I'm returning error
            return CANNOT_SERVE_MORE_THAN_NUM_OUTLETS_ORDERS;
        }
        return getBeveragePreparedMessage(beverageType);
    }
    
    private List<Ingredient> filterIngredients(BeverageType beverageType, Predicate<Map.Entry<Ingredient, Integer>> filter) {
        Map<Ingredient, Integer> requiredIngredientsAmounts = BeverageCompositions.beverageCompositions.get(beverageType);
        return requiredIngredientsAmounts.entrySet().stream()
                .filter(filter)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private boolean isIngredientInsufficient(Map.Entry<Ingredient, Integer> ingredientAmount) {
        Ingredient ingredient = ingredientAmount.getKey();
        if (!this.ingredientsContainers.containsKey(ingredient)) {
            throw new IngredientContainerDoesNotExistException(ingredient);
        }
        Integer requiredAmount = ingredientAmount.getValue();
        return this.ingredientsContainers.get(ingredient).getCurrentQuantity() < requiredAmount;
    }

    private boolean isIngredientUnavailable(Map.Entry<Ingredient, Integer> ingredientAmount) {
        Ingredient ingredient = ingredientAmount.getKey();
        return !this.ingredientsContainers.containsKey(ingredient);
    }


    /**
     * Used the builder pattern here since
     * 1. We are required to pass a lot of arguments to build a coffee machine
     * 2. A requirement can come to make different kinds of coffee machines,
     * for instance one with just water, milk and coffee, and one which has ginger syrup and lemon syrup also, etc.
     */
    public static class CoffeeMachineBuilder {
        private final CoffeeMachine coffeeMachine;

        public CoffeeMachineBuilder(int numOutlets) {
            coffeeMachine = new CoffeeMachine(numOutlets);
        }

        public CoffeeMachine build() {
            if (this.coffeeMachine.ingredientsContainers.isEmpty()) {
                throw new MachineWithoutIngredientContainerException();
            }
            return this.coffeeMachine;
        }

        public CoffeeMachineBuilder withWaterContainer(int initialQuantity, int maxCapacity) {
            return addIngredientContainer(Ingredient.HOT_WATER, new Container(initialQuantity, maxCapacity));
        }

        public CoffeeMachineBuilder withGreenMixtureContainer(int initialQuantity, int maxCapacity) {
            return addIngredientContainer(Ingredient.GREEN_MIXTURE, new Container(initialQuantity, maxCapacity));
        }

        public CoffeeMachineBuilder withGingerSyrupContainer(int initialQuantity, int maxCapacity) {
            return addIngredientContainer(Ingredient.GINGER_SYRUP, new Container(initialQuantity, maxCapacity));
        }

        public CoffeeMachineBuilder withMilkContainer(int initialQuantity, int maxCapacity) {
            return addIngredientContainer(Ingredient.HOT_MILK, new Container(initialQuantity, maxCapacity));
        }

        public CoffeeMachineBuilder withSugarSyrupContainer(int initialQuantity, int maxCapacity) {
            return addIngredientContainer(Ingredient.SUGAR_SYRUP, new Container(initialQuantity, maxCapacity));
        }

        public CoffeeMachineBuilder withTeaLeavesSyrupContainer(int initialQuantity, int maxCapacity) {
            return addIngredientContainer(Ingredient.TEA_LEAVES_SYRUP, new Container(initialQuantity, maxCapacity));
        }

        private CoffeeMachineBuilder addIngredientContainer(Ingredient ingredient, Container container) {
            this.coffeeMachine.ingredientsContainers.put(ingredient, container);
            return this;
        }
    }
}
