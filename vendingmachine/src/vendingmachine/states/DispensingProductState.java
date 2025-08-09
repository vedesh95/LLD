package vendingmachine.states;

import vendingmachine.Product;
import vendingmachine.VendingMachine;

import java.util.List;

public class DispensingProductState implements VendingMachineState {
    private String name;
    private VendingMachine machine;

    public DispensingProductState(VendingMachine machine) {
        this.machine = machine;
        this.name = VendingMachineStates.DISPENSING_PRODUCT.name();
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
        machine.getSelectedProduct().setQuantity(machine.getSelectedProduct().getQuantity() - 1);
        machine.setSelectedProduct(null);
        machine.changeState(StateFactory.createState(machine, VendingMachineStates.IDLE));
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
