package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    

    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }

    public Account addAccount(Account account){
        Account persistentAccount = new Account();
        if(account.getPassword().length() > 4 && account.getUsername().length() > 1){
            persistentAccount = accountDAO.insertAccount(account);
            return persistentAccount;
        }
        else
        return null;              
    }

    public Account loginAccount(Account account){
        Account login = accountDAO.getAccount(account.getUsername(), account.getPassword());
        return login;
    }
}
