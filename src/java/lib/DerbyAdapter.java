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
import utilities.Logger;

/**
 *
 * @author sergiu
 */
public class DerbyAdapter implements DatabaseAdapter {
  private Connection connection;
  private static final String DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
  private static final String CONNECTION_PREFIX = "jdbc:derby:";
  
  static {
    try {
      Class.forName(DRIVER_NAME);
    } catch (ClassNotFoundException ex) {
      Logger.reportException(ex);
    }
  }
  
  public DerbyAdapter(String database){
    try {
      connection = DriverManager.getConnection(CONNECTION_PREFIX + database);
    } catch (SQLException ex) {
      connection = create(database);
    }
  }
  
  @Override
  public Connection getConnection() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public ResultSet select(String fields, String from, String where, String... args) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void execute(String sql, String... args) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
  private Connection create(String database){
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(CONNECTION_PREFIX + database + ";create=true");
    } catch (SQLException ex) {
      Logger.reportException(ex);
    }
    return conn;
  }
  
  public void drop(String database){
    try {
      DriverManager.getConnection(CONNECTION_PREFIX + database + ";drop=true");
    } catch (SQLException ex) {
      Logger.reportException(ex);
    }
  }
  
  public static void shutdown(String database) throws SQLException{
    DriverManager.getConnection(CONNECTION_PREFIX + database + ";shutdown=true");
  }
}
