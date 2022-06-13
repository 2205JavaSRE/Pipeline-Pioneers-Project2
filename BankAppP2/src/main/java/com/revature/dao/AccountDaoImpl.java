package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao{
    @Override
    public void insertAccount(Account a, List<User> cList) {
        Connection connection = ConnectionFactory.getConnection();
        String sql = "INSERT INTO project0.accounts VALUES (DEFAULT, null, ?, ?, ?);";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, a.getNickname());
            ps.setString(2, a.getType());
            ps.setDouble(3, a.getBalance());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // The database created an account ID as a primary key, so now we need to set the account's ID to match
        // The most recently created account ID will be the largest one
        String newSql = "SELECT max(account_id) FROM project0.accounts;";

        try(PreparedStatement ps2 = connection.prepareStatement(newSql)) {
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                a.setAccountId(rs2.getInt("max"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Lastly, the relationships between the account and owners need to be defined.
        String lastSql = "INSERT INTO project0.users_accounts VALUES (?,?);";

        try (PreparedStatement ps3 = connection.prepareStatement(lastSql)) {
            for (User c: cList) {
                ps3.setInt(1, c.getId());
                ps3.setInt(2, a.getAccountId());
                ps3.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account selectAccount(int id) {
        Account a = null;
        Connection connection = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM project0.accounts WHERE account_id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<User> accountOwners = new ArrayList<>();
                String newSql = "SELECT u.user_id, u.username, u.user_password, u.user_fname, u.user_lname  FROM project0.users u\n" +
                        "JOIN project0.users_accounts ua ON u.user_id = ua.user_id \n" +
                        "JOIN project0.accounts a ON ua.account_id = a.account_id \n" +
                        "WHERE a.account_id = ?;";
                try (PreparedStatement ps2 = connection.prepareStatement(newSql)) {
                    ps2.setInt(1, id);
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        User c = new User(rs2.getInt("user_id"),
                                rs2.getString("username"),
                                rs2.getString("user_password"),
                                rs2.getString("user_fname"),
                                rs2.getString("user_lname"));
                        accountOwners.add(c);
                    }
                }
                a = new Account(rs.getInt("account_id"),
                                rs.getBoolean("approved"),
                                rs.getString("account_nickname"),
                                rs.getString("account_type"),
                                rs.getDouble("account_balance"),
                                accountOwners);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public List<Account> selectPendingAccounts() {
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT * FROM project0.accounts WHERE approved = null;";
        Connection connection = ConnectionFactory.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Account a = new Account(rs.getInt("account_id"),
                        rs.getBoolean("approved"),
                        rs.getString("account_nickname"),
                        rs.getString("account_type"),
                        rs.getDouble("account_balance"));
                accountList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    @Override
    public List<Account> selectAccountsByUsername(String username) {
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT u.user_id, a.account_id, a.approved, a.account_nickname, a.account_type, a.account_balance \n" +
                     "FROM project0.accounts a \n" +
                     "JOIN project0.users_accounts ua ON a.account_id = ua.account_id \n" +
                     "JOIN project0.users u ON ua.user_id = u.user_id \n" +
                     "WHERE u.user_id = (SELECT user_id FROM project0.users WHERE username = ?);";
        Connection connection = ConnectionFactory.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {


                Account a = new Account(rs.getInt("account_id"),
                                        rs.getBoolean("approved"),
                                        rs.getString("account_nickname"),
                                        rs.getString("account_type"),
                                        rs.getDouble("account_balance"));

                accountList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<User> accountOwners = new ArrayList<>();
        String newSql = "SELECT u.user_id, u.username, u.user_password, u.user_fname, u.user_lname  FROM project0.users u\n" +
                "JOIN project0.users_accounts ua ON u.user_id = ua.user_id \n" +
                "JOIN project0.accounts a ON ua.account_id = a.account_id \n" +
                "WHERE a.account_id = ?;";
        ResultSet rs2;
        for (Account a : accountList) {
            try (PreparedStatement ps2 = connection.prepareStatement(newSql)) {
                ps2.setInt(1, a.getAccountId());
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    User c = new User(rs2.getInt("user_id"),
                            rs2.getString("username"),
                            rs2.getString("user_password"),
                            rs2.getString("user_fname"),
                            rs2.getString("user_lname"));
                    accountOwners.add(c);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            a.setOwner(accountOwners);
        }



        return accountList;
    }

    @Override
    public List<Account> selectAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT * FROM project0.accounts;";
        Connection connection = ConnectionFactory.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Account a = new Account(rs.getInt("account_id"),
                        rs.getBoolean("approved"),
                        rs.getString("account_nickname"),
                        rs.getString("account_type"),
                        rs.getDouble("account_balance"));
                accountList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }


    @Override
    public void updateAccountBalance(Account a) {
        Connection connection = ConnectionFactory.getConnection();
        String sql = "UPDATE project0.accounts SET account_balance = ? WHERE account_id = ?;";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setDouble(1, a.getBalance());
            ps.setInt(2, a.getAccountId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void approveAccount(Account a) {
        Connection connection = ConnectionFactory.getConnection();
        String sql = "UPDATE project0.accounts SET approved = ? WHERE account_id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, a.isApproved());
            ps.setInt(2, a.getAccountId());
            ps.execute();
            a.setApproved(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateAccountNickname(Account a, String nickname) {

    }
}
