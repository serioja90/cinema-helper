/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import utilities.Logger;

/**
 *
 * @author sergiu
 */
public class Model {
  protected static String tableName;
  protected static String id;
  protected static Connection connection;
  protected Map<String,String> properties;
  
  public Model(ResultSet result) throws SQLException{
    properties = new HashMap<>();
    if(result != null){
      ResultSetMetaData meta = result.getMetaData(); 
      for(int i=0; i<meta.getColumnCount(); i++){
        properties.put(meta.getColumnLabel(i), result.getString(i));
      }
    }
  }
  
  public static ArrayList<Model> find(String idValue){
    Query query = new Query(connection);
    ArrayList<Model> result = new ArrayList<>();
    try {
      ResultSet rs = query.select("*").from(tableName).where(id + " = ?", idValue).execute();
      while(rs.next()){
        result.add(new Model(rs));
      }
    } catch (SQLException ex) {
      Logger.reportException(ex);
    }
    return result;
  }
  
  public String get(String field){ return properties.get(field); }
  public Model set(String field, String value){ 
    properties.put(field, value);
    return this;
  };
}
