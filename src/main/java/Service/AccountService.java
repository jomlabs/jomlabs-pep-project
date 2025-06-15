package Service;

import DAO.AccountDao;
import Model.Account;

public class AccountService {
    private AccountDao accountDao;



    public AccountService(){
        this.accountDao = new AccountDao();
    }



    public Account reqister(Account account){

        if(account.getUsername() == null|| account.getUsername().isBlank()){
            return null;
        }

        if(account.getPassword() == null || account.getPassword().length()<4){
            return null;
        }


        // checking if account exists
        if(accountDao.getAccountByUsername(account.getUsername()) != null) {
            return null;
        }

        return accountDao.inserAccount(account);
    }


    public Account login(Account account){
        if(account.getUsername()==null || account.getPassword() == null){
            return null;
        }

        return accountDao.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());

    }

}
