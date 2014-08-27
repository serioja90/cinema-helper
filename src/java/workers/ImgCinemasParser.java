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
  protected Document document;

  @Override
  public void run() {
    ArrayList<Film> films = new ArrayList<>();
    try {
      document = Jsoup.connect(URL).get();
      //Elements elements = document.select("div.flip-container.evidence");
      Elements elements = document.select("div#filmList .filmTeaserBox");
      for(Element el : elements){
        films.add(Film.create(getData(el)));
      }
    } catch (IOException ex) {
      Logger.reportException(ex);
    }
  }
  
  private Map<String,String> getData(Element element){
    Map<String,String> data = new HashMap<>();
    data.put("title", getTitle(element));
    data.put("tecnology", getTecnology(element));
    data.put("duration", getDuration(element));
    data.put("image", getImg(element));
    data.put("link", getLink(element));
    data.put("description", getDescription(element));
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
}
