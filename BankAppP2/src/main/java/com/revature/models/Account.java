package com.revature.models;

import java.util.List;

public class Account {

// Initial variables
    private int accountId;
    private boolean approved = false;
    private String nickname;
    private String type;
    private double balance;
    private List<Customer> owner;

    public static final String invalidInitialBalance = "Cannot have a balance less than 0. Initial balance will be set to 0.";


// Constructors
    public Account() {
        this.nickname = "My account";
        this.type = "Checking";
        this.balance = 0;
    }

    public Account(String nickname, String type, double balance, List<Customer> owner) {
        this.nickname = nickname;
        this.type = type;
        this.owner = owner;
        if (balance > 0) {
            this.balance = balance;
        } else {
            System.out.println(invalidInitialBalance);
        }
    }

    public Account(int accountId, boolean approved, String nickname, String type, double balance) {
        this.accountId = accountId;
        this.approved = approved;
        this.nickname = nickname;
        this.type = type;
        this.balance = balance;
    }

    public Account(int accountId, boolean approved, String nickname, String type, double balance, List<Customer> owner) {
        this.accountId = accountId;
        this.approved = approved;
        this.nickname = nickname;
        this.type = type;
        this.owner = owner;
        if (balance > 0) {
            this.balance = balance;
        } else {
            System.out.println(invalidInitialBalance);
        }
    }

// Getters and Setters    


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }
    public List<Customer> getOwner() {
        return owner;
    }
    public void setOwner(List<Customer> owner) {
        this.owner = owner;
    }

// Other methods

    // TODO - move to Dao/Service classes
    public TransferRequest transferTo(double amount, Account otherAccount) {
        TransferRequest transfer = null;
        if (this.approved) {
            if (amount >= 0 && amount < this.balance) {
                transfer = new TransferRequest(amount, this, otherAccount);
            } else {
                System.out.println("Invalid transaction: transfer amount must not exceed account balance.");
            }
        } else {
            this.printNotApprovedMessage();
        }
        return transfer;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // TODO - move methods to AccountDaoImp and AccoundService



    public void printNotApprovedMessage() {
        System.out.println("Account is still pending approval.");
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", approved=" + approved +
                ", nickname='" + nickname + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance  +
                '}';
    }
}
