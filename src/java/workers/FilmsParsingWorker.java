/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package workers;

import javax.servlet.ServletContext;
import utilities.Logger;

/**
 *
 * @author sergiu
 */
public class FilmsParsingWorker implements Runnable {
  protected ServletContext servletContext;
  public FilmsParsingWorker(ServletContext servletContext){
    this.servletContext = servletContext;
  }
  
  @Override
  public void run() {
    Logger.info("Going to parse films data ...");
    new Thread(new ImgCinemasParser()).start();
  }
  
}
