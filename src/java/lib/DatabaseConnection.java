/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.Map;

/**
 *
 * @author sergiu
 */
public class DatabaseConnection {
  private DatabaseAdapter adapter;
  private String host, port, database, user, password, adapterName;
  private Map<String,String> config;
  public DatabaseConnection(Map<String,String> config) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    this.config = config;
    this.host = config.get("host");
    this.port = config.get("port");
    this.database = config.get("database");
    this.user = config.get("user");
    this.password = config.get("password");
    this.adapterName = config.get("adapter");
    Class<DatabaseAdapter> c = (Class<DatabaseAdapter>)Class.forName(adapterName);
    Constructor<DatabaseAdapter> constr;
    constr = (Constructor<DatabaseAdapter>)c.getConstructor(String.class, String.class, String.class, 
                                                            String.class, String.class);
    this.adapter = constr.newInstance(host,port,database,user,password);
  }
  
  public ResultSet find(Map<String,String[]> params){ return adapter.find(params); }
  public ResultSet findBySql(String sql, String... args){ return adapter.findBySql(sql, args); }
  public boolean execute(String sql, String... args){ return adapter.execute(sql, args); }
}
