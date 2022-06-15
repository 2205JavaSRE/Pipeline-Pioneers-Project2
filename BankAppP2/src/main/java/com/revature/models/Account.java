package com.revature.models;

import java.util.List;

public class Account {

// Initial variables
    private int id;
    private boolean approved;
    private String nickname;
    private String type;
    private double balance;
    private List<User> owner;

    public static final String invalidInitialBalance = "Cannot have a balance less than 0. Initial balance will be set to 0.";


// Constructors
    public Account() {
        this.nickname = "My account";
        this.type = "Checking";
        this.balance = 0;
    }

    public Account(String nickname, String type, double balance, List<User> owner) {
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
        this.id = accountId;
        this.approved = approved;
        this.nickname = nickname;
        this.type = type;
        this.balance = balance;
    }

    public Account(int accountId, boolean approved, String nickname, String type, double balance, List<User> owner) {
        this.id = accountId;
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
        return balance;
    }
    public List<User> getOwner() {
        return owner;
    }
    public void setOwner(List<User> owner) {
        this.owner = owner;
    }





    public void printNotApprovedMessage() {
        System.out.println("Account is still pending approval.");
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + id +
                ", approved=" + approved +
                ", nickname='" + nickname + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance  +
                '}';
    }
}
