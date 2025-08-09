package vendingmachine.chainofresponsibility;

public class CoinDispenser implements Dispenser {
    Dispenser nextDispenser;
    private int denomination;
    private int numCoins;

    public CoinDispenser(int denomination, int numCoins) {
        this.denomination = denomination;
        this.numCoins = numCoins;
    }

    @Override
    public void setNextDispenser(Dispenser nextDispenser) {
        this.nextDispenser = nextDispenser;
    }

    @Override
    public void dispense(int amount) {
        if(!canDispense(amount)) {
            nextDispenser.dispense(amount);
        }
        int coinsToDispense = amount / denomination;
        int balance = amount - coinsToDispense* denomination;
        if(balance==0) {
            System.out.println("Dispensing " + coinsToDispense + " x " + denomination + " coin(s)");
        }else nextDispenser.dispense(balance);
    }


    @Override
    public boolean canDispense(int amount) {
        return amount >= denomination*numCoins;
    }
}
