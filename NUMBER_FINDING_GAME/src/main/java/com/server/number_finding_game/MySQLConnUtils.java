package com.server.number_finding_game;

import java.sql.*;

public class MySQLConnUtils {
//test jdbc
    public static void main(String[] args) throws SQLException, ClassNotFoundException {



    }

    // Kết nối vào MySQL.
    public static Connection getMySQLConnection() throws SQLException,
            ClassNotFoundException {
        String hostName = "localhost";

        String dbName = "numberfinding";
        String userName = "root";
        String password = "";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException,
            ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        if(conn!=null) System.out.println("Conection database successs");
        return conn;
    }
    public static ResultSet getAllAccountInf(String table, String [] cols) throws SQLException, ClassNotFoundException {
        Connection connection =getMySQLConnection();
        ResultSet resultSet = null;
        try {

            Statement statement = (Statement) connection.createStatement();
            String sql = "SELECT ";
            if(cols == null || cols.length == 0){
                sql += "* FROM";
            }else{
                for(int i = 0 ; i < cols.length; i++){
                    sql += "`" + cols[i] + "`, ";
                }
                sql += ";";
                System.out.println(sql);
                sql = sql.replace("`, ;", "` FROM");
                System.out.println(sql);
            }
            sql += " " + table;
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            return null;
        }
        return resultSet;
    }
    public static ResultSet FindAccountInf(String table, String username) throws SQLException, ClassNotFoundException {
        Connection connection =getMySQLConnection();
        ResultSet resultSet = null;
        try {
            Statement statement = (Statement) connection.createStatement();
            String sql = "SELECT username,passwd FROM `useraccount` WHERE username='"+username+"'";
            resultSet = statement.executeQuery(sql);
        }
        catch (SQLException e){
            return null;
        }
        return resultSet;
    }

}