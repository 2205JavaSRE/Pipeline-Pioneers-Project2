package com.revature.models;

public class TransferRequest {

    private boolean approved = false;
    private final double transferAmt;
    private final Account fromAccount;
    private final Account toAccount;

    public TransferRequest(double amt, Account from, Account to) {
        this.transferAmt = amt;
        this.fromAccount = from;
        this.toAccount = to;
    }

    @Override
    public String toString() {
        return "Approved: " + this.approved + "\nAmount: " + transferAmt + "\nfrom: " + fromAccount.getNickname() + "\nto: " + toAccount.getNickname();
    }

    // TODO - move to TransferDaoImpl/TransferService
    /*public void approveTransfer() {
        this.approved = true;
        this.fromAccount.withdraw(this.transferAmt);
        this.toAccount.deposit(this.transferAmt);
        System.out.println("Transferred " + this.transferAmt + " from " + this.fromAccount.getNickname() + " to " + this.toAccount.getNickname());
    }*/


}
