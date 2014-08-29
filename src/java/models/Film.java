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
import utilities.Tools;

/**
 *
 * @author sergiu
 */
public class Film extends Model{
  public Film(){
    super();
    tableName = "films";
    id = "id";
  }
  
  public static Film create(Map<String,String> params){
    Film film = new Film();
    film.setProperties(params);
    film.set("updated_at", Tools.now());
    film.save();
    return film;
  }
  
  public Film update(Map<String,String> params){
    this.setProperties(params);
    this.set("updated_at", Tools.now());
    this.save();
    return this;
  }
  
  public static Film find(String title, String tecnology){
    Map<String,String[]> params = new HashMap<>();
    ArrayList<Model> films;
    params.put("select", new String[]{"*"});
    params.put("where", new String[]{"title = ?","tecnology = ?"});
    params.put("params", new String[]{title, tecnology});
    films = new Film().find(params);
    return (Film)(films == null || films.isEmpty() ? null : films.get(0));
  }
  
  public static Film[] getFilmsList(){
    Map<String,String[]> params = new HashMap<>();
    String[] filmsIds = Schedule.getActiveFilmsIds();
    params.put("select", new String[]{"*"});
    params.put("where", new String[]{"id IN (" + Tools.join(Tools.space("?", filmsIds.length),", " ) + ")"});
    params.put("params", filmsIds);
    params.put("order", new String[]{"title", "tecnology"});
    return new Film().find(params).toArray(new Film[]{});
  }
  
  public static String[] getGenres(){
    String[] result = new String[]{};
    ArrayList<String> genres = new ArrayList<>();
    Map<String,String[]> params = new HashMap<>();
    params.put("select", new String[]{"DISTINCT genre"});
    params.put("order", new String[]{"genre ASC"});
    for(Model item : new Film().find(params)){
      genres.add(item.get("genre"));
    }
    result = genres.toArray(result);
    return result;
  }
  
  public static Film[] getFilmsByGenre(String genre){
    Map<String,String[]> params = new HashMap<>();
    params.put("select", new String[]{"*"});
    params.put("where", new String[]{"genre = ?"});
    params.put("params", new String[]{genre});
    return new Film().find(params).toArray(new Film[]{});
  }
  
  public static Film[] search(String query){
    Map<String,String[]> params = new HashMap<>();
    params.put("select", new String[]{"*"});
    params.put("where", new String[]{"lower(title) LIKE ?"});
    params.put("params", new String[]{"%" + query.toLowerCase() + "%"});
    return new Film().find(params).toArray(new Film[]{});
  }
  
  public Schedule[] setCalendar(String[] data){
    ArrayList<Schedule> result = new ArrayList<>();
    String filmId = this.get(id);
    Schedule.clear(filmId);
    for(String item : data){
      result.add(Schedule.create(filmId, item));
    }
    return result.toArray(new Schedule[]{});
  }
  
  public Schedule[] getCalendar(){
    return Schedule.getByFilmId(this.get(id));
  }
}
