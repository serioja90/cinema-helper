/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import lib.Model;

/**
 *
 * @author sergiu
 */
public class User extends Model{
  public User(){
    super();
    tableName = "users";
    id = "id";
  }
}