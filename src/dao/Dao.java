package dao;


import model.*;

import java.sql.*;

public class Dao {
    protected Connection connection;

    public boolean setConnection() {
        boolean isConnected;
        try {
             connection = DbConnection.getConnection();
             isConnected = true;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            isConnected = false;
        }
        return isConnected;
    }
    public void closeConnection(){
        try{
            connection.close();
        }catch(SQLException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}