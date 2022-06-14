package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.TransferRequest;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {
    @Override
    public void addTransaction(Transaction t) {
        String sql = "INSERT INTO project0.transactions (account_id, transaction_type," +
                     "transaction_amt) VALUES (?,?,?)";
        Connection connection = ConnectionFactory.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, t.getAccountId());
            ps.setString(2, t.getTransactionType());
            ps.setDouble(3, t.getTransactionAmount());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Transaction> viewTransactionsByUser(int userId) {
        List<Transaction> transactionList = new ArrayList<>();
        String sql = "SELECT t.account_id, t.transaction_type, t.transaction_amt, t.transaction_time FROM project0.users u \n" +
                "JOIN project0.users_accounts ua ON u.user_id = ua.user_id \n" +
                "JOIN project0.transactions t ON ua.account_id = t.account_id \n" +
                "WHERE u.user_id = ?;";
        Connection connection = ConnectionFactory.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction(rs.getString("transaction_time"),
                                                rs.getInt("account_id"),
                                                rs.getString("transaction_type"),
                                                rs.getDouble("transaction_amt"));
                transactionList.add(t);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    @Override
    public List<Transaction> viewTransactionsByAccount(int accountId) {
        List<Transaction> transactionList = new ArrayList<>();
        String sql = "SELECT t.account_id, t.transaction_type, t.transaction_amt, t.transaction_time FROM project0.users u" +
                "JOIN project0.users_accounts ua ON u.user_id = ua.user_id" +
                "JOIN project0.transactions t ON ua.account_id = t.account_id " +
                "WHERE t.account_id = ?;";
        Connection connection = ConnectionFactory.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction(rs.getString("transaction_time"),
                                                rs.getInt("account_id"),
                                                rs.getString("transaction_type"),
                                                rs.getDouble("transaction_amt"));
                transactionList.add(t);
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        }
        return transactionList;
    }

    @Override
    public List<Transaction> viewAllTransactions() {
        List<Transaction> transactionList = new ArrayList<>();

        String sql = "SELECT u.username, t.account_id, t.transaction_type, t.transaction_amt, t.transaction_time FROM project0.users u " +
        "JOIN project0.users_accounts ua ON u.user_id = ua.user_id " +
        "JOIN project0.transactions t ON ua.account_id = t.account_id ;";

        Connection connection = ConnectionFactory.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction(rs.getString("transaction_time"),
                                                rs.getInt("account_id"),
                                                rs.getString("transaction_type"),
                                                rs.getDouble("transaction_amt"));
                transactionList.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

	@Override
	public void intiateTransfer(TransferRequest tr) {
		String sql = "INSERT INTO project0.transfer_requests(account_from, account_to, transfer_amount) "
				+ "VALUES (?,?,?)";
		Connection connection = ConnectionFactory.getConnection();
		 try (PreparedStatement ps = connection.prepareStatement(sql)) {
	            ps.setInt(1, tr.getFromAccount().getAccountId());
	            ps.setInt(2, tr.getToAccount().getAccountId());
	            ps.setDouble(3, tr.getTransferAmt());
	            ps.execute();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}

	@Override
	public void completeTransfer(TransferRequest tr) {
		String sql = "DELETE FROM project0.transfer_requests WHERE transfer_id = ? ";
		Connection connection = ConnectionFactory.getConnection();
		 try (PreparedStatement ps = connection.prepareStatement(sql)) {
	            ps.setInt(1, tr.getFromAccount().getAccountId());
	            ps.execute();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
	}

	@Override
	public List<TransferRequest> selectPendingTransfersByRecivingAccount(Account a) {
		AccountDao aDao = new AccountDaoImpl();
		String sql = "SELECT * FROM project0.transfer_requests WHERE account_to = ?";
		List<TransferRequest> transferList = new ArrayList<>();
		Connection connection = ConnectionFactory.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TransferRequest tr = new TransferRequest(rs.getInt("transfer_id"), 
                		false,
                        rs.getDouble("transfer_amount"),
                        aDao.selectAccount(rs.getInt("account_from")),
                        aDao.selectAccount(rs.getInt("account_to")));
                transferList.add(tr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return transferList;
	}

	
}
