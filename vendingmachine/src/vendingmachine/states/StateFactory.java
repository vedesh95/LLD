package vendingmachine.states;

import vendingmachine.VendingMachine;

public class StateFactory {
    public static VendingMachineState createState(VendingMachine machine, VendingMachineStates state){
        switch (state){
            case IDLE:
                return new IdleState(machine);
            case ENTERING_PRODUCT_CODE:
                    return  new EnteringProductCodeState(machine);
            case ENTERING_COINS:
                return new EnteringCoinState(machine);
            case DISPENSING_PRODUCT:
                return new DispensingProductState(machine);
            case CANCELLING:
                return new CancelingState(machine);
            default:
                throw new IllegalArgumentException("Unknown state: " + state);
        }
    }
}
