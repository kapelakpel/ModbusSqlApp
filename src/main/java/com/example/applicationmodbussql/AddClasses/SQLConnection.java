package com.example.applicationmodbussql.AddClasses;

import java.sql.Connection;
import java.sql.*;

public class SQLConnection {
    private String url;
    private String user;
    private String password;
    private String dateBase;
    private Connection connection = null;
    private Statement statement = null;

    public SQLConnection(String url, String user, String password, String dateBase) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.dateBase = dateBase;
    }

    public SQLConnection() {

    }

    public void setUrl(String url) {
        this.url = "jdbc:sqlserver://"+url+";";
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateBase(String dateBase) {
        this.dateBase = "datebase="+dateBase+";";
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDateBase() {
        return dateBase;
    }

    public boolean sqlConnect(){
        try{
            this.connection = DriverManager.getConnection(this.url+this.dateBase,this.user,this.password);
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean sqlDisconnect(){
        if(this.sqlConnect()){
            try {
                this.connection.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        else{
            return false;
        }
    }

    public void sqlExecuteQuery(int[] values){
        try{
            String sql;
            String dateBase;
            Date firstValue = this.date();
            Time secondValue = this.time();
            String thirdValue = String.valueOf(values[0]);
            sql = "VALUES ("+"'"+firstValue+"', '"+secondValue+"', "+thirdValue+")";

            statement = this.connection.createStatement();
            statement.executeUpdate("INSERT INTO Levers.dbo.DateTimeSpeed " + sql);
            //connection.close();

        }catch(Exception e){
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    private Date date(){
        Date date = new Date(System.currentTimeMillis());
        return date;
    }
    private Time time(){
        Time time = new Time(System.currentTimeMillis());
        return time;
    }

}
