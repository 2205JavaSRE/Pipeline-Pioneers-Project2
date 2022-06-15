package com.revature.models;

public class TransferRequest {
	private int id;
    private boolean approved = false;
    private double transferAmt;
    private int fromAccount;
    private int toAccount;


	public TransferRequest() {
	}

	public TransferRequest(int id, boolean approved, double transferAmt, int fromAccount, int toAccount) {
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

	public int getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(int fromAccount) {
		this.fromAccount = fromAccount;
	}

	public int getToAccount() {
		return toAccount;
	}

	public void setToAccount(int toAccount) {
		this.toAccount = toAccount;
	}
}
