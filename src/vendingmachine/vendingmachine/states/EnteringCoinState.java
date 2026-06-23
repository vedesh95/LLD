package vendingmachine.states;

import vendingmachine.Product;
import vendingmachine.VendingMachine;

import java.util.List;
import java.util.Scanner;

public class EnteringCoinState implements VendingMachineState {
    private String name;
    private VendingMachine machine;

    public EnteringCoinState(VendingMachine machine) {
        this.name = VendingMachineStates.ENTERING_COINS.name();
        this.machine = machine;
    }

    @Override
    public void selectProduct(String productId) {
        System.out.println("Operation not allowed in " + this.name);
    }

    @Override
    public void enterCoins(Integer amount) {

        if(machine.getSelectedProduct().getPrice() > amount){
            System.out.println("Insufficient amount entered. Please enter at least " + machine.getSelectedProduct().getPrice() + " cents.");
            machine.changeState(StateFactory.createState(machine, VendingMachineStates.CANCELLING));
            machine.cancel();
            return;
        }
        machine.setAmountInserted(amount);
        machine.changeState(StateFactory.createState(machine, VendingMachineStates.DISPENSING_PRODUCT));
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
