package com.revature.dao;

import com.revature.models.Employee;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoImpl implements EmployeeDao{
    @Override
    public Employee selectEmployeeByLoginInfo(String username, String password) {
        Employee employee = null;
        String sql = "SELECT * FROM project0.users WHERE user_type = 'employee' AND username = ? AND user_password = ?;";
        Connection connection = ConnectionFactory.getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                employee = new Employee(rs.getString("username"),
                                           rs.getString("user_password"),
                                           rs.getString("user_fname"),
                                           rs.getString("user_lname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }
}
