package com.revature.dao;

import com.revature.models.Employee;

public interface EmployeeDao {
    public Employee selectEmployeeByLoginInfo(String username, String password);
}
