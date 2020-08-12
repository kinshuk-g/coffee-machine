import exceptions.MachineWithoutIngredientContainerException;

public class TestCoffeeMachineHelper {
    static CoffeeMachine createCoffeeMachineWithDefaultSpecs() throws MachineWithoutIngredientContainerException {
        CoffeeMachine.CoffeeMachineBuilder coffeeMachineBuilder = new CoffeeMachine.CoffeeMachineBuilder(3);
        return coffeeMachineBuilder.withWaterContainer(500, 1000)
                .withMilkContainer(500, 1000)
                .withGingerSyrupContainer(100, 1000)
                .withSugarSyrupContainer(100, 1000)
                .withTeaLeavesSyrupContainer(100, 1000)
                .build();
    }
}
