package vendingmachine.states;

import vendingmachine.Product;

import java.util.List;

public interface VendingMachineState {
    void selectProduct(String productId);
    void enterCoins(Integer amount);
    void dispenseProduct();
    List<Product> displayProducts();
    void cancel();
}
