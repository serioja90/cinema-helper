/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

/**
 *
 * @author sergiu
 */
public final class Tools {
  public static String join(String[] args, String joinChar){
    String result = "";
    boolean isFirst = true;
    if(args == null) return result;
    for(String arg : args){
      result += (isFirst ? "" : joinChar) + arg;
      if(isFirst) isFirst = false;
    }
    return result;
  }
  
  public static String[] space(String chr, int length){
    String[] result = new String[length];
    for(int i=0; i < length; i++) result[i] = chr + "";
    return result;
  }
}
