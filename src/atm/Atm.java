package atm;
/*
 * insert debit/credit card, enter otp, enter money, withdraw cash, finish
 *
 * cards - credit or debit
 * user - can withdraw cash
 * ATM - can deposit cash
 *
 * ATM can have various states - VERIFICATION, WITHDRAWING_CASH, TRNASACTIONSUCCESS, CANCELLED, IDLE, DEPOSITING_CASH
 * */

import java.util.Date;

enum CardType{
    CREDIT,
    DEBIT
}
class Card{
    int number;
    Date expiry;
    int cvv;

    // getters and setters
}

enum ATMStates{
    CARD_VERIFICATION, WITHDRAWING_CASH, TRANSACTION_SUCCESS, CANCELLED, IDLE, DEPOSITING_CASH;
}
interface ATMState {
    void verify(); // inserting card and input OTP
    void withdrawCash();
    void success();
    void cancel();
    void idle();
    void depositCash();
}

class ATM {
    ATMState atmState;
    Card card;

    ATM(){
        this.atmState = new ATMIdle(this);
    }
    public void start(){
        this.atmState = new ATMCardVerification(this);
        this.atmState.verify();
    }
    public void withdrawCash(){
        this.atmState.withdrawCash();
    }
    public void success(){
        this.atmState.success();
    }
    public void changeState(ATMState atmState) {
        this.atmState = atmState;
    }

}

class ATMCardVerification implements ATMState{
    ATM atm;
    ATMCardVerification(ATM atm){
        this.atm=atm;
    }
    public void verify(){
        //verify logic
        this.atm.changeState(new ATMWithdrawCash(this.atm));
        this.atm.withdrawCash();
    }
    public void withdrawCash() {throw new RuntimeException("invalid");}
    public void success() {throw new RuntimeException("invalid");}
    public void cancel() {throw new RuntimeException("invalid");}
    public void idle() {throw new RuntimeException("invalid");}
    public void depositCash() {throw new RuntimeException("invalid");}
}

class ATMWithdrawCash implements ATMState{
    ATM atm;
    ATMWithdrawCash(ATM atm){
        this.atm=atm;
    }
    public void verify() {throw new RuntimeException("invalid");}
    public void withdrawCash() {
        this.atm.changeState(new ATMWithdrawCash(this.atm));
        this.atm.success();
    }
    public void success() {throw new RuntimeException("invalid");}
    public void cancel() {throw new RuntimeException("invalid");}
    public void idle() {throw new RuntimeException("invalid");}
    public void depositCash() {throw new RuntimeException("invalid");}
}

class ATMIdle implements ATMState{
    ATM atm;
    ATMIdle(ATM atm){
        this.atm=atm;
    }
    public void verify() {throw new RuntimeException("invalid");}
    public void withdrawCash() {throw new RuntimeException("invalid");}
    public void success() {throw new RuntimeException("invalid");}
    public void cancel() {throw new RuntimeException("invalid");}
    public void idle() {}
    public void depositCash() {throw new RuntimeException("invalid");}
}

public class Atm {
}
