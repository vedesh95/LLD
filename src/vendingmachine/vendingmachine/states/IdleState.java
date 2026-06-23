package vendingmachine.states;
import vendingmachine.Product;
import vendingmachine.VendingMachine;

import java.util.List;

public class IdleState implements VendingMachineState {
    private String name;
    private VendingMachine machine;
    public IdleState(VendingMachine machine) {
        this.machine = machine;
        this.name = VendingMachineStates.IDLE.name();
    }
    @Override
    public void selectProduct(String productId) {
        System.out.println("Operation not allowed in " + this.name);
    }

    @Override
    public void enterCoins(Integer amount) {
        System.out.println("Operation not allowed in " + this.name);
    }

    @Override
    public void dispenseProduct() {
        System.out.println("Operation not allowed in " + this.name);
    }

    @Override
    public List<Product> displayProducts() {
        return machine.getProducts();
    }

    @Override
    public void cancel() {
        System.out.println("Operation not allowed in " + this.name);
    }
}
