import vendingmachine.VendingMachine;

public class Main {
    public static void main(String[] args) {
        VendingMachine machine = VendingMachine.getInstance();
        System.out.println(machine.getSelectedProduct());

        // happy flow
        System.out.println("----------");
        machine.selectProduct("1");
        machine.enterCoins(15);
        machine.dispenseProductAndCoins();

        // display an invalid flow where less coins entered
        System.out.println("----------");
        machine.selectProduct("1");
        machine.enterCoins(5);

        // demo cancel operation
        System.out.println("----------");
        machine.selectProduct("2");
        machine.cancel(); // prodcut selected so cancelled not allowed
    }
}
//Requirements
//
//vendingmachine.Product Management: The system manages a catalog of products, each with a price and available quantity.
//Inventory Management: The system tracks the quantity of each item and prevents dispensing if out of stock.
//Payment Handling: The system accepts coins, tracks total payment, and returns change if necessary.
//State Management: The system uses the State design pattern to manage its operational vendingmachine.states (Idle, Ready, Dispense, ReturnChange).
//User Interaction: Users can select products, insert coins/notes, and receive products and change.
//        Extensibility: Easy to add new item types, payment methods, or vendingmachine.states.


// user enters product code
// user enter csh
// change if any is dispensed
// product is dispensed
// Error handling: The system handles invalid inputs, insufficient funds, and out-of-stock items gracefully.