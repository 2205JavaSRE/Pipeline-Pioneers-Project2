package com.revature.models;

public class Transaction {
    private String transactionTime;
    private int accountId;
    private String transactionType;
    private double transactionAmount;

    public Transaction(String transactionTime, int accountId, String transactionType, double transactionAmount) {
        this.transactionTime = transactionTime;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }

    public Transaction(int accountId, String transactionType, double transactionAmount) {
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }

    public String gettransactionTime() {
        return transactionTime;
    }

    public void settransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transaction_time='" + transactionTime + '\'' +
                ", account_id=" + accountId +
                ", transaction_type='" + transactionType + '\'' +
                ", transaction_amount=" + transactionAmount +
                '}';
    }
}
