
package workers;

import java.io.IOException;
import java.util.ListIterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utilities.Logger;

public class GoogleNewsParser implements Runnable{
  public static final String URL = "https://news.google.it";
  protected Document document;
  
  @Override
  public void run(){
    try{
      document = Jsoup.connect(URL).get();
    }catch(IOException e){
      Logger.reportException(e);
    }
    if(document == null){
      Logger.error("GoogleNewsParser: The parser returned an empty document!");
    }else{
      Elements elements = document.select("div.story.anchorman-blended-story");
      ListIterator<Element> iterator = elements.listIterator();
      Element item;
      Elements title, snippet;
      String cid, link;
      while(iterator.hasNext()){
        item = iterator.next();
        cid = item.attr("cid");
        title = item.select("table tr td div.esc-lead-article-title-wrapper a");
        snippet = item.select("table tr td div.esc-lead-snippet-wrapper");
        if(!title.isEmpty()){
          Logger.info(title.first().text());
          link = title.first().attr("href");
          Logger.info(link);
        }
        if(!snippet.isEmpty()) Logger.info(snippet.first().text());
      }
    }
  }
}
