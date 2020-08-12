import static constants.Messages.TRYING_TO_FILL_MORE_INGREDIENT_THAN_CAPACITY;

public class Container {
    private int currentQuantity;
    private final int maxCapacity;

    public Container(int currentQuantity, int maxCapacity) {
        this.currentQuantity = currentQuantity;
        this.maxCapacity = maxCapacity;
    }

    public boolean isCurrentQuantityLow() {
        return currentQuantity < maxCapacity / 2;
    }

    public void use(int quantity) {
        if (this.currentQuantity < quantity) {
            throw new IllegalArgumentException("Can't use more than what's available");
        }
        this.currentQuantity = this.currentQuantity - quantity;
    }

    public void fill(int quantity) {
        int newQuantity = this.currentQuantity + quantity;
        if (newQuantity > this.maxCapacity) {
            System.out.println(TRYING_TO_FILL_MORE_INGREDIENT_THAN_CAPACITY);
            this.currentQuantity = this.maxCapacity;
        } else {
            this.currentQuantity = newQuantity;
        }
    }

    public int getCurrentQuantity() {
        return this.currentQuantity;
    }

    @Override
    public String toString() {
        return "{" +
                "currentQuantity=" + currentQuantity +
                ", maxCapacity=" + maxCapacity +
                '}';
    }
}
