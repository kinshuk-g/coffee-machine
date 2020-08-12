package exceptions;

import static constants.Messages.MACHINE_WITHOUT_ANY_CONTAINERS;

public class MachineWithoutIngredientContainerException extends RuntimeException {
    public MachineWithoutIngredientContainerException() {
        super(MACHINE_WITHOUT_ANY_CONTAINERS);
    }
}
