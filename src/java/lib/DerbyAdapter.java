/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import utilities.Logger;

/**
 *
 * @author sergiu
 */
public class DerbyAdapter implements DatabaseAdapter {
  private Connection connection = null;
  private final String DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
  private final String CONNECTION_PREFIX = "jdbc:derby:";
 
  public DerbyAdapter(String database){
    try {
      Class.forName(DRIVER_NAME);
      Logger.info("Opening new connection to '" + CONNECTION_PREFIX + database + "'");
      connection = DriverManager.getConnection(CONNECTION_PREFIX + database);
    } catch (SQLException ex) {
      Logger.warn("Database not found!");
      connection = create(database);
    } catch (ClassNotFoundException ex) {
      Logger.reportException(ex);
    }
  }
  
  @Override
  public Connection getConnection() { return this.connection; }
  
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
  
  public void shutdown(String database) throws SQLException{
    DriverManager.getConnection(CONNECTION_PREFIX + database + ";shutdown=true");
  }
}
