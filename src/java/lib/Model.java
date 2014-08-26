/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import utilities.Logger;

/**
 *
 * @author sergiu
 */
public abstract class Model {
  protected String tableName;
  protected String id;
  private DatabaseAdapter adapter;
  private Connection connection;
  protected Map<String,String> properties;
  
  public Model(){
    properties = new HashMap<>();
    adapter = new DerbyAdapter("//localhost:1527/test");
    connection = adapter.getConnection();
  }
  
  public Model parseResult(ResultSet result){
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
  
  public ArrayList<Model> find(String idValue){
    Query query = new Query(connection);
    ArrayList<Model> result = new ArrayList<>();
    try {
      query.select("*").from(tableName).where(id + " = ?", idValue);
      Logger.debug(query.toString());
      ResultSet rs = query.execute();
      Constructor<Model> constr = (Constructor<Model>) this.getClass().getConstructor();
      while(rs.next()){
        result.add(constr.newInstance().parseResult(rs));
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
}
