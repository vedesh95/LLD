package vendingmachine.states;

import vendingmachine.Product;
import vendingmachine.VendingMachine;

import java.util.List;

public class EnteringProductCodeState implements VendingMachineState {

    private String name;
    private VendingMachine machine;

    EnteringProductCodeState(VendingMachine machine) {
        this.machine = machine;
        this.name = VendingMachineStates.ENTERING_PRODUCT_CODE.name();
    }

    @Override
    public void selectProduct(String productId) {
        machine.changeState(StateFactory.createState(machine, VendingMachineStates.ENTERING_COINS));
        machine.setSelectedProduct(machine.getProductById(productId));
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
        machine.changeState(StateFactory.createState(machine, VendingMachineStates.CANCELLING));
        machine.cancel();
    }

}
