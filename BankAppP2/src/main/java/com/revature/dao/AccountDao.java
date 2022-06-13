package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.User;

import java.util.List;

public interface AccountDao {

    public void insertAccount(Account a, List<User> cList);

    public Account selectAccount(int id);

    public List<Account> selectPendingAccounts();

    public List<Account> selectAccountsByUsername(String username);

    public List<Account> selectAllAccounts();


    public void updateAccountBalance(Account a);

    public void approveAccount(Account a);

    public void updateAccountNickname(Account a, String nickname);
}
