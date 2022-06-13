package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao{

    public static final AccountDao aDao = new AccountDaoImpl();
    @Override
    public boolean insertCustomer(Customer c) {
        String sql = "INSERT INTO project0.users (user_type, username, user_password, user_fname, user_lname) VALUES (?,?,?,?,?)";
        Connection connection = ConnectionFactory.getConnection();

        if (this.selectCustomerByUsername(c.getUsername()) == null) {
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1,"customer");
                ps.setString(2, c.getUsername());
                ps.setString(3, c.getPassword());
                ps.setString(4, c.getFirstName());
                ps.setString(5, c.getLastName());
                ps.execute();
            } catch(SQLException e) {
                e.printStackTrace();
            }
            // The database created a user ID  as a primary key, so now we need to set the customer's ID to match
            String newSql = "SELECT user_id FROM project0.users WHERE username = ?;";
            try(PreparedStatement ps = connection.prepareStatement(newSql)) {
                ps.setString(1, c.getUsername());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    c.setId(rs.getInt("user_id"));
                }

            } catch(SQLException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            System.out.println("Username already exists. Please create a different one.");
            return false;
        }


    }

    @Override
    public void updateCustomer(Customer oldCust, Customer newCust) {
        String sql = "UPDATE project0.users SET username = ?, user_password = ?, user_fname = ?, user_lname = ? WHERE username = ?;";
        Connection connection = ConnectionFactory.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newCust.getUsername());
            ps.setString(2, newCust.getPassword());
            ps.setString(3, newCust.getFirstName());
            ps.setString(4, newCust.getLastName());
            ps.setString(5, oldCust.getUsername());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Customer selectCustomerByUsername(String username) {
        Customer c = null;
        Connection connection = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM project0.users WHERE username = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                c = new Customer(rs.getInt("user_id"),
                                 rs.getString("username"),
                                 rs.getString("user_password"),
                                 rs.getString("user_fname"),
                                 rs.getString("user_lname"),
                                 aDao.selectAccountsByUsername(rs.getString("username")));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public Customer selectCustomerByLoginInfo(String username, String password) {

        Customer c = null;
        Connection connection = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM project0.users WHERE username = ? AND user_password = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                c = new Customer(rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("user_password"),
                        rs.getString("user_fname"),
                        rs.getString("user_lname"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        try {
            c.setAccounts(aDao.selectAccountsByUsername(c.getUsername()));
            for (Account a : c.getAccounts()) {
                if (!a.isApproved()) {
                    c.setPendingAccounts(true);
                    break;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("User info not found");
        }
        return c;
    }

    @Override
    public List<Customer> selectCustomerByName(String lastName, String firstName) {
        List<Customer> customerList = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM project0.users WHERE user_fname = ? AND user_lname = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Customer c = new Customer(-1,
                                          rs.getString("username"),
                                          rs.getString("user_password"),
                                          rs.getString("user_fname"),
                                          rs.getString("user_lname"),
                        aDao.selectAccountsByUsername(rs.getString("username")));
                customerList.add(c);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        List<Customer> cList = new ArrayList<>();
        String sql = "SELECT * FROM project0.users WHERE user_type = 'customer';";
        Connection connection = ConnectionFactory.getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Customer c = new Customer(rs.getInt("user_id"),
                                          rs.getString("username"),
                                          rs.getString("user_password"),
                                          rs.getString("user_fname"),
                                          rs.getString("user_lname"));
                cList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cList;
    }
}
