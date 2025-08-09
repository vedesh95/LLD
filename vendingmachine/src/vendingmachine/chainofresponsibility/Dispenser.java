package vendingmachine.chainofresponsibility;

public interface Dispenser {
    void setNextDispenser(Dispenser nextDispenser);
    void dispense(int amount);
    boolean canDispense(int amount);
}
