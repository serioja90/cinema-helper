/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import utilities.Logger;

/**
 *
 * @author sergiu
 */
public class DerbyAdapter extends DatabaseAdapter {
  private static final String DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
  private static final String CONNECTION_PREFIX = "jdbc:derby:";
 
  public DerbyAdapter(String host, String port, String database, String user, String password) throws ClassNotFoundException{
    super(DRIVER_NAME, CONNECTION_PREFIX, host, port, database, user, password);
    try {
      connection = connect();
    } catch (SQLException ex) {
      Logger.warn("Database not found!");
      connection = create(database);
    }
  }
  
  private Connection create(String database){
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(getConnectionUrl() + ";create=true");
    } catch (SQLException ex) {
      Logger.reportException(ex);
    }
    return conn;
  }
  
  public void drop(String database){
    try {
      DriverManager.getConnection(getConnectionUrl() + ";drop=true");
    } catch (SQLException ex) {
      Logger.reportException(ex);
    }
  }
  
  public void shutdown(String database) throws SQLException{
    DriverManager.getConnection(getConnectionUrl() + ";shutdown=true");
  }

  @Override
  public ResultSet find(Map<String, String[]> params) {
    String[] select = params.get("select");
    String[] from = params.get("from");
    String[] conditions = params.get("where");
    String[] group = params.get("group");
    String[] having = params.get("having");
    String[] order = params.get("order");
    String[] limit = params.get("limit");
    String[] offset = params.get("offset");
    String[] args = params.get("params");
    String query = "SELECT " + join(select, ", ");
    query += " FROM " + join(from, ", ");
    if(conditions != null && conditions.length != 0) query += " WHERE " + join(conditions, " AND ");
    if(group != null && group.length != 0) query += " GROUP BY " + join(group, ", ");
    if(having != null && having.length != 0) query += " HAVING " + join(having, " AND ");
    if(order != null && order.length != 0) query += " ORDER BY " + join(order, ", ");
    if(limit != null && limit.length != 0) query += " LIMIT " + limit[0];
    if(offset != null && offset.length != 0) query += " OFFSET " + offset[0];
    return findBySql(query, args);
  }

  @Override
  public ResultSet findBySql(String sql, String... args) {
    PreparedStatement statement;
    ResultSet result = null;
    Gson json = new Gson();
    Logger.debug(sql + "; " + json.toJson(args));
    try {
      statement = connection.prepareStatement(sql);
      if(args != null){
        for(int i=0; i < args.length; i++){
          if(args[i] == null){
            statement.setNull(i + 1, java.sql.Types.NULL);
          }else{
            statement.setString(i + 1, args[i]);
          }
        }
      }
      result = statement.executeQuery();
    } catch (SQLException ex) {
      Logger.reportException(ex);
    }
    return result;
  }

  @Override
  public boolean execute(String sql, String... args) {
    PreparedStatement statement;
    boolean result = false;
    Gson json = new Gson();
    Logger.debug(sql + "; " + json.toJson(args));
    try {
      statement = connection.prepareStatement(sql);
      for(int i=0; i < args.length; i++){
        statement.setString(i + 1, args[i]);
      }
      statement.execute();
      result = true;
    } catch (SQLException ex) {
      Logger.reportException(ex);
    }
    return result;
  }

  @Override
  public String getConnectionUrl() {
    String url = CONNECTION_PREFIX + host + ":" + port + "/" + database;
    return url;
  }
}
