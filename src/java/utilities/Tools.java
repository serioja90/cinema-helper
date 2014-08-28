/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import static utilities.Logger.WEBINF;

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
  
  public static String getTimestamp(){
    Date now = new Date();
    String timestamp = (new Timestamp(now.getTime())).toString();
    String nano = timestamp.split("\\.")[1];
    for(int i = nano.length(); i < 3; i++){ timestamp += "0"; }
    return timestamp;
  }
  
  public static String now(){ return getTimestamp(); }
  
  public static String getWebInfPath(){
    URL url = Logger.class.getResource("Logger.class");
    String className = url.getFile();
    String filePath = className.substring(0,className.indexOf(WEBINF) + WEBINF.length());
    return filePath;
  }
}
