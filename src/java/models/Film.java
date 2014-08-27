/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import lib.Model;

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
    Iterator<Entry<String,String>> it = params.entrySet().iterator();
    Entry<String,String> el; 
    while(it.hasNext()){
      el = it.next();
      film.set(el.getKey(), el.getValue());
    }
    film.save();
    return film;
  }
  
  public static Film[] getFilmsList(){
    Film[] films = new Film[]{};
    films = new Film().all().toArray(films);
    return films;
  }
}
