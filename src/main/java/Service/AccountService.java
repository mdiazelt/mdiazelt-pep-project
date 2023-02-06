package Service;

import DAO.AccountDAO;
import Model.Account;
import Model.Message;

public class AccountService{
    private AccountDAO accountDAO;

        public AccountService(){
            accountDAO = new AccountDAO();
            
        }

        /**
         * 
         * @param accountDAO
         * @return 
         */
        public AccountService(AccountDAO accountDAO){
            this.accountDAO = accountDAO;
        }
        
        //setAccount
        //create a new account
        public Account createAccount(Account account){
            if(account.getUsername() == null || account.getUsername().isEmpty()){
                return null;
            }
            if(account.getPassword() == null || account.getPassword().length() < 4){
                return null;
            }
            return accountDAO.createAccount(account);
        }


        //getAccount???
        public Account getAccount(String username, String password){
            return accountDAO.getAccount(username, password);
        }

        public Account login(Account account){
            return accountDAO.getAccount(account.getUsername(), account.getPassword());
        }

        public Message getMessagesByAccount(int account_id) {
            return null;
        }
        
        
}
