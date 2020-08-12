import enums.BeverageType;
import enums.Ingredient;
import exceptions.IngredientContainerDoesNotExistException;
import exceptions.MachineWithoutIngredientContainerException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Could've used Junit or something here, but did it normally because of time crunch
 */
public class TestCoffeeMachine {


    /**
     * Test cases covered:
     * 1. The test case given in assignment doc: https://www.npoint.io/docs/e8cd5a9bbd1331de326a
     * 2. Low quantity ingredients indicator (it only shows ingredients which are less than half in quantity)
     * 3. Fill ingredient
     * 4. Trying to fill more ingredient than container capacity
     * 5. Trying to build coffee machine without any ingredient containers
     * 6. Trying to fill ingredient whose container is not available
     */

    public static void testAssignmentExampleCase() {
        CoffeeMachine ourCoffeeMachine = TestCoffeeMachineHelper.createCoffeeMachineWithDefaultSpecs();
        List<BeverageType> beverageTypes = Arrays.asList(BeverageType.HOT_TEA, BeverageType.HOT_COFFEE, BeverageType.BLACK_TEA, BeverageType.GREEN_TEA);
        Map<BeverageType, String> result = ourCoffeeMachine.serveBeverages(beverageTypes);
        result.values().forEach(System.out::println);
    }


    public static void testLowQuantityIndicator() {
        CoffeeMachine ourCoffeeMachine = TestCoffeeMachineHelper.createCoffeeMachineWithDefaultSpecs();
        List<BeverageType> beverageTypes = Arrays.asList(BeverageType.HOT_TEA, BeverageType.HOT_COFFEE, BeverageType.BLACK_TEA, BeverageType.GREEN_TEA);
        int repeat = 2;
        while (repeat-- > 0) ourCoffeeMachine.serveBeverages(beverageTypes);
        System.out.println("Ingredients Indicator" + ourCoffeeMachine.getIngredientsIndicator());
        ourCoffeeMachine.fillIngredient(Ingredient.HOT_MILK, 500);
        ourCoffeeMachine.fillIngredient(Ingredient.HOT_WATER, 500);
        System.out.println("Ingredients Indicator" + ourCoffeeMachine.getIngredientsIndicator());
    }

    public static void testFillIngredients() {
        CoffeeMachine ourCoffeeMachine = TestCoffeeMachineHelper.createCoffeeMachineWithDefaultSpecs();
        ourCoffeeMachine.fillIngredient(Ingredient.HOT_MILK, 400);
    }

    public static void testFillMoreIngredientsThanCapacity() {
        CoffeeMachine ourCoffeeMachine = TestCoffeeMachineHelper.createCoffeeMachineWithDefaultSpecs();
        ourCoffeeMachine.fillIngredient(Ingredient.HOT_MILK, 2000);
    }

    public static void testMachineCreationWithoutAnyIngredientContainers() {
        CoffeeMachine.CoffeeMachineBuilder coffeeMachineBuilder = new CoffeeMachine.CoffeeMachineBuilder(3);
        try {
            // expecting Runtime exception here
            CoffeeMachine ourCoffeeMachine = coffeeMachineBuilder.build();
        } catch (MachineWithoutIngredientContainerException e) {
            System.out.println(e);
        }
    }

    public static void testFillIngredientWhoseContainerUnavailable() {
        CoffeeMachine ourCoffeeMachine = TestCoffeeMachineHelper.createCoffeeMachineWithDefaultSpecs();
        try {
            // expecting Runtime exception here
            ourCoffeeMachine.fillIngredient(Ingredient.GREEN_MIXTURE, 100);
        } catch (IngredientContainerDoesNotExistException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        // PS: All test cases are independent of each other ie switching their order doesn't make any difference
        testAssignmentExampleCase();
        System.out.println("-----------------------------------------------------");
        testFillIngredients();
        System.out.println("-----------------------------------------------------");
        testLowQuantityIndicator();
        System.out.println("-----------------------------------------------------");
        testFillMoreIngredientsThanCapacity();
        System.out.println("-----------------------------------------------------");
        testMachineCreationWithoutAnyIngredientContainers();
        System.out.println("-----------------------------------------------------");
        testFillIngredientWhoseContainerUnavailable();
        System.out.println("-----------------------------------------------------");
    }
}
