/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lib.Model;

/**
 *
 * @author sergiu
 */
public class Role extends Model{

  public Role(){
    super();
    tableName = "roles";
    id = "id";
  }
  
  public static Role[] getRoles(){
    return new Role().all().toArray(new Role[]{});
  }
  
  public static Role getByName(String name){
    ArrayList<Model> result = null;
    Map<String,String[]> params = new HashMap<>();
    params.put("select", new String[]{"*"});
    params.put("where", new String[]{"name = ?"});
    params.put("params", new String[]{name});
    result = new Role().find(params);
    return (Role)(result == null || result.isEmpty() ? null : result.get(0));
  }
  
  public String toString(){ return this.get("name"); }
}
