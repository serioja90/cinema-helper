/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import lib.Model;
import utilities.Logger;

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
}
