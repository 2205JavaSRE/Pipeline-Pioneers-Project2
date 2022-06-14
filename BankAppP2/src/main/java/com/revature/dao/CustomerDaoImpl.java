package com.revature.dao;

import com.revature.exceptions.UserExistsException;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao{

    public static final AccountDao aDao = new AccountDaoImpl();
    @Override
    public boolean insertCustomer(User u) throws UserExistsException {
        String sql = "INSERT INTO project0.users (user_type, username, user_password) VALUES (?,?,?)";
        Connection connection = ConnectionFactory.getConnection();

        if (this.selectCustomerByUsername(u.getUsername()) == null) {
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, u.getUserType());
                ps.setString(2, u.getUsername());
                ps.setString(3, u.getPassword());
                ps.execute();
            } catch(SQLException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            throw new UserExistsException();
        }


    }

    @Override
    public void updateCustomer(User oldCust, User newCust) {
        String sql = "UPDATE project0.users SET username = ?, user_password = ? WHERE username = ?;";
        Connection connection = ConnectionFactory.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newCust.getUsername());
            ps.setString(2, newCust.getPassword());
            ps.setString(3, oldCust.getUsername());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public User selectCustomerByUsername(String username) {
        User u = null;
        Connection connection = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM project0.users WHERE username = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                u = new User(rs.getInt("user_id"),
                                 rs.getString("username"),
                                 rs.getString("user_password"),
                                 rs.getString("user_type"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public User selectCustomerByLoginInfo(String username, String password) {

        User u = null;
        Connection connection = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM project0.users WHERE username = ? AND user_password = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                u = new User(rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("user_password"),
                        rs.getString("user_type"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public List<User> selectCustomerByName(String lastName, String firstName) {
        List<User> customerList = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM project0.users WHERE user_fname = ? AND user_lname = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                User c = new User(rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("user_password"),
                        rs.getString("user_type"));
                customerList.add(c);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    @Override
    public List<User> selectAllCustomers() {
        List<User> cList = new ArrayList<>();
        String sql = "SELECT * FROM project0.users WHERE user_type = 'customer';";
        Connection connection = ConnectionFactory.getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                User c = new User(rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("user_password"),
                        rs.getString("user_type"));
                cList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cList;
    }
}
