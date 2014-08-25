/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author sergiu
 */
public interface DatabaseAdapter {
  public Connection getConnection();
  public ResultSet select(String fields, String from, String where, String... args);
  public void execute(String sql, String... args);
}
