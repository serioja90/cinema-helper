/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import models.Film;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utilities.Logger;

/**
 *
 * @author sergiu
 */
public class ImgCinemasParser implements Runnable{
  public final String URL = "http://www.imgcinemas.it/";

  @Override
  public void run() {
    try{
      ArrayList<Film> films = new ArrayList<>();
      Document document = getDocument(URL);
      if(document != null){
        Elements elements = document.select("div#filmList .filmTeaserBox");
        Map<String,String> data = null;
        Film film = null;
        for(Element el : elements){
          data = getData(el);
          film = Film.find(data.get("title"), data.get("tecnology"));
          if(film != null){
            films.add(film.update(data));
          }else{
            films.add(Film.create(getData(el)));
          }
        }
      }
      Logger.info("IMG Cinemas site successfully parsed!");
    }catch(Exception ex){
      Logger.reportException(ex);
    }
  }
  
  private Document getDocument(String url){
    Document document = null;
    try{
      Logger.info("Downloading " + url);
      document = Jsoup.connect(url).get();
    }catch(IOException ex){
      Logger.reportException(ex);
    }
    if(document == null) Logger.warn("Page not found!");
    return document;
  }
  
  private Map<String,String> getData(Element element){
    Map<String,String> data = new HashMap<>();
    data.put("title", getTitle(element));
    data.put("tecnology", getTecnology(element));
    data.put("duration", getDuration(element));
    data.put("image", getImg(element));
    data.put("link", getLink(element));
    data.put("description", getDescription(element));
    Document document = getDocument(data.get("link"));
    if(document != null){
      data.put("genre", getGenre(document));
      data.put("nation", getNation(document));
      data.put("release_year", getYear(document));
      data.put("director", getDirector(document));
      data.put("film_cast", getCast(document));
      data.put("official_site", getOfficialSite(document));
    }
    data.put("origin", "IMG Cinemas");
    return data;
  }
  
  private String getTitle(Element element){
    String title = null;
    Element e = element.select("div.filmTeaserData .filmDescription.top .title").first();
    if(e != null) title = e.text();
    return title;
  }
  
  private String getDuration(Element element){
    String duration = null;
    Element e = element.select("div.filmTeaserData .filmDescription").not(".top")
                       .not(".bottom").first().select("p").not(".bodytext").last();
    if(e != null) duration = e.select("span").first().text();
    return duration;
  }
  
  private String getDescription(Element element){
    String description = null;
    Element e = element.select("div.filmTeaserData .filmDescription .bodytext").first();
    if(e != null) description = e.text();
    return description;
  }
  
  private String getImg(Element element){
    String img = null;
    Element e = element.select("div.filmTeaserData .locandina img").first();
    if(e != null) img = e.baseUri() + e.attr("src").substring(1);
    return img;
  }
  
  private String getTecnology(Element element){
    String tecnology = "2D";
    Element e = element.select("div.filmTeaserData .filmDescription.top .filmTecnologie ul").first();
    if(e != null && !e.text().trim().equals("")) tecnology = e.text().trim();
    return tecnology;
  }
  
  private String getLink(Element element){
    String link = null;
    Element e = element.select("div.filmTeaserData .filmDescription.bottom a#schedaLink").first();
    if(e != null) link = e.baseUri() + e.attr("href").substring(1);
    return link;
  }
  
  private String getGenre(Document document){
    String genre = null;
    Element e = document.select("div#scheda .filmDescription p:contains(Genere) span").first();
    if(e != null) genre = e.text();
    return genre;
  }
  
  private String getNation(Document document){
    String nation = null;
    Element e = document.select("div#scheda .filmDescription p:contains(Nazione) span").first();
    if(e != null) nation = e.text();
    return nation;
  }
  
  private String getYear(Document document){
    String year = null;
    Element e = document.select("div#scheda .filmDescription p:contains(Anno) span").first();
    if(e != null) year = e.text();
    return year;
  }
  
  private String getDirector(Document document){
    String director = null;
    Element e = document.select("div#scheda .filmDescription p:contains(Regia di) span").first();
    if(e != null) director = e.text();
    return director;
  }
  
  private String getCast(Document document){
    String cast = null;
    Element e = document.select("div#scheda .filmDescription").first()
                        .select("p:contains(Con) span").first();
    if(e != null) cast = e.text();
    return cast;
  }
  
  private String getOfficialSite(Document document){
    String officialSite = null;
    Element e = document.select("div#scheda .filmDescription p:contains(Sito) span").first();
    if(e != null) officialSite = e.text();
    return officialSite;
  }
}
