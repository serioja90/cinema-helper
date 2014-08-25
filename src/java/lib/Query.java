/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author sergiu
 */
public class Query {
  private PreparedStatement statement;
  private Connection connection;
  private ArrayList<String> select, from, where, group, order, params;
  private String limit, offset;
  public Query(Connection conn){
    connection = conn;
    select = new ArrayList<>();
    from = new ArrayList<>();
    where = new ArrayList<>();
    group = new ArrayList<>();
    order = new ArrayList<>();
    params = new ArrayList<>();
    limit = null;
    offset = null;
  }
  
  public Query select(String... args){
    select.addAll(Arrays.asList(args));
    return this;
  }
  
  public Query from(String... args){
    from.addAll(Arrays.asList(args));
    return this;
  }
  
  public Query where(String conditions, String... params){
    where.add(conditions);
    this.params.addAll(Arrays.asList(params));
    return this;
  }
  
  public Query group(String... args){
    group.addAll(Arrays.asList(args));
    return this;
  }
  
  public Query order(String... args){
    group.addAll(Arrays.asList(args));
    return this;
  }
  
  public Query limit(int limit){
    this.limit = limit + "";
    return this;
  }
  
  public Query offset(int offset){
    this.offset = offset + "";
    return this;
  }
  
  public ResultSet execute() throws SQLException{
    statement = connection.prepareStatement(this.toString());
    for(int i=0; i<params.size(); i++){
      statement.setString(i, params.get(i));
    }
    return statement.executeQuery();
  }
  
  @Override
  public String toString(){
    String sql = join(select,", ", "SELECT ");
    sql += join(from, ", ", " FROM ");
    sql += join(where, " AND ", " WHERE ");
    sql += join(group, ", ", " GROUP BY ");
    sql += join(order, ", ", " ORDER BY ");
    if(limit != null){
      sql += " LIMIT " + limit;
      if(offset != null) sql += " OFFSET " + offset;
    }
    return sql;
  }
  
  private String join(ArrayList<String> args, String joinChar, String prefix){
    boolean isFirst = true;
    String result = "";
    if(args != null ){
      result = (prefix == null ? "" : prefix);
      for(String arg : args){
        result += (isFirst ? "" : joinChar) + arg;
      }
    }
    return result;
  }
}
