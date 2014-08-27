/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import utilities.Logger;
import utilities.Tools;

/**
 *
 * @author sergiu
 */
public abstract class DatabaseAdapter {
  protected String driverName, connectionPrefix, host, port, database, user, password;
  protected Connection connection;
  public DatabaseAdapter(String driverName, String connectionPrefix, String host,
                         String port, String database, String user, String password
                        ) throws ClassNotFoundException{
    this.driverName = driverName;
    this.connectionPrefix = connectionPrefix;
    this.host = host;
    this.port = port;
    this.database = database;
    this.user = user;
    this.password = password;
    Class.forName(driverName);
  }
  
  public Connection getConnection(){ return this.connection; }
  
  protected Connection connect() throws SQLException{
    Connection conn;
    Logger.info("Connecting to " + getConnectionUrl());
    if(user != null){
      conn = DriverManager.getConnection(getConnectionUrl(), user, password);
    }else{
      conn = DriverManager.getConnection(getConnectionUrl());
    }
    return conn;
  }
  
  protected String join(String[] args, String joinChar){
    return Tools.join(args,joinChar);
  }
  
  public abstract ResultSet find(Map<String,String[]> params);
  public abstract ResultSet findBySql(String sql, String... args);
  public abstract boolean execute(String sql, String... args);
  public abstract String getConnectionUrl();
}
