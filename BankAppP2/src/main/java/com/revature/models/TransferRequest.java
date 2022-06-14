package com.revature.models;

public class TransferRequest {
	private int id;
    private boolean approved = false;
    private double transferAmt;
    private Account fromAccount;
    private Account toAccount;
    

	public TransferRequest(int id, boolean approved, double transferAmt, Account fromAccount, Account toAccount) {
		super();
		this.id = id;
		this.approved = approved;
		this.transferAmt = transferAmt;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
	}


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


	public double getTransferAmt() {
		return transferAmt;
	}


	public void setTransferAmt(double transferAmt) {
		this.transferAmt = transferAmt;
	}


	public Account getFromAccount() {
		return fromAccount;
	}


	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}


	public Account getToAccount() {
		return toAccount;
	}


	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}



}
