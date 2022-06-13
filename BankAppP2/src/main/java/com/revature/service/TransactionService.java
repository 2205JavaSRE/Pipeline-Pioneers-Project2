package com.revature.service;

import com.revature.dao.TransactionDao;
import com.revature.dao.TransactionDaoImpl;
import com.revature.models.Transaction;

import java.util.List;

public class TransactionService {

    private static final TransactionDao tDao = new TransactionDaoImpl();

    public void listTransactions() {
        List<Transaction> transactionList= tDao.viewAllTransactions();
        for (Transaction t: transactionList){
            System.out.println("Time of transaction: " + t.gettransactionTime());
            System.out.println("Type: " + t.getTransactionType());
            System.out.println("Amount: " + t.getTransactionAmount());
            System.out.println("Account: " + t.getAccountId());
        }
    }
}
