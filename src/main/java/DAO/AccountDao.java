package DAO;

import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Util.ConnectionUtil;

public class AccountDao {


    // insert into account
    public Account inserAccount(Account account){
        Connection  connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO Account(username, password) VALUES (?, ?)";

        try{
            PreparedStatement ps = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2,account.getPassword());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                int id = rs.getInt(1);
                return new Account(id,account.getUsername(),account.getPassword());
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }


    // get account by username and password
    public Account getAccountByUsernameAndPassword(String username, String password){

        Connection connection  = ConnectionUtil.getConnection();
        String sql ="SELECT * FROM Account WHERE username =? AND password =?";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")

                );

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

//get account by id
    public Account getAccountById(int id) {
    Connection connection = ConnectionUtil.getConnection();
    String sql = "SELECT * FROM Account WHERE account_id = ?";

    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}



    // get account by username
    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM Account WHERE username =?";


        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")

                );
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }






}
