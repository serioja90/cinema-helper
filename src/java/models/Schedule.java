/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.Model;
import utilities.Logger;
import utilities.Tools;

/**
 *
 * @author sergiu
 */
public class Schedule extends Model {
  
  public Schedule(){
    super();
    tableName = "schedule";
    id = "id";
  }
  
  public String formatSchedule(String format){
    String schedule = null;
    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    try {
      schedule = formatter.format(parser.parse(this.get("schedule")));
    } catch (ParseException ex) {
      Logger.reportException(ex);
    }
    return schedule;
  }
  
  public String getDay(){
    return this.formatSchedule("d MMM");
  }
  
  public String getDate(){
    return this.formatSchedule("yyyy-MM-dd");
  }
  
  public String getHour(){
    String hour = null;
    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    try {
      hour = formatter.format(parser.parse(this.get("schedule")));
    } catch (ParseException ex) {
      Logger.reportException(ex);
    }
    return hour;
  }
  
  public static boolean clear(String filmId){
    Schedule schedule = new Schedule();
    String query = "DELETE FROM " + schedule.getTableName() + " WHERE film_id = ?";
    return schedule.execute(query, filmId);
  }
  
  public static Schedule create(String filmId, String schedule){
    Schedule s = new Schedule();
    s.set("film_id", filmId);
    s.set("schedule", schedule);
    s.save();
    return s;
  }
  
  public static Schedule[] getByFilmId(String filmId){
    Map<String,String[]> params = new HashMap<>();
    params.put("select", new String[]{"*"});
    params.put("where", new String[]{"film_id = ? AND schedule >= ?"});
    params.put("order", new String[]{"schedule"});
    params.put("params", new String[]{filmId, Tools.getTimestamp()});
    return new Schedule().find(params).toArray(new Schedule[]{});
  }
  
  public static String[] getActiveFilmsIds(){
    ArrayList<String> filmsIds = new ArrayList<>();
    Map<String,String[]> params = new HashMap<>();
    params.put("select", new String[]{"DISTINCT film_id"});
    params.put("where", new String[]{"schedule >= ?"});
    params.put("params", new String[]{Tools.getTimestamp()});
    for(Model item : new Schedule().find(params)){
      filmsIds.add(item.get("film_id"));
    }
    return filmsIds.toArray(new String[]{});
  }
}
