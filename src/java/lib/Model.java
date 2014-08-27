/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import utilities.ApplicationContextListener;
import utilities.Logger;
import utilities.Tools;

/**
 *
 * @author sergiu
 */
public abstract class Model {
  protected String tableName;
  protected String id;
  private DatabaseConnection connection;
  protected Map<String,String> properties;
  
  public Model(){
    properties = new HashMap<>();
  }
  
  public Model connect(){
    if(connection != null) return this;
    ServletContext context = ApplicationContextListener.getServletContext();
    Map<String,String> config = new HashMap<>();
    config.put("host", context.getInitParameter("dbhost"));
    config.put("port", context.getInitParameter("dbport"));
    config.put("database", context.getInitParameter("dbname"));
    config.put("adapter", context.getInitParameter("dbadapter"));
    config.put("user", context.getInitParameter("dbuser"));
    config.put("password", context.getInitParameter("dbpasword"));
    try {
      connection = new DatabaseConnection(config);
    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
      Logger.reportException(ex);
    }
    return this;
  }
  
  public Model populate(ResultSet result){
    if(result != null){
      ResultSetMetaData meta; 
      String label;
      try {
        meta = result.getMetaData();
        for(int i=0; i<meta.getColumnCount(); i++){
          label = meta.getColumnLabel(i + 1);
          Logger.debug(label + ": " + result.getString(label));
          properties.put(label.toLowerCase(), result.getString(label));
        }
      } catch (SQLException ex) {
        Logger.reportException(ex);
      }
    }
    return this;
  }
  
  public Model find(Object idValue){
    connect();
    Map<String,String[]> params = new HashMap<>();
    ArrayList<Model> result;
    params.put("select", new String[]{"*"});
    params.put("from", new String[]{tableName});
    params.put("where", new String[]{id + " = ?"});
    params.put("params", new String[]{idValue.toString()});
    result = parseResultSet(connection.find(params));    
    return result.isEmpty() ? null : result.get(0);
  }
  
  public ArrayList<Model> findBySql(String sql, String... args){
    connect();
    Logger.debug(sql);
    return parseResultSet(connection.findBySql(sql, args));
  }
    
  public ArrayList<Model> all(){
    connect();
    Map<String,String[]> params = new HashMap<>();
    params.put("select", new String[]{"*"});
    params.put("from", new String[]{tableName});
    return parseResultSet(connection.find(params));
  }
  
  public boolean save(){
    if(properties.isEmpty()) return true;
    connect();
    String[] columns = new String[]{};
    String[] values = new String[]{};
    columns = properties.keySet().toArray(columns);
    values = properties.values().toArray(values);
    String query = "INSERT INTO " + tableName + " (" + join(columns,",") + ")";
    query += " VALUES(" + join(Tools.space("?", properties.size()),",") + ")";
    return connection.execute(query, values);
  }
  
  public ArrayList<Model> parseResultSet(ResultSet rs){
    ArrayList<Model> result = new ArrayList<>();
    try {
      Constructor<Model> constr = (Constructor<Model>) this.getClass().getConstructor();
      while(rs.next()){
        result.add(constr.newInstance().populate(rs));
      }
    } catch (IllegalAccessException | IllegalArgumentException | 
             InstantiationException | NoSuchMethodException | SecurityException | 
             InvocationTargetException | SQLException ex) {
      Logger.reportException(ex);
    }
    return result;
  }
  
  public String get(String field){ return properties.get(field); }
  public Model set(String field, String value){ 
    properties.put(field, value);
    return this;
  };
  
  public String getTableName(){ return tableName; }
  public String getId(){ return id; }
  
  protected String join(String[] args, String joinChar){
    return Tools.join(args, joinChar);
  }
}
