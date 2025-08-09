package vendingmachine;

import vendingmachine.chainofresponsibility.*;
import vendingmachine.chainofresponsibility.Dispenser;
import vendingmachine.states.IdleState;
import vendingmachine.states.StateFactory;
import vendingmachine.states.*;
import java.util.*;

public class VendingMachine {
    VendingMachineState state;
    Map<String, Product> products;
    Integer amountInserted;
    Product selectedProduct;
    Dispenser dispenser;
    static VendingMachine instance;

    private VendingMachine() {
        this.state = new IdleState(this);
        this.products = new HashMap<>();
        // Initialize products
        products.put("1", new Product("1", "Soda", 10, 1));
        products.put("2", new Product("2", "Chips", 20, 2));
        products.put("3", new Product("3", "Candy", 5, 3));

        Dispenser d1 = new CoinDispenser10(1);
        Dispenser d2 = new CoinDispenser5(1);
        Dispenser d3 = new CoinDispenser1(5);
        d1.setNextDispenser(d2);
        d2.setNextDispenser(d3);
        dispenser = d1;
    }

    public synchronized static VendingMachine getInstance() {
        if (instance == null) {
            instance = new VendingMachine();
        }
        return instance;
    }

    public void selectProduct(String productId){
        this.state = StateFactory.createState(this, VendingMachineStates.ENTERING_PRODUCT_CODE);
        this.state.selectProduct(productId);
    }

    public void enterCoins(Integer amount){
        this.state.enterCoins(amount);
    }

    public void dispenseProductAndCoins(){
        this.dispenser.dispense(amountInserted - selectedProduct.getPrice());
        this.state.dispenseProduct();
    }

    public List<Product> displayProducts(){
        return getProducts();
    }
    public void cancel(){
        this.state.cancel();
    }



    public List<Product> getProducts(){
        return new ArrayList<>(products.values());
    }

    public void changeState(VendingMachineState state){
        this.state = state;
    }
    public Product getProductById(String productId) {
        return products.get(productId);
    }
    public Integer getAmountInserted() {
        return amountInserted;
    }
    public void setAmountInserted(Integer amountInserted) {
        this.amountInserted = amountInserted;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }
    public Product getSelectedProduct() {
        return selectedProduct;
    }
    public VendingMachineState getState() {
        return state;
    }


}
